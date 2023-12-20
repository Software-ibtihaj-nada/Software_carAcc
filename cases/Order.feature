
Feature: Order

Scenario: Admin view all order
    Given  admin login 
    When  admin want to view all order
    Then display all order successfully

Scenario: customer view shoping cart
    Given  customer logedin
    When  customer with name 'nour@gmail.com' want to view shoping cart
   Then  customer order will display
    
 
Scenario: customer make order successfully
    Given  customer was loged in
    When  customer with name 'nour@gmail.com' and customer id '2'
    And make order with product name 'Mobil Holder' and product id '95'and product price '20'
    And  make order with quantity '2'
    Then  make order successfully
    
    
    Scenario: customer update order successfully
    Given  customer was loged in
     When  customer with name 'nour@gmail.com' and customer id '2'
    And  order with product name 'Mobil Holder' and product id '95'and product price '40'
    And  set order with quantity '3'
    Then  update order successfully
    
    
 Scenario: customer delete order successfully
    Given  customer was loged in
    When  customer name 'nour@gmail.com' and customer id '2'
    And  order with product name 'Mobil Holder' and product id '95'and product price '40'
    And   order with quantity '3'
   Then  delete order successfully
    
 