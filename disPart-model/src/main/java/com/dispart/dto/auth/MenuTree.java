package com.dispart.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author:xts
 * @date:Created in 2021/6/20 19:13
 * @description 菜单树形结构信息
 * @modified by:
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {
    /**
     * 菜单类型
     */
    private String menuType;
    /**
     * 备注
     */
    private String remark;

    /**
     * 接口URL
     */
    private String url;
}