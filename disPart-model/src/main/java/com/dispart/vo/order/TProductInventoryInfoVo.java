package com.dispart.vo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TProductInventoryInfoVo {

    private String prdctId;

    private String provId;

    private String prdctNm;

    private Integer stock;

    private String unit;

    private String remark;

    private Date updateDt;

    private String prdctType;

    private Integer prdctTypeId;

    private String unitkey;
}
