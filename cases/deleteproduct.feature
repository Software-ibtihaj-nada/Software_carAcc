Feature: admin delete product
Scenario: admin delete product successfully
    Given  admin at admin dashboarrd
    When  admin set  product name 'LED'  and set category 'electronics' 
    Then product deleted successfully
 