package com.mjozwk.featureflag.service.db.repo;

import com.mjozwk.featureflag.service.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
