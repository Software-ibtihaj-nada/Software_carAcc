package car.AcceptTest;
import static org.junit.Assert.assertTrue;
import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class deleteproductTest {
	Admin admin;
	Product product;
	@Given("admin at admin dashboarrd")
	public void adminAtAdminDashboarrd() {
		admin=new Admin();
		product=new Product();  
	}
	@When("admin set  product name {string}  and set category {string}")
	public void adminSetProductNameAndSetCategory(String name, String category) {
		int idP=product.getProductId(name);
	    product.removeProdct(idP, category);
	}
	@Then("product deleted successfully")
	public void productDeletedSuccessfully() {
	   assertTrue(admin.flagdeleteP);
	}

}
