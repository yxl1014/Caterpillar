package yxl.List.CircularQueue;

/**
 * @Author: yxl
 * @Date: 2022/8/2 10:28
 * 执行用时：4 ms , 在所有 Java 提交中击败了100.00%的用户
 * 内存消耗：42.2 MB, 在所有 Java 提交中击败了9.98%的用户
 */
public class MyCircularQueue {

    private final int[] data;
    private int size;

    private final int len;
    private int first;
    private int last;

    public MyCircularQueue(int k) {
        this.data = new int[k];
        this.size = 0;
        this.len = k;
        this.first = -1;
        this.last = -1;
    }

    public boolean enQueue(int value) {
        if (this.first == -1) {
            this.first = this.last = 0;
            this.size++;
            this.data[first] = value;
        } else {
            if (this.size < this.len) {
                if (++this.last >= this.len) {
                    this.last = 0;
                }
                this.data[this.last] = value;
                this.size++;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean deQueue() {
        if (this.size == 0) {
            return false;
        }
        if (++this.first >= this.len) {
            this.first = 0;
        }
        this.size--;
        return true;
    }

    public int Front() {
        return isEmpty() ? -1 : this.data[this.first];
    }

    public int Rear() {
        return isEmpty() ? -1 : this.data[this.last];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.len;
    }
}
