package com.team4.goorm.community.Post.presentation;

import com.team4.goorm.community.Post.application.PostService;
import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;
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

@Tag(name = "Post", description = "게시글 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "내가 작성한 게시글 조회")
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<List<PostInfoRespDto>>> getMyPosts(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(postService.getPostsByMember(user.getEmail())));
    }

}
