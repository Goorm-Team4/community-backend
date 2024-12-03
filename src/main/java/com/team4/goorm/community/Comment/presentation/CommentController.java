package com.team4.goorm.community.Comment.presentation;

import com.team4.goorm.community.Comment.application.CommentService;
import com.team4.goorm.community.Comment.dto.response.CommentInfoRespDto;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Comment", description = "댓글 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "내가 작성한 댓글 조회")
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<List<CommentInfoRespDto>>> getMyPosts(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(commentService.getCommentsByMember(user.getEmail())));
    }

    @Operation(summary = "댓글 작성")
    @GetMapping("/comments/write")
    public ResponseEntity<SuccessResponse<String>> createComment(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success("댓글 작성 성공"));
    }

    // @Operation(summary = "게시글의 댓글 조회")
    // @GetMapping("/post/{postId}")
    // public ResponseEntity<SuccessResponse<List<CommentInfoRespDto>> getCommentsByPost(
    //         @PathVariable Long postId
    // ) {
    //     return ResponseEntity.ok(SuccessResponse.success(commentService.getCommentsByPost(postId)));
    // }

}
