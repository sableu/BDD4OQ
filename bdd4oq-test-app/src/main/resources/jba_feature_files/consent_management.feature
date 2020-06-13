#language: en

Feature: Consent management
  This specification describes how the nurse Patricia manages the participant's consent. 
  The participant's consent is an important aspect in order to achieve compliance with HFV Art. 31 (https://www.eknz.ch/gesuchseinreichung/)

  Covered Requirements:
  bddoq-22: Consent registration

  History (the last 8 versions are displayed on this list):
  ```
  |Sig. V.|Description                    | Name               | Date       |dig.Sig.|
  |-------|-------------------------------|--------------------|------------|--------|
  |0.0.0.1|FS initial version --> bddoq-22| Sabrina Leuenberger| 11-Jun-2020|le      |
  |0.0.0.2|FS initial version reviewed    | Patricia Walker    | 11-Jun-2020|wp      |
  |0.0.1.0|FS initial version approved    | Hank McKoy         | 11-Jun-2020|mh      |
  ```

  Size:
  1 active scenarios
  6 active steps


  Background:
    Given Patricia has the application open


  Scenario: Consent registration
  Patricia, the nurse, needs to know, if a participant has given her consent for the weight measurments and the subsequent data handling. Therefore she has to add that information, after the consent was given.
  This represents the simplest happy path.

    Given a participant with first name "Wanda", last name "Maximoff", birthday "12.12.1989", gender "female" is registered
    And "Wanda" did not give her consent so far
    When Patricia registers that "Wanda"'gave her consent
	And she displays "Wanda"'s details
	Then Patricia should see on the participant detail page that the consent was given.

  