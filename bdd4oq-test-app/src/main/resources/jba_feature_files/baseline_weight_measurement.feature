#language: en

Feature: Setting the baseline weight measurement
  This specification describes how the nurse Patricia sets the baseline weight measurement of the participants.

  Covered Requirements:
  bddoq-26: Setting of the Participant's Baseline Weight Measurement

  History (the last 8 versions are displayed on this list):
  Version    |  Description                          | Name                   | Date          |  Digital Signature  ||
  0.0.0.1    |  FS initial version based on bddoq-26 | Sabrina Leuenberger    | 28-May-2020   |  le (symbolic)      ||
  0.0.0.2    |  FS initial version reviewed          | Patricia Walker        | 29-May-2020   |  wp (symbolic)      ||
  0.0.1.0    |  FS initial version approved          | Hank McKoy             | 29-May-2020   |  mh (symbolic)      ||
  0.0.1.1    |  FS adapted as Test script (TS)       | Andreas Hosbach        | 03-Jun-2020   |  ha (symbolic)      ||
  0.0.1.2    |  TS reviewed                          | Patricia Walker        | 04-Jun-2020   |  wp (symbolic)      ||
  1.0.0.0    |  TS approved  --> ready for OQ        | Hank McKoy             | 04-Jun-2020   |  mh (symbolic)      ||

  Size:
  11 active scenarios
  57 active steps


  Background:
    Given Patricia has the application open


  Scenario: Setting of the baseline weight measurement of a participant
  Patricia, the nurse, needs to set Ava's baseline measurement by entering the weight, the date and time and a comment.
  This represents the simplest happy path.

    Given a participant with first name "Ava", last name "Johnson", birthday "01.01.1989", gender "female" is registered
    And "Ava" has no baseline weight measurement entry yet
    And Patricia wants to set "Ava"'s baseline weight measurement
    When Patricia enters 68.5 kg in the weight field, "15.5.20, 8:15am" in the time field and "measurement done right after breakfast" in the comment field
    And she saves these entries
    Then "Ava"'s baseline weight entry should be found in the system

  @Ignore
  Scenario: Denial or setting of the baseline weight measurement when it already exists.

    Given Ava with first name "Ava", last name "Johnson", birthday "01.01.1989", gender "female" is registered
    And Ava has already a baseline measurement
    When Patricia wants to register Ava's baseline weight measurement
    Then she should not be able to register a new baseline measurement

  Scenario Outline: Allowed weight entry values
  To minimise the risk of wrong entries only entries between >=0.5 and <=200 are valid

    Given <first_name>,<last_name>,<birthday> is registered
    And has no baseline weight measurement entry yet
    When Patricia enters <weight>
    Then she can set the baseline weight measurement
    Examples:
      | first_name | last_name | birthday     | weight |
      | "Alec"     | "Turner"  | "01.05.2020" | 0.5    |
      | "Elec"     | "Turner"  | "21.09.1947" | 200.0  |
      | "Ilex"     | "Turner"  | "21.09.2014" | 12.8   |

  Scenario Outline: Forbidden weight entry values
  To minimise the risk of wrong entries only outside the range of between >=0.5 and <=200 are invalid

    Given <first_name>,<last_name>,<birthday> is registered
    And has no baseline weight measurement entry yet
    When Patricia enters <weight>
    Then she cannot set the baseline weight measurement
    Examples:
      | first_name | last_name | birthday     | weight |
      | "Eric"     | "Turner"  | "01.05.2020" | 785    |
      | "Eric"     | "Turner"  | "21.09.1947" | 0.45   |
      | "Eric"     | "Turner"  | "21.09.2014" | 0.3    |
      | "Eric"     | "Turner"  | "21.09.2014" | -20.0  |
      | "Eric"     | "Turner"  | "21.09.2014" | 200.05 |
      | "Eric"     | "Turner"  | "21.09.2014" | 0.3    |
      | "Eric"     | "Turner"  | "21.09.2014" | 0.3    |