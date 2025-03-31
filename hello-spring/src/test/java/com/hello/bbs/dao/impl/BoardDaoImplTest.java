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
import com.hello.bbs.vo.BoardVO;

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
		int correctCount = 0; // DB가 다름
		
		// 두개가 같으면 성공, 다르면 실패
		Assertions.assertEquals(count, correctCount);
	}
	
	@Test
	public void testSelect() {
		List<BoardVO> boardlist = boardDaoImpl.selectAllBoard();
	      int size = boardlist.size();
	      int correctCount = 0;
	      Assertions.assertEquals(size, correctCount);
	}
}
