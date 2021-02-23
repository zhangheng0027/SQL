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
    public void addLeftIndex(IndexNode<T> le) {
        if (null == this.previous) {

        } else {
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

	public DataNode<T> find(T t) {
        final int com = t.compareTo(super.getData());
        if (com < 0)
            return btNode.find(t);
        if (this.next == null)
            return this.getNextBTreeNode().find(t);
		return this.next.find(t);
	}

}
