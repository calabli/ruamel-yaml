package com.example.yaml;

import java.util.*;
import java.io.*;

/**
 * A YAML dumper that preserves comments during output.
 * Similar to ruamel.yaml's RoundTripDumper but simplified for Java.
 */
public class CommentedYamlDumper {
    
    private int indent = 2;
    private int currentIndent = 0;
    
    public CommentedYamlDumper() {
    }
    
    public CommentedYamlDumper(int indent) {
        this.indent = indent;
    }
    
    /**
     * Dump a CommentedNode to YAML string, preserving comments
     */
    public String dump(CommentedNode node) {
        StringBuilder sb = new StringBuilder();
        dumpNode(node, sb, 0);
        return sb.toString();
    }
    
    private void dumpNode(CommentedNode node, StringBuilder sb, int level) {
        if (node == null) {
            return;
        }
        
        // Write pre-comments
        if (node.getComment() != null && !node.getComment().getPreComments().isEmpty()) {
            for (String comment : node.getComment().getPreComments()) {
                writeIndent(sb, level);
                sb.append(comment).append("\n");
            }
        }
        
        // Write the node content
        if (node.isScalar()) {
            writeIndent(sb, level);
            if (node.getKey() != null) {
                sb.append(node.getKey()).append(": ");
            }
            if (node.getValue() != null) {
                sb.append(node.getValue());
            }
            
            // Write post/inline comments
            if (node.getComment() != null && !node.getComment().getPostComments().isEmpty()) {
                sb.append(" ");
                for (String comment : node.getComment().getPostComments()) {
                    sb.append(comment).append(" ");
                }
            }
            sb.append("\n");
        } else if (node.isMapping()) {
            writeIndent(sb, level);
            if (node.getKey() != null) {
                sb.append(node.getKey()).append(":");
                
                // Write post/inline comments
                if (node.getComment() != null && !node.getComment().getPostComments().isEmpty()) {
                    sb.append(" ");
                    for (String comment : node.getComment().getPostComments()) {
                        sb.append(comment).append(" ");
                    }
                }
                sb.append("\n");
            }
            
            // Write children
            for (CommentedNode child : node.getChildren()) {
                dumpNode(child, sb, level + 1);
            }
        } else if (node.isSequence()) {
            writeIndent(sb, level);
            if (node.getKey() != null) {
                sb.append(node.getKey()).append(":");
                
                // Write post/inline comments
                if (node.getComment() != null && !node.getComment().getPostComments().isEmpty()) {
                    sb.append(" ");
                    for (String comment : node.getComment().getPostComments()) {
                        sb.append(comment).append(" ");
                    }
                }
                sb.append("\n");
            }
            
            // Write children as sequence items
            for (CommentedNode child : node.getChildren()) {
                writeIndent(sb, level);
                sb.append("-");
                
                if (child.isScalar()) {
                    sb.append(" ");
                    if (child.getValue() != null) {
                        sb.append(child.getValue());
                    }
                    
                    // Write post/inline comments
                    if (child.getComment() != null && !child.getComment().getPostComments().isEmpty()) {
                        sb.append(" ");
                        for (String comment : child.getComment().getPostComments()) {
                            sb.append(comment).append(" ");
                        }
                    }
                    sb.append("\n");
                } else {
                    // Complex item - need newline and indentation
                    sb.append("\n");
                    dumpNode(child, sb, level + 1);
                }
            }
        }
    }
    
    private void writeIndent(StringBuilder sb, int level) {
        for (int i = 0; i < level * indent; i++) {
            sb.append(" ");
        }
    }
}
