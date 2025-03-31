package com.ktdsuniversity.edu.jyj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.jyj.dao.BoardDao;
import com.ktdsuniversity.edu.jyj.vo.BoardListVO;
import com.ktdsuniversity.edu.jyj.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	// Bean Container에 등록된 boardDao Bean을 가져와 주입시킨다.
	@Autowired
	private BoardDao boardDao;

	@Override
	public BoardListVO selectAllBoard() {
		// 게시글 건수와 게시글 목록을 가지는 VO객체 선언
		BoardListVO boardListVO = new BoardListVO();
		// 게시글 총 건수 조회
		boardListVO.setBoardCnt(boardDao.selectBoardAllCount());
		// 게시글 목록 조회
		boardListVO.setBoardList(boardDao.selectAllBoard());
		return boardListVO;
	}

	@Override
	public boolean insertNewBoard(BoardVO boardVO) {
		// DB에 게시글 등록
		// createCount에는 DB에 등록한 게시글의 개수를 반환한다.
		int createCount = boardDao.insertNewBoard(boardVO);
		// DB에 등록한 개수가 0보다 크다면 성공, 아니면 실패.
		return createCount > 0;
	}

	@Override
	public BoardVO selectOneBoard(int id, boolean isIncrease) {
		if (isIncrease) {
			// 파리미터로 전달받은 게시글의 조회 수 증가
			// updateCount에는 DB에 업데이트한 게시글의 수를 반환
			int updateCount = boardDao.increaseViewCount(id);
			if (updateCount == 0) {
				// updateCount가 0이라는 것은 파라미터로 전달받은 id값이 DB에는 존재하지 않는다는 의미이다.
				// 이 경우, 잘못된 접근입니다. 라고 사용자에게 예외 메시지를 보내준다.
				throw new IllegalArgumentException("잘못된 접근입니다.");
			}
		}
		// 예외가 발생하지 않았다면, 게시글의 정보를 조회한다.
		BoardVO boardVO = boardDao.selectOneBoard(id);
		if (boardVO == null) {
			// 파라미터로 받은 id값이 DB에 존재하지 않을 경우
			// 잘못된 접근입니다. 라고 사용자에게 예외 메시지를 보낸다.
			throw new IllegalArgumentException("잘못된 접근입니다.");
		}
		return boardVO;
	}

	@Override
	public boolean updateOneBoard(BoardVO boardVO) {
		// 파라미터로 전달받은 수정된 게시글의 정보를 DB에 수정한다.
		// updateCount에는 DB에 업데이트한 게시글의 수
		int updateCount = boardDao.updateOneBoard(boardVO);
		return updateCount > 0;
	}

}
