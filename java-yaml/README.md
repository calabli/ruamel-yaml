# Java YAML Comment Preservation Library

A simple Java library that preserves YAML comments during parsing and dumping, inspired by the Python library [ruamel.yaml](https://yaml.dev/doc/ruamel.yaml/).

## Features

- **Comment Preservation**: Maintains pre-comments (full-line comments), post-comments (end-of-line comments), and inline comments
- **Round-trip Support**: Load YAML, modify it, and dump it back while preserving comments
- **Simple API**: Similar to ruamel.yaml's `YAML()` class interface
- **Minimal Dependencies**: No external dependencies required

## Project Structure

```
java-yaml/
├── pom.xml
└── src/main/java/com/example/yaml/
    ├── Comment.java           # Comment data structure
    ├── CommentedNode.java     # YAML node with comment support
    ├── CommentedYamlParser.java   # Parser that preserves comments
    ├── CommentedYamlDumper.java   # Dumper that outputs comments
    ├── Yaml.java              # Main API class
    └── Main.java              # Example usage
```

## Classes

### Comment
Represents a comment in YAML with three types:
- `preComments`: Comments before the node (full-line comments)
- `postComments`: Comments after the node (end-of-line comments)
- `inlineComments`: Inline comments within the node

### CommentedNode
A YAML node that can hold:
- A key (for mappings)
- A value (scalar or complex)
- Comments
- Child nodes
- Node type (SCALAR, MAPPING, SEQUENCE)

### CommentedYamlParser
Parses YAML content while extracting and preserving comments.

### CommentedYamlDumper
Dumps CommentedNode objects back to YAML format with comments.

### Yaml
Main API class providing:
- `load(String yamlContent)`: Parse YAML preserving comments
- `dump(CommentedNode node)`: Dump node to YAML string
- `roundTrip(String yamlContent)`: Load and dump back

## Usage

### Basic Round-trip

```java
import com.example.yaml.*;

public class Example {
    public static void main(String[] args) {
        String yamlInput = 
            "# This is a comment\n" +
            "name: John Doe  # inline comment\n" +
            "age: 30\n";
        
        Yaml yaml = new Yaml();
        
        // Load YAML preserving comments
        CommentedNode node = yaml.load(yamlInput);
        
        // Dump back to YAML
        String yamlOutput = yaml.dump(node);
        
        System.out.println(yamlOutput);
    }
}
```

### Manipulating Comments

```java
// Add comments programmatically
node.getComment().addPreComment("# Added via code");
node.getComment().addPostComment("# End comment");

String modifiedYaml = yaml.dump(node);
```

### Creating New Nodes with Comments

```java
CommentedNode newNode = new CommentedNode();
newNode.setKey("city");
newNode.setValue("New York");
newNode.getComment().addPostComment("# location comment");
newNode.getComment().addPreComment("# City information");

String newYaml = yaml.dump(newNode);
```

## Building

Compile with Maven:
```bash
mvn compile
```

Or with javac directly:
```bash
mkdir -p target/classes
javac -d target/classes src/main/java/com/example/yaml/*.java
```

## Running

```bash
java -cp target/classes com.example.yaml.Main
```

## Comparison with ruamel.yaml

| Feature | ruamel.yaml (Python) | This Library (Java) |
|---------|---------------------|---------------------|
| Comment preservation | ✓ Full support | ✓ Basic support |
| Round-trip | ✓ | ✓ |
| Anchor/Alias | ✓ | ✗ (simplified) |
| Custom tags | ✓ | ✗ (simplified) |
| Multiple documents | ✓ | ✗ (single doc) |
| Complex YAML features | ✓ | Limited |

## Limitations

This is a **simplified** implementation focusing on:
1. Basic YAML structures (mappings, sequences, scalars)
2. Comment preservation
3. Minimal modifications to original format

It does NOT support:
- Anchors and aliases
- Custom tags
- Multiple documents
- Complex flow styles
- All YAML 1.2 features

For production use with complex YAML, consider using [SnakeYAML](https://bitbucket.org/asomov/snakeyaml/) with custom extensions.

## License

MIT License - Feel free to use and modify as needed.
