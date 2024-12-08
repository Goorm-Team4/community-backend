package com.team4.goorm.community.Comment.application;


import org.springframework.stereotype.Service;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.domain.Reply;
import com.team4.goorm.community.Comment.dto.request.RepliesCreateReqDto;
import com.team4.goorm.community.Comment.dto.response.RepliesInfoRespDto;
import com.team4.goorm.community.Comment.repository.ReplyRepository;
import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.domain.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RepliesService {

    private final MemberQueryService memberQueryService;
    private final CommentQueryService commentQueryService;
    private final ReplyRepository replyRepository;

    public RepliesInfoRespDto createReply(RepliesCreateReqDto request, String user, Long commentId) {

        Member member = memberQueryService.findMemberByEmail(user);
        Comment comment = commentQueryService.findById(commentId);
        Reply reply = request.toEntity(comment);
        Reply savedReply = replyRepository.save(reply);
        return RepliesInfoRespDto.from(savedReply);
    }

    public RepliesInfoRespDto updateReply(RepliesCreateReqDto request, String user, Long replyId) {
        Member member = memberQueryService.findMemberByEmail(user);
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));
        reply.update(request.getContent());
        return RepliesInfoRespDto.from(reply);
    }

    public String deleteReply(String user, Long replyId) {
        Member member = memberQueryService.findMemberByEmail(user);
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다."));
        replyRepository.delete(reply);
        return "답글 삭제 성공";
    }
}