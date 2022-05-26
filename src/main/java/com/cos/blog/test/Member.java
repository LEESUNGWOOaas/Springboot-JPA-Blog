package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
// @RequiredArgsConstructor //final 붙은 애들의 생성자를 만들어
@Data //getter setter 둘다있음
/*
 * @AllArgsConstructor //모든 필드를 다쓰는 생성자 생성
 */

@NoArgsConstructor //빈(파라미터가 없는 ) 생성자   final로 된 필드는 초기화 할수 없다 
public class Member {
	private int id;
	private String userName;
	private String password;
	private String email;
	
	@Builder // 내가 짠 코드의 순서를 신경쓰지 않아도된다 빌더 패턴을 원하는 데로 쓸 수 있게 해준다.
	public Member(int id, String userName, String password, String email) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

}
