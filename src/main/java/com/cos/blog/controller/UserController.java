package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//인증이 안된 사용자들이 출입할수 있는 경로를 /auth/**만 허용
// 주소가 / 인 주소 -> index.jsp 허용


@Controller
public class UserController {
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {//@ResponseBody를 넣어주면 데이터를 리턴하는 컨트롤러 함수  (제거 이유: viewResolver를 호출해서 파일을 찾아가게 하기위해)
		
		//POST방식으로 key=value 데이터 요청(카카오 쪽으로)
		RestTemplate rt = new RestTemplate();
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//HttpBody 오브젝트 생성
		MultiValueMap<String,String> param = new LinkedMultiValueMap<>();
		param.add("grant_type","authorization_code");
		param.add("client_id","16ebb6ebce26d641a9995960714e4765");
		param.add("redirect_uri","http://localhost:8001/auth/kakao/callback");
		param.add("code",code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(param,headers); // 바디값과 헤더값을 가진 객체가 된다.
		
		//http요청하기 -Post방식으로 그리고 -response 변수수의 응답 받음 
		//exchnage가 httpEntity를 받기때문에  -> kakaoTokenRequest
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				
		);
		
		//Gson,Json Simple,ObjectMapper라이브러리로 Json데이터로 만든다
		ObjectMapper ObjectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			 oauthToken=ObjectMapper.readValue(response.getBody(),OAuthToken.class);
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		System.out.println("카카오 액세스 토큰 :"+ oauthToken.getAccess_token());
	
		
		RestTemplate rt2 = new RestTemplate();
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization"," Bearer " +oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//HttpBody 오브젝트 생성
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(param,headers2); // 바디값과 헤더값을 가진 객체가 된다.
		
		//http요청하기 -Post방식으로 그리고 -response 변수수의 응답 받음 
		//exchnage가 httpEntity를 받기때문에  -> kakaoTokenRequest
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class
				);
		System.out.println(response2.getBody());
		
		ObjectMapper ObjectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile=ObjectMapper2.readValue(response2.getBody(),KakaoProfile.class);
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		//User 오브젝트  : username password email
		System.out.println("카카오 아이디 : "+kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 :" + kakaoProfile.getKakao_account().getEmail()
				+"_"+kakaoProfile.getId());//+ 뒤의 부분은 만약 Email을 아이디로 쓰는 사람이 있는데 혹시라도 카카오 아이디와 중복됬을때는 뒤에 ID값을 넣어서 차별화를 제시
		
		System.out.println("블로그 서버 이메일 :"+ kakaoProfile.getKakao_account().getEmail());
		//UUID garbagePassword = UUID.randomUUID();
		//UUID란 중복되지 않는 어떤 특정값을 만드는 알고리즘 로그인마다 계속 바뀐 비번을 확인을 못한다(로그인 진행이 불가능) 기존회원이 아니면 상관없으나 기존회원이라면 문제가된다(기존회원이 카톡 로그인중에 카톡 로그인을 했다는 가정.)
		System.out.println("블로그 서버 패스워드 : "+cosKey);
		
		
		
		User kakaoUser = User.builder().username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
						.password(cosKey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
		//가입자 비가입자 체킹
		User OriginUser = userService.회원찾기(kakaoUser.getUsername());
		if(OriginUser.getUsername() == null) {	
			System.out.println("기존회원이 아닙니다.자동으로 회원가입이 진행됩니다");
			userService.회원가입(kakaoUser);
		}
		System.out.println("회원가입이 완료되었습니다.");
		//로그인 처리 
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
}
