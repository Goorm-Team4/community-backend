package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    public List<Post> findAllByOrderByCreatedAtDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Post findByTitle(String title) {
        return postRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("해당 제목의 게시글이 존재하지 않습니다."));
    }
}
