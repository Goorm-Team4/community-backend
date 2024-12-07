package com.team4.goorm.community.Comment.application;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Comment.domain.CommentLike;
import com.team4.goorm.community.Comment.dto.request.CommentCreateReqDto;
import com.team4.goorm.community.Comment.dto.response.CommentInfoRespDto;
import com.team4.goorm.community.Comment.repository.CommentLikeRepository;
import com.team4.goorm.community.Comment.repository.CommentRepository;
import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.application.PostQueryService;
import com.team4.goorm.community.Post.domain.Post;
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
    private final CommentRepository commentRepository;
    private final PostQueryService postQueryService;
    private final CommentLikeQueryService commentLikeQueryService;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional(readOnly = true)
    public List<CommentInfoRespDto> getCommentsByMember(String email) {
        Member member = memberQueryService.findMemberByEmail(email);
        List<Comment> comments = commentQueryService.findAllByMemberOrderByCreatedAtDesc(member);
        return comments.stream()
                .map(CommentInfoRespDto::from)
                .toList();
    }

    public CommentInfoRespDto createComment(CommentCreateReqDto request, String user, Long postId) {
        Member member = memberQueryService.findMemberByEmail(user);
        Post post = postQueryService.findById(postId);
        Comment comment = request.toEntity(member, post);
        Comment savedComment = commentRepository.save(comment);
        return CommentInfoRespDto.from(savedComment);
    }

    public CommentInfoRespDto updateComment(CommentCreateReqDto request, String user, Long commentId) {
        Member member = memberQueryService.findMemberByEmail(user);
        Comment comment = commentQueryService.findById(commentId);
        comment.update(request.getContent());
        return CommentInfoRespDto.from(comment);
    }

    public void deleteComment(String user, Long commentId) {
        Comment comment = commentQueryService.findById(commentId);
        commentRepository.delete(comment);
    }

    public void toggleLike(Long commentId, String email) {
        Comment comment = commentQueryService.findById(commentId);
        Member member = memberQueryService.findMemberByEmail(email);
        CommentLike commentLike = commentLikeQueryService.findByCommentAndMember(comment, member);

        if (commentLike != null) {
            comment.decreaseLikeCount();
            commentLikeRepository.delete(commentLike);
        } else {
            comment.increaseLikeCount();
            commentLikeRepository.save(new CommentLike(comment, member));
        }
    }
}
