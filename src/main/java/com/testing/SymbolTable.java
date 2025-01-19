package com.testing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description: This class will manage the mapping of variables to their assigned integer values.
 */

public class SymbolTable {
    private Map<String, Integer> variables = new HashMap<>();

    public void assign(String var, int value) {
        variables.put(var, value);
    }

    public int getValue(String var) {
        if (!variables.containsKey(var)) {
            throw new RuntimeException("Variable not initialized: " + var);
        }
        return variables.get(var);
    }

    public boolean contains(String varName) {
        return variables.containsKey(varName);
    }

    //Prints all the variables and their corresponding values stored in the symbol tabe. "variable = value"
    public void printAll() {
        variables.forEach((key, value) -> System.out.println(key + " = " + value));
    }

}

