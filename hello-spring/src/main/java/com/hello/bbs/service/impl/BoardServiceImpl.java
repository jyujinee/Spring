package com.hello.bbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.service.BoardService;
import com.hello.bbs.vo.BoardListVO;
import com.hello.bbs.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

	@Override
	public BoardListVO getBoardList() {
		int count = this.boardDao.selectBoardAllCount();
		List<BoardVO> boardList = this.boardDao.selectAllBoard();
		
		BoardListVO boardListVO = new BoardListVO();
		boardListVO.setBoardCnt(count);
		boardListVO.setBoardList(boardList);
		return boardListVO;
	}

}