package com.webapp.verticalascent.repository;

import com.webapp.verticalascent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
