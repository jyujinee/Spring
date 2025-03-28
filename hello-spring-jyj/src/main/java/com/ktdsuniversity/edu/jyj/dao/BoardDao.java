package com.ktdsuniversity.edu.jyj.dao;

import java.util.List;

import com.ktdsuniversity.edu.jyj.vo.BoardVO;

public interface BoardDao {

//	DB에 저장된 모든 게시글의 수를 조회
	public int selectBoardAllCount();
	
//	DB에 저장된 모든 게시글의 목록을 조회
	public List<BoardVO> selectAllBoard();
}
