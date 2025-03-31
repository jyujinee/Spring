package com.hello.bbs.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

}