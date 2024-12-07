package com.team4.goorm.community.Comment.presentation;

import com.team4.goorm.community.Comment.application.CommentService;
import com.team4.goorm.community.Comment.dto.request.CommentCreateReqDto;
import com.team4.goorm.community.Comment.dto.response.CommentInfoRespDto;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PostMapping("/write")
    public ResponseEntity<SuccessResponse<CommentInfoRespDto>> createComment(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody CommentCreateReqDto request,
            @RequestParam Long postId
    ) {
        return ResponseEntity.ok(SuccessResponse.success("댓글 작성 성공", commentService.createComment(request, user.getEmail(), postId)));
    }

    
    @Operation(summary = "게시글의 댓글 수정")
    @PatchMapping("/update")
    public ResponseEntity<SuccessResponse<CommentInfoRespDto>> updateComment(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody CommentCreateReqDto request,
            @RequestParam Long commentId
    ) {
        return ResponseEntity.ok(SuccessResponse.success("댓글 수정 성공", commentService.updateComment(request, user.getEmail(), commentId)));
    }

    //댓글 삭제하면 어떻게 할지 생각해보기
    @Operation(summary = "게시글의 댓글 삭제")
    @PatchMapping("/delete")
    public ResponseEntity<SuccessResponse<CommentInfoRespDto>> deleteComment(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Long commentId
    ) {
        commentService.deleteComment(user.getEmail(), commentId);
        return ResponseEntity.ok(SuccessResponse.success("댓글 삭제 성공"));
    }

}
