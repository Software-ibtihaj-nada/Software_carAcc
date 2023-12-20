package car.AcceptTest;
import static org.junit.Assert.assertTrue;

import car.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderTest {
     Admin admin;
	Order order;
	 int Orderid;
	 int quantity;
	 String email;
	 
	 @Given("admin login")
	 public void adminLogin() {
		 order=new Order();  
	
	 }

	 @When("admin want to view all order")
	 public void adminWantToViewAllOrder() {
			order.test=true; 
	 }

	 @Then("display all order successfully")
	 public void displayAllOrderSuccessfully() {
	     assertTrue(order.adminViewOrder());
	 }

	 
	 @Given("customer was loged in")
	 public void customerWasLogedIn() {
		order=new Order(); 
	}
	 
	 @Given("customer logedin")
	 public void customerLogedin() {
			order=new Order();   
	 }
	 @When("customer with name {string} want to view shoping cart")
	 public void customerWithNameWantToViewOrde(String user) {
		 order.test1=true;
	     email=user;
	     
	 }

	 @Then("customer order will display")
	 public void customerOrderWillDisplay() {
	    assertTrue(order.viewOrder(email));
	 }

	 
	@When("customer with name {string} and customer id {string}")
	public void customerWithNameAndCustomerId(String Cname, String Cid) {
		order.setcustomername(Cname);
		order.setcustomerId(Integer.parseInt(Cid));
	}
	@When("make order with product name {string} and product id '{int}'and product price '{int}'")
	public void makeOrderWithProductNameAndProductIdAndProductPrice(String Pname, Integer Pid, Integer Pprice) {
		order.setproductname(Pname);
		order.setproductId(Pid);
		order.setproductprice(Pprice);
	}
	@When("make order with quantity {string}")
	public void makeOrderWithQuantity(String string) {
	   order.setproductquntity(Integer.parseInt(string));
	}
	@Then("make order successfully")
	public void makeOrderSuccessfully() {
		order.insertOrder(order);
		assertTrue(Customer.finsertorder);
	}
	@When("customer name {string} and customer id {string}")
	public void customerNameAndCustomerId(String Cname, String Cid) {
		order.setcustomername(Cname);
		order.setcustomerId(Integer.parseInt(Cid));
	}
	@When("order with product name {string} and product id '{int}'and product price '{int}'")
	public void orderWithProductNameAndProductIdAndProductPrice(String pname, Integer PId, Integer Pprice) {
		int idd=PId;
		int pid=Pprice;
		order.setproductname(pname);
		order.setproductId(idd);
		order.setproductprice(pid);
	}
	@When("set order with quantity {string}")
	public void setOrderWithQuantity(String string) {
		quantity=Integer.parseInt(string);
		
	   // order.setproductquntity(quantity);
	    
	}
	@Then("update order successfully")
	public void updateOrderSuccessfully() {
		order.setproductquntity(2);
		Orderid=order.getOrderId(order) ;
		assertTrue(	order.updateOrder(Orderid, quantity));
	}
	
	
//	@Given("customer want to delete an order")
//	public void customerWantToDeleteAnOrder() {
//		order=new Order();
//	}
//
	

	@When("order with quantity {string}")
	public void orderWithQuantity(String quantity) {
		order.setproductquntity(Integer.parseInt(quantity));  
	}

	@Then("delete order successfully")
	public void deleteOrderSuccessfully() {
	   int id=order.getOrderId(order);
	   order.deleteOrder(id);
	   assertTrue(Customer.flagdeleteO);
	}

}
