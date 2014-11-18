package com.examw.netplatform.service.admin.teacher.impl;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.netplatform.dao.admin.IAnswersQuestionDao;
import com.examw.netplatform.model.admin.teacher.StatisticsInfo;
import com.examw.netplatform.service.admin.teacher.IAnswersQuestionStatisticsService;

/**
 * 答疑统计服务接口实现类
 * @author fengwei.
 * @since 2014年6月17日 下午3:13:09.
 */
public class AnswersQuestionStatisticsServiceImpl implements IAnswersQuestionStatisticsService{
	
	//private IAnswersQuestionDao answersQuestionDao;
	@Override
	public DataGrid<StatisticsInfo> datagrid(StatisticsInfo info) {
		DataGrid<StatisticsInfo> grid = new DataGrid<StatisticsInfo>();
		grid.setRows(this.find(info));
		grid.setTotal(Long.valueOf(grid.getRows().size()));
		return grid;
	}
	//找出所在机构的所有老师
	private List<StatisticsInfo> find(StatisticsInfo info){
		if(info == null) return null;
//		List<Teacher> t = this.teacherDao.find(info.getUserId());
//		Teacher i = null;
//		List<StatisticsInfo> list = new ArrayList<StatisticsInfo>();
//		if(t!=null&&t.size()>0)
//		{
//			i = t.get(0);
//			List<User> teachers = this.teacherDao.loadTeacher(i.getAgency().getId());//找出机构的所有老师
//			//一个老师教的所有班级
//			for(User u:teachers){
//				StatisticsInfo sinfo = new StatisticsInfo();
//				sinfo.setUserId(u.getId());
//				sinfo.setUserName(u.getName());
//				sinfo.setUserAccount(u.getAccount());
//				sinfo.setAgencyId(i.getAgency().getId());
//				sinfo.setAgencyName(i.getAgency().getName());
//				//String classPlanIds = getClassPlanIds(u.getClassPlans());
//				//sinfo.setTotalCount(this.answersQuestionDao.totalQuestions(classPlanIds));
//				//sinfo.setAnsweredCount(this.answersQuestionDao.totalAnswered(u, classPlanIds));
//				list.add(sinfo);
//			}
//		}
		//return list;
		return null;
	}
//	private String getClassPlanIds(Set<ClassPlan> classPlans){
//		if(classPlans==null||classPlans.isEmpty()) return null;
//		String ids = "";
//		for(ClassPlan data : classPlans){
//			ids += "'"+data.getId()+"',";
//		}
//		return ids.substring(0,ids.length()-1);
//	}
	
	/**
	 * 设置 答疑数据接口
	 * @param answersQuestionDao
	 * 
	 */
	public void setAnswersQuestionDao(IAnswersQuestionDao answersQuestionDao) {
		//this.answersQuestionDao = answersQuestionDao;
	}
	
}