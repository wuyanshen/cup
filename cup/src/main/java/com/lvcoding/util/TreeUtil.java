package com.lvcoding.util;

import com.lvcoding.entity.dto.TreeNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyanshen
 * @date 2020-03-29 5:40 下午
 * @discription 递归建树工具类
 */
@UtilityClass
public class TreeUtil {

    /**
     * 使用两次循环建树
     *
     * @param treeNodes
     * @param root
     * @return java.util.List<T>
     */
    public <T extends TreeNode> List<T> bulidTree(List<T> treeNodes,Object root) {

        List<T> trees = new ArrayList<T>();

        for (T treeNode : treeNodes) {

            //循环父节点
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            //循环第二层子节点
            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
