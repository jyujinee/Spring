package com.ktdsuniversity.edu.jyj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.jyj.service.BoardService;
import com.ktdsuniversity.edu.jyj.vo.BoardListVO;
import com.ktdsuniversity.edu.jyj.vo.BoardVO;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/hello-jyj/board/list")
	public ModelAndView viewBoardList() {
		BoardListVO boardListVO = boardService.selectAllBoard();
		
		// 게시글 목록을 보여주기 위한 View 작성
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardlist"); // 뷰의 이름은 뷰의 경로로 설정한다. @GetMapping과 달라도 됨.
		modelAndView.addObject("boardList", boardListVO);
		return modelAndView;
	}
	
	@GetMapping("/hello-jyj/board/write")
	public String viewBoardWritePage() {
		// 게시글 작성은 DB까지 갈 필요없이 화면만 보여주면 돼서 view로 반환한다.
		return "board/boardwrite";
	}
	
	@GetMapping("/hello-jyj/board/write")
	public ModelAndView doBoardWrite(BoardVO boardVO) {
		System.out.println("제목: " + boardVO.getSubject());
		System.out.println("이메일: " + boardVO.getEmail());
		System.out.println("내용: " + boardVO.getContent());
		System.out.println("등록일: " + boardVO.getCrtDt());
		System.out.println("수정일: " + boardVO.getMdfyDt());
		System.out.println("FileName: " + boardVO.getFileName());
		System.out.println("Origin FileName: " + boardVO.getOriginFileName());
		
		ModelAndView modelAndView = new ModelAndView();
		
		// 게시글 등록
		boolean isSuccess = boardService.insertNewBoard(boardVO);
		if(isSuccess) {
			// 게시글 등록 결과가 성공이라면 /board/list URL로 이동한다.
			modelAndView.setViewName("redirect:/board/list");
			return modelAndView;
		}
		else {
			// 게시글 등록 결과가 실패라면
			// 게시글 등록(작성) 화면으로 데이터를 보내준다.
			// 게시글 등록(작성) 화면에서 board VO값으로 등록 값을 설정해야한다.
			modelAndView.setViewName("board/boardwrite");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;
		}
	}
	
	// 게시글 조회
	@GetMapping("/hello-jyj/board/view")
	public ModelAndView viewOneBoard(@RequestParam int id) { // URL로 전달한 파라미터를 받아올 수 있는 방법
		// 게시글 id로 게시글 하나 조회하기
		BoardVO boardVO = boardService.selectOneBoard(id);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardview");
		modelAndView.addObject("boardVO", boardVO);
		return modelAndView;
	}
	
}
