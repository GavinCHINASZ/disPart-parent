package com.dispart.dto.auth;

import com.dispart.vo.commons.TRoleInfo;
import lombok.Data;

import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/25 16:52
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class MenuDto {
    private MenuTree menuTrees;
    private TRoleInfo role;
}