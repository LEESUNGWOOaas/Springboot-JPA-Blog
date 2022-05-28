package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Transactional // 하나의 트랜잭션으로 묶임 
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();//1234원문
		String encPassword = encoder.encode(rawPassword);//해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); // 이게 아래로 가면 회원가입값에 묻히나봄		
		userRepository.save(user);
	}
	@Transactional
	public void 회원수정(User user) {
		/*수정 시 영속성 컨텍스트 User 오브젝트를 영속화 시키고 영속화된 User 오브젝트를 수정 
		Select 해서 User오브젝스를 DB에서 가져오는 이유는 영속화 하기 위해서 
		영속화된 오브젝트 변경시 자동으로 DB에 Update를  요청함*/
		
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원 찾기 실패");
				});
		//찾으면 오브젝트가 들어왔을때 
		String rawPassword = user.getPassword();//사용자로 부터 비번을 받는다
		//패스워드 암호화
		String encPassword = encoder.encode(rawPassword);
		//패스워드 수정
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		//회원 수정 함수 종료 = 서비스 종료 =트랜잭션 종료 =Commit 자동 완료 
		
		//세션등록  (오류 -> 종료되기 전에 변경된 패스워드를 날리고있다 )
		/*
		 * Authentication authentication = authenticationManager.authenticate(new
		 * UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		 * SecurityContextHolder.getContext().setAuthentication(authentication);
		 */
	}
	

	
	/* 로그인은 시큐리티로 관리
	 * @Transactional(readOnly = true)//select할 때 트랜잭션 시작,서비스 종료시 트랜잭션 종료 (정합성 유지)
	 * public User 로그인(User user) { //select 여러번해도 같은 데이터가 나옴 return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword(
	 * )); }
	 */
}
