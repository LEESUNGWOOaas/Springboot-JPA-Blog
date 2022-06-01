package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder //빌더패턴
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment사용
	private int id;
	
	@Column(nullable=false,length=100)
	private String title;
	
	@Lob
	private String content; //섬머노트 라이브러리 사용 <html>태그 섞여서 디자인되서 크기가 커짐
	
	@ColumnDefault("0")
	private int count; // 조회수 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;//유저의 아이디값으로 select 혹은 join을 한다 근데  ORM은 key값으로 찾는(Foreign Key)게 아닌 
	
	@OneToMany(mappedBy="board" ,fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)//mappedBy는 연관관계의 주인이아니다.(FK가 아니다란 뜻)
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> reply;
	  
	@CreationTimestamp
	private Timestamp createDate;
	
}
