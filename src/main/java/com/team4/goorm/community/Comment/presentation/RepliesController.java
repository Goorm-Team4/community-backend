package com.team4.goorm.community.Comment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team4.goorm.community.Comment.application.RepliesService;
import com.team4.goorm.community.Comment.dto.request.RepliesCreateReqDto;
import com.team4.goorm.community.Comment.dto.response.RepliesInfoRespDto;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Comment", description = "댓글 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/replies")
public class RepliesController {

    private final RepliesService repliesService;

    @Operation(summary = "답글 작성")
    @Schema(description = "답글 내용", example = "답글 내용")
    @PostMapping("/write")
    public ResponseEntity<SuccessResponse<RepliesInfoRespDto>> createReply(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody RepliesCreateReqDto request,
            @RequestParam Long commentId
    ) {
        return ResponseEntity.ok(SuccessResponse.success("답글 작성 성공", repliesService.createReply(request, user.getEmail(), commentId)));
    }
    
    @Operation(summary = "답글 수정")
    @Schema(description = "답글 내용", example = "답글 내용")
    @PatchMapping("/update")
    public ResponseEntity<SuccessResponse<RepliesInfoRespDto>> updateReply(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody RepliesCreateReqDto request,
            @RequestParam Long replyId
    ) {
        return ResponseEntity.ok(SuccessResponse.success("답글 수정 성공", repliesService.updateReply(request, user.getEmail(), replyId)));
    }

    @Operation(summary = "답글 삭제")
    @Schema(description = "답글 ID", example = "1")
    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse<String>> deleteReply(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Long replyId
    ) {
        return ResponseEntity.ok(SuccessResponse.success("답글 삭제 성공", repliesService.deleteReply(user.getEmail(), replyId)));
    }

}
