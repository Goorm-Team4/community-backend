package com.team4.goorm.community.Post.dto.condition;

import com.team4.goorm.community.Member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberPostCond {

    private Member member;
}
