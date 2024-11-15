//package com.team4.goorm.community.Post.repository;
//
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.team4.goorm.community.Member.domain.Member;
//import com.team4.goorm.community.Post.dto.condition.MemberPostCond;
//import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//import static com.team4.goorm.community.Comment.domain.QComment.comment;
//import static com.team4.goorm.community.Post.domain.QPost.post;
//import static com.team4.goorm.community.Post.domain.QPostLike.postLike;
//
//@RequiredArgsConstructor
//public class PostRepositoryImpl implements PostRepositoryCustom {
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public List<PostInfoRespDto> searchByMember(MemberPostCond condition) {
//        return jpaQueryFactory
//                .select(
//                        Projections.constructor(PostInfoRespDto.class,
//                                post.postId,
//                                post.title,
//                                post.content,
//                                post.category,
//                                post.thumbnailImageUrl.as("imageUrl"),
//                                post.createdAt,
//                                postLike.count().as("likeCount"),
//                                comment.count().as("commentCount")
//                        )
//                )
//                .from(post)
//                .leftJoin(postLike).on(postLike.post.eq(post))
//                .leftJoin(comment).on(comment.post.eq(post))
//                .where(memberEq(condition.getMember()))
//                .groupBy(post.postId)
//                .orderBy(post.createdAt.desc())
//                .fetch();
//    }
//
//    BooleanExpression memberEq(Member member) {
//        return member != null ? post.member.eq(member) : null;
//    }
//}
