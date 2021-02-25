package com.zh.btree;

import lombok.Getter;
import lombok.Setter;

public class IndexNode<T extends Comparable<T>> extends Node<T> {
    
    @Getter @Setter
    private IndexNode<T> next;

    @Getter @Setter
    private IndexNode<T> previous;

    // 记录
    @Getter
    private BTreeNode<T> btNode;

    public IndexNode(T t) {
        super(t);
    }


    public BTreeNode<T> getNextBTreeNode() {
        if (null == next) {
            return super.getRoom().getEndBTreeNode();
        }
        return this.next.btNode;
    }

    /**
     * 在本节点左边增加节点
     */
    public BTreeNode<T> addLeftIndex(IndexNode<T> le, BTreeNode<T> btrNode, int maxDegree, int floor) {
        
        super.getRoom().increase();
        le.setRoom(this.getRoom());

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

        return super.getRoom().diviesionIndNode(maxDegree, floor);
    }

    /**
     * 正常情况调用 {@link IndexNode#addLeftIndex(IndexNode, BTreeNode)}
     * @param re
     * @param btrNode
     */
    public BTreeNode<T> addRightIndex(IndexNode<T> re, BTreeNode<T> btrNode, int maxDegree, int floor) {
        if (null != this.next) {
            throw new Error();
        }
        super.getRoom().increase();
        re.setRoom(this.getRoom());

        final BTreeNode<T> tempDataNode = super.getRoom().getEndBTreeNode();
        re.btNode = tempDataNode;
        super.getRoom().setEndBTreeNode(btrNode);

        btrNode.setLeftIndex(re);
        tempDataNode.setRightIndex(re);

        this.next = re;
        re.next = null;
        re.previous = this;
        
        return super.getRoom().diviesionIndNode(maxDegree, floor);
    }

    public void setBtNode(BTreeNode<T> b) {
        this.btNode = b;
        this.btNode.setRightIndex(this);
    }

	protected DataNode<T> find(T t) {
        final int com = t.compareTo(super.getData());
        if (com < 0)
            return btNode.find(t);
        if (this.next == null)
            return this.getNextBTreeNode().find(t);
		return this.next.find(t);
	}

    protected Node<T> next() {
        return this.next;
    }

}
