package yxl.RBTree;

import java.util.Stack;

public class RbTree<K extends Comparable<K>, V> {
    private RbTreeNode<K, V> root = null;

    public void rotateLeft(RbTreeNode<K, V> p) {
        if (p != null) {
            RbTreeNode<K, V> r = p.right;

            p.right = r.left;

            if (r.left != null) {
                r.left.parent = p;
            }

            r.parent = p.parent;
            if (p.parent == null) {
                this.root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }

            r.left = p;
            p.parent = r;

        }
    }

    public void rotateRight(RbTreeNode<K, V> p) {
        if (p != null) {
            RbTreeNode<K, V> l = p.left;

            p.left = l.right;

            if (l.right != null) {
                l.right.parent = p;
            }

            l.parent = p.parent;

            if (p.parent == null) {
                this.root = l;
            } else if (p.parent.left == p) {
                p.parent.left = l;
            } else {
                p.parent.right = l;
            }

            l.right = p;
            p.parent = l;
        }
    }

    public void add(K k, V v) {
        insertNode(new RbTreeNode<>(k, v));
    }

    private void insertNode(RbTreeNode<K, V> node) {
        RbTreeNode<K, V> troot = this.root;
        RbTreeNode<K, V> parent = null;
        while (null != troot) {
            parent = troot;
            if (node.key.compareTo(troot.key) < 0) {
                troot = troot.left;
            } else {
                troot = troot.right;
            }
        }

        node.parent = parent;

        if (null == parent) {
            this.root = node;
        } else {
            if (node.key.compareTo(parent.key) < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        }

        insertFixUp(node);
    }

    private void insertFixUp(RbTreeNode<K, V> node) {
        RbTreeNode<K, V> parent, gparent;
        while ((parent = getParent(node)) != null && isRed(parent)) {
            gparent = getParent(parent);
            /*如果其祖父节点是空怎么处理, 若父节点是祖父节点的左孩子*/
            if (parent == gparent.left) {
                RbTreeNode<K, V> uncle = gparent.right;
                if ((null != uncle) && isRed(uncle)) {
                    setColorBlack(uncle);
                    setColorBlack(parent);
                    setColorRed(gparent);
                    node = gparent;
                    continue;
                }

                if (parent.right == node) {
                    RbTreeNode<K, V> tmp;
                    rotateLeft(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                setColorBlack(parent);
                setColorRed(gparent);
                rotateRight(gparent);
            } else {
                RbTreeNode<K, V> uncle = gparent.left;
                if ((null != uncle) && isRed(uncle)) {
                    setColorBlack(uncle);
                    setColorBlack(parent);
                    setColorRed(gparent);
                    node = gparent;
                    continue;
                }

                if (parent.left == node) {
                    RbTreeNode<K, V> tmp;
                    rotateRight(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                setColorBlack(parent);
                setColorRed(gparent);
                rotateLeft(gparent);
            }
        }
        setColorBlack(this.root);
    }

    private RbTreeNode<K, V> getParent(RbTreeNode<K, V> node) {
        return node.parent;
    }

    private boolean isRed(RbTreeNode<K, V> node) {
        return node.color;
    }

    private void setColorBlack(RbTreeNode<K, V> node) {
        node.color = false;
    }

    private void setColorRed(RbTreeNode<K, V> node) {
        node.color = true;
    }

    /**
     * 功能描述：删除节点
     *
     * @author yanfengzhang
     * @date 2020-05-27 15:11
     */
    public void remove(RbTreeNode<K, V> node) {
        RbTreeNode<K, V> child, parent;
        boolean color;
        /*被删除节点左右孩子都不为空的情况*/
        if ((null != node.left) && (null != node.right)) {

            /*获取到被删除节点的后继节点*/
            RbTreeNode<K, V> replace = node;

            replace = replace.right;
            while (null != replace.left) {
                replace = replace.left;
            }

            /*node节点不是根节点*/
            if (null != getParent(node)) {
                /*node是左节点*/
                if (getParent(node).left == node) {
                    getParent(node).left = replace;
                } else {
                    getParent(node).right = replace;
                }
            } else {
                this.root = replace;
            }

            child = replace.right;
            parent = getParent(replace);
            color = getColor(replace);

            if (parent == node) {
                parent = replace;
            } else {
                if (null != child) {
                    setParent(child, parent);
                }
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;
            if (!color) {
                removeFixUp(child, parent);
            }

            node = null;
            return;
        }

        if (null != node.left) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        color = node.color;
        if (null != child) {
            child.parent = parent;
        }

        if (null != parent) {
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            this.root = child;
        }

        if (!color) {
            removeFixUp(child, parent);
        }
        node = null;
    }

    /**
     * 功能描述：删除修复
     *
     * @author yanfengzhang
     * @date 2020-05-27 15:11
     */
    private void removeFixUp(RbTreeNode<K, V> node, RbTreeNode<K, V> parent) {
        RbTreeNode<K, V> other;
        /*node不为空且为黑色，并且不为根节点*/
        while ((null == node || isBlack(node)) && (node != this.root)) {
            /*node是父节点的左孩子*/
            if (node == parent.left) {
                /*获取到其右孩子*/
                other = parent.right;
                /*node节点的兄弟节点是红色*/
                if (isRed(other)) {
                    setColorBlack(other);
                    setColorRed(parent);
                    rotateLeft(parent);
                    other = parent.right;
                }

                /*node节点的兄弟节点是黑色，且兄弟节点的两个孩子节点也是黑色*/
                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    setColorRed(other);
                    node = parent;
                    parent = getParent(node);
                } else {
                    /*node节点的兄弟节点是黑色，且兄弟节点的右孩子是红色*/
                    if (null == other.right || isBlack(other.right)) {
                        setColorBlack(other.left);
                        setColorRed(other);
                        rotateRight(other);
                        other = parent.right;
                    }
                    /*node节点的兄弟节点是黑色，且兄弟节点的右孩子是红色，左孩子是任意颜色*/
                    setColor(other, getColor(parent));
                    setColorBlack(parent);
                    setColorBlack(other.right);
                    rotateLeft(parent);
                    node = this.root;
                    break;
                }
            } else {
                other = parent.left;
                if (isRed(other)) {
                    setColorBlack(other);
                    setColorRed(parent);
                    rotateRight(parent);
                    other = parent.left;
                }

                if ((null == other.left || isBlack(other.left)) &&
                        (null == other.right || isBlack(other.right))) {
                    setColorRed(other);
                    node = parent;
                    parent = getParent(node);
                } else {
                    if (null == other.left || isBlack(other.left)) {
                        setColorBlack(other.right);
                        setColorRed(other);
                        rotateLeft(other);
                        other = parent.left;
                    }

                    setColor(other, getColor(parent));
                    setColorBlack(parent);
                    setColorBlack(other.left);
                    rotateRight(parent);
                    node = this.root;
                    break;
                }
            }
        }
        if (node != null) {
            setColorBlack(node);
        }
    }

    public boolean getColor(RbTreeNode<K, V> node) {
        return node.color;
    }

    public void setColor(RbTreeNode<K, V> node, boolean color) {
        node.color = color;
    }

    public boolean isBlack(RbTreeNode<K, V> node) {
        return !node.color;
    }

    public void setParent(RbTreeNode<K, V> child, RbTreeNode<K, V> parent) {
        child.parent = parent;
    }

    public void display() {
        midOrderTraversal(this.root);
    }

    private void midOrderTraversal(RbTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        midOrderTraversal(node.left);
        displayOneNode(node);
        midOrderTraversal(node.right);
    }

    public void displayOneNode(RbTreeNode<K, V> node) {
        System.out.println("Key:" + node.key + " Value:" + node.value + " Color:" + (node.color ? "Red" : "Black"));
    }
}
