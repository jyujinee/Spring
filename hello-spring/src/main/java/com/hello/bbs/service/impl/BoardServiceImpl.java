package com.hello.bbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.service.BoardService;
import com.hello.bbs.vo.BoardListVO;
import com.hello.bbs.vo.BoardUpdateRequestVO;
import com.hello.bbs.vo.BoardVO;
import com.hello.bbs.vo.BoardWriteRequestVO;

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
	
	@Override
	public boolean createNewBoard(BoardWriteRequestVO boardWriteRequestVO) {
		return this.boardDao.insertNewBoard(boardWriteRequestVO) > 0;
	}

	@Override
	public BoardVO getOneBoard(int id, boolean isIncrease) {
		
		if(isIncrease) {
			// 1. 조회하려는 게시글의 조회수를 증가시킨다.
			int updatedCount = this.boardDao.updateViewCountBy(id);
			
			// 2. 업데이트의 수가 0보다 크다면 게시글이 존재한다는 의미이므로, 게시글을 조회 해온다.
			if(updatedCount > 0) {
				BoardVO boardVO = this.boardDao.selectOneBoard(id);
				// 게시글 반환.
				return boardVO;
			}
//			return null;
			throw new IllegalArgumentException(id + "는 존재하지 않는 게시글 번호입니다.");		
		}
		// 게시글의 조회 수를 증가 시키지 않고, 화면(게시글)을 보여준다.
		BoardVO boardVO = this.boardDao.selectOneBoard(id);
		if (boardVO == null) {
			throw new IllegalArgumentException(id + "는 존재하지 않는 게시글 번호입니다.");
		}// 여기 나중에 보기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return boardVO;
	}

	@Override
	public boolean deleteOneBoard(int id) {
		int deleteCount = this.boardDao.deleteOneBoard(id);
		if(deleteCount == 0) {
			throw new IllegalArgumentException(id + "는 존재하지 않는 게시글 번호입니다.");
		}
		return deleteCount > 0;
	}

	@Override
	public boolean updateOneBoard(BoardUpdateRequestVO boardUpdateRequestVO) {
		int updateCount = this.boardDao.updateOneBoard(boardUpdateRequestVO);
		// 업데이트의 수가 0보다 크면 게시글이 수정되었다는 의미이므로, true를 반환한다.
		return updateCount > 0;
	}

}