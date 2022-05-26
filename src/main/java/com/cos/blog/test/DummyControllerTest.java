package com.cos.blog.test;


import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	@Autowired
	private UserRepository userRepository;
	
	//전체 셀렉트
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	//한 페이지당 2건의 데이터 리턴
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC)Pageable pageable){
		
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return users;	
	}
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
			
		//} catch (EmptyResultDataAccessException e) { 지금 오류는 테이블에 없는 데이터를 삭제하는데서 오는 오류를 정의한 것이지만 부모클래스인 Exception을 써도된다
		} catch (Exception e) {
			return id+"의 삭제를 실패했습니다.";
		}
		return "삭제되었습니다. id: "+id;
	}
	
	
	@Transactional 
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("ID :" + id);
		System.out.println("password :" + requestUser.getPassword());
		System.out.println("email :" + requestUser.getEmail());
		 
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패 했습니다.");
		});
		user.setPassword(requestUser.getPassword());;
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		//더티 체킹 
		return user;
	}
	
	//{id} 주소로 파라미터를 전달 받을수 있다
		//localhost:8001/blog/dummy/user/{3}
		@GetMapping("/dummy/user/{id}")
		public User detail(@PathVariable int id) {
			//user/4를 찾으면 내가 DB에서 못찾는 경우 user가 null이 될텐데 그럼 null이 리턴되니깐 프그램에 문제생김
			//Optional로 감싸서 user객체를 감싸서 가져오고 null인지 판단해서 리턴
			User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
				@Override
				public IllegalArgumentException get() {
					// TODO Auto-generated method stub
					return new IllegalArgumentException("해당 유저는 없습니다.");
				}
			});
			//요청 : 웹 브라우저
			//user 객체 =자바 오브젝트
			//변환 (웹브라우저가 이해할 수 있는 데이터)->json(gson 라이브러리  등)
			//스프링부트 = MessageConverter가 응답시 자동 작동
			//만약 자바오브젝트를 리턴하게되면 MessageConverter가 Jackson라이브러리를 호출해서
			//user오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
			return user;
		}
	
	
	//http://localhost:8001/blog/dummy/join (요청)
	//http의 body에 userName,password,email 데이터를 가지고 요청하게 되면 
	//파라미터에 들어가게된다.
	@PostMapping("/dummy/join")//Insert할 것이기 때문에 post를 써야한다	
	public String join(User user) {
		System.out.println("id : " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료!";
	}
}
