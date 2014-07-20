package com.onyas.oa.domain;

import java.util.List;

import javax.print.attribute.standard.MediaSize.Engineering;

public class PageBean {

	//要查数据库
	private List recordList;	//本面的数据列表
	private int recordCount;	//总记录数
	//传递的参数
	private int currentPage;	//当前页
	private int	pageSize;		//每页显示多少条记录
	//计算出来的
	private int pageCount;		//总页数
	private int beginPageIndex;	//页码列表的开始索引(包含)
	private int endPageIndex;	//页码列表的结束索引(包含)
	
	
	
	/**
	 * 只需要接收四个必要的属性，会自动计算其他三个属性的值
	 * @param recordList
	 * @param recordCount
	 * @param currentPage
	 * @param pageSize
	 */
	public PageBean(List recordList, int recordCount, int currentPage,
			int pageSize) {
		super();
		this.recordList = recordList;
		this.recordCount = recordCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		
		//计算pageCount
		this.pageCount = (recordCount+pageSize-1)/pageSize;
		
		//计算beginPageIndex,endPageIndex
		if(pageCount<=10){//1、总页码小于10页，全部显示
			this.beginPageIndex=1;
			this.endPageIndex=pageCount;
		}else{//2、总页码大于10页，就只显示当前页码附近10页
			//默认显示前4页+当前页+后面5页
			beginPageIndex = currentPage-4;
			endPageIndex = currentPage+5;
			
			if(beginPageIndex<1){//如果前面不足4页时，显示前10页
				beginPageIndex=1;
				endPageIndex=10;
			}else if(endPageIndex>pageCount){//如果后面多出，则只显示后10页
				beginPageIndex=pageCount-9;
				endPageIndex=pageCount;
			}
		}
		
		
	}
	
	
	public List getRecordList() {
		return recordList;
	}
	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public int getBeginPageIndex() {
		return beginPageIndex;
	}
	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}
	public int getEndPageIndex() {
		return endPageIndex;
	}
	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
}
