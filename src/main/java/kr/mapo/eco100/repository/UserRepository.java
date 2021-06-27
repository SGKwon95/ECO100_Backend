package kr.mapo.eco100.repository;

import kr.mapo.eco100.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUid(Long userId);
}
