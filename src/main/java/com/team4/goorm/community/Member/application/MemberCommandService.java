package com.team4.goorm.community.member.application;

import com.team4.goorm.community.image.service.AmazonS3Service;
import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.dto.response.ProfileInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberCommandService {

    private final MemberQueryService memberQueryService;
    private final AmazonS3Service amazonS3Service;

    public ProfileInfoRespDto updateMyProfile(String username, MultipartFile profileImage, String email) {
        Member member = memberQueryService.findMemberByEmail(email);

        if (username != null && !username.isEmpty()) {
            member.updateUsername(username);
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            String imageUrl = amazonS3Service.uploadImage(profileImage);
            member.updateProfileImageUrl(imageUrl);
        }

        return ProfileInfoRespDto.from(member);
    }

}
