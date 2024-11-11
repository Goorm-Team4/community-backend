package com.team4.goorm.community.member.application;

import com.team4.goorm.community.auth.exception.AuthErrorCode;
import com.team4.goorm.community.auth.exception.AuthException;
import com.team4.goorm.community.member.domain.Member;
import com.team4.goorm.community.member.dto.response.ProfileInfoRespDto;
import com.team4.goorm.community.member.exception.MemberException;
import com.team4.goorm.community.member.repository.MemberRepository;
import com.team4.goorm.community.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team4.goorm.community.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
    }

    public ProfileInfoRespDto getMyProfile(String email) {
        Member member = findMemberByEmail(email);
        return ProfileInfoRespDto.from(member);
    }

//    public boolean existsByEmail(String email) {
//        return memberRepository.existsByEmail(email);
//    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public void validateUniqueEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
//
//    public void validateUniqueUsername(String username) {
//        if (memberRepository.existsByUsername(username)) {
//            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
//        }
//    }

//    public List<PostInfoRespDto> getMyPosts(String email) {
//        Member member = findMemberByEmail(email);
//
//        postRepository.findAllByMember(member);
//        return null;
//    }
}
