package com.hello.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hello.exceptions.NotExistsException;
import com.hello.file.service.FileService;
import com.hello.file.vo.FileDownloadRequestVO;
import com.hello.file.vo.FileVO;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    
    @GetMapping("/file/{id}/{flId}")
    public ResponseEntity<Resource> doDownloadFile(@PathVariable int id, @PathVariable int flId) {
    	
    	// 다운로드 요청한 파일 정보를 담는 fileDownloadRequestVO를 만든다.
    	FileDownloadRequestVO fileDownloadRequestVO = new FileDownloadRequestVO();
    	fileDownloadRequestVO.setFlId(flId);
    	fileDownloadRequestVO.setId(id);
    	
    	// 요청 파일의 id로 DB에서 selectOne해서 가져온다. -> 반환타입이 FileVO임
    	FileVO fileVO = this.fileService.getOneFile(fileDownloadRequestVO);
    	
    	// 빈 파일인지 방어 코딩
    	if(fileVO == null) {
    		throw new NotExistsException();
    	}
    	
    	// fileVO.getObfsFlPth()가 파일 클래스의 생성자로 들어가서 File 인스턴스로 만든다. 
    	// File 클래스의 생성자에 파일의 "전체 경로"를 넘겨서 자바에서 해당 파일에 접근할 수 있는 File 객체를 생성한다.
    	File downloadFile = new File(fileVO.getObfsPth());
    	
    	// HTTP Response(헤더와 바디로 구성) 생성
    	// Header: 파일의 이름을 전달한다.
    	// Body: 파일 인스턴스를 전달한다.
    	
    	// Header 생성
    	HttpHeaders header = new HttpHeaders();
    	
    	// HttpHeaders.CONTENT_DISPOSITION: 파일 다운로드 응답을 브라우저에게 알림.
    	// "attachment; filename=...": 브라우저가 이 응답을 "파일 다운로드"로 처리하게 만듦.
    	// URLEncoder.encode(...): 파일 이름에 공백, 한글, 특수문자가 있을 경우 오류 방지를 위해 URL 인코딩.
    	header.add(HttpHeaders.CONTENT_DISPOSITION,
    			"attachment; filename=" + URLEncoder.encode(fileVO.getFlNm(), Charset.defaultCharset()));
    	
    	
    	// FileInputStream(downloadFile): File 객체를 통해 실제 파일을 바이트 스트림으로 연다.
    	InputStreamResource resource = null;
    	try {
			resource = new InputStreamResource(new FileInputStream(downloadFile));
		
		// FileNotFoundException: 파일이 존재하지 않거나 읽을 수 없을 때 발생 -> 커스텀 예외 NotExistsException으로 처리.
    	} catch (FileNotFoundException e) {
//			throw new IllegalArgumentException("파일이 없습니다.");
			throw new NotExistsException();
		}
    	
    	
    	// Body 생성해서 다운로드 시키기
		/*
		 * ok(): HTTP 상태 200 OK 설정
		 * .headers(header): 위에서 만든 Content-Disposition 포함
		 * .contentLength(fileVO.getFlSz()): 파일 크기 명시 (다운로드 바 표시 등 유용)
		 * .contentType(MediaType.APPLICATION_OCTET_STREAM): 바이너리 파일 전송이라는 의미
		 * .body(resource): 앞서 준비한 InputStream을 응답 본문으로 설정
		 */
    	return ResponseEntity.ok()
    						 .headers(header)
    						 .contentLength(fileVO.getFlSz())
    						 .contentType(MediaType.APPLICATION_OCTET_STREAM)
    						 .body(resource);
    }

}