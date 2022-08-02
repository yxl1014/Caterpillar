package yxl.List.SkipList;

/**
 * @Author: yxl
 * @Date: 2022/7/26 10:42
 */
public class SkipNode {
    private int data;
    private SkipNode[] next;

    public SkipNode(int data, int level) {
        this.data = data;
        this.next = new SkipNode[level];
    }

    public SkipNode[] getNext() {
        return next;
    }

    public void setNext(SkipNode[] next) {
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
