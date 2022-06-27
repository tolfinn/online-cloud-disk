package com.example.clouddisk.util;

import java.util.List;

public class Node {
    String label;
    List<Node> children;
    public Node() {
    }
    public Node(String label, List<Node> children) {
        this.label = label;
        this.children = children;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
