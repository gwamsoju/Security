package com.cos.security1.repository;

import com.cos.security1.vo.User1;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository가 CRUD 함수를 가지고 있음
//@Repository 어노테이션을 붙이지 않아도 JpaRepository를 상속했기 때문에 자동으로 IoC 된다.
public interface UserRepository extends JpaRepository<User1,Integer> {
    // findBy 까지는 규칙 -> Username은 문법
    // select * from user1 where username = ?
    public User1 findByUsername(String username);
}
