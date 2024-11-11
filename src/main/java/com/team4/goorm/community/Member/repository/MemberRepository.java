package com.team4.goorm.community.member.repository;

import com.team4.goorm.community.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	Optional<Member> findByEmail(String email);
	Optional<Member> findBySocialId(String socialId);
}
