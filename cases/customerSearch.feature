
Feature: Customer Search
  Scenario: search by name of product available
    Given customer login
    When customer  search about product with name "Seat Covers"
    Then product will display
   
   
Scenario: search by name of product unavailable
    Given customer login
    When customer  search about product with name "Amplifier"
    Then no product found message will display
   
     Scenario: search by price of product available
    Given customer login
    When customer  search about product with price "200"
    Then product will display
   
   
      
Scenario: search by price of product unavailable
    Given customer login
    When customer  search about product with name "2"
    Then no product found message will display
   
   
   Scenario: search by category of product available
    Given customer login
    When customer  search about product with category "interior"
    Then product will display
   
   Scenario: search by category of product unavailable
    Given customer login
    When customer  search about product with name "electromagnatic"
    Then no product found message will display
   