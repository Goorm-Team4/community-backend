package com.team4.goorm.community.member.presentation;

import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import com.team4.goorm.community.member.application.MemberCommandService;
import com.team4.goorm.community.member.application.MemberQueryService;
import com.team4.goorm.community.member.dto.response.ProfileInfoRespDto;
import com.team4.goorm.community.post.dto.response.PostInfoRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Tag(name = "Member", description = "멤버 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<ProfileInfoRespDto>> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(
                memberQueryService.getMyProfile(user.getEmail())));
    }

    @Operation(summary = "내 프로필 수정")
    @PatchMapping(value = "/me", consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponse<ProfileInfoRespDto>> updateMyProfile(
            @RequestPart(value = "image", required = false) MultipartFile profileImage,
            @RequestPart(required = false) String username,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        log.info("username: {}", username);
        return ResponseEntity.ok(SuccessResponse.success(
                memberCommandService.updateMyProfile(username, profileImage, user.getEmail())));
    }

    @GetMapping("/me/posts")
    public ResponseEntity<SuccessResponse<List<PostInfoRespDto>>> getMyPosts(
            @AuthenticationPrincipal CustomUserDetails user
            ) {
        return null;
    }
}
