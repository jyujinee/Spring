package com.hello.replies.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.exceptions.PageNotFoundException;
import com.hello.replies.dao.ReplyDao;
import com.hello.replies.service.ReplyService;
import com.hello.replies.vo.ReplyDeleteRequestVO;
import com.hello.replies.vo.ReplyRecommendRequestVO;
import com.hello.replies.vo.ReplyRegistRequestVO;
import com.hello.replies.vo.ReplyUpdateRequestVO;
import com.hello.replies.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao replyDao;

    // select만 수행하는 경우 (readOnly = true)를 붙인다. update, delete, insert는 에러발생
	@Transactional(readOnly = true)
	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return this.replyDao.selectAllReplies(boardId);
	}

	@Transactional
	@Override
	public boolean createNewReply(ReplyRegistRequestVO replyRegistRequestVO) {
		return this.replyDao.insertNewReply(replyRegistRequestVO) > 0;
	}

	@Transactional
	@Override
	public boolean deleteOneReply(ReplyDeleteRequestVO replyDeleteRequestVO) {
		int deleteCount = this.replyDao.deleteOneReply(replyDeleteRequestVO);
		
		if(deleteCount == 0) {
			
			// TO DO: Ajax 전용 Exception이 필요하다!
			throw new PageNotFoundException(replyDeleteRequestVO.getBoardId());
		}
		
		return deleteCount > 0;
	}
	
	@Transactional
	@Override
	public boolean modifyOneReply(ReplyUpdateRequestVO replyUpdateRequestVO) {
		int updateCount = this.replyDao.updateOneReply(replyUpdateRequestVO);
		
		if(updateCount == 0) {
			// TO DO: Ajax 전용 Exception이 필요하다!
			throw new PageNotFoundException(replyUpdateRequestVO.getBoardId());
		}
		return updateCount > 0;
	}
	
	@Transactional
	@Override
	public boolean recommendOneReply(ReplyRecommendRequestVO replyRecommendRequestVO) {
		int updateCount = this.replyDao.updateRecommendOneReply(replyRecommendRequestVO);
		
		if(updateCount == 0) {
			// TO DO: Ajax 전용 Exception이 필요하다!
			throw new PageNotFoundException(replyRecommendRequestVO.getBoardId());
		}
		return updateCount > 0;
	}

}