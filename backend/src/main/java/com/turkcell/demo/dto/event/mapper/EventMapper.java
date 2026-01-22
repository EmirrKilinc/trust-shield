package com.turkcell.demo.dto.event.mapper;


import com.turkcell.demo.dsl.context.EventContext;
import com.turkcell.demo.dto.event.request.CreateEventRequest;
import com.turkcell.demo.dto.event.request.UpdateEventRequest;
import com.turkcell.demo.dto.event.response.EventResponse;
import com.turkcell.demo.entity.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventMapper {

    public Event toEntity(CreateEventRequest request){
        return new Event(
                request.userId(),
                request.service(),
                request.eventType(),
                request.amount(),
                request.unit(),
                request.meta(),
                LocalDateTime.now()
        );

    }

    public EventResponse toDto(Event event){
        return new EventResponse(
                event.getEventId(),
                event.getUserId(),
                event.getService(),
                event.getEventType(),
                event.getAmount(),
                event.getUnit(),
                event.getMeta(),
                event.getTimestamp()
        );
    }

    public void update(Event event, UpdateEventRequest request){
        event.setUserId(request.userId());
        event.setEventType(request.eventType());
        event.setAmount(request.amount());
        event.setUnit(request.unit());
        event.setService(request.service());
        event.setMeta(request.meta());
    }

    public EventContext toContext(Event event) {
        return new EventContext(
                event.getUserId(),
                event.getService(),
                event.getEventType(),
                event.getAmount(),
                parseMeta(event.getMeta())
        );
    }

    private Map<String, String> parseMeta(String meta) {
        Map<String, String> map = new HashMap<>();
        if (meta == null) return map;

        String normalized = meta.replace("\t", ",");
        for (String pair : normalized.split(",")) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }
        return map;
    }

}
