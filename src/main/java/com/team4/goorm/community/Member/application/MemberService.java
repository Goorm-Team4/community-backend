package com.team4.goorm.community.Member.application;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Member.dto.response.ProfileInfoRespDto;
import com.team4.goorm.community.image.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final AmazonS3Service amazonS3Service;

    public ProfileInfoRespDto updateMyProfile(String username, MultipartFile profileImage, String email) {
        Member member = memberQueryService.findMemberByEmail(email);

        // 이미지 업로드
        String imageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            imageUrl = amazonS3Service.uploadImage(profileImage);
        }

        memberCommandService.updateMemberProfile(member, username, imageUrl);
        return ProfileInfoRespDto.from(member);
    }
}
