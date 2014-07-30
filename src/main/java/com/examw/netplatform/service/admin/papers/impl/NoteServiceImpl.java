package com.examw.netplatform.service.admin.papers.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.INoteDao;
import com.examw.netplatform.dao.admin.papers.IPaperDao;
import com.examw.netplatform.dao.admin.papers.IStructureItemDao;
import com.examw.netplatform.dao.admin.security.IUserDao;
import com.examw.netplatform.domain.admin.papers.Note;
import com.examw.netplatform.domain.admin.papers.Paper;
import com.examw.netplatform.domain.admin.papers.StructureItem;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.model.admin.papers.NoteInfo;
import com.examw.netplatform.service.admin.papers.INoteService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;

/**
 * 试卷笔记服务接口实现类
 * @author fengwei.
 * @since 2014年5月13日 上午11:57:06.
 */
public class NoteServiceImpl extends BaseDataServiceImpl<Note, NoteInfo> implements INoteService{
	private INoteDao noteDao;
	private IPaperDao paperDao;
	private IStructureItemDao structureItemDao;
	private IUserDao userDao;
	/**
	 * 设置试卷笔记数据接口。
	 * @param noteDao
	 * 试卷笔记数据接口。
	 */
	public void setNoteDao(INoteDao noteDao) {
		this.noteDao = noteDao;
	}
	/**
	 * 设置试卷数据接口。
	 * @param paperDao
	 * 试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		this.paperDao = paperDao;
	}
	/**
	 * 设置试卷结构数据接口。
	 * @param structureItemDao
	 */
	public void setStructureItemDao(IStructureItemDao structureItemDao) {
		this.structureItemDao = structureItemDao;
	}
	/**
	 * 设置用户数据接口。
	 * @return
	 */
	public void setUserDao(IUserDao userDao) {
		 this.userDao = userDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Note> find(NoteInfo info) {
		return this.noteDao.findNotes(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected NoteInfo changeModel(Note data) {
		if(data == null) return  null;
		NoteInfo info = new NoteInfo();
		BeanUtils.copyProperties(data, info);
		//用户信息
		if(data.getUser() != null){
			info.setUserId(data.getUser().getId());
			info.setUsername(data.getUser().getName() + "[" + data.getUser().getAccount() + "]");
		}
		//试卷
		if(data.getPaper() != null){
			info.setPaperId(data.getPaper().getId());
			info.setPaperName(data.getPaper().getName());
		}
		//试题
		if(data.getItem() != null){
			info.setItemId(data.getItem().getId());
			info.setItemSerial(data.getItem().getSerial());
		}
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(NoteInfo info) {
		 return this.noteDao.total(info);
	}
	/*
	 * 更新笔记。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public NoteInfo update(NoteInfo info) {
		if(info == null) return null;
		boolean isAdd = false;
		Note data = StringUtils.isEmpty(info.getId()) ?  null : this.noteDao.load(Note.class, info.getId());
		if(isAdd = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
				info.setCreateTime(new Date());
			}
			data = new Note();
		}
		if(data.getCreateTime() != null){
			info.setCreateTime(data.getCreateTime());
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getUserId()) && (data.getUser() == null)){
			User user = this.userDao.load(User.class, info.getUserId());
			if(user != null) data.setUser(user);
		}
		if(!StringUtils.isEmpty(info.getPaperId()) &&(data.getPaper() == null || !data.getPaper().getId().equalsIgnoreCase(info.getPaperId()))){
			Paper paper = this.paperDao.load(Paper.class, info.getPaperId());
			if(paper != null) data.setPaper(paper);
		}
		if(!StringUtils.isEmpty(info.getItemId()) && (data.getItem() == null || !data.getItem().getId().equalsIgnoreCase(info.getItemId()))){
			StructureItem item = this.structureItemDao.load(StructureItem.class, info.getItemId());
			if(item != null)data.setItem(item);
		}
		if(StringUtils.isEmpty(info.getPaperName()) && data.getPaper() != null){
			info.setPaperName(data.getPaper().getName());
		}
		if(StringUtils.isEmpty(info.getItemSerial()) && data.getItem() != null){
			info.setItemSerial(data.getItem().getSerial());
		}
		if(data.getUser() != null){
			info.setUserId(data.getUser().getId());
			info.setUsername(data.getUser().getName());
		}
		if(isAdd)this.noteDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Note data = this.noteDao.load(Note.class, ids[i]);
			if(data != null) this.noteDao.delete(data);
		}
	}
}