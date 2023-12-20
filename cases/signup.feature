
Feature: Sign Up

    Scenario Outline: Successful sig up
    Given user in Sign up page
    When  user with usertype <usertype> set email with <email> and username with <name> and password with <password> and confirmpassword with <confirmpassword> 
    Then the user should go to login page
    Examples:
    |usertype          |email                |name                  |password      |confirmpassword| 
    |"customer"        |"abood@gmail.com"    |"abood"               |"123"         |"123"|
    
  
    
    Scenario Outline: password does not match confirm password
    Given user in Sign up page
    When  user with usertype <usertype> set email with <email> and username with <name> and password with <password> and confirmpassword with <confirmpassword> 
    Then the user should recieve warning message and go back to sign page
    Examples:
   |usertype          |email                |name                  |password      |confirmpassword| 
    |"customer"        |"abood@gmail.com"    |"abood"               |"123"         |"111"|
    
    