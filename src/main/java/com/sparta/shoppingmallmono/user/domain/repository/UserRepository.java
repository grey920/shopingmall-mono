package com.sparta.shoppingmallmono.user.domain.repository;


import com.sparta.shoppingmallmono.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository< User, Long> {
}
