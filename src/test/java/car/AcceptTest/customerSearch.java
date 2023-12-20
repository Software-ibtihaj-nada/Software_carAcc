package car.AcceptTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import car.Customer;
import car.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class customerSearch {
Product product;
ArrayList<Product>p=new ArrayList<>();
	@Given("customer login")
	public void customerLogin() {
		product=new Product();
	}

	@When("customer  search about product with name {string}")
	public void customerSearchAboutProductWithName(String name) {
		p=product.searchByName(name);
	    
	}

	@Then("product will display")
	public void productWillDisplay() {
	   assertNotNull(p); 
	}

	@Then("no product found message will display")
	public void noProductFoundMessageWillDisplay() {
//	    assertFalse(Customer.flag_search);
		assertNull(p);
	}

	@When("customer  search about product with price {string}")
	public void customerSearchAboutProductWithPrice(String price) {
		p=product.searchByPrice(Integer.parseInt(price));
	    
	}

	@When("customer  search about product with category {string}")
	public void customerSearchAboutProductWithCategory(String category) {
		p=product.searchByCategory(category);
	    
	}
}
