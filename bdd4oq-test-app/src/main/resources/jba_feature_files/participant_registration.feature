#language: en

Feature: Registration of a participant
  This specification describes how a person that would like to
  participate in a clinical trial gets registered (or not) by the nurse Patricia.

  Covered Requirements:
  bddoq-21: Participant Registration
  bddoq-51: Re-Direction to the Participant's Page after successful Registration

  History (the last 8 versions are displayed on this list):
  Version    |  Description                          | Name                   | Date          |  Digital Signature  ||
  0.0.1.2    |  TS reviewed                          | Patricia Walker        | 24-May-2020   |  wp (symbolic)      ||
  0.1.0.0    |  TS approved                          | Hank McKoy             | 25-May-2020   |  mh (symbolic)      ||
  0.1.0.1    |  FS bddoq-51 included                 | Sabrina Leuenberger    | 28-May-2020   |  le (symbolic)      ||
  0.1.0.2    |  FS reviewed                          | Patricia Walker        | 29-May-2020   |  wp (symbolic)      ||
  0.1.1.0    |  FS approved                          | Hank McKoy             | 29-May-2020   |  mh (symbolic)      ||
  0.1.1.1    |  FS adapted as Test script (TS)       | Andreas Hosbach        | 03-Jun-2020   |  ha (symbolic)      ||
  0.1.1.2    |  TS reviewed                          | Patricia Walker        | 04-Jun-2020   |  wp (symbolic)      ||
  1.0.0.0    |  TS approved  --> ready for OQ        | Hank McKoy             | 04-Jun-2020   |  mh (symbolic)      ||

  Size:
  3 active scenarios
  21 active steps


  Background:
    Given Patricia has the application open


  Scenario: Registration of an unknown participant

  To register a new participant who has no entry in the DB yet, the first name, family name, gender and birth date needs to be entered in order to successfully register the new participant.
  This represents the simplest happy path.

    Given a participant with first name "Peter", last name "Parker", birthday "10.08.2001" and is "male"
    And "Peter" is not registered yet
    And Patricia wants to register "Peter"
    When Patricia enters "Peter"'s data
    And registers them
    Then "Peter"'s details should be displayed


  Scenario Outline: Registration of unknown participants with complicated names <description>

  Non standard characters, that might be used in some names, have to be accepted by the system

    Given a participant with first name <first_name>, last name <last_name>, birthday <birthday> and is <gender>
    And <first_name> is not registered yet
    And Patricia wants to register <first_name>
    When Patricia enters <first_name>'s data
    And registers them
    Then <first_name>'s details should be displayed
    Examples:
      | descriptions | first_name      | last_name         | birthday                | gender   |
      | specialities | "Hans-Peter J." | "Rudolf von Rohr" | "16th of May 1951"      | "male"   |
      | french       | "Céline"        | "d'Artagnan"      | "18th of November 1982" | "female" |


  @Ignore
  Scenario Outline: Denied registration of an unknown participant

  First name, family name, gender and birth date are mandatory fields and needs to be entered in order to finalise the registration.
  The mandatory fields are defined in a way to mitigate the risk of a mix-up of two participants.

    Given Alex is not registered yet
    And the registration form is displayed
    And the First Name field of the form has value <first_name>
    And the Last Name field of the form has value <last_name>
    And the birthday field of the form has value <birthday>
    And the Gender field of the form has value <gender>
    When Patricia confirms the registration
    Then The system should stay on the registration page
    And should display the message
	  """
	  Please fill in all mandatory fields!
	  """
    And Alex should not be registered

    Examples:
      | first_name | last_name | birthday     | gender |
      | "Alex"     | "Turner"  | "21.09.1946" | ""     |
      | "Alex"     | "Turner"  | ""           | "male" |
      | "Alex"     | ""        | "21.09.1946" | "male" |
      | ""         | "Turner"  | "21.09.1946" | "male" |
      | "Alex"     | "Turner"  | ""           | ""     |
      | ""         | "Turner"  | ""           | ""     |


  @Ignore
  Scenario: Registration with denial of participation

  A person would like to participate in the clinical trial, but he/she is not allowed to. He/she can still be registered in view of future clinical trials, where the person could participate.

    Given Alicia is not registered yet
    And Alicia is not allowed to participate yet
    And Patricia wants to register her for a future clinical trial
    When Patricia enters the following dates and confirms
      | field                 | value                                                                           |
      | "First Name"          | "Alicia"                                                                        |
      | "Last Name"           | "Bianchi"                                                                       |
      | "Gender"              | "female"                                                                        |
      | "Birthday"            | "9.2.1992"                                                                      |
      | "Participation denied | "yes"                                                                           |
      | "Comment"             | "Patricia is pregnant. She will be able to participate in 2022: alicia@foo.com" |
    Then Alicia should be found in the system with all the information entered.


  @Ignore
  Scenario: Update registration of known participant

  A person is already registered, but one or several of the entries needs to be updated.

    Given Mia is already registered as Mia Parker
    And Patricia wants to update her last name and add a comment
    When Patricia enters "Miller" in the respective field
    And adds into the comment field "married the 12th of June 2019"
    Then the new set of data should be found in the system


  @Ignore
  Scenario Outline: Denied registration of known participant

  A person is already registered, then it should not be possible to register him/her a second time.
  To mitigate the risk of mixing up participants, the combination of the first name, last name and birthday must be unique and case insensitive.

    Given Scott is already registered with following data: "Scott", "Lang", "1st of March 1997", "male"
    And Patricia wants to register Scott
    When Patricia enters <first_name>, <last_name>, <birthday>" and <gender> in the respective fields
    Then The system should display the message
	  """
	  Entry already exists!
	  """
    And the existing entry should show up.
    Examples:
      | first_name | last_name | birthday            | gender   |
      | "Scott"    | "Lang"    | "1st of March 1997" | "male"   |
      | "Scott"    | "Lang"    | "1st of March 1997" | "female" |
      | "Scott"    | "lang"    | "1st of March 1997" | "male"   |
      | "scott"    | "lang"    | "1st of March 1997" | "male"   |
      | "scott"    | "Lang"    | "1st of March 1997" | "male"   |


  @Ignore
  Scenario Outline: Accepted registration of a similar participant

  A person is already registered, then it should not be possible to register him/her a second time.

    Given Scott is already registered with following data: "Scott", "Lang", "1st of March 1997", "male"
    And Patricia wants to register a similar participant
    When Patricia enters <first_name>, <last_name>, <birthday>" and <gender> in the respective fields
    And wants to register them
    Then the similar participant should be found in the system
    Examples:
      | first_name | last_name | birthday             | gender |
      | "Scott"    | "Lang"    | "31st of March 1990" | "male" |
      | "Scotty"   | "Lang"    | "1st of March 1997"  | "male" |
      | "Scott"    | "Lango"   | "1st of March 1997"  | "male" |
