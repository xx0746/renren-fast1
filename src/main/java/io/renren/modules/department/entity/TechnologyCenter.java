package io.renren.modules.department.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 技术中心
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TechnologyCenter对象", description="技术中心")
public class TechnologyCenter implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ExcelIgnore
    private String id;

    @ApiModelProperty(value = "序号")
    @TableField("sortId")
    @ExcelProperty(value = "序号",index = 0)
    private String sortId;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty(value = "姓名",index = 1)
    private String name;

    @ApiModelProperty(value = "级别")
    @ExcelProperty(value = "级别",index = 2)
    private String level;

    @ApiModelProperty(value = "工作量")
    @ExcelProperty(value = "工作量 40%",index = 3)
    private String wordLoad;

    @ApiModelProperty(value = "先进性与创新性")
    @ExcelProperty(value = "先进性与创新性 10%",index = 4)
    private String advancedInnovation;

    @ApiModelProperty(value = "调查研究")
    @ExcelProperty(value = "调查研究 10%",index = 5)
    private String investigate;

    @ApiModelProperty(value = "进度管控")
    @ExcelProperty(value = "进度管控 10%",index = 6)
    private String progressController;

    @ApiModelProperty(value = "成果价值")
    @ExcelProperty(value = "成果价值 10%",index = 7)
    private String resultValue;

    @ApiModelProperty(value = "知识与技能")
    @ExcelProperty(value = "知识与技能 4%",index = 8)
    private String knowledgeSkills;

    @ApiModelProperty(value = "沟通协作")
    @ExcelProperty(value = "沟通协作 4%",index = 9)
    private String communicationCollaboration;

    @ApiModelProperty(value = "学习创新")
    @ExcelProperty(value = "学习创新 4%",index = 10)
    private String learningInnovation;

    @ApiModelProperty(value = "工作责任心")
    @ExcelProperty(value = "工作责任心 4%",index = 11)
    private String jobResponsibility;

    @ApiModelProperty(value = "工作纪律")
    @ExcelProperty(value = "工作纪律 4%",index = 12)
    private String workingDiscipline;

    @ApiModelProperty(value = "考核结果")
    @ExcelProperty(value = "考核结果",index = 13)
    private String evaluationResult;

    @ApiModelProperty(value = "考核等级")
    @ExcelProperty(value = "考核等级",index = 14)
    private String evaluationLevel;

    @ApiModelProperty(value = "绩效")
    @ExcelProperty(value = "绩效",index = 15)
    private String performance;

    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注",index = 16)
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    private String createTime;


}
