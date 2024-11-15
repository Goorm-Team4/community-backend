package com.team4.goorm.community.Comment.application;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.dto.response.CommentInfoRespDto;
import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {

    private final CommentQueryService commentQueryService;
    private final MemberQueryService memberQueryService;

    @Transactional(readOnly = true)
    public List<CommentInfoRespDto> getCommentsByMember(String email) {
        Member member = memberQueryService.findMemberByEmail(email);
        List<Comment> comments = commentQueryService.findAllByMemberOrderByCreatedAtDesc(member);
        return comments.stream()
                .map(CommentInfoRespDto::from)
                .toList();
    }
}
