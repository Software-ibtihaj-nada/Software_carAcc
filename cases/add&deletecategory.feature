

Feature: Add and Delete category
Scenario: admin add category successfully
    Given  admin was loged in
    When  admin set new category 'electric' 
    Then category added successfully

    Scenario: admin add category unsuccessfully
    Given  admin was loged in
    When  admin set new category 'electric' 
    Then category already exist and doesnt add
    
    
     Scenario: admin delete category successfully
    Given  admin was loged in
    When  admin set category to delete 'electric' 
    Then delete category successfully

  
    
    
    