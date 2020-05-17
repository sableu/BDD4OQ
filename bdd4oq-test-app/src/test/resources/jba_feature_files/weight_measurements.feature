#language: en

Feature: Registration of weight measurements of a participant

  This specification describes how the nurse patricia registers the weight measurements she did on the participants
  Input: bddoq-26


  Scenario: Registration of the baseline measurement of a participant

  To register the baseline weight measurement, .
  This represents the simplest happy path.

    Given Peter with first name "Peter", last name "Parker" and birthday "08.05.2002" is registered
    And Peter has no weight measurement entry yet
    And Patricia wants to register Peter's baseline weight measurement
    When Patricia enters "78,5"kg in the weight field, "15.5.20, 8:15am" in the time field and "measurement done right after breakfast" in the comment field
    And she wants to save these entries
    Then Peter's baseline weight entry should be found in the system