package com.examw.netplatform.dao.admin.papers.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.netplatform.dao.admin.papers.INoteDao;
import com.examw.netplatform.dao.impl.BaseDaoImpl;
import com.examw.netplatform.domain.admin.papers.Note;
import com.examw.netplatform.model.admin.papers.NoteInfo;

/**
 * 试卷笔记数据接口实现类
 * @author fengwei.
 * @since 2014年5月13日 上午11:54:19.
 */
public class NoteDaoImpl extends BaseDaoImpl<Note> implements INoteDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.INoteDao#findNotes(com.examw.netplatform.model.admin.NoteInfo)
	 */
	public List<Note> findNotes(NoteInfo info){
		String hql = "from Note n where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("paperName")){
				info.setSort("paper.name");
			}else if (info.getSort().equalsIgnoreCase("itemSerial")) {
				info.setSort("item.serial");
			}
			hql += " order by n." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.papers.INoteDao#total(com.examw.netplatform.model.admin.papers.NoteInfo)
	 */
	@Override
	public Long total(NoteInfo info) {
		String hql = "select count(*) from Note n where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(NoteInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getUsername())){
			hql += " and (n.user.account like :account)";
			parameters.put("account", "%"+ info.getUsername() +"%");
		}
		if(!StringUtils.isEmpty( info.getPaperName())){
			hql += " and (n.paper.name like :paperName)";
			parameters.put("paperName", "%"+ info.getPaperName() +"%");
		}
		if(!StringUtils.isEmpty(info.getPaperId())){
			hql += " and (n.paper.id ＝ :paperId)";
			parameters.put("paperId", info.getPaperId());
		}
		if(!StringUtils.isEmpty(info.getItemId())){
			hql += " and (n.item.id ＝ :itemId)";
			parameters.put("paperId", info.getItemId());
		}
		return hql;
	}
}