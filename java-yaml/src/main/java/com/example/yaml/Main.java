package com.example.yaml;

import java.util.*;

/**
 * Example usage of the YAML comment preservation library.
 */
public class Main {
    
    public static void main(String[] args) {
        // Example 1: Simple round-trip with comments
        String yamlInput = 
            "# This is a comment at the start\n" +
            "name: John Doe  # inline comment\n" +
            "age: 30\n" +
            "# Comment before hobbies\n" +
            "hobbies:\n" +
            "  - reading  # hobby comment\n" +
            "  - coding\n" +
            "  - hiking\n";
        
        System.out.println("=== Original YAML ===");
        System.out.println(yamlInput);
        
        Yaml yaml = new Yaml();
        CommentedNode node = yaml.load(yamlInput);
        
        System.out.println("\n=== Parsed Node ===");
        System.out.println(node);
        
        String yamlOutput = yaml.dump(node);
        
        System.out.println("\n=== Dumped YAML (with preserved comments) ===");
        System.out.println(yamlOutput);
        
        // Example 2: Manipulating comments
        System.out.println("\n=== Manipulating Comments ===");
        node.getComment().addPreComment("# Added programmatically");
        String modifiedYaml = yaml.dump(node);
        System.out.println(modifiedYaml);
        
        // Example 3: Creating a new node with comments
        System.out.println("\n=== Creating New Node with Comments ===");
        CommentedNode newNode = new CommentedNode();
        newNode.setKey("city");
        newNode.setValue("New York");
        newNode.getComment().addPostComment("location comment");
        newNode.getComment().addPreComment("# City information");
        
        String newYaml = yaml.dump(newNode);
        System.out.println(newYaml);
    }
}
