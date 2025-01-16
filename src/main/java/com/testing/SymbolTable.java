package com.testing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Set<Map.Entry<String, Integer>> getEntries() {
        return variables.entrySet();
    }

    public boolean contains(String varName) {
        return variables.containsKey(varName);
    }
}

