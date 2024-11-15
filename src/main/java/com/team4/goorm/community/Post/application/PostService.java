package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostQueryService postQueryService;
    private final MemberQueryService memberQueryService;

    @Transactional(readOnly = true)
    public List<PostInfoRespDto> getPostsByMember(String email) {
        Member member = memberQueryService.findMemberByEmail(email);
        List<Post> posts = postQueryService.findAllByMemberOrderByCreatedAtDesc(member);

        return posts.stream()
                .map(PostInfoRespDto::from)
                .toList();
    }
}
