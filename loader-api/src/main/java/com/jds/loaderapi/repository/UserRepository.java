package com.jds.loaderapi.repository;

import com.jds.loaderapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
