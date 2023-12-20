package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class addproductTest {
	
Admin admin;
Product product;
	@Given("admin at admin dashboardd")
	public void adminAtAdminDashboardd() {
		admin=new Admin();
		product=new Product();
	}

	@When("admin set new product name {string}")
	public void adminSetNewProductName(String name) {
		admin.checkProduct(name);
		product.setName(name);
	}

	@When("set new product description {string}")
	public void setNewProductDescription(String description) {
		product.setDescription(description);
	}

	@When("set new product price {string}")
	public void setNewProductPrice(String price) {
		product.setPrice(Integer.parseInt(price));
	}

	@When("set new product quantity {string}")
	public void setNewProductQuantity(String quantity) {
		product.setquantity(Integer.parseInt(quantity));
	}

	@When("set category {string}")
	public void setCategory(String category) {
		product.setCategory(category);
	}
	@Then("product added successfully")
	public void productAddedSuccessfully() {
		boolean flag=false;
		if(!admin.checkprod) {
			product.insertProduct(product);
			if(admin.flaginsertP) 
				flag=true;

			else
				flag=false;
		}
		assertTrue(flag);

	}
	
	
}
