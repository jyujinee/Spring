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
	 * @param isIncrease 값이 true면 조회수를 증가시킨다.(조회수 증가 방지)
	 * @return 게시글 정보
	 */
	public BoardVO selectOneBoard(int id, boolean isIncrease);
	
	/*
	 * DB에 게시글의 정보를 수정한다.
	 * BoardVO의 id값에 수정할 게시글의 ID값이 있어야한다.
	 * @param boardVO 사용자가 수정한 게시글의 정보
	 * @return DB에 정상적으로 수정되었는지 여부
	 */
	public boolean updateOneBoard(BoardVO boardVO);
	
	/*
	 * 파라미터로 전달받은 게시글 ID의 게시글을 삭제한다.
	 * @param id 게시글 ID
	 * @return 정상적으로 삭제되었는지 여부
	 */
	public boolean deleteOneBoard(int id);
}
