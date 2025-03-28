package com.ktdsuniversity.edu.jyj.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.jyj.dao.BoardDao;
import com.ktdsuniversity.edu.jyj.vo.BoardListVO;

public class BoardServiceImpl implements BoardService{

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

}
