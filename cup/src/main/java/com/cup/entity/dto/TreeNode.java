package com.cup.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyanshen
 * @date 2020-03-29 4:14 下午
 * @discription 树形菜单公共类
 */
@Data
public class TreeNode {

    protected int id;
    protected int parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();
    public void add(TreeNode treeNode){
        children.add(treeNode);
    }
}
