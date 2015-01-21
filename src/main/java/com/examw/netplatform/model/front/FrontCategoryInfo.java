package com.examw.netplatform.model.front;

import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.netplatform.model.admin.settings.CategoryInfo;

/**
 * 考试类别前台模型
 * @author fengwei.
 * @since 2014年9月4日 下午12:03:56.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FrontCategoryInfo extends CategoryInfo  {	
	private static final long serialVersionUID = 1L;
	private List<FrontExamInfo> exams;
	private Set<FrontCategoryInfo> children;
	/**
	 * 获取考试集合。
	 * @return 考试集合。
	 */
	public List<FrontExamInfo> getExams() {
		return exams;
	}
	/**
	 * 设置考试集合。
	 * @param exams
	 * 考试集合。
	 */
	public void setExams(List<FrontExamInfo> exams) {
		this.exams = exams;
	}
	/**
	 * 获取子结构集合。
	 * @return 子结构集合。
	 */
	public Set<FrontCategoryInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子结构集合。
	 * @param children 
	 *	子结构集合。
	 */
	public void setChildren(Set<FrontCategoryInfo> children) {
		this.children = children;
	}
}