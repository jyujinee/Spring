package com.hello.bbs.vo;

import java.util.List;

import com.hello.file.vo.FileVO;

/**
 * @TableName BOARD
 * @TableComment null
 */
public class BoardVO {

    private int id;
    private String subject;
    private String content;
    private String email;
    private int viewCnt;
    private String crtDt;
    private String mdfyDt;
    
    private String fileName;
    private String originFileName;
  
    private List<FileVO> fileList;

    public List<FileVO> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileVO> fileList) {
		this.fileList = fileList;
	}

	public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getViewCnt() {
        return this.viewCnt;
    }
    
    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }
    
    public String getCrtDt() {
        return this.crtDt;
    }
    
    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }
    
    public String getMdfyDt() {
        return this.mdfyDt;
    }
    
    public void setMdfyDt(String mdfyDt) {
        this.mdfyDt = mdfyDt;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getOriginFileName() {
        return this.originFileName;
    }
    
    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }
    
    @Override
    public String toString() {
        return "BoardVO(id: " + id + ", subject: " + subject + ", content: " + content + ", email: " + email + ", viewCnt: " + viewCnt + ", crtDt: " + crtDt + ", mdfyDt: " + mdfyDt + ", fileName: " + fileName + ", originFileName: " + originFileName + ", )";
    }
}