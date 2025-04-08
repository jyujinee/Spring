package com.hello.replies.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/ajax") // 공통으로 end-point앞에 ajax임을 붙여서, Ajax명시하기 위해 사용
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
    		
    		// 에러가 존재할 때만 
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
    		
    		// 각 에러마다 에러 메시지를 넣어주기 위해 Map을 이용한다.
    		Map<String, String> errorMap = new HashMap<>();
    		
    		for(ObjectError error : allErrors) {
    			// 댓글의 내용을 작성하세요.
    			String message = error.getDefaultMessage();
    			String fieldName="";
    			
    			// content가 fieldName에 들어간다.
    			if(error instanceof FieldError fieldError) {
    				fieldName = fieldError.getField();
    			}
    			// 맵에 데이터를 넣어준다.
    			errorMap.put(fieldName, message);
    		}
    		
    		return new AjaxResponse(HttpStatus.BAD_REQUEST.value(), errorMap);
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
    									@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	
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
    		
    		// 에러가 존재할 때만 
    		List<ObjectError> allErrors = bindingResult.getAllErrors();
    		
    		// 각 에러마다 에러 메시지를 넣어주기 위해 Map을 이용한다.
    		Map<String, String> errorMap = new HashMap<>();
    		
    		for(ObjectError error : allErrors) {
    			// 댓글의 내용을 작성하세요.
    			String message = error.getDefaultMessage();
    			String fieldName="";
    			
    			// content가 fieldName에 들어간다.
    			if(error instanceof FieldError fieldError) {
    				fieldName = fieldError.getField();
    			}
    			// 맵에 데이터를 넣어준다.
    			errorMap.put(fieldName, message);
    		}
    		
    		return new AjaxResponse(HttpStatus.BAD_REQUEST.value(), errorMap);
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
    	
    	int recommendCnt = this.replyService.recommendOneReply(recommendRequestVO);
    	
    	return new AjaxResponse(recommendCnt);
    	
    }
    

}