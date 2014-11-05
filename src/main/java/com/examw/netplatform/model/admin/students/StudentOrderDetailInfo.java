package com.examw.netplatform.model.admin.students;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;
/**
 * 机构学员订单明细信息。
 * @author yangyong.
 * @since 2014-07-17.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StudentOrderDetailInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,title,typeName,classId,packId;
	private Integer type;
	private BigDecimal price;
	private Date createTime;
	/**
	 * 获取订单明细ID。
	 * @return 订单明细ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置订单明细ID。
	 * @param id
	 * 订单明细ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取标题。
	 * @return 标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置标题。
	 * @param title
	 * 标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取类型。
	 * 1－班级，2-套餐。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type
	 * 类型（1－班级，2-套餐）。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取类型名称。
	 * @return 类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置类型名称。
	 * @param typeName
	 * 类型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取所属班级ID。
	 * @return 所属班级ID。
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置所属班级ID。
	 * @param classId
	 * 所属班级ID。
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取所属套餐ID。
	 * @return 所属套餐ID。
	 */
	public String getPackId() {
		return packId;
	}
	/**
	 * 设置所属套餐ID。
	 * @param packId
	 * 所属套餐ID。
	 */
	public void setPackId(String packId) {
		this.packId = packId;
	}
	/**
	 *  设置购买价格。
	 * @return 购买价格。
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置购买价格。
	 * @param price
	 * 购买价格。
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取购买时间。
	 * @return 购买时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置购买时间。
	 * @param createTime
	 * 购买时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}