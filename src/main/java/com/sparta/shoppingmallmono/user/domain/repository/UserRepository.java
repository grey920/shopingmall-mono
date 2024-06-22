package com.sparta.shoppingmallmono.user.domain.repository;


import com.sparta.shoppingmallmono.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository< User, Long> {
    Optional<User> findByEmail( String email );

}
