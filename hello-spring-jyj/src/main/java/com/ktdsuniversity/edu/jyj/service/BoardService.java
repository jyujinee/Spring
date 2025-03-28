package com.ktdsuniversity.edu.jyj.service;

import com.ktdsuniversity.edu.jyj.vo.BoardListVO;

public interface BoardService {

	// 게시글의 목록과 게시글의 건수를 모두 조회한다.
	public BoardListVO selectAllBoard();
}
