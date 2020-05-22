#language: en

Feature: Setting of the baseline weight measurement of a participant

  This specification describes how the nurse patricia sets the baseline weight measurement of the participants
  Input: bddoq-26


  Scenario: Setting of the baseline weight measurement of a participant

  Patricia, the nurse, needs to set Ava's baseline measurement by entering the weight, the date and time and a comment.
  This represents the simplest happy path.

    Given Ava with first name "Ava", last name "Johnson", birthday "01.01.1989", gender "female" is registered
    #And Ava has no weight measurement entry yet
    And Patricia wants to register Ava's baseline weight measurement
    When Patricia enters 68,5 kg in the weight field, "15.5.20, 8:15am" in the time field and "measurement done right after breakfast" in the comment field
    And she saves these entries
    Then Ava's baseline weight entry should be found in the system

    @Ignore
  Scenario: Denial or setting of the baseline weight measurement when it already exists.

    Given Ava with first name "Ava", last name "Johnson", birthday "01.01.1989", gender "female" is registered
    And Ava has already a baseline measurement
    When Patricia wants to register Ava's baseline weight measurement
    Then she should not be able to register a new baseline measurement
