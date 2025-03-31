package com.hello.bbs.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.vo.BoardVO;

@Repository
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao{
	private static final String NAME_SPACE = "com.hello.bbs.dao.impl.BoardDaoImpl.";
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int selectBoardAllCount() {
		return getSqlSession().selectOne(NAME_SPACE + "selectBoardAllCount");
	}

	@Override
	public List<BoardVO> selectAllBoard() {
		return getSqlSession().selectList(NAME_SPACE + "selectAllBoard");
	}
	
	@Override
	public int insertNewBoard(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int increaseViewCount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoardVO selectOneBoard(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateOneBoard(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteOneBoard(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}