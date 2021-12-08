package com.dispart.dto.MCardInfoDto;

import com.dispart.model.MCardInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddMCardInfoInDto {

    private String mcardNum;

    private String payOrder;

    private String provId;

    private String provNm;

    private String vehicleNum;

    private String cardNo;

    private String mcardTp;

    private String openCardDt;

    private String startDt;

    private String dueDt;

    private String inNum;

    private String outNum;

    private String status;

    private String remark;

    private String operId;

    private String operNm;

    private Date creatTime;

    private Date upTime;

    private BigDecimal payAmt;

    private BigDecimal preferPrice;

    private BigDecimal recvAmt;

}
