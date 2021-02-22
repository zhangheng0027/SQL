package com.zh.btree;

import lombok.Getter;

public class IndexNode<T extends Comparable<T>> extends Node<T> {
    
    @Getter
    private IndexNode<T> next;

    @Getter
    private IndexNode<T> previous;

    // 记录
    @Getter
    private BTreeNode<T> btNode;



    /**
     * 在本节点左边增加节点
     */
    public void addLeftIndex(IndexNode<T> le) {
        // 该节点是最左边的节点
        IndexNode<T> temp = this.previous;
        le.previous = temp;
        this.previous = le;
        le.next = this;


        btNode.setLeftIndex(le);
        le.btNode.setLeftIndex(temp);
        // TODO a
        if (null == temp) {
        } else {
            temp.next = le;

            temp.next = le;
            le.next = this;
        }
    }

    public void addRightIndex(IndexNode<T> re, BTreeNode<T> btNode) {
        // 该节点为最右边的节点
        if (null == this.next) {

        }
    }

    public void setBtNode(BTreeNode<T> b) {
        this.btNode = b;
        this.btNode.setRightIndex(this);
    }

}
