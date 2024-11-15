package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;

    public List<Post> findAllByMemberOrderByCreatedAtDesc(Member member) {
        return postRepository.findAllByMemberOrderByCreatedAtDesc(member);
//        MemberPostCond condition = new MemberPostCond(member);
//        return postRepository.searchByMember(condition);
    }
}
