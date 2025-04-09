package com.hello.bbs.service;

import com.hello.bbs.vo.BoardDeleteRequestVO;
import com.hello.bbs.vo.BoardListVO;
import com.hello.bbs.vo.BoardSearchRequestVO;
import com.hello.bbs.vo.BoardUpdateRequestVO;
import com.hello.bbs.vo.BoardVO;
import com.hello.bbs.vo.BoardWriteRequestVO;

public interface BoardService {
	
	// 게시글 목록 조회
	public BoardListVO getBoardList(BoardSearchRequestVO boardSearchRequestVO);

//	Dao는 몇개를 insert 했는지 반환하는 int를 보고, Service는 boolean
//	서비스 제공에 성공했으면 true, 아니면 false
	public boolean createNewBoard(BoardWriteRequestVO boardWriteRequestVO);	
	
//	public BoardVO getOneBoard(int id);
	public BoardVO getOneBoard(int id, boolean isIncrease);

	public boolean deleteOneBoard(BoardDeleteRequestVO boardDeleteRequestVO);

	public boolean updateOneBoard(BoardUpdateRequestVO boardUpdateRequestVO);

}