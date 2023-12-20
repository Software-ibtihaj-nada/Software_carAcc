Feature: admin  add product

Scenario: admin add product successfully
    Given  admin at admin dashboardd
    When  admin set new product name 'LED' 
    And set new product description 'white color' 
    And set new product price '10' 
    And set new product quantity '5' 
     And set category 'electronics' 
    Then product added successfully
 
 