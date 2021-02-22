package com.zh.btree;

import lombok.Getter;
import lombok.Setter;

public class BTreeNode<T extends Comparable<T>> {
    
    @Getter
    private int size;

    @Getter @Setter
    private IndexNode<T> leftIndex;

    @Getter @Setter
    private IndexNode<T> rightIndex;

    @Getter @Setter
    private Node<T> headNode;

    @Getter @Setter
    private BTreeNode<T> endBTreeNode;

    /**
     * size 增加 1
     */
    public void increase() {
        this.size++;
    }

    /**
     * size 减少 1
     */
    public void decrement() {
        this.size--;
    }

    /**
     * 分裂叶子节点
     * @param maxDegree 最大容量
     * @param floor 当前层数
     * @return 如果整棵树的 root 节点发生变化， 则返回变化后的节点
     */
    public BTreeNode<T> diviesionLeafNode(int maxDegree, int floor) {
        if (maxDegree > size)
            return null;
        
        int centre = size >> 1;
        size = centre;

        // final Node<T> headTemp = this.headNode;
        final BTreeNode<T> newBtreeNode = new BTreeNode<>();
        DataNode<T> temp = this.headNode.getDataNode();
        while (centre-- > 0) {
            temp = temp.getRightDataNode();
        }
        final Node<T> centreTemp = temp;
        while (null != temp && temp.getRoom() == this) {
            temp.setRoom(newBtreeNode);
            newBtreeNode.increase();
            temp = temp.getRightDataNode();
        }
        newBtreeNode.setHeadNode(centreTemp);


        return null;
    }
}
