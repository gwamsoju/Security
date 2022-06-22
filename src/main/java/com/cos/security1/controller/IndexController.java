package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.repository.UserRepository;
import com.cos.security1.vo.User1;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view를 리턴하겠다.
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("test/login ===========");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("anthentication : " + principalDetails.getUser1());

        System.out.println("userDetails : " + userDetails.getUser1());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,@AuthenticationPrincipal OAuth2User oauth){
        System.out.println("test/oauth/login ===========");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("anthentication : " + oAuth2User.getAttributes());
        System.out.println("oauth2User :" + oauth.getAttributes());
        return "OAuth 세션 정보 확인하기";
    }

    @GetMapping({"","/"})
    public String index(){
        return "index"; // 여기서 mustache 템플릿을 사용하는데 이 상태이면 뷰 리졸버는 index.mustache를 찾기 때문에
        //에러가 날 것이다. 왜냐하면 index.html로 파일을 만들었기 때문에 그래서 config 패키지에 WebMvcConfig 클래스에서
        // 오버라이딩을 통해서 .mustache -> .html로 반환하도록 했다.
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails" + principalDetails.getUser1());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    // Security가 기본적으로 제공하는 Login 화면을 보여주다가 SecurityCofig를 설정하니까 login이 그대로 나온다.
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User1 user1){
        System.out.println(user1);
        user1.setRole("ROLE_USER");
        String rawPwd = user1.getPassword(); // BCryptPasswordEncoder 를 SecurityConfig에서 Bean처리해놓고 여기서 씀.
        String encodePwd = bCryptPasswordEncoder.encode(rawPwd);
        user1.setPassword(encodePwd);
        userRepository.save(user1); // 회원가입은 잘 되지만 비밀번호가 암호화가 안되었기 때문에 시큐리티에서 로그인이 안된다. 그래서 암호화 진행함.
        return "redirect:/loginForm";
    }

    // 굳이 하나만 제한을 걸고 싶을 때 이렇게 할 수 있다.
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }
}
