
Feature: Installation

  Scenario: customer view installation request
    Given customer is logedin
    When customer with name "nour" want to view installation request
    Then display installation request sucsessfully
    
 Scenario: Admin view installation request
    Given Admin was login
    When Admin want to view installation request
    Then all installation request will  display
    
  Scenario: Installer update installation request day
    Given Installer was login
    When installer want to change installation day with id "11" to day "monday"
    Then update day sucsessfully and send email to customer
    
 