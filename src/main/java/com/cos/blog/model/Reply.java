package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplyRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder //빌더패턴
@Entity
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false,length = 200)
	private String content;
	
	@ManyToOne
	//답변이 어느 게시글에 있는지 모르기에 연관관계로 Board에 가져온다
	@JoinColumn(name = "boardId")
	private Board board;
	
	@ManyToOne
	//답변을 누가 적었는지도 알아야한다
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	// 이방식은 service 에 build 대신 Reply reply = new Reply() update 문을 가져와서 필요한 객체만 넣어주는 형식을 쓸때 사용  
	//public void update(ReplyDto reply) {
	//	setUser(user);
	//	setBoard(board);
	//	setContent(content);
	//}
}
