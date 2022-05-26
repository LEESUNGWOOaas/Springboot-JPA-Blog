package com.cos.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional // 하나의 트랜잭션으로 묶임 
	public void 글쓰기(Board board,User user) { // title 과 content 만 받음 
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	public Page<Board>글목록(Pageable pageable){
		
		
		return boardRepository.findAll(pageable);
	}
}
