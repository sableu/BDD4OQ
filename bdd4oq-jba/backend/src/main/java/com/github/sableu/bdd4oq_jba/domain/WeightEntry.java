package com.github.sableu.bdd4oq_jba.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WeightEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long participantId;

    private Double weight;
    private String dateTime;
    private String comment;
    private Boolean isBaselineMeasurement;

    public WeightEntry() {
    }

    public WeightEntry(Double weight, String dateTime, String comment, Long participantId, boolean isBaselineMeasurement) {
        this.weight = weight;
        this.dateTime = dateTime;
        this.comment = comment;
        this.participantId = participantId;
        this.isBaselineMeasurement = isBaselineMeasurement;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Boolean isBaselineMeasurement() {
        return isBaselineMeasurement;
    }

    public void setBaselineMeasurement(Boolean baselineMeasurement) {
        isBaselineMeasurement = baselineMeasurement;
    }

}
