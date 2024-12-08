package com.team4.goorm.community.Post.application;

import com.team4.goorm.community.Comment.application.CommentQueryService;
import com.team4.goorm.community.Comment.domain.Comment;
import com.team4.goorm.community.Member.application.MemberQueryService;
import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.Post.domain.Category;
import com.team4.goorm.community.Post.domain.Post;
import com.team4.goorm.community.Post.domain.PostLike;
import com.team4.goorm.community.Post.dto.request.PostCreateReqDto;
import com.team4.goorm.community.Post.dto.request.PostPageDto;
import com.team4.goorm.community.Post.dto.response.PostDetailRespDto;
import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;
import com.team4.goorm.community.Post.dto.response.PostListRespDto;
import com.team4.goorm.community.Post.exception.PostException;
import com.team4.goorm.community.Post.repository.PostLikeRepository;
import com.team4.goorm.community.Post.repository.PostRepository;
import com.team4.goorm.community.image.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.team4.goorm.community.Post.exception.PostErrorCode.POST_NOT_AUTHORIZED;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostQueryService postQueryService;
    private final MemberQueryService memberQueryService;
    private final AmazonS3Service amazonS3Service;
    private final PostRepository postRepository;
    private final CommentQueryService commentQueryService;
    private final PostLikeQueryService postLikeQueryService;
    private final PostLikeRepository postLikeRepository;

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
        Post post = request.toEntity(member, imageUrl);
        Post savedPost = postRepository.save(post);
        return PostInfoRespDto.from(savedPost);
    }

    public void deletePost(Long postId, String email) {
        Member member = memberQueryService.findMemberByEmail(email);
        Post post = postQueryService.findById(postId);
        if (!post.getMember().equals(member)) {
            throw new PostException(POST_NOT_AUTHORIZED);
        }
        postRepository.delete(post);
    }

    public PostInfoRespDto updatePost(Long postId, PostCreateReqDto request, MultipartFile postImage, String email) {
        Member member = memberQueryService.findMemberByEmail(email);
        Post post = postQueryService.findById(postId);
        String imageUrl = amazonS3Service.uploadImage(postImage);
        if (!post.getMember().equals(member)) {
            throw new PostException(POST_NOT_AUTHORIZED);
        }
        post.update(request.getTitle(), request.getContent(), imageUrl, request.getCategory());
        return PostInfoRespDto.from(post);
    }

    @Transactional(readOnly = true)
    public PostDetailRespDto getPost(Long postId) {
        Post post = postQueryService.findById(postId);
        List<Comment> comments = commentQueryService.findAllByPost(post);
        return PostDetailRespDto.of(post, comments);
    }

    @Transactional(readOnly = true)
    public PostListRespDto getAllPosts(String category, int page, String sortBy, String direction) {
        Pageable pageable = new PostPageDto(page, sortBy, direction).toPageable();

        if (category != null) {
            return PostListRespDto.from(postQueryService.findAllByCategory(Category.valueOf(category), pageable));
        } else {
            return PostListRespDto.from(postQueryService.findAll(pageable));
        }
    }

    public PostInfoRespDto getPostsByTitle(String title) {
        return PostInfoRespDto.from(postQueryService.findByTitle(title));
    }

    public void toggleLike(Long postId, String email) {
        Post post = postQueryService.findById(postId);
        Member member = memberQueryService.findMemberByEmail(email);

       postLikeRepository.findByPostAndMember(post, member)
                .ifPresentOrElse(
                        (like) -> {
                            post.decreaseLikeCount();
                            postLikeRepository.delete(like);
                        },
                        () -> {
                            post.increaseLikeCount();
                            postLikeRepository.save(new PostLike(post, member));
                        });
    }


    // public void toggleLike(Long postId, String email) {
    //     Post post = postQueryService.findById(postId);
    //     Member member = memberQueryService.findMemberByEmail(email);
    //     post.toggleLike(member);
    // }
}
