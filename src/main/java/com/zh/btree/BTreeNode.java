package com.zh.btree;

import lombok.Getter;
import lombok.Setter;

public class BTreeNode<T extends Comparable<T>> {
    
    // 是否是叶子节点
    @Getter @Setter
    private boolean leaf = false;

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
        IndexNode<T> iNode = new IndexNode<>(centreTemp.getData());

        if (floor == 0) {
            BTreeNode<T> root = new BTreeNode<>();
            root.increase();
            root.setHeadNode(iNode);
            iNode.setRoom(root);

            iNode.setBtNode(this);
            this.rightIndex = iNode;

            root.setEndBTreeNode(newBtreeNode);
            newBtreeNode.setLeftIndex(iNode);

            return root;
        }

        if (null == this.rightIndex) {
            return this.leftIndex.addRightIndex(iNode, newBtreeNode, maxDegree, floor - 1);
        } else {
            return this.rightIndex.addLeftIndex(iNode, newBtreeNode, maxDegree, floor - 1);
        }
    }

    /**
     * 分裂节点
     * @param maxDegree
     * @param floor
     */
    public BTreeNode<T> diviesionIndNode(int maxDegree, int floor) {
        if (maxDegree > size)
            return null;

        int centre = size >> 1;
        size = centre;

        BTreeNode<T> newBTreeNode = new BTreeNode<>();
        IndexNode<T> temp = this.headNode.getIndexNode();
        while (centre-- > 0)
            temp = temp.getNext();

        final IndexNode<T> centretemp = temp;
        
        temp = temp.getNext();
        newBTreeNode.setHeadNode(temp);
        while (null != temp && temp.getRoom() == this) {
            newBTreeNode.increase();
            temp.setRoom(newBTreeNode);
            temp = temp.getNext();
        }

        newBTreeNode.setEndBTreeNode(this.getEndBTreeNode());

        this.setEndBTreeNode(centretemp.getBtNode());
        centretemp.getBtNode().setRightIndex(null);

        centretemp.getPrevious().setNext(null);
        centretemp.setPrevious(null);

        final IndexNode<T> newHead = newBTreeNode.headNode.getIndexNode();
        newHead.getBtNode().setLeftIndex(null);

        centretemp.getNext().setPrevious(null);
        centretemp.setNext(null);

        if (floor == 0) {
            BTreeNode<T> root = new BTreeNode<>();
            root.increase();
            root.setHeadNode(centretemp);
            root.setEndBTreeNode(newBTreeNode);
            centretemp.setBtNode(this);
            
            this.setRightIndex(centretemp);
            newBTreeNode.setLeftIndex(centretemp);
            return root;
        }

        if (null == this.rightIndex) {
            this.leftIndex.addRightIndex(centretemp, newBTreeNode, maxDegree, floor - 1);
        } else {
            this.rightIndex.addLeftIndex(centretemp, newBTreeNode, maxDegree, floor - 1);
        }
        return null;
    }

    /**
     * 返回和目标值最接近的节点
     * @param t
     * @return
     */
	protected DataNode<T> find(T t) {
        return headNode.find(t);
	}
}
