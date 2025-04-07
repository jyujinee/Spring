package com.hello.bbs.dao.impl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.vo.BoardDeleteRequestVO;
import com.hello.bbs.vo.BoardUpdateRequestVO;
import com.hello.bbs.vo.BoardVO;
import com.hello.bbs.vo.BoardWriteRequestVO;

@MybatisTest
// 실제 SQL을 테스트해야하는 설정
// MyBatis 전용의 테스트 데이터베이스는 쓰지말고,
// application.yml에 설정된 실제 데이터베이스를 대상으로 테스트하는 설정이다.
@AutoConfigureTestDatabase(replace = Replace.NONE)
// BoardDaoImpl의 테스트용 Spring Bean이 만들어진다. (MyBatis 설정이 완료된 Bean)
@Import({BoardDaoImpl.class}) // BoardDaoImpl가 DI로 주입된다.
public class BoardDaoImplTest {
	
	@Autowired
	private BoardDao boardDaoImpl; // 이곳으로 주입된다!
	
	@Test
	public void testCount() {
		int count = boardDaoImpl.selectBoardAllCount();
		
		Assertions.assertTrue(count > 0);
//		int correctCount = 6; // 사용하는 컴퓨터 마다 DB가 다름(DB에서 확인하기)
//		Assertions.assertEquals(count, correctCount); // 두개가 같으면 성공, 다르면 실패
	}
	
	@Test
	public void testSelect() {
		List<BoardVO> boardlist = boardDaoImpl.selectAllBoard();
	      int size = boardlist.size();
	      
	      Assertions.assertTrue(size > 0);
//	      int correctCount = 6;
//	      Assertions.assertEquals(size, correctCount);
	}
	
	// testInsert는 자동으로 RollBack되어 DB에 데이터가 저장되지 않는다.
	@Test
	public void testInsert() {
		BoardWriteRequestVO testVO = new BoardWriteRequestVO();
		testVO.setContent("testContent");
		testVO.setEmail("testEmail");
		testVO.setSubject("testSubject");
		
		int insertedCount = this.boardDaoImpl.insertNewBoard(testVO);
		Assertions.assertEquals(insertedCount, 1);
	}
	
	@Test
	public void testUpdateViewCount() {
		int updateCount = this.boardDaoImpl.updateViewCountBy(26);
		Assertions.assertTrue(updateCount == 1);
	}
	
	@Test
	public void testSelectOne() {
		// insert -> 게시글 ID-> 조회
		BoardVO boardVO = this.boardDaoImpl.selectOneBoard(68);
		Assertions.assertNotNull(boardVO);
		Assertions.assertNotNull(boardVO.getFileList());
		Assertions.assertTrue(boardVO.getFileList().size()>0);
	}
	
	@Test
	public void testDeleteOne() {
		BoardDeleteRequestVO bdrv = new BoardDeleteRequestVO();
		bdrv.setId(4);
		bdrv.setEmail("test01@gmail.com");
		int deletedCount = this.boardDaoImpl.deleteOneBoard(bdrv);
		Assertions.assertTrue(deletedCount == 1);
	}
	
	@Test
	public void testUpdateOne() {
		BoardUpdateRequestVO testVO = new BoardUpdateRequestVO();
		testVO.setId(26);
		testVO.setSubject("testSubject");
		testVO.setContent("testContent");
		testVO.setEmail("testEmail");
		
		int updateCount = this.boardDaoImpl.updateOneBoard(testVO);
		Assertions.assertEquals(updateCount, 1);
	}
	
}
