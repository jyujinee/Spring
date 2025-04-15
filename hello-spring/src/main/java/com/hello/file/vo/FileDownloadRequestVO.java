package com.hello.file.vo;

public class FileDownloadRequestVO {
	
	// 게시글의 아이디 (맵퍼에서 쓰기 위해서)
	private int id;
	
	// 파일의 아이디
	private int flId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFlId() {
		return flId;
	}
	public void setFlId(int flId) {
		this.flId = flId;
	}

}
