package com.cos.blog.config.auth;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;
//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유 세션저장소에 저장해준다 (UserDetails 타입의 PrincipalDetail이 저장된다) 그때 
//User도 같이 저장해야한다
@Getter
public class PrincipalDetail implements UserDetails{
	private User user; //모델이 들고있는 User (객체를 품고있는 것을  컴포지션이라고한다.extends 상속)

	//principalDetailService에서 user 리턴이 null이기 떄문에 만들어진 생성자
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	//계정이 만료되지 않았는지 리턴한다.(true : 만료안됨 / false는 만료된 계정) 
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정이 잠겨있지 않았는지 리턴한다 (true : 잠금해제상태 / false : 잠금상태)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨 / false : 만료상태)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정활성화 여부 리턴한다(true : 활성화 / false : 비활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정 권한 리턴(권한이 여러개 있을 수 있어서 루프를 돌아야하는데 지금은 권한이 한개이다.)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//ArrayList의 부모에 Collection이 존재한다.(ArrayList는 Collection타입이다)
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*
		 * collectors.add(new GrantedAuthority() {
		 * 
		 * @Override public String getAuthority() { // TODO Auto-generated method stub
		 * return "ROLE_"+user.getRole(); //ROLE_(user)리턴 *ROLE_(스프링 규약) } });
		 */
		//어차피 add안에 들어올 수 있는  함수 타입은 GrantedAuthority바께없기 때문에(인지하고있다) 람다식 사용가능 
		collectors.add(()->{ return "ROLE_"+user.getRole();});
		return collectors;
	}	
	
}
