package com.turkcell.demo.service;

import com.turkcell.demo.dsl.context.EventContext;
import com.turkcell.demo.dto.event.mapper.EventMapper;
import com.turkcell.demo.dto.event.request.CreateEventRequest;
import com.turkcell.demo.dto.event.request.UpdateEventRequest;
import com.turkcell.demo.dto.event.response.EventResponse;
import com.turkcell.demo.entity.Event;
import com.turkcell.demo.exception.ErrorMessages;
import com.turkcell.demo.exception.EventNotFoundException;
import com.turkcell.demo.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final RuleEngineService ruleEngineService;
    private final RiskProfileService riskProfileService;

    public EventService(EventMapper eventMapper, EventRepository eventRepository, RuleEngineService ruleEngineService, RiskProfileService riskProfileService) {
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
        this.ruleEngineService = ruleEngineService;
        this.riskProfileService = riskProfileService;
    }

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {
        Event event = eventMapper.toEntity(request);
        Event savedEvent = eventRepository.save(event);

        EventContext context = eventMapper.toContext(savedEvent);

        riskProfileService.updateProfileForEvent(context);
        ruleEngineService.evaluateRules(context);
        return eventMapper.toDto(savedEvent);
    }


    public EventResponse updateEvent(Long eventId, UpdateEventRequest request){
        Event event = eventRepository.findById(eventId).
                orElseThrow(()->new EventNotFoundException(String.format(ErrorMessages.EVENT_NOT_FOUND_WITH_ID, eventId)));
        eventMapper.update(event, request);
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toDto(updatedEvent);
    }

    public EventResponse getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).
                orElseThrow(()->new EventNotFoundException(String.format(ErrorMessages.EVENT_NOT_FOUND_WITH_ID, eventId)));
        return eventMapper.toDto(event);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }



}
