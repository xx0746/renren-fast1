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
 * 科研中心员工绩效考核评分表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 10:24:28
 */
@Data
@TableName("tb_performance")
public class PerformanceEntity implements Serializable {
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
	 * 状态 1 待审核
	 */
	@ExcelIgnore
	private Integer status;
	/**
	 * 工作量
	 */
	@ExcelProperty(value = "工作量", index = 1)
	private Integer workload;
	/**
	 * 先进性与创新性
	 */
	@ExcelProperty(value = "先进性与创新性", index = 2)
	private Integer advanced;
	/**
	 * 调查研究
	 */
	@ExcelProperty(value = "调查研究", index = 3)
	private Integer research;
	/**
	 * 进度管控
	 */
	@ExcelProperty(value = "进度管控", index = 4)
	private Integer process;
	/**
	 * 成果价值
	 */
	@ExcelProperty(value = "成果价值", index = 5)
	private Integer achievement;
	/**
	 * 承担科研项目评价
	 */
	@ExcelIgnore
	private Integer evaluation;
	/**
	 * 岗位知识与技能
	 */
	@ExcelProperty(value = "岗位知识与技能", index = 6)
	private Integer knowledge;
	/**
	 * 沟通协作能力
	 */
	@ExcelProperty(value = "沟通协作能力", index = 7)
	private Integer cooperation;
	/**
	 * 学习创新能力
	 */
	@ExcelProperty(value = "学习创新能力", index = 8)
	private Integer learning;
	/**
	 * 工作责任心
	 */
	@ExcelProperty(value = "工作责任心", index = 9)
	private Integer responsibility;
	/**
	 * 工作纪律
	 */
	@ExcelProperty(value = "工作纪律", index = 10)
	private Integer discipline;
	/**
	 * 工作能力及行为
	 */
	@ExcelIgnore
	private Integer behavior;
	/**
	 * 考核结果
	 */
	@ExcelIgnore
	private Integer examine;
	/**
	 * 绩效考核得分排名
	 */
	@ExcelIgnore
	private Integer rankage;
	/**
	 * 绩效系数
	 */
	@ExcelProperty(value = "绩效系数", index = 11)
	private Integer coefficient;
	/**
	 * 录入者ID
	 */
	@ExcelIgnore
	private Long createUserId;
	/**
	 * 审核者ID
	 */
	@ExcelIgnore
	private Long checkUserId;

}
