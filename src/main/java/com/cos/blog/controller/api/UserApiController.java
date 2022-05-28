package com.cos.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	@Autowired
	private UserService userService; 
	@Autowired
	private AuthenticationManager authenticationManager;
	/*
	 * @Autowired private HttpSession session;
	 */
	//회원가입 로직
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {//user로 받는 것 3가지 username password email
		/*
		 * System.out.println("UserApiController : save호출 됨"); //실제 DB에 insert를 하고 아래에
		 * 리턴이 됨 user.setRole(RoleType.USER); // 이게 아래로 가면 회원가입값에 묻히나봄 서비스로 옮겨서 씀 int
		 * result =
		 */ userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 자바 오브젝트를 json으로 변환 jackson라이브러리를 이용해서
	}
	
	/* 시큐리티로 로그인 할것이기에 
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user,HttpSession session){
	 * System.out.println("UserApiController: login호출"); User principal =
	 * userService.로그인(user); // principal => 접근주체라는 용어
	 * 
	 * if(principal != null) { session.setAttribute("principal", principal); //세션 생성
	 * } return new ResponseDto<Integer>(HttpStatus.OK.value(),1); // 세션 생성 후 1 응답하면
	 * loginForm에서 user.JS 실행시 로그인이 완료되면 블로그로 이동 }
	 */
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//이 시점에 트랜잭션이 종료되기 때문에 DB값은 변경되어있음
		//하지만 세션값은  변경되지 않은 상태이기 때문에 (-> DB값은 변경되지만 브라우저에선 바로 적용이 안되서 재기동 같은 재빌드를 해야한다)
		//직접 세션값을 변경해야한다. (시큐리티가 어떻게 로그인되는지 알아야한다.)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
