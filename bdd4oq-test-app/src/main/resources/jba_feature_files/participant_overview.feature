#language: en

Feature: Participant's overview
 An overview of all registered participants is displayed, so that starting from this overview, the baseline weight measurement can be set for the participants where it was not done so far
  Input: bddoq-50
  Size:
  1 active scenario
  4 active step


  Background:
    Given Patricia has the application open


  Scenario: Overview of all registered participants

    Given a participant with first name "Natasha", last name "Romanoff", birthday "1st of January 1984" and is "female"
    And "Natasha" is not registered yet
    When Patricia registers "Natasha"
    Then "Natasha" should be found in the overview


