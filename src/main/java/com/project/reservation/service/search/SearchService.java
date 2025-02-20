package com.project.reservation.service.search;

import com.project.reservation.entity.search.SearchType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchService {

    private final EntityManager entityManager;

    public Page<?> search(String type, String keyword, Pageable pageable) {

        // keyword와 type이 유효한지 검사
        if (keyword == null || keyword.isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }

        if (type == null) {
            throw new IllegalArgumentException("Search type cannot be null");
        }

        // SearchType을 enum으로 변환
        SearchType searchType;
        try {
            searchType = SearchType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid SearchType: " + type);
        }
        // 검색할 엔티티 클래스 가져오기
        Class<?> entityClass = getEntityClass(searchType);

        // CriteriaBuilder 생성
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> query = cb.createQuery(entityClass);
        Root<?> root = query.from(entityClass);


        // 검색 조건 생성
        Predicate predicate = cb.disjunction();
        List<String> fields = searchType.getFields();
        List<SearchType.FieldType> fieldTypes = searchType.getFieldTypes();

        for (int i = 0; i< fields.size(); i++) {
            String field = fields.get(i);
            SearchType.FieldType fieldType = fieldTypes.get(i);
            // 타입별 매칭 메서드 설정
            if (fieldType == SearchType.FieldType.STRING){
                predicate = cb.or(predicate, cb.like(root.get(field), "%" + keyword + "%"));
            }else if (fieldType == SearchType.FieldType.LOCAL_DATE_TIME){
                LocalDateTime date = parseDate(keyword); // String -> LocalDateTime 타입으로 파싱
                predicate = cb.or(predicate, cb.equal(root.get(field), date));
            }else if (fieldType == SearchType.FieldType.INTEGER){
                Integer value = Integer.parseInt(keyword); // String -> Integer 타입으로 파싱
                predicate = cb.or(predicate, cb.equal(root.get(field), value));
            }
        }

        query.where(predicate);

        // 정렬 추가
        if (pageable.getSort().isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
            pageable.getSort().forEach(order -> {
                String property = order.getProperty();
                if (order.getDirection().isAscending()) {
                    orders.add(cb.asc(root.get(property)));
                } else {
                    orders.add(cb.desc(root.get(property)));
                }
            });
            query.orderBy(orders);
        }

        TypedQuery<?> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());  // 시작 인덱스
        typedQuery.setMaxResults(pageable.getPageSize());  // 한 페이지에 반환할 결과 수

        List<?> results = typedQuery.getResultList();

        // 전체 데이터 수 구하기
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<?> countRoot = countQuery.from(Long.class);
        countQuery.select(cb.count(countRoot)).where(predicate);
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countQuery);
        Long total = countTypedQuery.getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    private Class<?> getEntityClass(SearchType type) {
        return switch (type) {
            case NOTICE -> com.project.reservation.entity.notice.Notice.class;
            case MEMBER, MEMBER_CREATE -> com.project.reservation.entity.member.Member.class;
            case RESERVATION,RESERVATION_DATE -> com.project.reservation.entity.onlineReserve.Reservation.class;
            case REVIEW -> com.project.reservation.entity.customerReviews.Review.class;
            case QUESTION -> com.project.reservation.entity.onlineConsult.Question.class;
            case PET -> com.project.reservation.entity.member.Pet.class;
        };

    }
    private LocalDateTime parseDate(String date) {
        try {
            // 예시: "yyyy-MM-dd HH:mm:ss" 포맷을 사용한다고 가정
            return LocalDateTime.parse(date);
        } catch (Exception e) {
            // 예외 처리: 날짜 파싱 실패 시 적절한 메시지나 기본값을 반환하도록 처리
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }
    }
}