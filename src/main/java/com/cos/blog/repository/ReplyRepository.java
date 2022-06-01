package com.cos.blog.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
 
import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	@Modifying
	@Query(value="INSERT INTO reply(userId,boardId,content,createDate)VALUES(?1,?2,?3,now())",nativeQuery = true)//createDate는 자동으로 들어간다 ?뒤에는 숫자가 필요하다 
	public int mSave(int userId,int boardId,String content);
}
