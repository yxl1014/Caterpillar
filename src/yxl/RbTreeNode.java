package yxl;

public class RbTreeNode<K extends Comparable<K>, V> {
    public K key;
    public V value;
    public RbTreeNode<K, V> left;
    public RbTreeNode<K, V> right;
    public RbTreeNode<K, V> parent;
    public boolean color;//true-->red  false-->black

    public RbTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.color = true;
    }

    public RbTreeNode() {
    }
}
