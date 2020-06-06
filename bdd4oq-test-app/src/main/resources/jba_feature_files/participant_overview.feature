#language: en

Feature: Participant's overview
  An overview of all registered participants is displayed, so that starting from this overview, the baseline weight measurement can be set for the participants where it was not done so far

  Covered Requirements:
  bddoq-50: Overview (List) of all Registered Participants

  History (the last 8 versions are displayed on this list):
  Sig. Version |  Description                          | Name                   | Date          |  Digital Signature  ||
  0.0.0.1      |  FS initial version based on bddoq-50 | Sabrina Leuenberger    | 23-May-2020   |  le (symbolic)      ||
  0.0.0.2      |  FS initial version reviewed          | Patricia Walker        | 24-May-2020   |  wp (symbolic)      ||
  0.0.1.0      |  FS initial version approved          | Hank McKoy             | 25-May-2020   |  mh (symbolic)      ||
  0.0.1.1      |  FS adapted as Test script (TS)       | Andreas Hosbach        | 03-Jun-2020   |  ha (symbolic)      ||
  0.0.1.2      |  TS reviewed                          | Patricia Walker        | 04-Jun-2020   |  wp (symbolic)      ||
  1.0.0.0      |  TS approved  --> ready for OQ        | Hank McKoy             | 04-Jun-2020   |  mh (symbolic)      ||

  Size:
  1 active scenario
  3 active step


  Background:
    Given Patricia has the application open

  Scenario: Overview of all registered participants

    Given participants with first name, last name, birthday, gender are registered
    | "Natasha" | "Romanoff" | "1st of January 1984" | "female" |
    | "Scott"   | "Lang"     | "1st of March 1997"   | "male"   |
    | "Bruce"   | "Banner"   | "1st of May 1962"     | "male"   |
    | "Betty"   | "Ross"     | "1st of May 1962"     | "female" |
    Then the participants should be found in the overview
