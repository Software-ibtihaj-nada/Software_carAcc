
Feature: Update product

  Scenario:  Update product name
    Given admine was login
    When product with name "Seat Covers" 
    And  new name for product is "Seat Covers"
    Then product update succsessfulley
   

  Scenario:  Update product description
    Given admine was login
    When product with name "Seat Covers" 
    And  new description for product is "Protect your car seats and add a touch of style"
    Then product update succsessfulley
    
    
    
  Scenario:  Update product price
    Given admine was login
    When product with name "Seat Covers" 
    And  new price for product is "55"
    Then product update succsessfulley
    
    
      Scenario:  Update product quantity
    Given admine was login
    When product with name "Seat Covers" 
    And  new quantity for product is "15"
    Then product update succsessfulley
    
    