package io.github.anuchek.btree;

public class BTree {
    // max children per B-tree node = M-1
    // (must be even and greater than 2)
    private int T;
    private Node root;

    //constructor
    public BTree(int t) {
        T = t;
        root = new Node();
        root.n = 0;
        root.leaf = true;
    }

    public class Node {
        int n; // number of children
        int[] key = new int[2 * T - 1];
        Node[] child = new Node[2 * T];
        boolean leaf = true;


        public int Find(int k) {
            for (int i = 0; i < this.n; i++) {
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
        z.n = T - 1;
        if (T - 1 >= 0) System.arraycopy(node2.key, T, z.key, 0, T - 1);
        if (!node2.leaf) {
            if (T >= 0) System.arraycopy(node2.child, T, z.child, 0, T);
        }
        node2.n = T - 1;
        if (node1.n + 1 - (pos + 1) >= 0) System.arraycopy(node1.child, pos + 1, node1.child, pos + 1 + 1, node1.n + 1 - (pos + 1));
        node1.child[pos + 1] = z;

        if (node1.n - pos >= 0) System.arraycopy(node1.key, pos, node1.key, pos + 1, node1.n - pos);
        node1.key[pos] = node2.key[T - 1];
        node1.n = node1.n + 1;
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
        for (i = 0; i < node.n; i++) {
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
        if (root.n == 2 * T - 1) {
            Node node = new Node();
            this.root = node;
            node.leaf = false;
            node.n = 0;
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
            for (i = node.n - 1; i >= 0 && k < node.key[i]; i--) {
                node.key[i + 1] = node.key[i];
            }
            node.key[i + 1] = k;
            node.n = node.n + 1;
        } else {
            int i;
            for (i = node.n - 1; i >= 0 && k < node.key[i]; i--) {
            }
            i++;
            Node tmp = node.child[i];
            if (tmp.n == 2 * T - 1) {
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
                for (i = 0; i < node.n && node.key[i] != key; i++) {
                }
                for (; i < node.n; i++) {
                    if (i != 2 * T - 2) {
                        node.key[i] = node.key[i + 1];
                    }
                }
                node.n--;
                return;
            }
            if (!node.leaf) {
                Node pred = node.child[pos];
                int predKey;
                if (pred.n >= T) {
                    for (;;) {
                        if (pred.leaf) {
                            //System.out.println(pred.n);
                            predKey = pred.key[pred.n - 1];
                            break;
                        } else {
                            pred = pred.child[pred.n];
                        }
                    }
                    deletion(pred, predKey);
                    node.key[pos] = predKey;
                    return;
                }

                Node nextNode = node.child[pos + 1];
                if (nextNode.n >= T) {
                    int nextKey = nextNode.key[0];
                    if (!nextNode.leaf) {
                        nextNode = nextNode.child[0];
                        for (;;) {
                            if (nextNode.leaf) {
                                nextKey = nextNode.key[nextNode.n - 1];
                                break;
                            } else {
                                nextNode = nextNode.child[nextNode.n];
                            }
                        }
                    }
                    deletion(nextNode, nextKey);
                    node.key[pos] = nextKey;
                    return;
                }

                int temp = pred.n + 1;
                pred.key[pred.n++] = node.key[pos];
                for (int i = 0, j = pred.n; i < nextNode.n; i++) {
                    pred.key[j++] = nextNode.key[i];
                    pred.n++;
                }
                for (int i = 0; i < nextNode.n + 1; i++) {
                    pred.child[temp++] = nextNode.child[i];
                }

                node.child[pos] = pred;
                for (int i = pos; i < node.n; i++) {
                    if (i != 2 * T - 2) {
                        node.key[i] = node.key[i + 1];
                    }
                }
                for (int i = pos + 1; i < node.n + 1; i++) {
                    if (i != 2 * T - 1) {
                        node.child[i] = node.child[i + 1];
                    }
                }
                node.n--;
                if (node.n == 0) {
                    if (node == root) {
                        root = node.child[0];
                    }
                    node = node.child[0];
                }
                deletion(pred, key);
            }
        } else {
            for (pos = 0; pos < node.n; pos++) {
                if (node.key[pos] > key) {
                    break;
                }
            }
            Node tempNode = node.child[pos];
            if(!(tempNode == null)) {
                if (tempNode.n >= T) {
                    deletion(tempNode, key);
                }
                return;
            }
            if (true) {
                Node nb;
                int devider;

                if (pos != node.n && node.child[pos + 1].n >= T) {
                    devider = node.key[pos];
                    nb = node.child[pos + 1];
                    node.key[pos] = nb.key[0];
                    tempNode.key[tempNode.n++] = devider;
                    tempNode.child[tempNode.n] = nb.child[0];
                    if (nb.n - 1 >= 0) System.arraycopy(nb.key, 1, nb.key, 0, nb.n - 1);
                    if (nb.n >= 0) System.arraycopy(nb.child, 1, nb.child, 0, nb.n);
                    nb.n--;
                    deletion(tempNode, key);
                } else if (pos != 0 && node.child[pos - 1].n >= T) {

                    devider = node.key[pos - 1];
                    nb = node.child[pos - 1];
                    node.key[pos - 1] = nb.key[nb.n - 1];
                    Node child = nb.child[nb.n];
                    nb.n--;

                    if (tempNode.n >= 0) System.arraycopy(tempNode.key, 0, tempNode.key, 1, tempNode.n);
                    tempNode.key[0] = devider;
                    if (tempNode.n + 1 >= 0) System.arraycopy(tempNode.child, 0, tempNode.child, 1, tempNode.n + 1);
                    tempNode.child[0] = child;
                    tempNode.n++;
                    deletion(tempNode, key);
                } else {
                    Node left;
                    Node right;
                    boolean last = false;
                    if (pos != node.n) {
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
                    if (node.n - 1 - pos >= 0) System.arraycopy(node.key, pos + 1, node.key, pos, node.n - 1 - pos);
                    if (node.n - (pos + 1) >= 0) System.arraycopy(node.child, pos + 1 + 1, node.child, pos + 1, node.n - (pos + 1));
                    node.n--;
                    left.key[left.n++] = devider;

                    for (int i = 0, j = left.n; i < right.n + 1; i++, j++) {
                        if (i < right.n) {
                            left.key[j] = right.key[i];
                        }
                        left.child[j] = right.child[i];
                    }
                    left.n += right.n;
                    if (node.n == 0) {
                        if (node == root) {
                            root = node.child[0];
                        }
                        node = node.child[0];
                    }
                    deletion(left, key);
                }
            }
        }
    }

    // ______________Display method_________________
    public void display() {
        show(root);
    }

    private void show(Node x) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            System.out.print(x.key[i] + " ");
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                show(x.child[i]);
            }
        }
    }
}

