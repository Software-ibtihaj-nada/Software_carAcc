package car.AcceptTest;

import static org.junit.Assert.assertTrue;

import car.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class updateProductTest {
	Product product;
	String value;
	String name;
	String type;
	@Given("admine was login")
	public void admineWasLogin() {
		product=new Product();
	}

	@When("product with name {string}")
	public void productWithName(String productName) {
	 name=productName;
	}

	@When("new name for product is {string}")
	public void newNameForProductIs(String newName) {
		type="name";
		value=newName;
	}

	@When("new description for product is {string}")
	public void newDescriptionForProductIs(String description) {
		type="description";
	    value=description;
	}

	@When("new price for product is {string}")
	public void newPriceForProductIs(String price ){
		type="price";
	    value=price;
	}

	@When("new quantity for product is {string}")
	public void newQuantityForProductIs(String quantity) {
		type="quantity";
	    value=quantity;
	}
	@Then("product update succsessfulley")
	public void productUpdateSuccsessfulley() {
	    Integer id_prod=product.getProductId(name);
	    String id=id_prod.toString();
	    assertTrue(product.updateProduct(id, type, value));
	}
}
