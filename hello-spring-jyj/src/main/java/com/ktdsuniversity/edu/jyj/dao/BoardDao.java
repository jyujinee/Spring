package com.ktdsuniversity.edu.jyj.dao;

import java.util.List;

import com.ktdsuniversity.edu.jyj.vo.BoardVO;

public interface BoardDao {

	//	DB에 저장된 모든 게시글의 수를 조회
	public int selectBoardAllCount();
	
	//	DB에 저장된 모든 게시글의 목록을 조회
	public List<BoardVO> selectAllBoard();
	
	/*	게시글 작성을 위한 메소드 정의
	 *  DB에 새로운 게시글을 등록한다
	 *  @param boardVO 사용자가 입력한 게시글의 정보
	 *  @return DB에 Insert한 개수
	 */
	public int insertNewBoard(BoardVO boardVO);
	
	/*
	 * 파라미터로 전달받은 게시글 ID의 조회 수를 1증가 시킨다.
	 * @param id 게시글 ID(번호)
	 * @return DB에 Update한 개수
	 */
	public int increaseViewCount(int id);
	
	/*
	 * 파라미터로 전달받은 게시글 ID의 게시글 정보를 조회한다.
	 * @param id 게시글 ID(번호)
	 * @return 해당 id의 게시글 정보
	 */
	public BoardVO selectOneBoard(int id);

	/*
	 * DB에 게시글의 정보를 수정한다.
	 * BoardVO의 id값에 수정할 게시글의 ID값이 있어야한다.
	 * @param boardVO 사용자가 수정한 게시글의 정보
	 * @return DB에 Update한 게시글의 수
	 */
	public int updateOneBoard(BoardVO boardVO);
	
	/*
	 * 파라미터로 전달받은 게시글 ID의 게시글을 삭제한다.
	 * @param id 게시글 ID
	 * @return DB에 delete한 게시글의 수 
	 */
	public int deleteOneBoard(int id);

}
