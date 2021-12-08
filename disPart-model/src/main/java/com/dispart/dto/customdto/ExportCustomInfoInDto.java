package com.dispart.dto.customdto;

import lombok.Data;

import java.util.List;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 导出客户信息请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class ExportCustomInfoInDto {
    private List<ExportCustomInfoInParamDto> list;
}
