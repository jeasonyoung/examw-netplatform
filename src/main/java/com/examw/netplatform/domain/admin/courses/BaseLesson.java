package com.examw.netplatform.domain.admin.courses;

import java.io.Serializable;

/**
 * 课时资源基础类。
 * 
 * @author jeasonyoung
 * @since 2015年8月28日
 */
public class BaseLesson implements Serializable,Comparable<BaseLesson> {
	private static final long serialVersionUID = 1L;
	private String id,name,videoUrl,highVideoUrl,superVideoUrl;
	private Integer time,orderNo;
	/**
	 * 获取课时资源ID。
	 * @return 课时资源ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置课时资源ID。
	 * @param id
	 * 课时资源ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取课时资源名称。
	 * @return 课时资源名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置课时资源名称。
	 * @param name
	 * 课时资源名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取视频地址。
	 * @return 视频地址。
	 */
	public String getVideoUrl() {
		return videoUrl;
	}
	/**
	 * 设置视频地址。
	 * @param videoUrl
	 * 视频地址。
	 */
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	/**
	 * 获取高清视频地址。
	 * @return 高清视频地址。
	 */
	public String getHighVideoUrl() {
		return highVideoUrl;
	}
	/**
	 * 设置高清视频地址。
	 * @param highVideoUrl
	 * 高清视频地址。
	 */
	public void setHighVideoUrl(String highVideoUrl) {
		this.highVideoUrl = highVideoUrl;
	}
	/**
	 * 获取超清视频地址。
	 * @return 超清视频地址。
	 */
	public String getSuperVideoUrl() {
		return superVideoUrl;
	}
	/**
	 * 设置超清视频地址。
	 * @param superVideoUrl 
	 *	  超清视频地址。
	 */
	public void setSuperVideoUrl(String superVideoUrl) {
		this.superVideoUrl = superVideoUrl;
	}
	/**
	 * 获取视频时长。
	 * @return 视频时长。
	 */
	public Integer getTime() {
		return time;
	}
	/**
	 * 设置视频时长。
	 * @param time 
	 *	  视频时长。
	 */
	public void setTime(Integer time) {
		this.time = time;
	}
	/**
	 * 获取课时资源排序。
	 * @return
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置课时资源排序。
	 * @param orderNo
	 * 课时资源排序。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BaseLesson o) {
		return this.orderNo - o.orderNo;
	}
}