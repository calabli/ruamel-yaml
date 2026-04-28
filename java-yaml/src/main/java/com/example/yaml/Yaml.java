package com.example.yaml;

import java.util.*;
import java.io.*;

/**
 * Main YAML class that provides a simple API for loading and dumping YAML with comment preservation.
 * Similar to ruamel.yaml's YAML() class but simplified for Java.
 */
public class Yaml {
    
    private int indent = 2;
    private CommentedYamlParser parser;
    private CommentedYamlDumper dumper;
    
    public Yaml() {
        this.parser = new CommentedYamlParser();
        this.dumper = new CommentedYamlDumper(indent);
    }
    
    public Yaml(int indent) {
        this.indent = indent;
        this.parser = new CommentedYamlParser();
        this.dumper = new CommentedYamlDumper(indent);
    }
    
    /**
     * Load YAML content preserving comments
     */
    public CommentedNode load(String yamlContent) {
        return parser.parse(yamlContent);
    }
    
    /**
     * Load YAML from a file preserving comments
     */
    public CommentedNode loadFile(String filePath) throws IOException {
        String content = readFile(filePath);
        return parser.parse(content);
    }
    
    /**
     * Dump a CommentedNode to YAML string, preserving comments
     */
    public String dump(CommentedNode node) {
        return dumper.dump(node);
    }
    
    /**
     * Dump a CommentedNode to a file, preserving comments
     */
    public void dumpFile(CommentedNode node, String filePath) throws IOException {
        String yamlContent = dumper.dump(node);
        writeFile(filePath, yamlContent);
    }
    
    /**
     * Round-trip: load YAML and dump it back, preserving comments
     */
    public String roundTrip(String yamlContent) {
        CommentedNode node = load(yamlContent);
        return dump(node);
    }
    
    private String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
    
    private void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
