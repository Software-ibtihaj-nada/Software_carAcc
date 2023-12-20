package car.AcceptTest;

import static org.junit.Assert.assertTrue;

import car.Installer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstallationRequest {
	String customer_name;
Installer installer;
 @Given("customer is logedin")
public void customerIsLogedin() {
	 installer=new Installer(); 
}

	@When("customer with name {string} want to view installation request")
	public void customerWithNameWantToViewInstallationRequest(String name) {
		installer.test=true;
		customer_name=name;
	}

	@Then("display installation request sucsessfully")
	public void displayInstallationRequestSucsessfully() {
	    assertTrue(installer.customerViewInstallation(customer_name));
	}

	@Given("Admin was login")
	public void adminWasLogin() {
		 installer=new Installer();
	}

	@When("Admin want to view installation request")
	public void adminWantToViewInstallationRequest() {
		installer.test=true;
	}

	@Then("all installation request will  display")
	public void allInstallationRequestWillDisplay() {
		assertTrue(installer.veiwInstallationRequestAdmin());
	}

}
