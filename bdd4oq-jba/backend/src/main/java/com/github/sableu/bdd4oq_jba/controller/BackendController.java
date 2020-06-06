package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.domain.Participant;
import com.github.sableu.bdd4oq_jba.domain.WeightEntry;
import com.github.sableu.bdd4oq_jba.repository.ParticipantRepository;
import com.github.sableu.bdd4oq_jba.repository.WeightEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger logger = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private WeightEntryRepository weightEntryRepository;

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
        return participants;
    }

    @RequestMapping(path = "/participant/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ParticipantDto getParticipantById(@PathVariable("id") Long id) {
        logger.info("GET /participant/" + id);
        ParticipantDto participantDto = ParticipantDto.fromParticipant(participantRepository.findById(id).orElseThrow(() -> new ParticipantNotFoundException("Participant not found")));
        return participantDto;
    }

    @RequestMapping(path = "/participant/search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantDto> getParticipantByAttributes(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("birthday") String birthday) {
        logger.info("GET /participant/search" + firstName + "_" + lastName + "_" + birthday);

        Collection<Participant> participants = participantRepository.findByAttributes(firstName, lastName, birthday);

        if (participants.isEmpty()) {
            logger.info("Participant not found " + firstName + "_" + lastName + "_" + birthday);
            throw new ParticipantNotFoundException("The participant requested by attributes could not be found");
        }

        logger.info(participants.size() + " Participant(s) found with " + firstName + "_" + lastName + "_" + birthday);
        List<ParticipantDto> participantDtos = participants.stream().map(p -> ParticipantDto.fromParticipant(p)).collect(Collectors.toList());
        return participantDtos;
    }

    @RequestMapping(path = "/participant", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addParticipant(@RequestBody ParticipantDto participantDto) {
        logger.info("POST /participant");
        Participant participant = participantRepository.save(participantDto.toParticipant());
        logger.info(participant.toString() + " added");
        return participant.getId();
    }

    @RequestMapping(path = "/participant/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipant(@PathVariable("id") Long id) {
        logger.info("DELETE /participant/" + id);
        if(!participantRepository.existsById(id)){
            throw new ParticipantNotFoundException("The participant with id " + id + "could not be found");
        }
        participantRepository.deleteById(id);
    }

    @RequestMapping(path = "/participant/{participantId}/weights/baseline", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addPWeight(@PathVariable("participantId") Long participantId, @RequestBody WeightEntryDto weightEntryDto) {
        logger.info("POST /participant/"+participantId+"/weights/baseline");
        if(!weightEntryRepository.findByParticipantId(participantId).isEmpty()){
            logger.info("Already a baseline present refusing new values");
            throw new BaselineMeasurementAlreadyExistsException("Baseline measurement already exists");
        }
        WeightEntry weightEntry = weightEntryRepository.save(weightEntryDto.toWeightEntry(participantId));
        return weightEntry.getId();
    }

    //TODO  GET /participant/{id}/measurement
    //TODO POST /participant/{id}/measurement
}
