package yxl.List.SkipList;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author: yxl
 * @Date: 2022/7/26 10:41
 */
public class SkipList {
    private SkipNode head;
    private int level;
    private int maxLevel;
    private final double LP = 0.25;
    private Random RL;

    public SkipList(int maxLevel) {
        this.maxLevel = maxLevel;
        this.level = 0;
        this.head = new SkipNode(-1, maxLevel);
        this.RL = new Random();
    }

    private int randomLevel() {
        int lv = 1;
        while (this.RL.nextDouble() < this.LP && lv < this.maxLevel) {
            lv++;
        }
        return lv;
    }

    public void add(int num) {
        SkipNode[] temp = new SkipNode[this.maxLevel];
        Arrays.fill(temp, head);
        SkipNode p = this.head;
        //从最顶层开始遍历
        for (int i = this.level - 1; i >= 0; i--) {
            //若下一个节点存在                 且下一个节点的只小于插入的数
            while (p.getNext()[i] != null && p.getNext()[i].getData() < num) {
                //p指向当前层的下一个节点
                p = p.getNext()[i];
            }
            //若下一个节点为null 或者下一个节点得知不小于插入的值
            //则将最后一个值存到temp中
            temp[i] = p;
        }
        //通过随机因子随机生成一个level
        int lv = randomLevel();
        //改变当前level
        this.level = Math.max(lv, this.level);
        //new一个节点
        SkipNode newNode = new SkipNode(num, lv);
        //list的顺序插入步骤
        for (int i = 0; i < lv; i++) {
            newNode.getNext()[i] = temp[i].getNext()[i];
            temp[i].getNext()[i] = newNode;
        }
    }

    public boolean remove(int num) {
        SkipNode[] temp = new SkipNode[this.maxLevel];
        SkipNode p = this.head;

        //从最顶层开始遍历
        for (int i = level - 1; i >= 0; i--) {
            //若下一个节点存在                 且下一个节点的只小于插入的数
            while (p.getNext()[i] != null && p.getNext()[i].getData() < num) {
                //p指向当前层的下一个节点
                p = p.getNext()[i];
            }
            //若下一个节点为null 或者下一个节点得知不小于插入的值
            //则将最后一个值存到temp中
            temp[i] = p;
        }
        //因为上面的操作，所以p一定是小于目标数据的最后一个数据，所以p的下一个一定是需要删除的数
        p = p.getNext()[0];
        // 若p不存在   或者  p的值不是需要删除的数
        if (p == null || p.getData() != num) {
            //那么该数不存在
            return false;
        }
        //因为进行了上面的操作，所以temp中存的都是每层中p的前面一个节点
        //所以从最底层开始向上进行删除list节点操作
        for (int i = 0; i < level; i++) {
            //如果temp在i层的下一个节点不是p，说明p删除完毕，退出
            if (temp[i].getNext()[i] != p) {
                break;
            }
            //常规的删除list节点操作
            temp[i].getNext()[i] = p.getNext()[i];
        }
        // 更新当前的level
        //从当前level开始  level一定大于1 且  level的下一级有东西，如果没东西说明这一层被删除了
        while (level > 1 && head.getNext()[level - 1] == null) {
            //level--
            level--;
        }
        return true;
    }


    public boolean search(int target) {
        SkipNode temp = this.head;
        //从最顶层开始遍历
        for (int i = level - 1; i >= 0; i--) {
            //     若下一个节点存在                 且下一个节点的只小于插入的数
            while (temp.getNext()[i] != null && temp.getNext()[i].getData() < target) {
                temp = temp.getNext()[i];
            }
        }
        //由于跳表是有序的 所以temp的下一个节点一定是target
        temp = temp.getNext()[0];
        //若下一个 为空或者 与目标值不同则不存在  否则就存在
        if (temp != null && temp.getData() == target) {
            return true;
        }
        return false;
    }
}
