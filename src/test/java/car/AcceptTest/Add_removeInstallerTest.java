package car.AcceptTest;

import static org.junit.Assert.assertTrue;

import car.Admin;
import car.Installer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Add_removeInstallerTest {
	Installer installer;
	String type;
	int installer_id;
	@Given("admin is loged in")
	public void adminIsLogedIn() {
		installer=new Installer();
	}

	@When("want to add installer set first name {string}")
	public void wantToAddInstallerSetFirstName(String fname) {
		installer.setfname(fname);
	}

	@When("set last name {string}")
	public void setLastName(String lname) {
		installer.setlname(lname);
	}

	@When("set email {string}")
	public void setEmail(String email) {
		installer.setemail(email);
	}

	@When("set password {string}")
	public void setPassword(String password) {
		installer.setpassword(password);
	}

	@When("set phone {string}")
	public void setPhone(String phone) {
	    installer.setphone(phone);
	}

	@When("set user type {string}")
	public void setUserType(String usertype) {
	     type=usertype;
	}

	@Then("installer added succssesfully")
	public void installerAddedSuccssesfully() {
		assertTrue(installer.insertInstaller(installer));
	}

	@When("admin set installer email {string}")
	public void adminSetInstallerName(String email) {
	   
	  installer_id=installer.getInstallerId(email);
		
	}

	@Then("installer removed succssesfully")
	public void installerRemovedSuccssesfully() {
	   assertTrue(installer.removeInstaller(installer_id));
	}
}
