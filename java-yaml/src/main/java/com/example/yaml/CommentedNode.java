package com.example.yaml;

import java.util.*;

/**
 * A YAML node that can hold comments.
 * Similar to ruamel.yaml's CommentedMap and CommentedSeq but simplified for Java.
 */
public class CommentedNode {
    private String key;
    private Object value;
    private Comment comment;
    private List<CommentedNode> children;
    private NodeType type;
    
    public enum NodeType {
        SCALAR,
        MAPPING,
        SEQUENCE
    }
    
    public CommentedNode() {
        this.children = new ArrayList<>();
        this.type = NodeType.SCALAR;
        this.comment = new Comment();
    }
    
    public CommentedNode(Object value) {
        this();
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
        if (value instanceof List || value instanceof Map) {
            this.type = value instanceof List ? NodeType.SEQUENCE : NodeType.MAPPING;
        }
    }
    
    public Comment getComment() {
        return comment;
    }
    
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    
    public List<CommentedNode> getChildren() {
        return children;
    }
    
    public void setChildren(List<CommentedNode> children) {
        this.children = children;
    }
    
    public void addChild(CommentedNode child) {
        this.children.add(child);
    }
    
    public NodeType getType() {
        return type;
    }
    
    public void setType(NodeType type) {
        this.type = type;
    }
    
    public boolean isMapping() {
        return type == NodeType.MAPPING;
    }
    
    public boolean isSequence() {
        return type == NodeType.SEQUENCE;
    }
    
    public boolean isScalar() {
        return type == NodeType.SCALAR;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (comment != null && !comment.getPreComments().isEmpty()) {
            for (String c : comment.getPreComments()) {
                sb.append(c).append("\n");
            }
        }
        
        if (key != null) {
            sb.append(key).append(": ");
        }
        
        if (value != null) {
            sb.append(value);
        } else if (!children.isEmpty()) {
            sb.append("\n");
            for (CommentedNode child : children) {
                String childStr = child.toString();
                for (String line : childStr.split("\n")) {
                    sb.append("  ").append(line).append("\n");
                }
            }
        }
        
        if (comment != null && !comment.getPostComments().isEmpty()) {
            for (String c : comment.getPostComments()) {
                sb.append(" ").append(c);
            }
        }
        
        return sb.toString();
    }
}
