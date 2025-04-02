package com.hello.bbs.service.impl;

import java.util.List;

import org.assertj.core.api.BDDAssertions;
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
import com.hello.bbs.vo.BoardUpdateRequestVO;
import com.hello.bbs.vo.BoardVO;
import com.hello.bbs.vo.BoardWriteRequestVO;

@SpringBootTest // @Controller, @Repository, @Service Bean들을 모두 생성한다.
@Import({ BoardServiceImpl.class, BoardDaoImpl.class }) // Bean을 가져오기 위함.
public class BoardServiceImplTest {
	// 단위 테스트 목적
	// 의도한 실패가 잘 일어나는가?

	@Autowired
	private BoardService boardService;

	@MockitoBean // 가짜 Bean : 스프링 Bean 컨테이너에 Bean을 만드는게 아니라, BoardDaoImpl로 Bean을 만든다.
	private BoardDao boardDao; // DB한테 직접 연결되는 객체 -> Service는 DB에 직접 연결하면 안됨! -> 역할 정의시, 기대값을 준다.

	@Test
	public void getTest() {
		// service의 역할 놀이 시작: given-willReturn-when
		// selectBoardAllCount()가 호출되면 100을 반환시켜라 라는 의미. (실제로 100이 들어간다.)
		BDDMockito.given(this.boardDao.selectBoardAllCount()).willReturn(100); // BDDMockito가 BoardDao로 주입된다.

		// selectAllBoard()가 호출되면 null 값으로 채워진 List가 반환되고, BoardListVO로 주입된다.
		BDDMockito.given(this.boardDao.selectAllBoard())
				.willReturn(List.of(new BoardVO(), new BoardVO(), new BoardVO(), new BoardVO(), new BoardVO(),
						new BoardVO(), new BoardVO(), new BoardVO(), new BoardVO(), new BoardVO()));

		// 테스트 하고자 하는 것은 가짜 빈이니깐, DB에 접근할 수 없어서 가짜 데이터(기대값)를 넣어준다.
		BoardListVO boardListVO = this.boardService.getBoardList();
		Assertions.assertEquals(boardListVO.getBoardCnt(), 100); // assertEquals (객체의 메소드,Value) 두개의 값이 같아야한다.
		Assertions.assertEquals(boardListVO.getBoardList().size(), 10);

	}

	@Test
	public void createTest() {
		
		BoardWriteRequestVO testVO = new BoardWriteRequestVO();
		// 역할 놀이 시작
		
		// Given
		BDDMockito.given(this.boardDao.insertNewBoard(testVO))
				  .willReturn(1);
		
		// When
		boolean success = this.boardService.createNewBoard(testVO);

		// Then
		Assertions.assertTrue(success);
	}

	@Test
	public void createTestFail() {
		// Given
		BDDMockito.given(this.boardDao.insertNewBoard(null))
				  .willReturn(0);
		// When
		boolean isFail = this.boardService.createNewBoard(null);

		// Then
		Assertions.assertFalse(isFail);
	}

	@Test
	public void testViewBoard() {
		// Given
		BDDMockito.given(this.boardDao.selectOneBoard(1_000_000))
				  .willReturn(new BoardVO());
		
		BDDMockito.given(this.boardDao.updateViewCountBy(1_000_000))
				  .willReturn(1);
		// When
		BoardVO boardVO = this.boardService.getOneBoard(1_000_000, true);
		
		// Then
		Assertions.assertNotNull(boardVO); // Null이 아니면 서비스 동작 성공.		
	}

	@Test
	public void testViewBoardFail() {
		// Given
		BDDMockito.given(this.boardDao.selectOneBoard(1_000_000))
				  .willReturn(null); // 게시글이 생성되지 않음 -> getOneBoard할 때 null임.
		
		BDDMockito.given(this.boardDao.updateViewCountBy(1_000_000))
				  .willReturn(0);
		// When
//		BDDMockito.when(this.boardService.getOneBoard(1_000_000)).thenThrow(IllegalArgumentException.class);
		 
		try{
			this.boardService.getOneBoard(1_000_000, true);
		}
		catch(IllegalArgumentException iae){
			Assertions.assertEquals("1000000는 존재하지 않는 게시글 번호입니다.", iae.getMessage());
		}
		
		// Then
//		Assertions.assertNull(boardVO); // Null이면 서비스 동작 성공.	
	}
	
	@Test
	public void deleteBoard() {
		BDDMockito.given(this.boardDao.deleteOneBoard(1))
				  .willReturn(1);
		
		boolean isDeleted = this.boardService.deleteOneBoard(1);
		Assertions.assertTrue(isDeleted);
	}
	
	@Test
	public void deleteBoardFail() {
		BDDMockito.given(this.boardDao.deleteOneBoard(1))
				  .willReturn(0);
		
		String message = BDDAssertions.catchThrowable( () -> this.boardService.deleteOneBoard(1) ).getMessage();
		Assertions.assertEquals(1 + "는 존재하지 않는 게시글 번호입니다.", message);
	}
	
	@Test
	public void updateBoard() {
		
		BoardUpdateRequestVO testVO = new BoardUpdateRequestVO();
		
		BDDMockito.given(this.boardDao.updateOneBoard(testVO))
				  .willReturn(1);
		
		boolean isUpdated = this.boardService.updateOneBoard(testVO);
		Assertions.assertTrue(isUpdated);
	
	}

}
