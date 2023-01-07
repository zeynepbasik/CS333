package Project4;

import java.util.ArrayList;
import java.util.List;

import Project4.Node;

public class Node{
    private List<Node> children = new ArrayList<Node>();
    private Node parent = null;
    private int value;
    private int index;

    public Node() {
    }

    public Node(int value, Node parent, int index) {
        this.value = value;
        this.parent = parent;
        this.index = index;
    }

    public List<Node> getChildren() {
        return children;
    }
    
    public void removeChildren() {
    	this.children = null;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public Node getParent() {
    	return parent;
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
