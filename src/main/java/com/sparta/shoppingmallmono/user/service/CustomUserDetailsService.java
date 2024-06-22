package com.sparta.shoppingmallmono.user.service;

import com.sparta.shoppingmallmono.security.EncryptionUtil;
import com.sparta.shoppingmallmono.user.domain.entity.User;
import com.sparta.shoppingmallmono.user.domain.repository.UserRepository;
import com.sparta.shoppingmallmono.user.web.request.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EncryptionUtil encryptionUtil;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {

        // email은 DB에 암호화되어 저장되어 있다. 따라서 입력받은 username을 암호화하여 DB에서 찾아야 한다.
        String encryptedUsername = encryptionUtil.encrypt( username );

        Optional<User> userData = userRepository.findByEmail( encryptedUsername );
        return userData.map( CustomUserDetails::new ).orElse( null );

    }
}
