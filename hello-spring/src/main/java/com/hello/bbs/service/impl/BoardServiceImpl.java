package com.hello.bbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hello.bbs.dao.BoardDao;
import com.hello.bbs.service.BoardService;
import com.hello.bbs.vo.BoardDeleteRequestVO;
import com.hello.bbs.vo.BoardListVO;
import com.hello.bbs.vo.BoardSearchRequestVO;
import com.hello.bbs.vo.BoardUpdateRequestVO;
import com.hello.bbs.vo.BoardVO;
import com.hello.bbs.vo.BoardWriteRequestVO;
import com.hello.beans.FileHandler;
import com.hello.beans.FileHandler.StoredFile;
import com.hello.exceptions.PageNotFoundException;
import com.hello.file.dao.FileDao;
import com.hello.file.vo.FileVO;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private FileHandler fileHandler; // Bean Container에 있는 fileHandler 인스턴스가 들어온다.
    @Autowired
    private FileDao fileDao;
    
    // select만 수행하는 경우 (readOnly = true)를 붙인다. update, delete, insert는 에러발생
    @Transactional(readOnly = true)
	@Override
	public BoardListVO getBoardList(BoardSearchRequestVO boardSearchRequestVO) {
		int count = this.boardDao.selectBoardAllCount(boardSearchRequestVO);
		// 총 게시글의 수를 가져와서 넣어준다.
		boardSearchRequestVO.setPageCount(count);
		
		List<BoardVO> boardList = this.boardDao.selectAllBoard(boardSearchRequestVO);
		
		BoardListVO boardListVO = new BoardListVO();
		boardListVO.setBoardCnt(count);
		boardListVO.setBoardList(boardList);
		return boardListVO;
	}
	
    // transactional: insert수행 중 예외가 발생하면, 자동으로 롤백함
    @Transactional
	@Override
	public boolean createNewBoard(BoardWriteRequestVO boardWriteRequestVO) {
		
		int insertCount = this.boardDao.insertNewBoard(boardWriteRequestVO);
		System.out.println(insertCount);
		// if insertCount > 0 then file upload
		if(insertCount > 0) {
			
			for(MultipartFile file: boardWriteRequestVO.getFile()) {
				System.out.println(boardWriteRequestVO.getFile());
				StoredFile storedFile = this.fileHandler.store(file);
				// NullpointerException을 확인해야한다. 
				// storedFile null이 되는 경우를 방어 코딩 해야한다.
				if(storedFile != null) {
					// 파일 업로드가 정상적으로 이루어졌다.
					// 파일 테이블에 필요한 파일 데이터를 추가한다.
					// 게시글 아이디를 미리 발급받아서 insert한다. => mybatis에서 지원
//					System.out.println("새로운 게시글의 아이디는 " + boardWriteRequestVO.getId() + "입니다.");
//					System.out.println(storedFile.getFileName());
//					System.out.println(storedFile.getFileSize());
//					System.out.println(storedFile.getRealFileName());
//					System.out.println(storedFile.getRealFilePath());
					
					FileVO fileVO = new FileVO();
					fileVO.setId(boardWriteRequestVO.getId());
					fileVO.setFlNm(storedFile.getFileName());
					fileVO.setObfsFlNm(storedFile.getRealFileName());
					fileVO.setObfsPth(storedFile.getRealFilePath());
					fileVO.setFlSz(storedFile.getFileSize());
					
					// 데이터 베이스에 파일 테이블에 파일 정보를 저장한다(insert).
					this.fileDao.insertNewFile(fileVO);
				}
			}
		}
		return insertCount > 0;
	}
    
    @Transactional
	@Override
	public BoardVO getOneBoard(int id, boolean isIncrease) {
		
		if(isIncrease) {
			// 1. 조회하려는 게시글의 조회수를 증가시킨다.
			int updatedCount = this.boardDao.updateViewCountBy(id);
			
			// 2. 업데이트의 수가 0보다 크다면 게시글이 존재한다는 의미이므로, 게시글을 조회 해온다.
//			if(updatedCount > 0) {
//				BoardVO boardVO = this.boardDao.selectOneBoard(id);
//				// 게시글 반환.
//				return boardVO;
//			}
			
			// 게시글 조회수 증가 방지
			if(updatedCount == 0) {
//				throw new IllegalArgumentException(id + "는 존재하지 않는 게시글 번호입니다.");		
				throw new PageNotFoundException(id);
			}
		}
		
		
		// 게시글의 조회 수를 증가 시키지 않고, 화면(게시글)을 보여준다.
		BoardVO boardVO = this.boardDao.selectOneBoard(id);
		
		if (boardVO == null) {
//			throw new IllegalArgumentException(id + "는 존재하지 않는 게시글 번호입니다.");
			throw new PageNotFoundException(id);
		}
		// 게시글 반환.
		return boardVO;
	}

    @Transactional
	@Override
	public boolean deleteOneBoard(BoardDeleteRequestVO boardDeleteRequestVO) {
		int deleteCount = this.boardDao.deleteOneBoard(boardDeleteRequestVO);
		if(deleteCount == 0) {
//			throw new IllegalArgumentException(boardDeleteRequestVO.getId() + "는 존재하지 않는 게시글 번호입니다.");
			throw new PageNotFoundException(boardDeleteRequestVO.getId());
		}
		return deleteCount > 0;
	}

    @Transactional
	@Override
	public boolean updateOneBoard(BoardUpdateRequestVO boardUpdateRequestVO) {
		int updateCount = this.boardDao.updateOneBoard(boardUpdateRequestVO);
		// 업데이트의 수가 0보다 크면 게시글이 수정되었다는 의미이므로, true를 반환한다.
		return updateCount > 0;
	}

}