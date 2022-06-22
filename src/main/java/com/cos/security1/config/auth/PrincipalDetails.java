package com.cos.security1.config.auth;

import com.cos.security1.vo.User1;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//시큐리티가 /login을 낚아채서 로그인을 진행시킴
// 로그인이 완료되면 시큐티리 session을 만들어준다.  (Security ContextHolder)
// 오브젝트 타입 -> Authentication 타입 객체
// Authentication 안에 유저 정보가 있어야 됨.
// 유저 오브젝트 타입 -> UserDetails 타입 객체

// Security Session -> Authentication 이 들어가도록 정해놓음 -> UserDetails(PrincipalDetails) 타입이 들어가도록 정해놓음.
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User1 user1;
    private Map<String,Object> attributes; // OAuth2User oAuth2User = super.loadUser(userRequest); 로 받아온 정보가 Map 형태이기 때문에


    // 일반 로그인 시 사용하는 생성자
    public PrincipalDetails(User1 user1) {
        this.user1 = user1;
    }

    //OAuth 로그인 시 사용하는 생성자
    public PrincipalDetails(User1 user1, Map<String, Object> attributes) {
        this.user1 = user1;
        this.attributes = attributes;
    }

    // 해당 유저의 권한을 리턴하는 곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() { // user1.getRole()는 String 타입이므로 반환할 수 없으니 이렇게 만들어서 반환해줌.
            @Override
            public String getAuthority() {
                return user1.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user1.getPassword();
    }

    @Override
    public String getUsername() {
        return user1.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 마지막 로그인 시간이 1년이 넘었으면 휴먼 계정으로 전환하기 위한 경우에 사용
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
