Feature: customer edit profile
 
Scenario: customer edit email
    Given  customer was login
    When  customer with email 'Alaa@gmail.com' 
    And  want to set new email 'lolo@gmail.com'
    Then edit email successfully

Scenario: customer edit name
    Given customer was login
    When  customer with email 'lolo@gmail.com' 
    And  want to set new name 'Alaa'
    Then edit name successfully
    
    Scenario: customer edit password
    Given  customer was login
    When  customer with email 'lolo@gmail.com' 
    And  want to set new password 'alaa44'
    Then edit password successfully
    

    
 Scenario: customer edit email
    Given  customer was login
    When  customer with email 'lolo@gmail.com' 
    And  want to set new email 'Alaa@gmail.com'
    Then edit email successfully
 

  