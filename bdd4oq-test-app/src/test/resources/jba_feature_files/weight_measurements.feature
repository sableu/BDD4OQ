#language: en

Feature: Registration of weight measurements of a participant

  This specification describes how the nurse patricia registers the weight measurements she did on the participants
  Input: bddoq-26

  @Ignore
  Scenario: Registration of the baseline measurement of a participant

  To register the baseline weight measurement, .
  This represents the simplest happy path.

    Given A participant Peter
    And Peter has first name "Peter", last name "Parker", birthday "08.05.2020" and is "male"
    And Peter is not registered yet
    And Patricia wants to register Peter
    When Patricia enters Peter's data
    And wants to register them
    Then Peter should be found in the system