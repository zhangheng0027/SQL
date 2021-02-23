package com.zh.btree;

import lombok.Getter;
import lombok.Setter;

public class BTree<T extends Comparable<T>> {
    
    // 根节点
    @Setter @Getter
    private BTreeNode<T> root;

    @Setter @Getter
    private int size;

    public DataNode<T> find(T t) {
        return root.find(t);
    }
}
