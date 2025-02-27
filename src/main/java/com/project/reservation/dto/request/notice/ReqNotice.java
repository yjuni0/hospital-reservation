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

    @Builder
    public ReqNotice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Notice ofEntity(ReqNotice req,Member member) {
        return Notice.builder().title(req.getTitle()).content(req.getContent()).admin(member).build();
    }
}
