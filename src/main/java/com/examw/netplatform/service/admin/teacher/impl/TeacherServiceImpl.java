package com.examw.netplatform.service.admin.teacher.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.netplatform.dao.admin.security.IUserDao;
import com.examw.netplatform.dao.admin.settings.IAgencyDao;
import com.examw.netplatform.dao.admin.teacher.ITeacherDao;
import com.examw.netplatform.domain.admin.security.User;
import com.examw.netplatform.domain.admin.teacher.Teacher;
import com.examw.netplatform.model.admin.teacher.TeacherInfo;
import com.examw.netplatform.service.admin.teacher.ITeacherService;
import com.examw.netplatform.service.impl.BaseDataServiceImpl;
import com.examw.netplatform.support.PasswordHelper;

/**
 * 教师服务接口实现类
 * 
 * @author fengwei.
 * @since 2014年5月29日 下午3:27:21.
 */
public class TeacherServiceImpl extends
		BaseDataServiceImpl<Teacher, TeacherInfo> implements ITeacherService {
	private ITeacherDao teacherDao;
	private IUserDao userDao;
	//private IAgencyDao agencyDao;
	private Map<Integer, String> genderNames, statusNames;
	private PasswordHelper passwordHelper;
	private Map<String, String> typeNames;
	/**
	 * 设置 教师数据接口
	 * 
	 * @param teacherDao
	 * 
	 */
	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}
	
	/**
	 * 设置 学员数据接口
	 * @param userDao
	 * 
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 设置 机构数据接口
	 * 
	 * @param agencyDao
	 * 
	 */
	public void setAgencyDao(IAgencyDao agencyDao) {
		//this.agencyDao = agencyDao;
	}
	
	/**
	 * 设置 性别映射
	 * 
	 * @param genderNames
	 * 
	 */
	public void setGenderNames(Map<Integer, String> genderNames) {
		this.genderNames = genderNames;
	}

	/**
	 * 设置 状态映射
	 * 
	 * @param statusNames
	 * 
	 */
	public void setStatusNames(Map<Integer, String> statusNames) {
		this.statusNames = statusNames;
	}
	
	/**
	 * 设置 类型映射
	 * @param typeNames
	 * 
	 */
	public void setTypeNames(Map<String, String> typeNames) {
		this.typeNames = typeNames;
	}

	/**
	 * 设置 密码工具类
	 * 
	 * @param passwordHelper
	 * 
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		this.passwordHelper = passwordHelper;
	}

	@Override
	protected List<Teacher> find(TeacherInfo info) {
		return this.teacherDao.findTeachers(info);
	}

	@Override
	protected TeacherInfo changeModel(Teacher data) {
		if (data == null)
			return null;
		TeacherInfo info = new TeacherInfo();
		User user = data.getUser();
		BeanUtils.copyProperties(data, info);
		BeanUtils.copyProperties(user, info, new String[] { "password" });
		// 解密密码。
		info.setPassword(this.passwordHelper.decryptAESPassword(data.getUser()));

//		if (info.getType() != null)
//			info.setTypeName(this.typeNames.get(info.getType().toString()));
		if (info.getGender() != null)
			info.setGenderName(this.loadGenderName(info.getGender()));
		if (info.getStatus() != null)
			info.setStatusName(this.loadUserStatusName(info.getStatus()));
		// 机构
//		if (data.getUser().getAgencies() != null) {
//			List<String> list = new ArrayList<>();
//			for (Agency agency : data.getUser().getAgencies()) {
//				if (agency != null)
//					list.add(agency.getId());
//			}
//			info.setAgencyId(list.toArray(new String[0]));
//		}
		info.setAgencyName(data.getAgency().getName());
		return info;
	}

	@Override
	protected Long total(TeacherInfo info) {
		return this.teacherDao.total(info);
	}

	@Override
	public TeacherInfo update(TeacherInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		User  data = StringUtils.isEmpty(info.getId()) ?  null : this.userDao.load(User.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new User();
		}
		//info.setLastLoginTime(new Date());
		if(!isAdded)info.setCreateTime(data.getCreateTime());
		BeanUtils.copyProperties(info, data, new String[]{"password"});
		//加密密码。
		data.setPassword(this.passwordHelper.encryptAESPassword(info));
		//添加培训机构。
		//Set<Agency> agencySets = new HashSet<>();
		List<Teacher> teachers = new ArrayList<>();
//		if(info.getAgencyId() != null && info.getAgencyId().length > 0){
//			for(String id : info.getAgencyId()){
//				if(StringUtils.isEmpty(id)) continue;
//				Agency agency = this.agencyDao.load(Agency.class, id);
//				if(agency != null){
//					agencySets.add(agency);
//					Teacher agencyUser = new Teacher();
//					agencyUser.setAgency(agency);
//					agencyUser.setIdentity(AgencyUser.IDENTITY_AGENCY_TEACHER);
//					agencyUser.setCreateUsername(info.getCreateUsername());
//					agencyUser.setDescription(info.getDescription());
//					agencyUser.setUser(data);
//					teachers.add(agencyUser);
//				}
//			}
//		}
//		data.setAgencies(agencySets);
		//新增数据。
		if(isAdded){
			this.userDao.save(data);
			if(teachers.size()>0){
				this.teacherDao.batchAdd(teachers);
			}
		}else{
			this.teacherDao.delete(data.getId());
			this.teacherDao.batchAdd(teachers);
		}
		//性别名称。
		if(StringUtils.isEmpty(info.getGenderName()) && info.getGender() != null){
			info.setGenderName(this.loadGenderName(info.getGender()));
		}
//		if(StringUtils.isEmpty(info.getTypeName()) && info.getType() != null){
//			info.setTypeName(this.loadUserTypes().get(info.getType().toString()));
//		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids==null||ids.length==0) return;
		for(String id:ids){
			User data = this.userDao.load(User.class, id);
			if(data!=null)
			{
				this.teacherDao.delete(data.getId());	//删除关联
				this.userDao.delete(data);	//实际帐号
			}
		}
	}

	/*
	 * 加载用户状态名称。
	 * @see com.examw.netplatform.service.admin.IUserService#loadUserStatusName(int)
	 */
	@Override
	public String loadUserStatusName(Integer status) {
		if(this.statusNames == null || this.statusNames.size() == 0) return status.toString();
		return this.statusNames.get(status);
	}
	/*
	 * 加载性别名称
	 * @see com.examw.netplatform.service.admin.IUserService#loadGenderName(java.lang.Integer)
	 */
	@Override
	public String loadGenderName(Integer gender) {
		if(this.genderNames == null || this.genderNames.size() == 0) return gender.toString();
		return this.genderNames.get(gender);
	}
	/*
	 * 加载用户类型集合。
	 * @see com.examw.netplatform.service.admin.IUserService#loadUserTypes()
	 */
	@Override
	public Map<String, String> loadUserTypes() {
		return this.typeNames;
	}

	@Override
	public List<TreeNode> loadAllTeachers(String agencyId) {
		List<TreeNode> treeNodes = new ArrayList<>();
		if(StringUtils.isEmpty(agencyId)) return treeNodes;
		List<User> users = this.teacherDao.loadTeacher(agencyId);
		if(users!=null&&users.size()>0){
			for(final User u:users){
				treeNodes.add(new TreeNode(){
					private static final long serialVersionUID = 1L;
					@Override
					public String getId(){return u.getId();}
					@Override
					public String getText(){return u.getName();}
				});
			}
		}
		return treeNodes;
	}
}
