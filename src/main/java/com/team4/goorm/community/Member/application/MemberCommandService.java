package com.team4.goorm.community.Member.application;

import com.team4.goorm.community.Member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberCommandService {

    public void updateMemberProfile(Member member, String username, String imageUrl) {
        if (username != null && !username.isEmpty()) {
            member.updateUsername(username);
        }
        if (imageUrl != null) {
            member.updateProfileImageUrl(imageUrl);
        }
    }
}
