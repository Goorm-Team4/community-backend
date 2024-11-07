package com.team4.goorm.community.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team4.goorm.community.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);
	Optional<Member> findByEmail(String email);
	Optional<Member> findBySocialId(String socialId);

}
