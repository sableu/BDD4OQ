package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.domain.WeightEntry;

public class WeightEntryDto {
    public Double weight;
    public String dateTime;
    public String comment;

    public WeightEntry toWeightEntry(long participantId) {
        return new WeightEntry(weight, dateTime, comment, participantId);
    }
}
