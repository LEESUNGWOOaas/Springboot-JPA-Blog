package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplyRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Transactional // 하나의 트랜잭션으로 묶임 
	public void 글쓰기(Board board,User user) { // title 과 content 만 받음 
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	@Transactional(readOnly = true)//select만 하는 것이라 붙여줫다.
	public Page<Board>글목록(Pageable pageable){
		
		
		return boardRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}
	
	@Transactional 
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		// TODO Auto-generated method stub
		Board board=boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});//영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시 트랜젝션이 service종료시 트랜잭션 종료 (더티 채킹 실행)-> 자동업데이트
	}
	
	@Transactional
	public void 댓글쓰기(ReplyRequestDto replyRequestDto) {
		/*
		 * User
		 * user=userRepository.findById(replyRequestDto.getUserId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패: 유저 ID를 찾을 수 없습니다."); }); Board
		 * board =
		 * boardRepository.findById(replyRequestDto.getBoardId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패: 게시글 ID를 찾을 수 없습니다."); });
		 * 
		 * 
		 * Reply reply = Reply.builder() .user(user) .board(board)
		 * .content(replyRequestDto.getContent()) .build();
		 * 
		 * 
		 * replyRepository.save(reply);
		 */
		replyRepository.mSave(replyRequestDto.getUserId(),replyRequestDto.getBoardId(),replyRequestDto.getContent());
		
	}
	public void 댓글삭제(int replyId) {
		// TODO Auto-generated method stub
		replyRepository.deleteById(replyId);
	}
}
