#language: en

Feature: Participant's overview
 An overview of all registered participants is displayed, so that starting from this overview, the baseline weight measurement can be set for the participants where it was not done so far
  Input: bddoq-50
  Size:
  1 active scenario
  4 active step


  Scenario: Overview of all registered participants

    Given "Natasha" "Romanoff" born the "1st of January 1984" cannot be found in the overview
    And she is not registered yet
    When Patricia registers her
    Then Natasha should be found in the overview.





