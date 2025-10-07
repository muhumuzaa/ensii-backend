package com.ensiibackend.repos;

import com.ensiibackend.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository implements JpaRepository<UserAccount> {
}
