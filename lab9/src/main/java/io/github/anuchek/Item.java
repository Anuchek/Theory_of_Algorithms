package io.github.anuchek;

public class Item {
    private int value;
    private int size;
    private String name;

    public Item(String name, int value, int size) {
        this.value = value;
        this.size = size;
        this.name = name;
    }

    public double valuePerSize(){
        return value / (double) size;
    }

    public int getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" + name + " - price: " + value + ", size: " + size + '}' + "  vPS: " + valuePerSize() + "\n";
    }
}
