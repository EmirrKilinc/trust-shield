package com.turkcell.demo.dsl;

public class Condition {

    private final String field;
    private final Operator operator;
    private final String value;

    public Condition(String field, Operator operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
