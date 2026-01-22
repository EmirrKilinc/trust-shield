package com.turkcell.demo.dsl;

import com.turkcell.demo.dsl.context.EventContext;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RuleEvaluator {

    private final DSLParser parser;

    public RuleEvaluator(DSLParser parser) {
        this.parser = parser;
    }

    public boolean evaluate(String ruleCondition, EventContext ctx) {
        if (ruleCondition == null || ruleCondition.isBlank()) {
            return false;
        }

        List<Condition> conditions = parser.parse(ruleCondition);

        if (conditions.isEmpty()) {
            return false;
        }

        for (Condition c : conditions) {
            if (!match(c, ctx)) {
                return false;
            }
        }
        return true;
    }

    private boolean match(Condition c, EventContext ctx) {
        return switch (c.getField()) {
            case "service" ->
                    ctx.getService() != null && ctx.getService().equalsIgnoreCase(c.getValue());

            case "event_type" ->
                    ctx.getEventType() != null && ctx.getEventType().name().equalsIgnoreCase(c.getValue());

            case "value" ->
                    ctx.getValue() != null && compare(ctx.getValue(), c);

            case "meta" -> matchMeta(ctx, c.getValue());

            default -> false;
        };
    }

    private boolean matchMeta(EventContext ctx, String searchValue) {
        if (ctx.getMeta() == null || searchValue == null) {
            return false;
        }

        if (searchValue.contains("=")) {
            String[] kv = searchValue.split("=");
            String key = kv[0].trim();
            String value = kv[1].trim();
            return value.equals(ctx.getMeta().get(key));
        }

        return ctx.getMeta().containsValue(searchValue);
    }

    private boolean compare(Double actual, Condition c) {
        if (actual == null) return false;

        try {
            double expected = Double.parseDouble(c.getValue());
            return switch (c.getOperator()) {
                case GREATER -> actual > expected;
                case GREATER_EQUALS -> actual >= expected;
                case LESS -> actual < expected;
                case LESS_EQUALS -> actual <= expected;
                case EQUALS -> actual.equals(expected);
                default -> false;
            };
        } catch (NumberFormatException e) {
            return false;
        }
    }
}