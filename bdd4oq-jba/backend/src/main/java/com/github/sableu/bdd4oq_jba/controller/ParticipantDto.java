package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.domain.Participant;

public class ParticipantDto {
    public Long id;
    public String lastName;
    public String firstName;
    public String birthday;
    public String gender;
    public Boolean consent;

    public static ParticipantDto fromParticipant(Participant participant){
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.id = participant.getId();
        participantDto.lastName = participant.getLastName();
        participantDto.firstName = participant.getFirstName();
        participantDto.birthday = participant.getBirthday();
        participantDto.gender = participant.getGender();
        participantDto.consent = participant.getConsent();
        return participantDto;
    }

    public Participant toParticipant(){
      return new Participant(firstName, lastName, birthday, gender, consent);
    }

    public void updateParticipant(Participant participant){
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setGender(gender);
        participant.setConsent(consent);
    }
}
