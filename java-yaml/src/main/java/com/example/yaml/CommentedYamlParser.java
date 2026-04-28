package com.example.yaml;

import java.util.*;
import java.util.regex.*;

/**
 * A YAML parser that preserves comments during parsing.
 * Similar to ruamel.yaml's RoundTripLoader but simplified for Java.
 */
public class CommentedYamlParser {
    
    private static final Pattern COMMENT_PATTERN = Pattern.compile("#.*$");
    private static final Pattern LINE_PATTERN = Pattern.compile("^(\\s*)(.*?)(\\s*#.*)?$");
    
    /**
     * Parse YAML content preserving comments
     */
    public CommentedNode parse(String yamlContent) {
        List<String> lines = Arrays.asList(yamlContent.split("\n", -1));
        return parseDocument(lines, 0);
    }
    
    private CommentedNode parseDocument(List<String> lines, int startLine) {
        CommentedNode root = new CommentedNode();
        root.setComment(new Comment());
        
        int indent = 0;
        boolean inSequence = false;
        
        for (int i = startLine; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                // Handle blank lines and full-line comments
                if (line.trim().startsWith("#")) {
                    root.getComment().addPreComment(line.trim());
                }
                continue;
            }
            
            // Extract comment from line
            Matcher matcher = LINE_PATTERN.matcher(line);
            if (matcher.find()) {
                String whitespace = matcher.group(1);
                String content = matcher.group(2);
                String comment = matcher.group(3);
                
                if (comment != null && !comment.trim().isEmpty()) {
                    root.getComment().addPostComment(comment.trim());
                }
            }
            
            // Simple parsing logic - determine if it's a mapping or sequence
            if (line.trim().startsWith("-")) {
                // Sequence item
                inSequence = true;
                parseSequenceItem(root, line, lines, i);
            } else if (line.contains(":")) {
                // Mapping item
                parseMappingItem(root, line, lines, i);
            }
        }
        
        return root;
    }
    
    private void parseSequenceItem(CommentedNode parent, String line, List<String> lines, int currentLine) {
        // Simplified sequence parsing
        String trimmed = line.trim();
        if (trimmed.startsWith("-")) {
            String value = trimmed.substring(1).trim();
            CommentedNode item = new CommentedNode();
            item.setValue(value);
            item.setComment(new Comment());
            
            // Check for inline comment
            if (value.contains("#")) {
                int commentIdx = value.indexOf("#");
                item.setValue(value.substring(0, commentIdx).trim());
                item.getComment().addPostComment(value.substring(commentIdx).trim());
            }
            
            parent.addChild(item);
        }
    }
    
    private void parseMappingItem(CommentedNode parent, String line, List<String> lines, int currentLine) {
        // Simplified mapping parsing
        String trimmed = line.trim();
        int colonIdx = trimmed.indexOf(":");
        if (colonIdx > 0) {
            String key = trimmed.substring(0, colonIdx).trim();
            String valueAndComment = trimmed.substring(colonIdx + 1).trim();
            
            CommentedNode node = new CommentedNode();
            node.setKey(key);
            
            // Check for inline comment
            if (valueAndComment.contains("#")) {
                int commentIdx = valueAndComment.indexOf("#");
                String value = valueAndComment.substring(0, commentIdx).trim();
                String comment = valueAndComment.substring(commentIdx).trim();
                node.getComment().addPostComment(comment);
                if (!value.isEmpty()) {
                    node.setValue(value);
                }
            } else {
                if (!valueAndComment.isEmpty()) {
                    node.setValue(valueAndComment);
                }
            }
            
            parent.addChild(node);
        }
    }
}
