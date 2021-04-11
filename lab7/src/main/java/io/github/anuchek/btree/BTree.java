package io.github.anuchek.btree;

import java.util.Objects;

public class BTree {
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private final int Degree = 4;
    private Node root;
    //constructor
    public BTree() {
        root = new Node();
        root.numberOfChildren = 0;
        root.leaf = true;
    }

    // _______________Search method_______________
    public boolean search(int k) {
        return this.searcher(root, k) != null;
    }

    // Search the key
    private BTree.Node searcher(BTree.Node node, int key) {
        int i;
        if (node == null)
            return null;
        for (i = 0; i < node.numberOfChildren; i++) {
            if (key < node.key[i]) {
                break;
            }
            if (key == node.key[i]) {
                return node;
            }
        }
        if (node.leaf) {
            return null;
        } else {
            return searcher(node.child[i], key);
        }
    }

    public class Node {
        int numberOfChildren; // number of children
        int[] key = new int[2 * Degree - 1];
        Node[] child = new Node[2 * Degree];
        boolean leaf = true;


        public int Find(int k) {
            for (int i = 0; i < this.numberOfChildren; i++) {
                if (this.key[i] == k) {
                    return i;
                }
            }
            return -1;
        }
    }

    // Split function
    private void split(Node node1, int pos, Node node2) {
        Node z = new Node();
        z.leaf = node2.leaf;
        z.numberOfChildren = Degree - 1;
        if (Degree - 1 >= 0) {
            System.arraycopy(node2.key, Degree, z.key, 0, Degree - 1);
        }
        if (!node2.leaf) {
            if (Degree >= 0) {
                System.arraycopy(node2.child, Degree, z.child, 0, Degree);
            }
        }
        node2.numberOfChildren = Degree - 1;
        if (node1.numberOfChildren + 1 - (pos + 1) >= 0) {
            System.arraycopy(node1.child, pos + 1, node1.child, pos + 1 + 1, node1.numberOfChildren + 1 - (pos + 1));}
        node1.child[pos + 1] = z;

        if (node1.numberOfChildren - pos >= 0) {
            System.arraycopy(node1.key, pos, node1.key, pos + 1, node1.numberOfChildren - pos);
        }
        node1.key[pos] = node2.key[Degree - 1];
        node1.numberOfChildren = node1.numberOfChildren + 1;
    }

    // _______________Search method_______________
    public boolean containsBool(int k) {
        return this.search(root, k) != null;
    }

    public void containsStr(int k) {
        if (this.search(root, k) != null) {
            System.out.println("There is such element");
        } else {
            System.out.println("There no such element");
        }
    }
    // Search the key
    private Node search(Node node, int key) {
        int i;
        if (node == null)
            return null;
        for (i = 0; i < node.numberOfChildren; i++) {
            if (key < node.key[i]) {
                break;
            }
            if (key == node.key[i]) {
                return node;
            }
        }
        if (node.leaf) {
            return null;
        } else {
            return search(node.child[i], key);
        }
    }


    // ______________insertion method______________
    // Insert the key
    public void insert(final int key) {
        Node root = this.root;
        if (root.numberOfChildren == 2 * Degree - 1) {
            Node node = new Node();
            this.root = node;
            node.leaf = false;
            node.numberOfChildren = 0;
            node.child[0] = root;
            split(node, 0, root);
            insertion(node, key);
        } else {
            insertion(root, key);
        }
    }

    // Insert the node
    private void insertion(Node node, int k) {

        if (node.leaf) {
            int i;
            for (i = node.numberOfChildren - 1; i >= 0 && k < node.key[i]; i--) {
                node.key[i + 1] = node.key[i];
            }
            node.key[i + 1] = k;
            node.numberOfChildren = node.numberOfChildren + 1;
        } else {
            int i;
            for (i = node.numberOfChildren - 1; i >= 0 && k < node.key[i]; i--) {
            }
            i++;
            Node tmp = node.child[i];
            if (tmp.numberOfChildren == 2 * Degree - 1) {
                split(node, i, tmp);
                if (k > node.key[i]) {
                    i++;
                }
            }
            insertion(node.child[i], k);
        }
    }

    // delete method
    public void delete(int key) {
        Node node = search(root, key);
        if (node == null) {
            return;
        }
        deletion(root, key);
    }

