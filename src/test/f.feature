 Feature: login 
Scenario Outline: successful login
    Given  user is on the login page
    When  user with usertype"<usertype>" set email with 'nada@gmail.com' and user set password with '12345' 
    #And  user click on login 
    Then the user should go to his panel
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    
    Scenario Outline: unsuccessful login with incorrect password
    Given  user is on the login page
    When  user with usertype"<usertype>" set email with 'nada@gmail.com' and user set password with '1234' 
    #And  user click on login 
    Then the user should go back to login
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    
    
    Scenario Outline: unsuccessful login with invalid user email
    Given  user is on the login page
    When  user with usertype"<usertype>" set email with 'nana@gmail.com' and user set password with '12345' 
    #And  user click on login 
    Then the user should recieve invalid user email message and go back to login
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    
    
    Scenario Outline: Successful sig up
    Given user in Sign up page
    When  user with usertype"<usertype>" set email with 'ibtihaj@gmail.com' and username with 'ibtihaj' and password with '592002' and confirmpassword with '592002' 
    Then the user should go to login page
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    
    Scenario Outline: password does not match confirm password
    Given user in Sign up page
    When  user with usertype"<usertype>" set email with 'ibtihaj@gmail.com' and username with 'ibtihaj' and password with '592002' and confirmpassword with '59111' 
    Then the user should recieve warning message and go back to sign page
    Examples:
    |usertype|
    |1|
    |2|
    |3|
    
    
    