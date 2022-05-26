package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	@Autowired
	private UserService userService; 
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
}
