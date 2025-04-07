package com.hello.replies.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hello.common.vo.AjaxResponse;
import com.hello.member.vo.MembersVO;
import com.hello.replies.service.ReplyService;
import com.hello.replies.vo.ReplyDeleteRequestVO;
import com.hello.replies.vo.ReplyRecommendRequestVO;
import com.hello.replies.vo.ReplyRegistRequestVO;
import com.hello.replies.vo.ReplyUpdateRequestVO;
import com.hello.replies.vo.ReplyVO;

import jakarta.validation.Valid;


//@Controller
//@ResponseBody // 클래스에 모든 메소드의 응답 데이터가 전부 다 jason이다. => 이 경우에 클래스 위에 붙인다.
// 위에 @Controller + @ResponseBody를 합쳐 한번에 작성하는 방법은 @RestController 이다.
@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    
    // 모든 메소드는 ajax니깐 데이터만 주면 돼서 view를 쓰지 않는다!
    
    // http://localhost:8080/reply/{boardId}
    @GetMapping("/reply/{boardId}")
    public AjaxResponse getAllReplies(@PathVariable int boardId) {
    	
    	List<ReplyVO> replyList = this.replyService.getAllReplies(boardId);
    	
    	return new AjaxResponse(replyList);
    }
    
    @PostMapping("/reply/{boardId}")
    public AjaxResponse doCreateNewReply(@Valid ReplyRegistRequestVO replyRegistRequestVO
    										, BindingResult bindingResult
    										, @PathVariable int boardId,
    										@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	// JSP:<form:form> 폼 기반 전송을 하지 않으면 Model이 필요하지 않다!
    	
    	if(bindingResult.hasErrors()) {
    		// TO-DO: Validation Check 결과를 JSON으로 돌려주기
    	
    		// 400 번 반환(실패)
    	return new AjaxResponse(HttpStatus.BAD_REQUEST.value(), null);
    	}
    	
    	replyRegistRequestVO.setBoardId(boardId);
    	replyRegistRequestVO.setEmail(memberVO.getEmail());
    	
    	boolean isCreate = this.replyService.createNewReply(replyRegistRequestVO);
    	
    	// 200 번 반환(성공)
    	return new AjaxResponse(HttpStatus.OK.value(), isCreate);
    }
    
    // http://localhost:8080/reply/delete/{boardId}/{replyId}
    // @DeleteMapping 을 지원하지 않아서 GetMapping을 사용하는데, 이러면 end-point가 같아서 에러가 남
    // 따라서 중간 url에 명시해줘야한다.
    @GetMapping("/reply/delete/{boardId}/{replyId}")
    public AjaxResponse doDeleteOneReply(@PathVariable int boardId,
    									@PathVariable int replyId,
    									@SessionAttribute("__USER_LOGIN__") MembersVO memberVO) {
    	
    	ReplyDeleteRequestVO replyDeleteRequestVO = new ReplyDeleteRequestVO();
    	replyDeleteRequestVO.setBoardId(boardId);
    	replyDeleteRequestVO.setReplyId(replyId);
    	replyDeleteRequestVO.setEmail(memberVO.getEmail());
    	
    	boolean isDelete = this.replyService.deleteOneReply(replyDeleteRequestVO);
    	
    	return new AjaxResponse(isDelete);
    }
    
    // 데이터를 받아오니깐 PostMapping
    @PostMapping("/reply/modify/{boardId}/{replyId}")
    public AjaxResponse doModifyOneReply(@PathVariable int boardId,
    									@PathVariable int replyId,
    									@Valid ReplyUpdateRequestVO replyUpdateRequestVO,
    									BindingResult bindingResult,
    									@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	if(bindingResult.hasErrors()) {
    		return new AjaxResponse(HttpStatus.BAD_REQUEST.value(), null);
    	}
    	
    	replyUpdateRequestVO.setBoardId(boardId);
    	replyUpdateRequestVO.setReplyId(replyId);
    	replyUpdateRequestVO.setEmail(memberVO.getEmail());
    	
    	boolean isUpdate = this.replyService.modifyOneReply(replyUpdateRequestVO);
    	
    	return new AjaxResponse(isUpdate);
    }
    
    @GetMapping("/reply/recommend/{boardId}/{replyId}")
    public AjaxResponse doRecommendOneReply(@PathVariable int boardId,
											@PathVariable int replyId,
											@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	
    	ReplyRecommendRequestVO recommendRequestVO = new ReplyRecommendRequestVO();
    	
    	recommendRequestVO.setBoardId(boardId);
    	recommendRequestVO.setReplyId(replyId);
    	recommendRequestVO.setEmail(memberVO.getEmail());
    	
    	boolean isRecommend = this.replyService.recommendOneReply(recommendRequestVO);
    	
    	return new AjaxResponse(isRecommend);
    	
    }
    

}