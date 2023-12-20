package car.AcceptTest;

import static org.junit.Assert.assertTrue;

import car.Login;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class signupTest {
	Login obj;
 
	@Given("user in Sign up page")
	public void userInSignUpPage() {
		 obj=new Login(); 
	}
	boolean p=false,n=false,e;

@When("user with usertype {string} set email with {string} and username with {string} and password with {string} and confirmpassword with {string}")
public void userWithUsertypeSetEmailWithAndUsernameWithAndPasswordWithAndConfirmpasswordWith(String usertype, String email, String name,String pass ,String confpass) {
	
	obj.checkEmail(email, usertype.toString());
	e=obj.flagemail;
	
	 if(confpass.equals(pass)) {
		 p=true; 
	 }
	
	 else {
		 p=false;
	 }
	 int count=0;
		for(int i=0;i<name.length();i++) {
			if(Character.isDigit(name.charAt(i))) {
				count++;
			}
		}
		if(count!=name.length() && !Character.isDigit(name.charAt(0))) {
			n=true;
		}
  }
	

	@Then("the user should go to login page")
	public void theUserShouldGoToLoginPage() {
		if(p==true && n==true && e==false)
	    assertTrue(true);
		else assertTrue(false);
	}

	@Then("the user should recieve warning message and go back to sign page")
	public void theUserShouldRecieveWarningMessageAndGoBackToSignPage() {
		if(p==false && n==true && e==false)
		    assertTrue(true);
			else assertTrue(false);
	}


	
	
	

	
	
	

}
