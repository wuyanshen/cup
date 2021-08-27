/**
 * 递归建树
 * @param {列表数据} data
 * @returns
 */
export const castToTree3 = data => {
    let tree = []
    data.forEach(p => {
        if (p.pid === 0) {
            tree.push(recurse(p, data))
        }
    })

    function recurse(parent, list) {
        list.forEach(child => {
            if (child.pid === parent.id) {
                if (parent.children) {
                    parent.children.push(recurse(child, list))
                } else {
                    parent.children = [recurse(child, list)]
                }
            }
        })
        return parent
    }

    return tree
}

/**
 * 两次for循环建树
 * @param {列表数据} data
 * @returns
 */
export const castToTree2 = data => {
    let tree = []
    data.forEach(parent => {
        if (parent.pid === 0) {
            tree.push(parent)
        }
        data.forEach(child => {
            if (child.pid === parent.id) {
                if (parent.children) {
                    parent.children.push(child)
                } else {
                    parent.children = [child]
                }
            }
        })
    })
    return tree
}

/**
 * 递归建树
 * @param {列表数据} data
 * @returns
 */
export const castToTree = data => {
    let parents = data.filter(item => item.pid === 0)
    let childrens = data.filter(item => item.pid !== 0)
    recursion(parents, childrens)

    function recursion(parents, childrens) {
        parents.forEach(p => {
            childrens.forEach((c, i) => {
                if (c.pid === p.id) {
                    if (p.children) {
                        p.children.push(c)
                    } else {
                        p.children = [c]
                    }

                    //   let _c = JSON.parse(JSON.stringify(childrens));
                    //   _c.splice(i, 1);
                    //   recursion([c], _c);
                    recursion([c], childrens)
                }
            })
        })
    }
    return parents
}

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 * @param {*} rootId 根Id 默认 0
 */
export function castToTree4(data, id, parentId, children, rootId) {
    id = id || 'id'
    parentId = parentId || 'parentId'
    children = children || 'children'
    rootId =
        rootId ||
        Math.min.apply(
            Math,
            data.map(item => {
                return item[parentId]
            })
        ) ||
        0
    //对源数据深度克隆
    const cloneData = JSON.parse(JSON.stringify(data))
    //循环所有项
    const treeData = cloneData.filter(father => {
        let branchArr = cloneData.filter(child => {
            //返回每一项的子级数组
            return father[id] === child[parentId]
        })
        branchArr.length > 0 ? (father.children = branchArr) : ''
        //返回第一层
        return father[parentId] === rootId
    })
    return treeData != '' ? treeData : data
}
