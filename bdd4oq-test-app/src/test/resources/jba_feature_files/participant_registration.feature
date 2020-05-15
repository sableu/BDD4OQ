#language: en

Feature: Registration of a participant

  Specification brief: This specification file describes how a person that would like to
  participate in a clinical trial gets registered (or not) by the nurse Patricia.
  Input: bddoq-21

  Document History:
  By				In				On				Version			Remark

  Created					Sabrina			Notepad++	   	10-May-2020		1.0
  Added to GitHub: Version
  control aligned with
  code version control 	Sabrina			GitHub			10-May-2020		1.0				Part of the BachelorThesis evaluation - Is the ideal way, but Business is not used to GitHub --> Confluence GitHub integration?
  Added to Confluence
  to test Confluence
  version control  		Sabrina			Confluence		10-May-2020		1.0				Part of the BachelorThesis evaluation - Is probably easier for Business, but media break between creation process and usage as code


  Scenario: Registration of an unknown participant

  To register a new participant who has no entry in the DB yet, the first name, family name, gender and birth date needs to be entered in order to successfully register the new participatn.
  This represents the simplest happy path.

	  Given A participant Peter
	  And Peter has first name "Peter", last name "Parker", birthday "08.05.2020" and is "male"
	  And Peter is not registered yet
	  And Patricia wants to register Peter
	  When Patricia enters Peters data
	  Then Peter can be found in the system


  @Ignore
  Scenario Outline: Denied registration of an unknown participant

  First name, family name, gender and birth date are mandatory fields and needs to be entered in order to finalise the registration.

    Given Alex is not registered yet
    And the registration form is displayed
    And the First Name field of the form has value <first_name>
    And the Last Name field of the form has value <last_name>
    And the Birthdate field of the form has value <birthdate>
    And the Gender field of the form has value <gender>
    When Patricia confirms the registration
    Then The system should display the message
	  """
	  Please fill in all mandatory fields!
	  """
    And Alex should not be registered

    Examples:
      | first_name | last_name | birthdate    | gender |
      | "Alex"     | "Turner"  | "21.19.1946" | ""     |
      | "Alex"     | "Turner"  | ""           | "male" |
      | "Alex"     | ""        | "21.19.1946" | "male" |
      | ""         | "Turner"  | "21.19.1946" | "male" |
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
      | "Gender"              | "9.2.1992"                                                                      |
      | "Birthdate"           | "female"                                                                        |
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
    Then The new entries should be found in the system

  @Ignore
  Scenario: Denied registration of known participant

  A person is already registered, then it should not be possible to register hime/her a second time.

    Given Jan is already registered
    And Patricia wants to register Jan
    When Patricia enters "Jan", "Parker", "13.2.1999" and "male" in the respecive fields
    Then The system should display the message
	  """
	  Entry already exists!
	  """
    And Patricia should be able to update Jan's data
	
	