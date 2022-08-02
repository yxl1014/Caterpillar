package yxl.List.SkipList;

/**
 * @Author: yxl
 * @Date: 2022/7/26 11:14
 */
public class Test {
    public static void main(String[] args) {
        SkipList list = new SkipList(32);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);

        System.out.println(list.search(1));
        System.out.println(list.search(10));
        System.out.println(list.remove(1));
        System.out.println(list.search(1));
    }
}
