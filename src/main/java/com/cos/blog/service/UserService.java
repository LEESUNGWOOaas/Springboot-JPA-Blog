package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional // 하나의 트랜잭션으로 묶임 
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();//1234원문
		String encPassword = encoder.encode(rawPassword);//해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); // 이게 아래로 가면 회원가입값에 묻히나봄		
		userRepository.save(user);
	}
	
	/* 로그인은 시큐리티로 관리
	 * @Transactional(readOnly = true)//select할 때 트랜잭션 시작,서비스 종료시 트랜잭션 종료 (정합성 유지)
	 * public User 로그인(User user) { //select 여러번해도 같은 데이터가 나옴 return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword(
	 * )); }
	 */
}
