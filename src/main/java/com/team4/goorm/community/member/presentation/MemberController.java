package com.team4.goorm.community.member.presentation;

import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.global.common.dto.SuccessResponse;
import com.team4.goorm.community.member.application.MemberCommandService;
import com.team4.goorm.community.member.application.MemberQueryService;
import com.team4.goorm.community.member.dto.request.UpdateMemberProfileReqDto;
import com.team4.goorm.community.member.dto.response.ProfileInfoRespDto;
import com.team4.goorm.community.post.dto.response.PostInfoRespDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "멤버 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<ProfileInfoRespDto>> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(
                memberQueryService.getMyProfile(user.getEmail())));
    }

    @PatchMapping("/me")
    public ResponseEntity<SuccessResponse<ProfileInfoRespDto>> updateMyProfile(
            @RequestPart("image") MultipartFile profileImage,
            @RequestPart String username,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(SuccessResponse.success(
                memberCommandService.updateMyProfile(username, profileImage, user.getEmail())));
    }

}
