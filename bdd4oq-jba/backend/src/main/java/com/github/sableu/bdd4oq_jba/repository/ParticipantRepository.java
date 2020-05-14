package com.github.sableu.bdd4oq_jba.repository;

import com.github.sableu.bdd4oq_jba.domain.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
    @Query("SELECT u FROM Participant u WHERE u.firstName = ?1 and u.lastName = ?2 and u.birthday = ?3")
    Collection<Participant> findByAttributes(String firstName, String lastName, String birthday);
}
