package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;
//DAO
//자동으로 Bean 등록이됨
//@Repository //생략 가능  
public interface UserRepository extends JpaRepository<User, Integer>{
	//Select * FROM user Where username = ?(username 첫번째가 들어가면서 실행);
	Optional<User> findByUsername(String username);
	//로그인을 위한 함수 생성 (JPA Naming전략)
	/* User findByUsernameAndPassword(String username,String password); */
}