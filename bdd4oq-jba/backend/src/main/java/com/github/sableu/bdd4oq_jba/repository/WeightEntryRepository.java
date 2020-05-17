package com.github.sableu.bdd4oq_jba.repository;

import com.github.sableu.bdd4oq_jba.domain.WeightEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface WeightEntryRepository extends CrudRepository<WeightEntry, Long> {
    @Query("SELECT we FROM WeightEntry we WHERE we.participantId = ?1")
    Collection<WeightEntry> findByParticipantId(Long participantId);
}
