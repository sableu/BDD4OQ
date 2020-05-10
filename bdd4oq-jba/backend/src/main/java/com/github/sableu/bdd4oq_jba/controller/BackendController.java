package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.domain.Participant;
import com.github.sableu.bdd4oq_jba.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger logger = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private ParticipantRepository participantRepository;

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend for ";

    @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String sayHello(@PathVariable("name") String name) {
        logger.info("GET /hello/" + name);
        return HELLO_TEXT + name;
    }

    @RequestMapping(path = "/participant", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantDto> getParticipants() {
        logger.info("GET /participant");
        List<ParticipantDto> participants = new ArrayList<>();
        participantRepository.findAll().forEach(p -> participants.add(ParticipantDto.fromParticipant(p)));
        return  participants;
    }

    @RequestMapping(path = "/participant/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ParticipantDto getParticipantById(@PathVariable("id") Long id) {
        logger.info("GET /participant/" + id);
        ParticipantDto participantDto = ParticipantDto.fromParticipant(participantRepository.findById(id).get());
        return  participantDto;
    }

    @RequestMapping(path = "/participant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addParticipant(@RequestBody ParticipantDto participantDto) {
        logger.info("PUT /participant");
        Participant participant = participantRepository.save(participantDto.toParticipant());
        logger.info(participant.toString() + " added");
        return participant.getId();
    }

    //TODO  GET /participant/{id}/measurement
    //TODO POST /participant/{id}/measurement
}
