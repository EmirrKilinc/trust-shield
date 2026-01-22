package com.turkcell.demo.dsl;
public enum Operator {

    EQUALS("=="),
    GREATER(">"),
    GREATER_EQUALS(">="),
    LESS("<"),
    LESS_EQUALS("<="),
    CONTAINS("contains");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public static Operator from(String symbol) {
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unsupported operator: " + symbol);
    }
}