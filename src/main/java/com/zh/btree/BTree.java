package com.zh.btree;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BTree<T extends Comparable<T>> {
    
    // 根节点
    @Setter @Getter
    private BTreeNode<T> root;

    @Setter @Getter
    private int size;

    @Getter
    private int floor;

    @Getter
    private int maxDegree;

    public BTree(int max) {
        if (max < 3)
            max = 3;
        this.maxDegree = max;
        this.size = 0;
        this.floor = 0;
    }

    public static void main(String[] args) {
        BTree<Integer> btree = new BTree<>(3);
        for (int i = 20; i > 0; i--) {
            btree.add(i);
            System.out.println(i + " " + btree.root.getHeadNode().toString());
        }
        System.out.println(btree.root);
        System.out.println(btree.disPlay());
    }

    /**
     * 如果t 小于当前树的最小值，则返回最小值的节点。
     * 否则返回 等于或小于t 的节点
     * @param t
     * @return
     */
    public DataNode<T> find(T t) {
        return root.find(t);
    }

    public boolean add(T t) {

        if (size == 0) {
            DataNode<T> d = new DataNode<>(t);
            BTreeNode<T> room = new BTreeNode<>();

            room.setLeaf(true);
            room.increase();
            room.setHeadNode(d);
            d.setRoom(room);
            this.root = room;
            this.size++;
            return true;
        }

        DataNode<T> dataN = find(t);
        final int com = t.compareTo(dataN.getData());
        if (com == 0)
            return false;
        DataNode<T> d = new DataNode<>(t);
        if (com > 0)
            dataN.addRightData(d);
        else dataN.addLeftData(d);
        size++;
        BTreeNode<T> rootT = d.getRoom().diviesionLeafNode(maxDegree, floor);
        if (null != rootT) {
            this.root = rootT;
            floor++;
        }
        return true;
    }

    public String disPlay() {
        if (size <= 1)
            return this.root + "";
        List<BTreeNode<T>> lb = new ArrayList<>(size);
        StringBuffer sb = new StringBuffer(size << 4);
        lb.add(this.root);
        for (int i = 0; i < lb.size(); i++) {
            BTreeNode<T> btn = lb.get(i);
            sb.append("{ " + btn.toString() + " }, ");
            if (btn.isLeaf()) {
                continue;
            }
            IndexNode<T> temp = btn.getHeadNode().getIndexNode();
            while (null != temp && temp.getRoom() == btn) {
                lb.add(temp.getBtNode());
                temp = temp.getNext();
            }
            lb.add(btn.getEndBTreeNode());
        }
        return sb.toString();
    }
}
