package com.testing;

import java.util.ArrayList;

public class Tokenizer {
    public static ArrayList<Token> tokenize(String s){
        ArrayList<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < s.length()){
            i = scan(s, i, tokens);
            if (i == -1){
                throw new RuntimeException("error");
            }
        }
        return tokens;
    }

    /**
     * Extract one token starting at index i, add it to tokens,
     * and return the index after the token.
     **/
    static int scan(String s, int i, ArrayList<Token> tokens){
        while (i < s.length() && (s.charAt(i) == ' ' || s.charAt(i) == '\t')){
            i++;
        }
        if (i == s.length()) return i;
        if (Character.isDigit(s.charAt(i))) {
            StringBuilder numLexeme = new StringBuilder();
            do {
                numLexeme.append(s.charAt(i));
                i++;
            } while (i < s.length() && Character.isDigit(s.charAt(i)));
            int val = Integer.parseInt(numLexeme.toString());
            tokens.add(new Num(val, numLexeme.toString()));
            return i;
        }
        if (Character.isLetter(s.charAt(i)) || s.charAt(i) == '_') { // Allows underscore as a valid starting character
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(s.charAt(i));
                i++;
            } while (i < s.length() && (Character.isLetterOrDigit(s.charAt(i)) || s.charAt(i) == '_')); // Allows underscores within the identifier
            tokens.add(new Identifier(sb.toString()));
            return i;
        }
        if (s.startsWith("!!!", i)) {
            tokens.add(new Operator("!!!"));
            return i + 3;
        }
        if ("+-*/!=();".indexOf(s.charAt(i)) >= 0){
            tokens.add(new Operator(s.substring(i, i + 1)));
            i++;
            return i;
        }
        return -1; // if Unrecognized character return -1.
    }
}

interface Tag {
    int NUM = 0;
    int ID = 1;
    int OP = 2;
}

class Token {
    public final int tag;
    public Token(int t){
        tag = t;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class Num extends Token {
    public final int value;
    private final String lexeme; // Store the original string representation here.

    public Num(int v, String lexeme) {
        super(Tag.NUM);
        this.value = v;
        this.lexeme = lexeme;
    }

    public int getValue() {
        return value;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Num(" + value + ")";
    }
}


class Identifier extends Token {
    public final String lexeme;

    public Identifier(String s) {
        super(Tag.ID);
        lexeme = s;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Identifier(" + lexeme + ")";
    }
}

class Operator extends Token {
    public final String lexeme;

    public Operator(String s) {
        super(Tag.OP);
        lexeme = s;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Operator(" + lexeme + ")";
    }
}


