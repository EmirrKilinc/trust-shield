package com.turkcell.demo.dsl;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DSLParser {

    public List<Condition> parse(String rule) {
        if (rule == null || rule.isBlank()) {
            return new ArrayList<>();
        }
        List<Condition> conditions = new ArrayList<>();

        String[] parts = rule.split("&&");
        for (String part : parts) {
            conditions.add(parseSingle(part.trim()));
        }
        return conditions;
    }

    private Condition parseSingle(String exp) {
        if (exp.contains("contains")) {
            String[] p = exp.split("contains");
            return new Condition(
                    p[0].trim(),
                    Operator.CONTAINS,
                    clean(p[1])
            );
        }

        String[] tokens = exp.split("\\s+");
        return new Condition(
                tokens[0],
                Operator.from(tokens[1]),
                clean(tokens[2])
        );
    }

    private String clean(String s) {
        return s.replace("'", "").trim();
    }
}
