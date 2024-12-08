package com.team4.goorm.community.Member.presentation;

import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.application.MemberService;
import com.team4.goorm.community.Member.dto.request.UpdateMemberProfileReqDto;
import com.team4.goorm.community.Member.dto.response.ProfileInfoRespDto;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "Member", description = "멤버 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberService memberService;

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<ProfileInfoRespDto>> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(
                memberQueryService.getMyProfile(user.getEmail())));
    }

    @Operation(summary = "내 프로필 수정", description = "이미지는 url말고 파일로 보내주셔야합니다.")
    @PatchMapping(value = "/me", consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponse<ProfileInfoRespDto>> updateMyProfile(
            @RequestPart(value = "image", required = false) MultipartFile profileImage,
            @RequestPart(required = false) UpdateMemberProfileReqDto request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(
                memberService.updateMyProfile(request, profileImage, user.getEmail())));
    }
}
