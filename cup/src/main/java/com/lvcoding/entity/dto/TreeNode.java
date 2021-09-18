/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyanshen
 * @date 2020-03-29 4:14 下午
 * @description 树形菜单公共类
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
