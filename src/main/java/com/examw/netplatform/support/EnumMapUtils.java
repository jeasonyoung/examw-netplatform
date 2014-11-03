package com.examw.netplatform.support;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 枚举Map工具类。
 * 
 * @author yangyong
 * @since 2014年11月3日
 */
public final class EnumMapUtils {
	/**
	 * 创建以键名排序的TreeMap对象。
	 * @return
	 */
	public static final Map<String, String> createTreeMap(){
		return new TreeMap<>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				Integer x = Integer.parseInt(o1), y = Integer.parseInt(o2);
				if(x == null || y == null){
					return o1.compareToIgnoreCase(o2);
				}
				return x - y;
			}
		});
	}
 	
}