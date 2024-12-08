package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Category;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    public List<Post> findAllByOrderByCreatedAtDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> findAllByCategory(Category category, Pageable pageable) {
        return postRepository.findAllByCategory(category, pageable);
    }

    public Post findByTitle(String title) {
        return postRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("해당 제목의 게시글이 존재하지 않습니다."));
    }
}
