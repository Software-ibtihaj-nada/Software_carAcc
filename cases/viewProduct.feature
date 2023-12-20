
Feature: View Product
  Scenario Outline: View Product
    Given  user login
    When <usertype> want to view product with category <category>
    Then products will displays

    Examples: 
      |usertype         |categoty |   
      | "admin"         |"interior"|
      | "customer"      | "interior"|
      | "admin"         |"exterior"|
      | "customer"      |"exterior"|
      | "admin"         |"electronics"|
      | "customer"      | "electronics"|
