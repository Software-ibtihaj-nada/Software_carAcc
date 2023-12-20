package car.AcceptTest;

import static org.junit.Assert.assertTrue;
import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class editprofileTest {
	Customer customer;
	String name;
	String email;
	boolean flag=false;
	String newpassword;
	@Given("customer was login")
	public void customerWasLogin() {
		customer=new Customer();
	}
	@When("customer with email {string}")
	public void customerWithEmail(String string) {
	   email=string; 
	}

	@When("want to set new name {string}")
	public void wantToSetNewName(String string) {
		name=string;
	}

	@Then("edit name successfully")
	public void editNameSuccessfully() {
	    assertTrue(customer.editName(email, name));
	}

	@When("want to set new email {string}")
	public void wantToSetNewEmail(String string) {
	    name=string;
	}

	@Then("edit email successfully")
	public void editEmailSuccessfully() {
		assertTrue(customer.editEmail( email,name));
	}
	

@When("want to set new password {string}")
public void wantToSetNewPassword(String newpass) {
	  newpassword=newpass;
 
}
//	
//	
//	@When("old password {string} want to set new password {string}")
//	public void oldPasswordWantToSetNewPassword(String old, String newpass) {
//		
//	  if(old.equals(customer.getCustomerPassword(email))){
//		 
//		  newpassword=newpass;
//		  flag=true;
//	  }
//	}
	@Then("edit password successfully")
	public void editPasswordSuccessfully() {
		
			assertTrue(customer.editPassword(email, newpassword));
		
	}


	
	
}
