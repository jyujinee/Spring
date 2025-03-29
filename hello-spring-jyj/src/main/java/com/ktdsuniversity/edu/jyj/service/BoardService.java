package com.ktdsuniversity.edu.jyj.service;

import com.ktdsuniversity.edu.jyj.vo.BoardListVO;
import com.ktdsuniversity.edu.jyj.vo.BoardVO;

public interface BoardService {

	// 게시글의 목록과 게시글의 건수를 모두 조회한다.
	public BoardListVO selectAllBoard();
	
	/*
	 * 새로운 게시글 등록을 처리한다.
	 * @param boardVO 사용자가 입력한 게시글 정보
	 * @return 정상적으로 등록되었는지 여부
	 */
	public boolean insertNewBoard(BoardVO boardVO);
	
	/*
	 * 파라미터로 전달받은 id로 게시글을 조회한다.
	 * 게시글 조회시 조회수도 1 증가한다.
	 * @param id 조회할 게시글의 ID
	 * @return 게시글 정보
	 */
	public BoardVO selectOneBoard(int id);
}
