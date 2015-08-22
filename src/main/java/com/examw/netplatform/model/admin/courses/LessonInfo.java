package com.examw.netplatform.model.admin.courses;

import java.util.Date;

import com.examw.model.IPaging;
import com.examw.netplatform.domain.admin.courses.Lesson;

/**
 * 课时资源信息
 * @author fengwei.
 * @since 2014年5月22日 上午11:28:23.
 */
public class LessonInfo extends Lesson implements IPaging{
	private static final long serialVersionUID = 1L;
	private String videoModeName,handoutModeName,order,sort;
	private Integer page,rows;
	private String[] chapterIds;
	/**
	 * 获取视频模式名称。
	 * @return 视频模式名称。
	 */
	public String getVideoModeName() {
		return videoModeName;
	}
	/**
	 * 设置视频模式名称。
	 * @param videoModeName 
	 *	  视频模式名称。
	 */
	public void setVideoModeName(String videoModeName) {
		this.videoModeName = videoModeName;
	}
	/**
	 * 获取讲义模式名称。
	 * @return 讲义模式名称。
	 */
	public String getHandoutModeName() {
		return handoutModeName;
	}
	/**
	 * 设置讲义模式名称。
	 * @param handoutModeName 
	 *	  讲义模式名称。
	 */
	public void setHandoutModeName(String handoutModeName) {
		this.handoutModeName = handoutModeName;
	}
	/**
	 * 获取关联的章节集合。
	 * @return 关联的章节集合。
	 */
	public String[] getChapterIds() {
		return chapterIds;
	}
	/**
	 * 设置关联的章节集合。
	 * @param chapterIds 
	 *	  关联的章节集合。
	 */
	public void setChapterIds(String[] chapterIds) {
		this.chapterIds = chapterIds;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@Override
	public Date getCreateTime() {
		return super.getCreateTime();
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@Override
	public Date getLastTime() {
		return super.getLastTime();
	}
	/*
	 * 获取页码。
	 * @see com.examw.model.IPaging#getPage()
	 */
	@Override
	public Integer getPage() {
		return this.page;
	}
	/*
	 * 设置页码。
	 * @see com.examw.model.IPaging#setPage(java.lang.Integer)
	 */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	/*
	 * 获取页数据。
	 * @see com.examw.model.IPaging#getRows()
	 */
	@Override
	public Integer getRows() {
		return this.rows;
	}
	/*
	 * 设置页数据。
	 * @see com.examw.model.IPaging#setRows(java.lang.Integer)
	 */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/*
	 * 获取排序字段。
	 * @see com.examw.model.IPaging#getOrder()
	 */
	@Override
	public String getOrder() {
		return this.order;
	}
	/*
	 * 设置排序字段。
	 * @see com.examw.model.IPaging#setOrder(java.lang.String)
	 */
	@Override
	public void setOrder(String order) {
		 this.order = order;
	}
	/*
	 * 获取排序方式。
	 * @see com.examw.model.IPaging#getSort()
	 */
	@Override
	public String getSort() {
		return this.sort;
	}
	/*
	 * 设置排序方式。
	 * @see com.examw.model.IPaging#setSort(java.lang.String)
	 */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
}