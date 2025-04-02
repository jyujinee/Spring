package com.hello.file.dao.impl;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import com.hello.file.dao.FileDao;
import com.hello.file.vo.FileVO;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({FileDaoImpl.class})
public class FileDaoImplTest {
	
	@Autowired
	private FileDao fileDao;
	
	@Test
	public void insertTest() {
		FileVO fileVO = new FileVO();
		fileVO.setFlId(1);
		fileVO.setFlNm("dff");
		fileVO.setObfsFlNm("dfkhsfjdhkjfhk");
		fileVO.setObfsPth("sdajkjaskdhasklhdksajs");
		fileVO.setFlSz(100000);
		
	}
}
