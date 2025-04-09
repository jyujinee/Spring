package com.hello.bbs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hello.bbs.service.BoardService;
import com.hello.bbs.vo.BoardDeleteRequestVO;
import com.hello.bbs.vo.BoardListVO;
import com.hello.bbs.vo.BoardSearchRequestVO;
import com.hello.bbs.vo.BoardUpdateRequestVO;
import com.hello.bbs.vo.BoardVO;
import com.hello.bbs.vo.BoardWriteRequestVO;
import com.hello.member.vo.MembersVO;

import jakarta.validation.Valid;

@Controller
public class BoardController {
	
	// 로그를 쓰기 위한 설정
	// Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardService boardService;
    
    // localhost:8080/board/list?pageNo=0&listSize=20
    // 0번 페이지를 보여달라. 그 페이지에는 20개의 게시글을 보여달라는 의미
    @GetMapping("/board/list") // 페이지 주소(URL)에 접속하면, 아래의 메소드에 맵핑해준다.
    public String viewBoardList(Model model 	// 데이터를 전송해주는 모델
    							,BoardSearchRequestVO boardSearchRequestVO) { 
    	
    	// 로그 출력
    	LOGGER.trace("/board/list 를 방문했습니다.");
    	LOGGER.debug("/board/list 를 방문했습니다.");
    	LOGGER.info("/board/list 를 방문했습니다.");
    	LOGGER.warn("/board/list 를 방문했습니다.");
    	LOGGER.error("/board/list 를 방문했습니다.");
    	
    	BoardListVO boardListVO = this.boardService.getBoardList(boardSearchRequestVO);
    	model.addAttribute("boardList",boardListVO);
    	// 총 페이지의 수, 현재 페이지 번호를 알 수 있는 boardSearchRequestVO를 model에 넣어서 알려줌.
    	model.addAttribute("pagination", boardSearchRequestVO); 
    	return "board/boardlist"; // 화면에 보이는 뷰(파일)네임
    }
    
    // 게시글 등록
    @GetMapping("/board/write")
    public String viewBoardWritePage() {
    	return "board/boardwrite";
    }
    
    // 게시글 등록과 같은 URL이지만, 맵핑되는 메소드는 다르다.
    // 브라우저가 서버에게 보내는 데이터를 받아온다 -> Controller의 파라미터 사용
    @PostMapping("/board/write")
    public String doBoardWrite(@Valid @ModelAttribute BoardWriteRequestVO boardWriteRequestVO,
    							BindingResult bindingResult,
    							Model model, @SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	// @Valid는 파라미터 입력값 검사를 요청한다, 바로 BindingResult가 와야하며
    	// BindingResult는 @Valid의 검사 결과를 받아온다.
    	// @ModelAttribute 스프링 form taglib를 사용할 떄만 작성
    	
    	
    	// 에러가 있을 때 (유효성 검사의 에러) 게시글을 등록하면 안된다.
    	// 사용자에게 잘못 입력했음을 알려줘야한다.
    	// 사용자가 입력했던 모든 내용들을 글쓰기 페이지로 다 보내줘야 한다.
    	// 에러의 내용도 보내줘야한다. --> 자동 전송됨
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("userWriteBoard", boardWriteRequestVO);
    		return "board/boardwrite";
    	}
//    	System.out.println(bindingResult.hasErrors());
    	boardWriteRequestVO.setEmail(memberVO.getEmail());
    	
    	boolean isCreated = this.boardService.createNewBoard(boardWriteRequestVO); //setter
    	
    	if(isCreated) {
    		/*
    		 * HTTP Status Code: 302 Found
    		 * Location: http://localhost:8080/board/list
    		 * 브라우저는 location주소를 받은 즉시 다른 URL로 이동한다. -> 해당 URL의 Mapping된 메소드로 다시 동작한다.
    		 */
    		return "redirect:/board/list"; // @GetMapping("/board/list")의 메소드 실행됨!
    	}
    	// 절대 실행되지 않는 케이스 -> else
    		return "board/boardwrite";
    }
    
    // 파라미터를 전달받는 방법
    // 1. Query string parameter: /board/view?id=3 (물음표를 이용해서 파라미터를 전달하는 방법)
    @GetMapping("/board/view")
    public String viewBoardDetailPageUseQueryStringParameter(@RequestParam int id, Model model) { 
    	// 파라미터가 하나밖에 없어서 (BoardVO boardVO)대신 int로 받음 
    	// Model은 jsp에게 데이터를 전송하기 위해서 사용한다. -> $ {}에 들어가는 값은 model의 key값이다.
    	// 따라서 Model에서 받은 데이터를 가진 새로운 화면으로 이동한다.
    	BoardVO boardVO = this.boardService.getOneBoard(id, true);
    	model.addAttribute("boardVO", boardVO);
    	return "board/boardview";
    }
    // 2. Path Variable Parameter: /board/view/3 (url 자체를 파라미터로 전달하는 방법)
    @GetMapping("/board/view/{id}")
    public String viewBoardDetailPageUsePathVariableParameter(@PathVariable int id, Model model) {
    	BoardVO boardVO = this.boardService.getOneBoard(id, true);
    	model.addAttribute("boardVO", boardVO);
    	return "board/boardview";
    }
    
    @GetMapping("/board/delete/{id}")
    public String doDeleteOneBoard(@PathVariable int id,
    								@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	
    	BoardDeleteRequestVO boardDeleteRequestVO = new BoardDeleteRequestVO();
    	boardDeleteRequestVO.setId(id);
    	boardDeleteRequestVO.setEmail(memberVO.getEmail());
    	
    	boolean isSuccess = this.boardService.deleteOneBoard(boardDeleteRequestVO);
    	
    	if(isSuccess) {
    		return "redirect:/board/list";
    	}
    	
    	// 절대 실행될 수 없는 코드
    	return "redirect:/board/view/" + id;
    }
    
   
    // 수정된 게시글을 보여준다 -> View가 필요하기 때문에 model이 파라미터로 들어간다.
    @GetMapping("/board/modify/{id}")
    public String viewBoardUpdatePage(@PathVariable int id, Model model,
    									@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	
    	// 수정된 게시글을 보여주기 때문에 조회수 증가를 방지하기 위해 false를 준다.
    	BoardVO boardVO = this.boardService.getOneBoard(id, false);
    	
    	// 같은 회원이 아니라면 modify 뷰가 보이면 안된다.
    	if( !boardVO.getEmail().equals(memberVO.getEmail())) {
    		return "redirect:/board/list";
    	}
    	
    	model.addAttribute("boardVO", boardVO);
    	return "board/boardmodify";
    }
    
    // 게시글을 수정해서 DB에 데이터를 전송한다.
    @PostMapping("/board/modify/{id}")
    public String doUpdateOneBoard(@PathVariable int id, BoardUpdateRequestVO boardUpdateRequestVO,
    								@SessionAttribute("__LOGIN_USER__") MembersVO memberVO) {
    	
    	// DB에서도 내가 쓴 글이 아니라면 수정하지 못하도록 한다.
    	boardUpdateRequestVO.setEmail(memberVO.getEmail());

    	boolean isSuccess = this.boardService.updateOneBoard(boardUpdateRequestVO);
    	
    	if(isSuccess) {
    		return "redirect:/board/view/" + id;
    	}
    	
    	return "redirect:/board/list";
    }

}