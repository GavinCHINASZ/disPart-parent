package com.dispart.dto.dataquery;

import com.dispart.vo.dataQuery.Disp20210353InfoOutVo;
import com.dispart.vo.dataQuery.Disp20210353OutVo;
import lombok.Data;

import java.util.List;

@Data
public class Disp20210353OutDto {

    private List<Disp20210353InfoOutVo> txnInfo;

    private List<Disp20210353OutVo> txnInfoMx;

}
