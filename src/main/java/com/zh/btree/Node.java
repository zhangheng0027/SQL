package com.zh.btree;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class Node<T extends Comparable<T>> {
    
    @Setter @Getter @NonNull
    private T data;

    @Setter @Getter @NonNull
    private BTreeNode<T> room;

    public DataNode<T> getDataNode() {
        if (this instanceof DataNode) {
            return (DataNode<T>)this;
        }
        throw new Error();
    }

    public IndexNode<T> getIndexNode() {
        if (this instanceof IndexNode) 
            return (IndexNode<T>) this;
        throw new Error();
    }

}