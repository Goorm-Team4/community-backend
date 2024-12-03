package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.dto.request.PostCreateReqDto;
import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;
import com.team4.goorm.community.Post.repository.PostRepository;
import com.team4.goorm.community.auth.domain.CustomUserDetails;
import com.team4.goorm.community.image.service.AmazonS3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostQueryService postQueryService;
    private final MemberQueryService memberQueryService;
    private final AmazonS3Service amazonS3Service;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostInfoRespDto> getPostsByMember(String email) {
        Member member = memberQueryService.findMemberByEmail(email);
        List<Post> posts = postQueryService.findAllByMemberOrderByCreatedAtDesc(member);

        return posts.stream()
                .map(PostInfoRespDto::from)
                .toList();
    }

    public PostInfoRespDto createPost(PostCreateReqDto request, MultipartFile postImage, String user) {
        String imageUrl = amazonS3Service.uploadImage(postImage);
        Member member = memberQueryService.findMemberByEmail(user);
        Post post = request.toEntity(member.getMemberId(), imageUrl);
        Post savedPost = postRepository.save(post);
        return PostInfoRespDto.from(savedPost);
    }

    public void deletePost(Long postId) {
        Post post = postQueryService.findById(postId);
        postRepository.delete(post);
    }

    public PostInfoRespDto updatePost(Long postId, PostCreateReqDto request, MultipartFile postImage) {
        Post post = postQueryService.findById(postId);
        String imageUrl = amazonS3Service.uploadImage(postImage);
        post.update(request.getContent(), imageUrl,request.getCategory());
        return PostInfoRespDto.from(post);
    }

    public PostInfoRespDto getPost(Long postId) {
        Post post = postQueryService.findById(postId);
        return PostInfoRespDto.from(post);
    }

    public List<PostInfoRespDto> getAllPosts(int page, int size, String sort) {
        List<Post> posts = postQueryService.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(PostInfoRespDto::from)
                .toList();
    }

    public PostInfoRespDto getPostsByTitle(String title) {
        return PostInfoRespDto.from(postQueryService.findByTitle(title));
    }

    // public void toggleLike(Long postId, String email) {
    //     Post post = postQueryService.findById(postId);
    //     Member member = memberQueryService.findMemberByEmail(email);
    //     post.toggleLike(member);
    // }


}
