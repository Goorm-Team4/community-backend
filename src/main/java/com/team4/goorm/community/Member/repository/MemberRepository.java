package com.team4.goorm.community.Member.repository;

import com.team4.goorm.community.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
	Optional<Member> findByEmail(String email);
	Optional<Member> findBySocialId(String socialId);
}
