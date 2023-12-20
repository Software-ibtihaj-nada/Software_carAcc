package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.EMAIL;
import io.cucumber.java.en.*;
public class NotificationTest {
String email;


EMAIL e;
@Given("that order No. {string} of user No. {string} is ready")
public void thatOrderNoOfUserNoIsReady(String string, String string2) {
	e=new EMAIL();
}

@When("order No. {string} is ready and under delivery, an email is sent to the user with hi email address {string}")
public void orderNoIsReadyAndUnderDeliveryAnEmailIsSentToTheUserWithHiEmailAddress(String string, String string2) {
   email=string2;
}



@Given("that installation No. {string} of user No. {string}")
public void thatInstallationNoOfUserNo(String string, String string2) {
	e=new EMAIL(); 
}

@When("installation No. {string} is change day to new day {string} an email is sent to the user with hi email address {string}")
public void installationNoIsChangeDayToNewDayAnEmailIsSentToTheUserWithHiEmailAddress(String string, String string2, String string3) {
	email=string3;  
}

@When("customer make an installation request to installer with No.{string} an email is sent to the installer with email address {string}")
public void customerMakeAnInstallationRequestToInstallerWithNoAnEmailIsSentToTheInstallerWithEmailAddress(String string, String string2) {
	email=string2; 
}

@Then("The email was sent successfully")
public void theEmailWasSentSuccessfully() {
   assertTrue(e.sendEmail(email, "email ", " send emil succsessfully"));
}
	
	
}
