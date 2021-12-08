package com.dispart.dto.auth;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/20 19:02
 * @description 树形结构通用类
 * @modified by:
 * @version: 1.0
 */
@Data
public class TreeNode {
    private String  id;
    private String parentId;
    private String title;
    private String icon;
    private String path;
    private List<TreeNode> children=new ArrayList<>();
    public void add(TreeNode node){
        children.add(node);
    }
}