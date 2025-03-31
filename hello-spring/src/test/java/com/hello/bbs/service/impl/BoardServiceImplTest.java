package com.hello.bbs.service.impl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.dao.impl.BoardDaoImpl;
import com.hello.bbs.service.BoardService;
import com.hello.bbs.vo.BoardListVO;
import com.hello.bbs.vo.BoardVO;

@SpringBootTest // @Controller, @Repository, @Service Bean들을 모두 생성한다.
@Import({BoardServiceImpl.class, BoardDaoImpl.class}) // Bean을 가져오기 위함.
public class BoardServiceImplTest {

	@Autowired
	private BoardService boardService;
	
	@MockitoBean // 가짜 Bean : 스프링 Bean 컨테이너에 Bean을 만드는게 아니라, BoardDaoImpl로 Bean을 만든다.
	private BoardDao boardDao; // DB한테 직접 연결되는 객체 -> Service는 DB에 직접 연결하면 안됨! -> 역할 정의시, 기대값을 준다.
	
	@Test
	public void getTest() {
		// service의 역할 놀이 시작: given-willReturn-when
		// selectBoardAllCount()가 호출되면 100을 반환시켜라 라는 의미. (실제로 100이 들어간다.)
		BDDMockito.given(this.boardDao.selectBoardAllCount()).willReturn(100); //BDDMockito가 BoardDao로 주입된다.
		
		// selectAllBoard()가 호출되면 null 값으로 채워진 List가 반환되고, BoardListVO로 주입된다.
		BDDMockito.given(this.boardDao.selectAllBoard())
				  .willReturn(List.of(new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()
						  			 ,new BoardVO()	));
		
		// 테스트 하고자 하는 것은 가짜 빈이니깐, DB에 접근할 수 없어서 가짜 데이터(기대값)를 넣어준다.
		BoardListVO boardListVO = this.boardService.getBoardList();
		Assertions.assertEquals(boardListVO.getBoardCnt(), 100); // assertEquals (객체의 메소드,Value) 두개의 값이 같아야한다.
		Assertions.assertEquals(boardListVO.getBoardList().size(), 10);
		
	}
}
