package io.renren.modules.performance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 专家博士绩效考核
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
@Data
@TableName("tb_performance_doctor")
public class PerformanceDoctorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 专报成果
	 */
	private String report;
	/**
	 * 科研课题成果
	 */
	private String achievement;
	/**
	 * 发表论文情况
	 */
	private String paper;
	/**
	 * 院内外授课情况
	 */
	private String classes;
	/**
	 * 中心科研工作情况
	 */
	private String workstu;
	/**
	 * 所在中心考核意见
	 */
	private Integer centerSug;
	/**
	 * 科研管理部考核得分
	 */
	private Integer scientificSug;
	/**
	 * 人力资源部审核意见
	 */
	private String examineSug;

	/**
	 * 用户名
	 */
	@TableField(exist=false)
	private String userName;

}
