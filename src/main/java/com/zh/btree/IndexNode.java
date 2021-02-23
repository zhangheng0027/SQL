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

    public BTreeNode<T> getNextBTreeNode() {
        if (null == next) {
            return super.getRoom().getEndBTreeNode();
        }
        return this.next.btNode;
    }

    /**
     * 在本节点左边增加节点
     */
    public void addLeftIndex(IndexNode<T> le, BTreeNode<T> btrNode) {
        
        BTreeNode<T> leftTempBTN = this.btNode;
        this.btNode = btrNode;
        le.btNode = leftTempBTN;
    
        leftTempBTN.setRightIndex(le);
        btrNode.setRightIndex(this);
        btrNode.setLeftIndex(le);

        if (null == this.previous) {

            le.next = this;
            le.previous = null;
            this.previous = le;

            super.getRoom().setHeadNode(le);
        } else {
            IndexNode<T> leftTempInd = this.previous;

            leftTempInd.next = le;
            le.next = this;

            this.previous = le;
            le.previous = leftTempInd;

        }
    }

    /**
     * 正常情况调用 {@link IndexNode#addLeftIndex(IndexNode, BTreeNode)}
     * @param re
     * @param btrNode
     */
    public void addRightIndex(IndexNode<T> re, BTreeNode<T> btrNode) {
        if (null != this.next) {
            throw new Error();
        }
        
    }

    public void setBtNode(BTreeNode<T> b) {
        this.btNode = b;
        this.btNode.setRightIndex(this);
    }

	public DataNode<T> find(T t) {
        final int com = t.compareTo(super.getData());
        if (com < 0)
            return btNode.find(t);
        if (this.next == null)
            return this.getNextBTreeNode().find(t);
		return this.next.find(t);
	}

}
