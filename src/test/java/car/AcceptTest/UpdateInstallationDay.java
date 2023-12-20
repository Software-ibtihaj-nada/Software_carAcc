package car.AcceptTest;

import static org.junit.Assert.assertTrue;

import car.Installer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateInstallationDay {
	int id;
	String day;
Installer installer;
@Given("Installer was login")
public void installerWasLogin() {
	installer=new Installer();
}

@When("installer want to change installation day with id {string} to day {string}")
public void installerWantToChangeInstallationDayWithIdToDay(String ins_id, String ins_day) {
    id=Integer.parseInt(ins_id);
    day=ins_day;
}

@Then("update day sucsessfully and send email to customer")
public void updateDaySucsessfullyAndSendEmailToCustomer() {
    assertTrue(installer.updateDayforcustomer(id, day));
}
}
