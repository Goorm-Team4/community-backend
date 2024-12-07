package com.team4.goorm.community.Post.presentation;

import com.team4.goorm.community.Post.application.PostService;
import com.team4.goorm.community.Post.dto.request.PostCreateReqDto;
import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;
import com.team4.goorm.community.Post.dto.response.PostDetailRespDto;
import com.team4.goorm.community.Post.dto.response.PostListRespDto;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "게시글 작성", description = "Contnet-type info는 application/json으로 요청해주세요. (이미지는 url말고 파일 보내주셔야합니다.)")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponse<PostInfoRespDto>> createMyPost(
        @RequestPart("content") PostCreateReqDto request,
        @RequestPart(value = "image", required = false) MultipartFile postImage,
        @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success("게시글 작성 성공",postService.createPost(request, postImage, user.getEmail())));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse<String>> deleteMyPost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.ok(SuccessResponse.success("게시글 삭제 성공"));
    }

    @Operation(summary = "게시글 수정", description = "Contnet-type info는 application/json으로 요청해주세요. (이미지는 url말고 파일 보내주셔야합니다.)")
    @PutMapping(value = "/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponse<PostInfoRespDto>> updateMyPost(
            @PathVariable Long postId,
            @RequestPart("content") PostCreateReqDto request,
            @RequestPart(value = "image", required = false) MultipartFile postImage
    ) {
        return ResponseEntity.ok(SuccessResponse.success("게시글 수정 성공",postService.updatePost(postId, request, postImage)));
    }

    @Operation(summary = "게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse<PostDetailRespDto>> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(SuccessResponse.success(postService.getPost(postId)));
    }

    @Operation(summary = "게시글 전체 조회")
    @GetMapping()
    public ResponseEntity<SuccessResponse<PostListRespDto>> getAllPosts(
        @Parameter(description = "현 카테고리: 카테고리1, 카테고리2, 카테고리3", example = "카테고리1")
        @RequestParam(required = false) String  category,
        @Parameter(description = "페이지 번호", example = "1")
        @RequestParam(required = false, defaultValue = "1") int page,
        @Parameter(description = "정렬 기준: createdAt(날짜순), likeCount(좋아요순)", example = "createdAt")
        @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
        @Parameter(description = "정렬 방향: asc(오름차순), desc(내림차순)", example = "desc")
        @RequestParam(required = false, defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(SuccessResponse.success(postService.getAllPosts(category, page, sortBy, direction)));
    }

    //JPQL로 수정, like 절 사용, list로 반환
    @Operation(summary = "게시글 제목 조회")
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<PostInfoRespDto>> getPostsByTitle(
        @RequestParam String title
    ) {
        return ResponseEntity.ok(SuccessResponse.success(postService.getPostsByTitle(title)));
    }

     @Operation(summary = "게시글 좋아요 토글")
     @PatchMapping("/{postId}/like/toggle")
     public ResponseEntity<SuccessResponse<String>> toggleLike(
             @Parameter(description = "게시글 ID", example = "1")
             @PathVariable Long postId,
             @AuthenticationPrincipal CustomUserDetails user
     ) {
         postService.toggleLike(postId, user.getEmail());
         return ResponseEntity.ok(SuccessResponse.success("좋아요 토글 성공"));
     }
}
