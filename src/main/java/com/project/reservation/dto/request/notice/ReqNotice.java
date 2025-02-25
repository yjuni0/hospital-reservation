package com.project.reservation.dto.request.notice;

import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqNotice {
    private String title;
    private String content;
    private Member member;

    @Builder
    public ReqNotice(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Notice ofEntity(ReqNotice req) {
        return Notice.builder().title(req.getTitle()).content(req.getContent()).admin(req.getMember()).build();
    }
}
