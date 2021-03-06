package com.zh.btree;

import lombok.Getter;

public class DataNode<T extends Comparable<T>> extends Node<T> {
    
    public DataNode(T t) {
        super(t);
    }

    @Getter
    private DataNode<T> leftDataNode;

    @Getter
    private DataNode<T> rightDataNode;

    public void addLeftData(DataNode<T> dn) {
        DataNode<T> temp = this.leftDataNode;
        dn.setRoom(this.getRoom());
        dn.getRoom().increase();

        this.leftDataNode = dn;
        dn.leftDataNode = temp;
        dn.rightDataNode = this;
        if (null != temp) 
            temp.rightDataNode = dn;
        
        // 当前节点为 BTreeNode 的头节点
        if (this.getRoom().getHeadNode().getDataNode() == this) {
            this.getRoom().setHeadNode(dn);
        }


    }

    public void addRightData(DataNode<T> dn) {
        DataNode<T> temp = this.rightDataNode;
        dn.setRoom(this.getRoom());
        dn.getRoom().increase();

        this.rightDataNode = dn;
        dn.rightDataNode = temp;
        if (temp != null)
            temp.leftDataNode = dn;
        dn.leftDataNode = this;
    }

	protected DataNode<T> find(T t) {
        if (t.compareTo(super.getData()) < 0) {
            if (null == this.leftDataNode)
                return this;
            return this.leftDataNode;
        }
        if (null == this.rightDataNode)
            return this;
		return this.rightDataNode.find(t);
	}

    protected Node<T> next() {
        return this.rightDataNode;
    }
}
