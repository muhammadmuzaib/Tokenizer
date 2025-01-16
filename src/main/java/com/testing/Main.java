package com.testing;

import java.util.ArrayList;

//Program takes expressions and assignments, evaluates them, and stores the result in a symbol table (map of variables to their values).
public class Main {
    public static void main(String[] args) {
        //String input = "x = 1; y = 2; z = (x+y)*(x+y);";
        //String input = "x = 001;"; Pass
        //String input = "x_2 = 0;"; Pass
        //String input = "x = 1; y = 2; z = (x + y);"; //Pass
        //String input = "x = 2; y = 1; z = x - y;"; //Output = 1. Pass Subtraction
        //String input = "x = 2; y = 1; z = x + y;"; //Output = 3: Pass Addition
        //String input = "x = 2; y = 1; z = x * y;"; //Output: 2: Pass Multiplication
        //String input = "x = 2; y = 2; z = x / y;"; //Output: 1: Pass Division
        //String input = "x = -2; y = 2; z = x * y;"; // Output: -4: Pass negation
        String input = "x = 2 y = 3";
        ArrayList<Token> tokens = Tokenizer.tokenize(input);

        System.out.println("Tokens: " + tokens);

        Parser parser = new Parser(tokens);
        try {
            parser.parse();
            System.out.println("Parsing successful");
            parser.printSymbolTable(); // This will print the variable assignments
        } catch (RuntimeException e) {
            System.out.println("Parsing failed: " + e.getMessage());
        }
    }
}

