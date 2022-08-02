package yxl.Tree.TireTree;


/**
 * @Author: yxl
 * @Date: 2022/7/11 10:48
 */
public class TireTree {
    private TireNode root;

    public TireTree() {
        this.root = new TireNode();
    }

    public void insertWord(String word) {
        int len = word.length();
        TireNode temp = this.root;
        for (int i = 0; i < len; i++) {
            int idx = word.charAt(i) - 'a';
            if (temp.getChildren()[idx] == null) {
                temp.getChildren()[idx] = new TireNode();
            }
            temp = temp.getChildren()[idx];
        }
        temp.setSuccess(true);
    }

    public boolean search(String word){
        int len = word.length();
        TireNode temp = this.root;
        for (int i = 0; i < len; i++) {
            int idx = word.charAt(i) - 'a';
            if (temp.getChildren()[idx] == null) {
                return false;
            }
            temp = temp.getChildren()[idx];
        }
        return temp.isSuccess();
    }
}
