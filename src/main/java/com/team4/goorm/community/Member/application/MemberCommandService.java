package com.team4.goorm.community.member.application;

import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.dto.request.UpdateMemberProfileReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandService {

    private final MemberQueryService memberQueryService;

    public void updateMyProfile(UpdateMemberProfileReqDto request, String email) {
        Member member = memberQueryService.findMemberByEmail(email);

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            member.updateUsername(request.getUsername());
        }

        if (request.getProfileImageUrl() != null && !request.getProfileImageUrl().isEmpty()) {
            member.updateProfileImageUrl(request.getProfileImageUrl());
        }
    }
}
