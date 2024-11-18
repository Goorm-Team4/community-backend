package com.team4.goorm.community.Post.repository;

import com.team4.goorm.community.Post.dto.condition.MemberPostCond;
import com.team4.goorm.community.Post.dto.response.PostInfoRespDto;

import java.util.List;

public interface PostRepositoryCustom {

    List<PostInfoRespDto> searchByMember(MemberPostCond condition);
}
