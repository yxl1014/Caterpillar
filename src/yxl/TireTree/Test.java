package yxl.TireTree;

/**
 * @Author: yxl
 * @Date: 2022/7/11 10:56
 */
public class Test {
    public static void main(String[] args) {
        TireTree tireTree=new TireTree();
        tireTree.insertWord("aaa");
        tireTree.insertWord("abc");
        tireTree.insertWord("aab");
        tireTree.insertWord("aba");
        System.out.println(tireTree.search("abc"));
        System.out.println(tireTree.search("acv"));
    }
}
