package io.github.anuchek.btree;

class Node {

    int[] keys; // keys of nodes
    int MinDeg; // Minimum degree of B-tree node
    Node[] children; // Child node
    int num; // Number of keys of node
    boolean isLeaf; // True when leaf node

    // Constructor
    public Node(int deg, boolean isLeaf) {

        this.MinDeg = deg;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * this.MinDeg - 1]; // Node has 2*MinDeg-1 keys at most
        //this.keys = new int[2*this.MinDeg - 1]; // Doesn't work with - 1
        this.children = new Node[2 * this.MinDeg];
        this.num = 0;
    }

    // Find the first location index equal to or greater than key
    public int findKey(int key) {

        int index = 0;
        // The conditions for exiting the loop are:
        // 1. index == num, i.e. scan all of them once
        // 2. IDX < num, i.e. key found or greater than key
        while (index < num && keys[index] < key)
            ++index;
        return index;
    }


    public void remove(int key) {

        int index = findKey(key);
        if (index < num && keys[index] == key) { // Find key
            if (isLeaf) // key in leaf node
                removeFromLeaf(index);
            else // key is not in the leaf node
                removeFromNonLeaf(index);
        } else {
            if (isLeaf) { // If the node is a leaf node, then the node is not in the B tree
                System.out.printf("The key %d is does not exist in the tree\n", key);
                return;
            }

            // Otherwise, the key to be deleted exists in the subtree with the node as the root

            // This flag indicates whether the key exists in the subtree whose root is the last child of the node
            // When index is equal to num, the whole node is compared, and flag is true
            boolean flag = index == num;

            if (children[index].num < MinDeg) // When the child node of the node is not full, fill it first
                fill(index);


            //If the last child node has been merged, it must have been merged with the previous child node, so we recurse on the (index-1) child node.
            // Otherwise, we recurse to the (index) child node, which now has at least the keys of the minimum degree
            if (flag && index > num)
                children[index - 1].remove(key);
            else
                children[index].remove(key);
        }
    }

    public void removeFromLeaf(int index) {

        // Shift from index
        for (int i = index + 1; i < num; ++i)
            keys[i - 1] = keys[i];
        num--;
    }

    public void removeFromNonLeaf(int index) {

        int key = keys[index];

        // If the subtree before key (children[index]) has at least t keys
        // Then find the precursor 'pred' of key in the subtree with children[index] as the root
        // Replace key with 'pred', recursively delete pred in children[index]
        if (children[index].num >= MinDeg) {
            int pred = getPred(index);
            keys[index] = pred;
            children[index].remove(pred);
        }
        // If children[index] has fewer keys than MinDeg, check children[index+1]
        // If children[index+1] has at least MinDeg keys, in the subtree whose root is children[index+1]
        // Find the key's successor 'succ' and recursively delete succ in children[index+1]
        else if (children[index + 1].num >= MinDeg) {
            int succ = getSucc(index);
            keys[index] = succ;
            children[index + 1].remove(succ);
        } else {
            // If the number of keys of children[index] and children[index+1] is less than MinDeg
            // Then key and children[index+1] are combined into children[index]
            // Now children[index] contains the 2t-1 key
            // Release children[index+1], recursively delete the key in children[index]
            merge(index);
            children[index].remove(key);
        }
    }

    public int getPred(int index) { // The predecessor node is the node that always finds the rightmost node from the left subtree

        // Move to the rightmost node until you reach the leaf node
        Node cur = children[index];
        while (!cur.isLeaf)
            cur = cur.children[cur.num];
        return cur.keys[cur.num - 1];
    }

    public int getSucc(int index) { // Subsequent nodes are found from the right subtree all the way to the left

        // Continue to move the leftmost node from children[index+1] until it reaches the leaf node
        Node cur = children[index + 1];
        while (!cur.isLeaf)
            cur = cur.children[0];
        return cur.keys[0];
    }

    // Fill children[index] with less than MinDeg keys
    public void fill(int index) {

        // If the previous child node has multiple MinDeg-1 keys, borrow from them
        if (index != 0 && children[index - 1].num >= MinDeg)
            borrowFromPrev(index);
            // The latter sub node has multiple MinDeg-1 keys, from which to borrow
        else if (index != num && children[index + 1].num >= MinDeg)
            borrowFromNext(index);
        else {
            // Merge children[index] and its brothers
            // If children[index] is the last child node
            // Then merge it with the previous child node or merge it with its next sibling
            if (index != num)
                merge(index);
            else
                merge(index - 1);
        }
    }

    // Borrow a key from children[index-1] and insert it into children[index]
    public void borrowFromPrev(int index) {

        Node child = children[index];
        Node sibling = children[index - 1];

        // The last key from children[index-1] overflows to the parent node
        // The key[index-1] underflow from the parent node is inserted as the first key in children[index]
        // Therefore, sibling decreases by one and children increases by one
        for (int i = child.num - 1; i >= 0; --i) // children[index] move forward
            child.keys[i + 1] = child.keys[i];

        if (!child.isLeaf) { // Move children[index] forward when they are not leaf nodes
            for (int i = child.num; i >= 0; --i)
                child.children[i + 1] = child.children[i];
        }

        // Set the first key of the child node to the keys of the current node [index-1]
        child.keys[0] = keys[index - 1];
        if (!child.isLeaf) // Take the last child of sibling as the first child of children[index]
            child.children[0] = sibling.children[sibling.num];

        // Move the last key of sibling up to the last key of the current node
        keys[index - 1] = sibling.keys[sibling.num - 1];
        child.num += 1;
        sibling.num -= 1;
    }

    // Symmetric with borowfromprev
    public void borrowFromNext(int index) {

        Node child = children[index];
        Node sibling = children[index + 1];

        child.keys[child.num] = keys[index];

        if (!child.isLeaf)
            child.children[child.num + 1] = sibling.children[0];

        keys[index] = sibling.keys[0];

        for (int i = 1; i < sibling.num; ++i)
            sibling.keys[i - 1] = sibling.keys[i];

        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.num; ++i)
                sibling.children[i - 1] = sibling.children[i];
        }
        child.num += 1;
        sibling.num -= 1;
    }

    // Merge childre[index + 1] into childre[index]
    public void merge(int index) {

        Node child = children[index];
        Node sibling = children[index + 1];

        // Insert the last key of the current node into the MinDeg-1 position of the child node
        child.keys[MinDeg - 1] = keys[index];

        // keys: children[index+1] copy to children[index]
        for (int i = 0; i < sibling.num; ++i)
            child.keys[i + MinDeg] = sibling.keys[i];

        // children: children[index+1] copy to children[index]
        if (!child.isLeaf) {
            for (int i = 0; i <= sibling.num; ++i)
                child.children[i + MinDeg] = sibling.children[i];
        }

        // Move keys forward, not gap caused by moving keys[index] to children[index]
        for (int i = index + 1; i < num; ++i)
            keys[i - 1] = keys[i];
        // Move the corresponding child node forward
        for (int i = index + 2; i <= num; ++i)
            children[i - 1] = children[i];

        child.num += sibling.num + 1;
        num--;
    }

    //insert method
    public void insertNotFull(int key) {

        int i = num - 1; // Initialize i as the rightmost index

        if (isLeaf) { // When it is a leaf node
            // Find the location where the new key should be inserted
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i]; // keys backward shift
                i--;
            }
            keys[i + 1] = key;
            num = num + 1;
        } else {
            // Find the child node location that should be inserted
            while (i >= 0 && keys[i] > key)
                i--;
            if (children[i + 1].num == 2 * MinDeg - 1) { // When the child node is full
                splitChild(i + 1, children[i + 1]);
                // After splitting, the key in the middle of the child node moves up, and the child node splits into two
                if (keys[i + 1] < key)
                    i++;
            }
            children[i + 1].insertNotFull(key);
        }
    }


    public void splitChild(int i, Node y) {

        // First, create a node to hold the keys of MinDeg-1 of y
        Node z = new Node(y.MinDeg, y.isLeaf);
        z.num = MinDeg - 1;

        // Pass the properties of y to z
        for (int j = 0; j < MinDeg - 1; j++)
            z.keys[j] = y.keys[j + MinDeg];
        if (!y.isLeaf) {
            for (int j = 0; j < MinDeg; j++)
                z.children[j] = y.children[j + MinDeg];
        }
        y.num = MinDeg - 1;

        // Insert a new child into the child
        for (int j = num; j >= i + 1; j--)
            children[j + 1] = children[j];
        children[i + 1] = z;

        // Move a key in y to this node
        for (int j = num - 1; j >= i; j--)
            keys[j + 1] = keys[j];
        keys[i] = y.keys[MinDeg - 1];

        num = num + 1;
    }

    //display method
    public void traverse() {
        int i;
        for (i = 0; i < num; i++) {
            if (!isLeaf)
                children[i].traverse();
            System.out.printf(" %d", keys[i]);
        }

        if (!isLeaf) {
            children[i].traverse();
        }
    }

    //search method
    public Node search(int key) {
        int i = 0;
        while (i < num - 1 && key > keys[i]){ // Corrected: num - 1
            i++;
        }
        if (keys[i] == key){
            return this;
        }
        if (isLeaf) {
            return null;
        }
        return children[i].search(key);
    }
}

