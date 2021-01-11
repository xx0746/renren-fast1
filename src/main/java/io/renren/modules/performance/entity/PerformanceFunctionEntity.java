package io.renren.modules.performance.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 职能部门员工绩效考核评分
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
@Data
@TableName("tb_performance_function")
public class PerformanceFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ExcelIgnore
	private Long id;
	/**
	 * 用户ID
	 */
	@ExcelIgnore
	private Long userId;
	/**
	 * 用户名
	 */
	@TableField(exist=false)
	@ExcelProperty(value = "用户名", index = 0)
	private String userName;
	/**
	 * 创建时间
	 */
	@ExcelIgnore
	private Date createTime;
	/**
	 * 状态
	 */
	@ExcelIgnore
	private Integer status;
	/**
	 * 工作业绩
	 */
	@ExcelProperty(value = "工作业绩", index = 1)
	private Integer workload;
	/**
	 * 履职能力
	 */
	@ExcelProperty(value = "履职能力", index = 2)
	private Integer duty;
	/**
	 * 工作纪律
	 */
	@ExcelProperty(value = "工作纪律", index = 3)
	private Integer discipline;
	/**
	 * 工作态度
	 */
	@ExcelProperty(value = "工作态度", index = 4)
	private Integer attitude;
	/**
	 * 总分
	 */
	@ExcelIgnore
	private Integer scorecount;
	/**
	 * 绩效扣减
	 */
	@ExcelProperty(value = "绩效扣减", index = 5)
	private Integer deduction;

}
