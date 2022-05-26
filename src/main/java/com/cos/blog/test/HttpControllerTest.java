package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답 controller (Data를 응답)
//사용자가 요청 -> 응답(HTML를 응답)시 @Controller
@RestController
public class HttpControllerTest {

	private static final String TAG="HttpControllerTest:";
	
	@RequestMapping("/http/lombok")
	public String lombokTest() {
		/* Member m = new Member(1,"ss","1111","sss@naver.com"); */
		Member m = Member.builder().userName("sdsd").email("sss@naver.com").password("12342").build(); // 빌더 패턴.
		System.out.println(TAG+"getter:"+m.getId());
		m.setId(5000);
		System.out.println(TAG+"setter:"+m.getId());
		
		return "LombokTest Clear";
	}
	//http://localhost:8001/http/~  -----
		@GetMapping("/http/get")
	public String getTest(Member m) {
			
		return "get 요청 :" +m.getId() + "," + m.getUserName() + "," + m.getPassword() + "," + m.getEmail();
	}
		//인터넷 브라우저 요청(주소창)은 무조건 get요청만 할 수 바께없다
		//그냥  실행시 405Error 발생 -> 메소드가 허용하지 않는다.() 
		@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청 : "+m.getId() + "," + m.getUserName() + "," + m.getPassword() + "," + m.getEmail();
	}
		@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청" +m.getId() + "," + m.getUserName() + "," + m.getPassword() + "," + m.getEmail();
	}
		@DeleteMapping("/http/delete")
	public String deleteTest(Member m) {
		return "delete 요청";
	}
	
}
