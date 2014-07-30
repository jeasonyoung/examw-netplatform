package com.examw.netplatform.model.admin.papers;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 试卷预览。
 * @author yangyong.
 * @since 2014-06-13.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaperPreview extends PaperInfo  {
	private static final long serialVersionUID = 1L;
	private List<StructurePreview> structures;
	/**
	 * 获取试卷结构集合。
	 * @return
	 * 试卷结构集合。
	 */
	public List<StructurePreview> getStructures() {
		return structures;
	}
	/**
	 * 设置试卷结构集合。
	 * @param structures
	 * 试卷结构集合。
	 */
	public void setStructures(List<StructurePreview> structures) {
		this.structures = structures;
	}
}