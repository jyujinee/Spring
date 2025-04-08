package com.hello.replies.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.exceptions.AjaxException;
import com.hello.replies.dao.ReplyDao;
import com.hello.replies.service.ReplyService;
import com.hello.replies.vo.ReplyDeleteRequestVO;
import com.hello.replies.vo.ReplyRecommendRequestVO;
import com.hello.replies.vo.ReplyRegistRequestVO;
import com.hello.replies.vo.ReplyUpdateRequestVO;
import com.hello.replies.vo.ReplyVO;



@Service
public class ReplyServiceImpl implements ReplyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyServiceImpl.class);
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
		
		/* Null 체크를 해야하는 이유?
		 * 닌텐도가 특가할인해서 50%이상 할인한다.
		 * 5월 5일 오전 10시부터 시작! 재고된 재고 수는 100개 한정!
		 * 9시 59분 59초에 구매 버튼 클릭 -> 서버 지연 시간 때문이다.
		 * 
		 * 결제 버튼 클릭!
		 * 결제 비밀 번호를 한번 틀림. -> 두번 틀림 -> 다시 입력하고 결제 성공?
		 * 품절됨!
		 * 
		 * 이런 것처럼, 다시 입력했는데 이미 삭제된 댓글에 댓글을 달면 에러가 나야하기 때문이다.
		 *
		*/ 
		ReplyVO replyVO = this.replyDao.selectOneReply(replyDeleteRequestVO.getReplyId());
		
		// 사용자에게 무엇이 잘못되었는지 상세하게 알려주기 위해, Exception을 여러 개 던진다.
		if(replyVO == null) {
//			Logger.debung("안되는 이유:")
			throw new AjaxException("잘못된 댓글 번호 입니다.");
		}
		
		// 내가 쓴건지 먼저 검사를 한다.
		if( !replyVO.getEmail().equals(replyDeleteRequestVO.getEmail()) ) {
			throw new AjaxException("내가 작성한 댓글이 아닙니다. 삭제할 수 없는 댓글 입니다.");
		}
		
		// 삭제가 안됨 ( 이 예외 하나면 모두 처리가 되긴 함)
		if(deleteCount == 0) {
			// TO DO: Ajax 전용 Exception이 필요하다!
			throw new AjaxException("잘못된 요청입니다.");
		}
		
		return deleteCount > 0;
	}
	
	@Transactional
	@Override
	public boolean modifyOneReply(ReplyUpdateRequestVO replyUpdateRequestVO) {
		
		ReplyVO replyVO = this.replyDao.selectOneReply(replyUpdateRequestVO.getReplyId());
		
		if(replyVO == null) {
			throw new AjaxException("잘못된 댓글 번호입니다.");
		}
		
		if( !replyVO.getEmail().equals(replyUpdateRequestVO.getEmail()) ) {
			throw new AjaxException("내가 작성한 댓글이 아닙니다. 수정할 수 없는 댓글입니다.");
		}
		
		int updateCount = this.replyDao.updateOneReply(replyUpdateRequestVO);
		
		if(updateCount == 0) {
			throw new AjaxException("잘못된 요청입니다.");
		}
		return updateCount > 0;
	}
	
	@Transactional
	@Override
	public int recommendOneReply(ReplyRecommendRequestVO replyRecommendRequestVO) {
		int updateCount = this.replyDao.updateRecommendOneReply(replyRecommendRequestVO);
		
		ReplyVO replyVO = this.replyDao.selectOneReply(replyRecommendRequestVO.getReplyId());
		
		if(replyVO == null) {
			throw new AjaxException("잘못된 댓글 번호입니다.");
		}
		// 내가 작성한 댓글은 내가 추천할 수 없다
		if( replyVO.getEmail().equals(replyRecommendRequestVO.getEmail()) ) {
			throw new AjaxException("내가 작성한 댓글은 추천할 수 없습니다.");
		}
		if(updateCount == 0) {
			// TO DO: Ajax 전용 Exception이 필요하다!
			throw new AjaxException("잘못된 요청입니다.");
		}
		return this.replyDao
					.selectOneReply(replyRecommendRequestVO.getReplyId()).getRecommendCnt();
	}

}