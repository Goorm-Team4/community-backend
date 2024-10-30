package com.team4.goorm.community.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team4.goorm.community.Member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
