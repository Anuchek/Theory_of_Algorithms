package io.github.anuchek.btree;

public interface Tree {
    void insert(int key);
    void remove(int key);
    Node search(int key);
    void display();
}

