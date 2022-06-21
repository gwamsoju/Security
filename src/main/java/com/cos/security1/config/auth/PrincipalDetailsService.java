package com.cos.security1.config.auth;

import com.cos.security1.repository.UserRepository;
import com.cos.security1.vo.User1;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login")
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행된다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 Session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User1 userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
