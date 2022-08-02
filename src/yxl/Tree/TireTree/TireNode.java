package yxl.Tree.TireTree;

/**
 * @Author: yxl
 * @Date: 2022/7/11 10:42
 */
public class TireNode {
    private boolean isSuccess;
    private TireNode[] children;

    public TireNode(){
        this.isSuccess=false;
        this.children=new TireNode[26];
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public TireNode[] getChildren() {
        return children;
    }

    public void setChildren(TireNode[] children) {
        this.children = children;
    }
}
