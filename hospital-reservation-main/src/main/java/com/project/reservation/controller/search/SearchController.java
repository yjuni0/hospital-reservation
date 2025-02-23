package com.project.reservation.controller.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;
    // 페이징 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ResNoticeList>> search(@PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, @RequestParam("title") String title, @RequestParam("content") String content){
        SearchDto searchDto = SearchDto.searchNotice(title,content);
        Page<ResNoticeList> searchList = searchService.notice(searchDto,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(searchList);
    }
}
