package com.dispart.dto.departmentdto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DISP20210019DepFindByParamOutDto {
    private List<DISP20210019DepFindByParamPOutDto> depArrayList = new ArrayList<>();

    //总条数
    private Integer tolPageNum;
}
