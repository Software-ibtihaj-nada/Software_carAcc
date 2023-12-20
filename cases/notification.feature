Feature: Send an email notification

Description : Sending an email to the customer that his order is ready and under delivery.
Actor: company
	
Scenario: Send an email to the customer.
	Given that order No. "3" of user No. "3" is ready
	When order No. "3" is ready and under delivery, an email is sent to the user with hi email address "ibtihajsami@gmail.com" 
	Then The email was sent successfully
	
	Scenario: Send an email to the customer.
	Given that installation No. "3" of user No. "3" 
	When installation No. "3" is change day to new day "sunday" an email is sent to the user with hi email address "ibtihajsami@gmail.com" 
	Then The email was sent successfully
	
	Scenario: Send an email to the installer.
	Given customer was login 
	When customer make an installation request to installer with No."5" an email is sent to the installer with email address "nada.ajaj@gmail.com" 
	Then The email was sent successfully
	