    private void deletion(Node node, int key) {
        int pos = node.Find(key);
        if (pos != -1) {
            if (node.leaf) {
                int i;
                for (i = 0; i < node.numberOfChildren && node.key[i] != key; i++) {
                }
                for (; i < node.numberOfChildren; i++) {
                    if (i != 2 * Degree - 2) {
                        node.key[i] = node.key[i + 1];
                    }
                }
                node.numberOfChildren--;
                return;
            }
            Node pred = node.child[pos];
            int predKey;
            if (pred.numberOfChildren >= Degree) {
                for (;;) {
                    if (pred.leaf) {
                        //System.out.println(pred.n);
                        predKey = pred.key[pred.numberOfChildren - 1];
                        break;
                    } else {
                        pred = pred.child[pred.numberOfChildren];
                    }
                }
                deletion(pred, predKey);
                node.key[pos] = predKey;
                return;
            }

            Node nextNode = node.child[pos + 1];
            if (nextNode.numberOfChildren >= Degree) {
                int nextKey = nextNode.key[0];
                if (!nextNode.leaf) {
                    nextNode = nextNode.child[0];
                    for (;;) {
                        if (nextNode.leaf) {
                            nextKey = nextNode.key[nextNode.numberOfChildren - 1];
                            break;
                        } else {
                            nextNode = nextNode.child[nextNode.numberOfChildren];
                        }
                    }
                }
                deletion(nextNode, nextKey);
                node.key[pos] = nextKey;
                return;
            }

            int temp = pred.numberOfChildren + 1;
            pred.key[pred.numberOfChildren++] = node.key[pos];
            for (int i = 0, j = pred.numberOfChildren; i < nextNode.numberOfChildren; i++) {
                pred.key[j++] = nextNode.key[i];
                pred.numberOfChildren++;
            }
            for (int i = 0; i < nextNode.numberOfChildren + 1; i++) {
                pred.child[temp++] = nextNode.child[i];
            }

            node.child[pos] = pred;
            for (int i = pos; i < node.numberOfChildren; i++) {
                if (i != 2 * Degree - 2) {
                    node.key[i] = node.key[i + 1];
                }
            }
            for (int i = pos + 1; i < node.numberOfChildren + 1; i++) {
                if (i != 2 * Degree - 1) {
                    node.child[i] = node.child[i + 1];
                }
            }
            node.numberOfChildren--;
            if (node.numberOfChildren == 0) {
                if (node == root) {
                    root = node.child[0];
                }
                node = node.child[0];
            }
            deletion(pred, key);
        } else {
            for (pos = 0; pos < node.numberOfChildren; pos++) {
                if (node.key[pos] > key) {
                    break;
                }
            }
            Node tempNode = node.child[pos];
            if(!(tempNode == null)) {
                if (tempNode.numberOfChildren >= Degree) {
                    deletion(tempNode, key);
                }
                return;
            }
            Node nb;
            int devider;

            if (pos != node.numberOfChildren && node.child[pos + 1].numberOfChildren >= Degree) {
                devider = node.key[pos];
                nb = node.child[pos + 1];
                node.key[pos] = nb.key[0];
                tempNode.key[tempNode.numberOfChildren++] = devider;
                tempNode.child[tempNode.numberOfChildren] = nb.child[0];
                if (nb.numberOfChildren - 1 >= 0) System.arraycopy(nb.key, 1, nb.key, 0, nb.numberOfChildren - 1);
                if (nb.numberOfChildren >= 0) System.arraycopy(nb.child, 1, nb.child, 0, nb.numberOfChildren);
                nb.numberOfChildren--;
                deletion(tempNode, key);
            } else if (pos != 0 && node.child[pos - 1].numberOfChildren >= Degree) {
                devider = node.key[pos - 1];
                nb = node.child[pos - 1];
                node.key[pos - 1] = nb.key[nb.numberOfChildren - 1];
                Node child = nb.child[nb.numberOfChildren];
                nb.numberOfChildren--;

                if (Objects.requireNonNull(tempNode).numberOfChildren >= 0) System.arraycopy(tempNode.key, 0, tempNode.key, 1, tempNode.numberOfChildren);
                tempNode.key[0] = devider;
                if (tempNode.numberOfChildren + 1 >= 0) System.arraycopy(tempNode.child, 0, tempNode.child, 1, tempNode.numberOfChildren + 1);
                tempNode.child[0] = child;
                tempNode.numberOfChildren++;
                deletion(tempNode, key);
            } else {
                Node left;
                Node right;
                boolean last = false;
                if (pos != node.numberOfChildren) {
                    devider = node.key[pos];
                    left = node.child[pos];
                    right = node.child[pos + 1];
                } else {
                    devider = node.key[pos - 1];
                    right = node.child[pos];
                    left = node.child[pos - 1];
                    last = true;
                    pos--;
                }
                if (node.numberOfChildren - 1 - pos >= 0) System.arraycopy(node.key, pos + 1, node.key, pos, node.numberOfChildren - 1 - pos);
                if (node.numberOfChildren - (pos + 1) >= 0) System.arraycopy(node.child, pos + 1 + 1, node.child, pos + 1, node.numberOfChildren - (pos + 1));
                node.numberOfChildren--;
                left.key[left.numberOfChildren++] = devider;

                for (int i = 0, j = left.numberOfChildren; i < right.numberOfChildren + 1; i++, j++) {
                    if (i < right.numberOfChildren) {
                        left.key[j] = right.key[i];
                    }
                    left.child[j] = right.child[i];
                }
                left.numberOfChildren += right.numberOfChildren;
                if (node.numberOfChildren == 0) {
                    if (node == root) {
                        root = node.child[0];
                    }
                    node = node.child[0];
                }
                deletion(left, key);
            }
        }
    }

    // ______________Display method_________________
    public void display() {
        show(root);
    }

    private void show(Node x) {
        assert (x == null);
        for (int i = 0; i < x.numberOfChildren; i++) {
            System.out.print(x.key[i] + " ");
        }
        if (!x.leaf) {
            for (int i = 0; i < x.numberOfChildren + 1; i++) {
                show(x.child[i]);
            }
        }
    }
}

