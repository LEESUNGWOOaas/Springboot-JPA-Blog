package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// ORM -> 오브젝트를 테이블로 매핑해주는 것 
@Entity //user 클래스가 Mysql에 테이블이 생성된다.
@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder //빌더패턴`
//@DynamicInsert insert시 null인 데이터 제외
public class User {
	@Id //primary key를 의미
	@GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링 전략 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다													// 오라클을 연결하면 시퀀스를 쓰고 mysql이면 auto_increment를 쓰는 것 
	private int id;//시퀀스 , auto_increment
	
	@Column(nullable = false,length = 300) // null이 될수 없고 길이가 300자 까지 제한 
	private String username;//아이디
	@Column(nullable = false,length = 300,unique=true)
	private String password;
	@Column(nullable = false,length = 50)
	private String email;
	
	
	//@ColumnDefault("'user'")//회원가입시 디폴트 값 ColumnDefault는 안에 ("  '' ") 쌍따옴 안에 홑따옴을 만들어야함  
	//DB는 RoleType이 없다 
	@Enumerated(EnumType.STRING)//해당 객체가 String 값이라는 것 을 의미해줌 
	private RoleType role;//Enum을 써야한다 Enum은 데이터의 도메인(어떤 범위)을 만든다 , admin,user,manager등 권한 타입이 string이면 오타를 낼수있다
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
	
	//카카오 로그인인지 판단하는 부분 
	private String oauth;
	
	
}
