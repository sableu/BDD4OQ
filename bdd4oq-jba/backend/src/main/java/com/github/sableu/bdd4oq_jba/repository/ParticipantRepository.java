package com.github.sableu.bdd4oq_jba.repository;

import com.github.sableu.bdd4oq_jba.domain.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    Participant findById(long id);
}
