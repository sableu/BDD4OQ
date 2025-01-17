#language: en

Feature: Setting the baseline weight measurement
  This specification describes how the nurse Patricia sets the baseline weight measurement of the participants.

  Covered Requirements:
  bddoq-26: Setting of the Participant's Baseline Weight Measurement

  History (the last 8 versions are displayed on this list):
  ```
  |Sig. V.|Description                    | Name               | Date       |dig.Sig.|
  |-------|-------------------------------|--------------------|------------|--------|
  |0.0.0.1|FS initial version --> bddoq-26| Sabrina Leuenberger| 28-May-2020|le      |
  |0.0.0.2|FS initial version reviewed    | Patricia Walker    | 29-May-2020|wp      |
  |0.0.1.0|FS initial version approved    | Hank McKoy         | 29-May-2020|mh      |
  |0.0.1.1|FS adapted as Test script (TS) | Andreas Hosbach    | 03-Jun-2020|ha      |
  |0.0.1.2|TS reviewed                    | Patricia Walker    | 04-Jun-2020|wp      |
  |1.0.0.0|TS approved: Ready for OQ      | Hank McKoy         | 04-Jun-2020|mh      |
  ```

  Size:
  10 active scenarios
  61 active steps


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

  Scenario: Displaying baseline weight measurement of a participant
  Patricia needs to see the baseline weight measurement entry of a participant once it is set.

    Given a participant with first name "Ava", last name "Johnson", birthday "01.01.1989", gender "female" is registered
    And "Ava"'s baseline weight measurement is set
    And Patricia is on the participants overview page
    When Patricia opens "Ava"'s detail page
    Then "Ava"'s baseline weight entry should be displayed on that page

  @Ignore
  Scenario: Denial of setting the baseline weight measurement when it already exists.

    Given Ava with first name "Ava", last name "Johnson", birthday "01.01.1989", gender "female" is registered
    And Ava has already a baseline measurement
    When Patricia wants to register Ava's baseline weight measurement
    Then she should not be able to register a new baseline measurement

  Scenario Outline: Allowed weight entry values: <weight>
  To minimise the risk of wrong entries, only entries between >=0.5 and <=200 are valid

    Given a participant with first name <first_name>, last name <last_name>, birthday "21.09.2014", gender "male" is registered
    And <first_name> has no baseline weight measurement entry yet
    And Patricia wants to set <first_name>'s baseline weight measurement
    When Patricia enters <weight> kg and any valid date time
    Then she can set the baseline weight measurement
    Examples:
      | first_name | last_name | weight |
      | "Alec"     | "Turner"  | 0.5    |
      | "Elec"     | "Turner"  | 200.0  |
      | "Ilex"     | "Turner"  | 12.8   |

  Scenario Outline: Forbidden weight entry values: <weight>
  To minimise the risk of wrong entries, values are invalid when they are not in the range between >=0.5 and <=200

    Given a participant with first name "Eric", last name "Turner", birthday "21.09.2014", gender "male" is registered
    And "Eric" has no baseline weight measurement entry yet
    And Patricia wants to set "Eric"'s baseline weight measurement
    When Patricia enters <weight> kg and any valid date time
    Then she cannot set the baseline weight measurement
    Examples:
      | weight |
      | 785    |
      | 0.45   |
      | 0.3    |
      | -20.0  |
      | 200.05 |