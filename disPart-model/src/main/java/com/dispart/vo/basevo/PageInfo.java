package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public abstract class PageInfo {
    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer curPage;

}