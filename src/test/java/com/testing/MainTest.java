package com.testing;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    void testWhenGivenStringThenTokenizeAndParseSuccessfully() {
        //given
        String input = "x = 2; y = 3; z = x + y;";

        //when
        ArrayList<Token> tokens = Tokenizer.tokenize(input);
        Parser parser = new Parser(tokens);

        //then
        assertDoesNotThrow(() -> parser.parse());
        assertNotNull(tokens);
        assertFalse(tokens.isEmpty());
    }

    @Test
    void testWhenGivenStringThenTokenizeAndParseFailure() {
        //given
        String input = "x = 2 y = 3";

        //when
        ArrayList<Token> tokens = Tokenizer.tokenize(input);
        Parser parser = new Parser(tokens);

        //then
        Exception exception = assertThrows(RuntimeException.class, () -> parser.parse());
        assertTrue(exception.getMessage().contains("Parsing failed"));
    }

}
