package car.AcceptTest;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import car.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewProductTest {
	Product product;
	ArrayList<Product>p=new ArrayList<>();
	@Given("user login")
	public void userLogin() {
		product=new Product(); 
	}

	@When("{string} want to view product with category <category>")
	public void wantToViewProductWithCategoryCategory(String category) {
	    p=product.viewProduct(category);
	}

	@Then("products will displays")
	public void productsWillDisplays() {
	    assertNotNull(p);
	}

}
