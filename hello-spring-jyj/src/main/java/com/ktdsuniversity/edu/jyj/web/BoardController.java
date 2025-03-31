package com.ktdsuniversity.edu.jyj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	// 게시글 작성
	@PostMapping("/hello-jyj/board/write")
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
		if (isSuccess) {
			// 게시글 등록 결과가 성공이라면 /board/list URL로 이동한다.
			modelAndView.setViewName("redirect:/board/list");
			return modelAndView;
		} else {
			// 게시글 등록 결과가 실패라면
			// 게시글 등록(작성) 화면으로 데이터를 보내준다.
			// 게시글 등록(작성) 화면에서 board VO값으로 등록 값을 설정해야한다.
			modelAndView.setViewName("board/boardwrite");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;
		}
	}

	// 게시글 조회
	@GetMapping("/hello-jyj/board/view") // Query String Parameter
	public ModelAndView viewOneBoard(@RequestParam int id) { // URL로 전달한 파라미터를 받아올 수 있는 방법
		// 게시글 id로 게시글 하나 조회하기
		BoardVO boardVO = boardService.selectOneBoard(id, true);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardview");
		modelAndView.addObject("boardVO", boardVO);
		return modelAndView;
	}

	@GetMapping("/hello-jyj/board/modify/{id}") // URL 일부분을 파라미터 변수로 사용하는 방법
	public ModelAndView viewBoardModifyPage(@PathVariable int id) {
		// 게시글 수정을 위해 게시글의 내용을 조회한다.
		// 게시글 조회와 동일한 코드 호출
		BoardVO boardVO = boardService.selectOneBoard(id, false);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("board/boardmodify");
		modelAndView.addObject("boardVO", boardVO);
		return modelAndView;
	}

	// 게시글 수정 요청 처리
	@PostMapping("/hello-jyj/board/modify")
	public ModelAndView doBoardUpdate(@ModelAttribute BoardVO boardVO) {
		System.out.println("ID: " + boardVO.getId());
		System.out.println("제목: " + boardVO.getSubject());
		System.out.println("이메일: " + boardVO.getEmail());
		System.out.println("내용: " + boardVO.getContent());
		System.out.println("등록일: " + boardVO.getCrtDt());
		System.out.println("수정일: " + boardVO.getMdfyDt());
		System.out.println("FileName: " + boardVO.getFileName());
		System.out.println("Origin FileName: " + boardVO.getOriginFileName());

		ModelAndView modelAndView = new ModelAndView();
		// 게시글 수정
		boolean isSuccess = boardService.updateOneBoard(boardVO);
		if (isSuccess) {
			// 게시글 수정 결과가 성공이면, /board/view=?id=id URL로 이동한다.
			modelAndView.setViewName("redirect:/board/view=?id=" + boardVO.getId());
			return modelAndView;
		} else {
			// 실패라면, 게시글 수정화면으로 데이터를 보내준다.
			modelAndView.setViewName("board/boardmodify");
			modelAndView.addObject("boardVO", boardVO);
			return modelAndView;
		}
	}
	
	@GetMapping("/hello-jyj/board/delete/{id}")
	public String doDeleteBoard(@PathVariable int id) {
		boolean isSuccess = boardService.deleteOneBoard(id);
		if(isSuccess) {
			return "redirect:/board/list";
		}
		else {
			return "redirect:/board/view?id=" + id;
		}
	}
}