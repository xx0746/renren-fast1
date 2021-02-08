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
 * Audit对象
 * </p>
 *
 * @author java
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Audit对象", description="审核按钮")
public class Audit implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ExcelIgnore
    private String id;

    @ApiModelProperty(value = "center_id")
    @TableField("center_id")
    @ExcelProperty(value = "center_id",index = 0)
    private int centerId;

    @ApiModelProperty(value = "status")
    @ExcelProperty(value = "status",index = 1)
    private int status;




}

