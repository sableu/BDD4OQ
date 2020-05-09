package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.domain.Participant;
import com.github.sableu.bdd4oq_jba.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/participant/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Participant getParticipantById(@PathVariable("id") Long id) {
        logger.info("GET /participant/" + id);
        return participantRepository.findById(id).get();
    }

    @RequestMapping(path = "/participant", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public long addParticipant(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName) {
        logger.info("PUT /participant");
        Participant participant = participantRepository.save(new Participant(firstName, lastName));
        logger.info(participant.toString() + " added");
        return participant.getId();
    }
}
