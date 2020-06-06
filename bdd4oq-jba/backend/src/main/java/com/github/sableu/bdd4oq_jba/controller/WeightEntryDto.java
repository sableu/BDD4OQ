package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.domain.WeightEntry;

public class WeightEntryDto {
    public Double weight;
    public String dateTime;
    public String comment;


    public static WeightEntryDto fromWeightEntry(WeightEntry weightEntry){
        WeightEntryDto weightEntryDto = new WeightEntryDto();
        weightEntryDto.weight = weightEntry.getWeight();
        weightEntryDto.dateTime = weightEntry.getDateTime();
        weightEntryDto.comment = weightEntry.getComment();

        return weightEntryDto;
    }

    public WeightEntry toWeightEntry(long participantId) {
        return new WeightEntry(weight, dateTime, comment, participantId, false);
    }

    public WeightEntry toBaselineWeightEntry(Long participantId) {
        return new WeightEntry(weight, dateTime, comment, participantId, true);
    }
}
