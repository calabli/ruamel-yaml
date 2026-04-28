package com.example.yaml;

import java.util.*;

/**
 * Represents a comment in YAML.
 * Similar to ruamel.yaml's Comment class but simplified for Java.
 */
public class Comment {
    private List<String> preComments;   // Comments before the node (full line comments)
    private List<String> postComments;  // Comments after the node (end-of-line comments)
    private List<String> inlineComments; // Inline comments within the node
    
    public Comment() {
        this.preComments = new ArrayList<>();
        this.postComments = new ArrayList<>();
        this.inlineComments = new ArrayList<>();
    }
    
    public List<String> getPreComments() {
        return preComments;
    }
    
    public void setPreComments(List<String> preComments) {
        this.preComments = preComments;
    }
    
    public List<String> getPostComments() {
        return postComments;
    }
    
    public void setPostComments(List<String> postComments) {
        this.postComments = postComments;
    }
    
    public List<String> getInlineComments() {
        return inlineComments;
    }
    
    public void setInlineComments(List<String> inlineComments) {
        this.inlineComments = inlineComments;
    }
    
    public void addPreComment(String comment) {
        if (comment != null && !comment.trim().isEmpty()) {
            this.preComments.add(comment.startsWith("#") ? comment : "# " + comment);
        }
    }
    
    public void addPostComment(String comment) {
        if (comment != null && !comment.trim().isEmpty()) {
            this.postComments.add(comment.startsWith("#") ? comment : "# " + comment);
        }
    }
    
    public boolean hasComments() {
        return !preComments.isEmpty() || !postComments.isEmpty() || !inlineComments.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!preComments.isEmpty()) {
            for (String c : preComments) {
                sb.append(c).append("\n");
            }
        }
        if (!inlineComments.isEmpty()) {
            for (String c : inlineComments) {
                sb.append(" ").append(c);
            }
        }
        if (!postComments.isEmpty()) {
            for (String c : postComments) {
                sb.append(" ").append(c);
            }
        }
        return sb.toString();
    }
}
