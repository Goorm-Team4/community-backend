package com.team4.goorm.community.Post.domain;

import com.team4.goorm.community.Member.domain.Member;
import com.team4.goorm.community.global.common.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@Size(min = 1, max = 45)
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "like_count")
	private Long likeCount = 0L;

	@Column(name = "comment_count")
	private Long commentCount = 0L;

	@Column(name = "thumbnail_image_url")
	private String thumbnailImageUrl;
}
