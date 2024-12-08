package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.domain.PostLike;
import com.team4.goorm.community.Post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostLikeQueryService {

    private final PostLikeRepository postLikeRepository;

    public PostLike findByPostAndMember(Post post, Member member) {
        return postLikeRepository.findByPostAndMember(post, member).orElse(null);
    }
}
