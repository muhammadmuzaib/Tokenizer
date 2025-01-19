package com.testing;

import java.util.ArrayList;
import java.util.Map;

/**
 * Description: This class will take a lsit of tokens to validate their syntax, evalute expressions,
 * and manage their assignments in the symbol table.
 */
public class Parser {
    private final ArrayList<Token> tokens;
    private int currentTokenIndex = 0;
    private SymbolTable symbolTable = new SymbolTable();
    private int currentValue = 0;   //This will hold the value fo the most recently assigned expression.

    //constructor
    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    private Token currentToken() {
        if (currentTokenIndex < tokens.size()) {
            return tokens.get(currentTokenIndex);
        }
        return null;
    }

    private void readNextToken() {
        if (currentTokenIndex < tokens.size()) {
            currentTokenIndex++;
        }
    }

    //main method for parsing an expression. Expression = 1 or more terms combines using + or -.
    private void exp() {
        term();
        expPrime();
    }

    //Helper method that handles terms which have '+' or '-'.
    private void expPrime() {
        Token token = currentToken();
        if (token instanceof Operator) {
            String operator = ((Operator) token).getLexeme();
            if (operator.equals("+") || operator.equals("-")) {
                readNextToken();
                term();
                expPrime();
            }
        }
    }

    //Term = part of an expression, consisting of one or more factors which are combined using * or /.
    private void term() {
        factor();
        termPrime();
    }

    private void termPrime() {
        Token token = currentToken();
        if (token instanceof Operator) {
            String operator = ((Operator) token).getLexeme();
            if (operator.equals("*") || operator.equals("/")) {
                readNextToken();
                factor();
                termPrime();
            }
        }
    }

    //factor = smallest component of a term adn represents a single value or an expression inside a paranthesis.
    //Will store a number, variable, or parenthesized expression.
    private void factor() {
        Token token = currentToken();
        boolean isNegative = false; // Flag for unary negation

        // Check for unary negation
        if (token instanceof Operator && ((Operator) token).getLexeme().equals("-")) {
            isNegative = true;
            readNextToken(); // Consume the unary minus
            token = currentToken();
        }

        //When a number, directly sotre its numeric value.
        if (token instanceof Num) {
            Num numberToken = (Num) token;
            currentValue = numberToken.getValue();
            if (isNegative) {
                currentValue = -currentValue; // Apply negation
            }
            readNextToken(); // Consume the number token
        } else if (token instanceof Identifier) { //when a variable, I retrieve stored value from the table and store that value.
            String varName = ((Identifier) token).getLexeme();
            currentValue = symbolTable.getValue(varName);
            if (isNegative) {
                currentValue = -currentValue; // Apply negation
            }
            readNextToken(); // Consume the identifier
        } else if (token instanceof Operator && ((Operator) token).getLexeme().equals("(")) {
            readNextToken(); // Consume '('
            exp(); // let exp evaluate whats isnide the parenthesis.
            if (isNegative) {
                currentValue = -currentValue;
            }
            if (!(currentToken() instanceof Operator && ((Operator) currentToken()).getLexeme().equals(")"))) {
                throw new RuntimeException("Syntax error: expected ')'");
            }
            readNextToken();
        } else {
            throw new RuntimeException("Syntax error: expected a number, variable, unary operator, or '('");
        }
    }





    private void statement() {
        // Assuming the format: identifier = expression;
        Token token = currentToken();
        if (token instanceof Identifier) {
            String varName = ((Identifier) token).getLexeme();
            readNextToken(); // Consume identifier

            // Expect an equals sign next
            if (!(currentToken() instanceof Operator && ((Operator) currentToken()).getLexeme().equals("="))) {
                throw new RuntimeException("Syntax error: expected '='");
            }
            readNextToken(); // Consume '='

            // Check if the next token is a number with leading zeros
            token = currentToken();
            if (token instanceof Num) {
                Num numberToken = (Num) token;
                String numberLexeme = numberToken.getLexeme();
                if (numberLexeme.startsWith("0") && (numberLexeme.length() > 1 && !numberLexeme.equals("0"))) {
                    throw new RuntimeException("Syntax error: leading zeros are not allowed");
                }
            }

            // Here we would normally evaluate the expression or simply consume the token
            // In a full implementation, evaluateExpression() would be responsible for consuming all tokens of the expression
            int value = evaluateExpression();

            // Expect a semicolon
            if (!(currentToken() instanceof Operator && ((Operator) currentToken()).getLexeme().equals(";"))) {
                throw new RuntimeException("Syntax error: expected ';'");
            }
            readNextToken(); // Consume ';'

            // Assign the result to the variable
            symbolTable.assign(varName, value);
        } else {
            throw new RuntimeException("Syntax error: expected identifier");
        }
    }

    private int evaluateExpression() {
        int result = 0;
        boolean add = true;
        boolean multiply = false;
        boolean divide = false;

        while (currentToken() != null && !(currentToken() instanceof Operator && ((Operator) currentToken()).getLexeme().equals(";"))) {
            if (currentToken() instanceof Num || currentToken() instanceof Identifier) {
                factor(); // factor updates currentValue
                if (multiply) {
                    result *= currentValue;  // Perform multiplication
                    multiply = false;
                } else if (divide) {
                    if (currentValue == 0) {
                        throw new RuntimeException("Runtime error: Division by zero");
                    }
                    result /= currentValue;
                    divide = false;
                } else {
                    result = add ? result + currentValue : result - currentValue; // Handle addition and subtraction
                }
            } else if (currentToken() instanceof Operator) {
                String operator = ((Operator) currentToken()).getLexeme();
                if (operator.equals("+")) {
                    add = true;
                    readNextToken();
                } else if (operator.equals("-")) {
                    add = false;
                    readNextToken();
                } else if (operator.equals("*")) {
                    multiply = true;
                    readNextToken(); // Consume the operator
                } else if (operator.equals("/")) {
                    divide = true;
                    readNextToken(); // Consume the operator
                } else if (operator.equals("(") || operator.equals(")")) {
                    // Handle parentheses  TODO: FIX THIS
                    readNextToken(); // Consume the parenthesis
                } else {
                    throw new RuntimeException("Syntax error: invalid operator '" + operator + "'");
                }
            }
        }
        return result;
    }

    public void printSymbolTable() {
        symbolTable.printAll();
    }

    // Call this method instead of 'exp()' in 'parse()' for handling statements
    public void parse() {
        while (currentToken() != null) {
            statement(); // Process each statement
        }
    }

}

