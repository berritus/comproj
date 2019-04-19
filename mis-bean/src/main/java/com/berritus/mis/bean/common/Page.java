/**
 * 
 */
package com.berritus.mis.bean.common;

import com.berritus.mis.core.bean.MisBean;

/**
 * @author Qin Guihe
 *
 */
public class Page extends MisBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5928271634138374612L;
	/**
	 * 每页数
	 */
	private int               pageSize;
	/**
	 * 当前页数
	 */
    private int               pageNum;
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
