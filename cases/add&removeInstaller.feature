Feature: Add and remove installer

  Scenario: Add installer succssesfully
    Given admin is loged in
    When want to add installer set first name "ALi" 
    And set last name "Qasem"
    And set email "ali@gmail.com"
    And set password "98765"
    And set phone "059757882"
    And set user type "installer"
    Then installer added succssesfully

  Scenario: Remove installer succssesfully
    Given admin is loged in
    When admin set installer email "ali@gmail.com"
    Then installer removed succssesfully
  