package com.examw.netplatform.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.examw.netplatform.model.admin.papers.ItemInfo;
import com.examw.utils.MD5Util;

/**
 * 试题校验码生成工具类。
 * @author yangyong.
 * @since 2014-05-28.
 */
public class ItemCheckCodeHelper {
	/**
	 * 创建校验码。
	 * @param info
	 * @return
	 */
	public synchronized String createCheckCode(final ItemInfo info){
		if(info == null) return null;
		List<String> list = this.buildItemCheckCode(info);
		if(list == null || list.size()  == 0) return null;
		String[] array = list.toArray(new String[0]);
		Arrays.sort(array);
		String data = StringUtils.trimAllWhitespace(Arrays.toString(array));
		if(StringUtils.isEmpty(data)) return null;
		return MD5Util.MD5(data).toUpperCase();
	}
	/**
	 * 构建题目校验码内容。
	 * @param info
	 * @return
	 */
	private List<String> buildItemCheckCode(ItemInfo info){
		if(info == null) return null; 
		List<String> list = new ArrayList<>();
		if(!StringUtils.isEmpty(info.getAgencyId()) && !list.contains(info.getAgencyId())){
			list.add(info.getAgencyId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId()) && !list.contains(info.getSubjectId())){
			list.add(info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getChapterId()) && !list.contains(info.getChapterId())){
			list.add(info.getChapterId());
		}
		if(!StringUtils.isEmpty(info.getContent())){
			list.add(StringUtils.trimAllWhitespace(info.getContent()));
		}
		if(!StringUtils.isEmpty(info.getAnswer())){
			list.add(StringUtils.trimAllWhitespace(info.getAnswer()));
		}
		if(!StringUtils.isEmpty(info.getAnalysis())){
			list.add(StringUtils.trimAllWhitespace(info.getAnalysis()));
		}
		if(info.getChildren() != null && info.getChildren().size() > 0){
			for(ItemInfo data: info.getChildren()){
				List<String> childs = this.buildItemCheckCode(data);
				if(childs != null && childs.size() > 0){
					list.addAll(childs);
				}
			}
		}
		return list.size() > 0 ?  list : null;
	}
}