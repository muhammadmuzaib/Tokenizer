# Tokenizer - Java-based Expression Parsing

## Overview

Tokenizer is a high-performance Java-based expression parser designed to efficiently tokenize and evaluate complex arithmetic expressions. This project implements a parsing engine capable of handling variables, unary negation, and parenthetical expressions while maintaining optimized performance and code readability.

## Features

- **High-Performance Tokenization**: Efficiently parses arithmetic expressions into structured tokens.
- **Supports Variables**: Recognizes and processes variables within expressions.
- **Unary Negation Handling**: Correctly interprets and evaluates negative values.
- **Parenthetical Expressions**: Properly processes expressions enclosed in parentheses for accurate evaluation.
- **Optimized Code Design**: Improved readability and performance through structured coding techniques and efficient data handling.

## Installation

### Prerequisites

- Java Development Kit (JDK) 8 or later
- A Java IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code)
- Apache Maven (optional, for dependency management)
- JUNIT 5

### Clone the Repository

```sh
git clone https://github.com/muhammadmuzaib/Tokenizer.git
cd tokenizer

### Example Code
public static void main(String[] args) {
        String input = "x = -2; y = 2; z = x * y;"; // Output: -4: Pass negation
        ArrayList<Token> tokens = Tokenizer.tokenize(input);

        System.out.println("Tokens: " + tokens);

        Parser parser = new Parser(tokens);
        try {
            parser.parse();
            System.out.println("Parsing successful");
            parser.printSymbolTable();
        } catch (RuntimeException e) {
            System.out.println("Parsing failed: " + e.getMessage());
        }
    }

