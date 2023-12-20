 Feature: login 
Scenario Outline: successful login
    Given  user is on the login page
    When  user with usertype <usertype> set email with <email> and user set password with <password>
    Then the user should go to his panel
    Examples:
    |usertype          |email                  |password|
    |"admin"           |"nada@gmail.com"       |"123456"|
    |"customer"        |"nour@gmail.com"       |"123"|
    |"installer"       |"omar@gmail.com"       |"12345"|
    
    Scenario Outline: unsuccessful login with incorrect password
    Given  user is on the login page
    When  user with usertype <usertype> set email with <email> and user set password with <password>
    Then the user should go back to login
    Examples:
    |usertype         |email                  |password|
    |"admin"           |"nada@gmail.com"       |"1256"|
    |"customer"        |"nour@gmail.com"       |"12322"|
    |"installer"       |"omar@gmail.com"       |"1231145"|
    
    
    Scenario Outline: unsuccessful login with invalid user email
    Given  user is on the login page
    When  user with usertype <usertype> set email with <email> 
    Then the user should recieve invalid user email message and go back to login
    Examples:
    |usertype          |email|                 
    |"admin"           |"nana@gmail.com"|     
    |"customer"        |"nana@gmail.com"|      
    |"installer"       |"nana@gmail.com"|      
    
   
    
    