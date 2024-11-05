package com.team4.goorm.community.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team4.goorm.community.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
