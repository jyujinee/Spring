package com.hello.common.vo;

// Pagination을 만들 Search => 추상 클래스로 만든다
// Pagination이 필요한 곳에서 상속해서 사용할 수 있도록한다. (매번 코드작성할 필요없도록)
public abstract class Search {
	
	private int pageNo; // 검색할 페이지 번호
	private int listSize;// 한 페이지에 보여줄 게시글 수
	private int pageCount; // 총 페이지 수
	
	private int pageCountInGroup; // 한 페이지 그룹에 보여줄 페이지 번호 개수
	private int groupCount; // 총 페이지 그룹 수
	private int groupNo; // 현재 페이지 그룹 번호
	private int groupStartPageNo; // 페이지 그룹 번호의 시작 페이지 번호
	private int groupEndPageNo; // 페이지 그룹 번호의 끝 페이지 번호
	private boolean hasNextGroup; // 다음 그룹이 존재하는지 확인
	private boolean hasPrevGroup; // 이전 그룹이 존재하는지 확인
	private int nextGroupStartPageNo; // 다음 그룹의 시작 페이지 번호
	private int prevGroupStartPageNo; // 이전 그룹의 시작 페이지 번호
	
	
	public int getPageCountInGroup() {
		return pageCountInGroup;
	}

	public int getGroupCount() {
		return groupCount;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public int getGroupStartPageNo() {
		return groupStartPageNo;
	}

	public int getGroupEndPageNo() {
		return groupEndPageNo;
	}

	public boolean isHasNextGroup() {
		return hasNextGroup;
	}

	public boolean isHasPrevGroup() {
		return hasPrevGroup;
	}

	public int getNextGroupStartPageNo() {
		return nextGroupStartPageNo;
	}

	public int getPrevGroupStartPageNo() {
		return prevGroupStartPageNo;
	}

	/*
	 * 생성자에 한 페이지에 기본 게시글을 보여줄 수는 10이다.
	 */
	public Search() {
		this.listSize = 10;
		this.pageCountInGroup = 10; // 한 페이지 그룹에 보여줄 페이지 번호의 수
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	// 한 페이지에 보여줄 게시물의 수
	public void setPageCount(int searchCount) {
		// 총 페이지 수 구하기
		this.pageCount = (int)Math.ceil( (double)searchCount / this.listSize);
		
		// 총 그룹 수 구하기
		this.groupCount = (int)Math.ceil( (double)this.pageCount / this.pageCountInGroup);
		
		// 현재 페이지 그룹 번호 구하기
		this.groupNo = this.pageNo / this.pageCountInGroup;
		
		// 현재 페이지 그룹의 시작 페이지 번호 구하기
		this.groupStartPageNo = this.groupNo * this.pageCountInGroup;
		
		//현재 페이지 그룹의 마지막 페이지 번호 구하기
		this.groupEndPageNo = (this.groupNo + 1) * this.pageCountInGroup - 1;
		
		// 총 페이지 수가 현재 페이지 그룹의 마지막 페이지보다 작을 때
		// 현재 페이지 그룹의 마지막 페이지 수정
		if(this.groupEndPageNo > this.pageCount) {
			this.groupEndPageNo = this.pageCount - 1;
		}
		
		// 다음 그룹이 있는지 확인하기
		// 내가 보고 있는 그룹 + 1 < 전체 그룹 수
		this.hasNextGroup = this.groupNo + 1 < this.groupCount;
		
		// 이전 그룹이 있는지 확인하기
		// 내가 보고 있는 그룹 > 0 이면 이전 그룹이 있다 (페이지는 0부터 시작)
		this.hasPrevGroup = this.groupNo > 0;
		
		// 다음 페이지 그룹의 시작 페이지 구하기
		this.nextGroupStartPageNo = this.groupEndPageNo + 1;
		this.prevGroupStartPageNo = this.groupStartPageNo - this.pageCountInGroup;
		
		
	}
	
	

}
