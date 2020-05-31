#language: en

Feature: Zen OQ Test App Controller

  This script tests the OQ Test App. The goal is to show, that steps that should fail are failing and steps that work properly are passing in order to mitigate the risk of a not properly working test automation.

  @Ignore
  Scenario: Control Test Failure In Output

  AppTester will not be registered in the step 'registers AppTester'

    Given A participant AppTester
    And AppTester has first name "FirstName", last name "LastName", birthday "Birthday" and is "Gender"
    And AppTester is not registered yet
    And Checky wants to register AppTester
    When Checky enters AppTester's data
    And registers AppTester
    Then AppTester should be found in the system
    And AppTester's details should be displayed

  @Ignore
  Scenario: Control Test Success

    Given A participant AppTester
    And AppTester has first name "FirstName", last name "LastName", birthday "Birthday" and is "Gender"
    And AppTester is not registered yet
    And Checky wants to register AppTester
    When Checky enters AppTester's data
    And Checky registers AppTester
    Then AppTester should be found in the system
    And AppTester's details should be displayed

  @Ignore
  Scenario: Control Test Failure In Input

  AppTester is already in the system --> Scenario 'Control Test Success'

    Given A participant AppTester
    And AppTester has first name "FirstName", last name "LastName", birthday "Birthday" and is "Gender"
    And AppTester is not registered yet
    And Checky wants to register AppTester
    When Checky enters AppTester's data
    And Checky registers AppTester
    Then AppTester should be found in the system
    And AppTester's details should be displayed