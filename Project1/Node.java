package Project1;

import java.util.ArrayList;
import java.util.List;

public class Node{
    private List<Node> children = new ArrayList<Node>();
    private Node parent = null;
    private int score = 0;
    private int level;

    public Node(int score) {
        this.score = score;
    }

    public Node(int score, Node parent) {
        this.score = score;
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public Node getParent() {
    	return parent;
    }

    public void addChild(int score) {
        Node child = new Node(score);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
