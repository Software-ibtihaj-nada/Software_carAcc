package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
public class Customer {
	   public static final Logger LOGGER = Logger.getLogger(Login.class.getName());
	    public static final Scanner SCANN = new Scanner(System.in);
	private  String scann;
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	private static boolean isavaliable=false;
	private static boolean finsertorder=false;
	private static boolean flagSearch=false;
private static boolean flagdeleteO=false;
private Product product=new Product();
private Order order=new Order();
private Installer installer=new Installer();
private String customername;
private String phone;
private String city;
private String street;
private String customeremail;
private String password;
private int customerid;
private static final String SELECT_USERS_QUERY = "Select * from users where email='";
private static final String SELECT_PRODUCT_BY_ID_QUERY = "Select * from product where id='";
private static final String QUANTITY_LITERAL = "quantity";

	public Customer() {

	}
	public void setName( String customername) {
		this.customername=customername;
	}
	public void setEmail( String customeremail) {
		this.customeremail=customeremail;
	}

	public void setPassword( String password) {
		this.password=password;
	}
	public void setid( int customerid) {
		this.customerid=customerid;
	}

	public String getname() {
	return customername;
	}
	public String getemail() {
		return customeremail;
		}
	public String getpassword() {
		return password;
		}

	public int getid() {
	return customerid;
	}
	public void customerDashboard(String user) {
		int x=0;
		while(x!=1) {
			LOGGER.info("Welcome, CUSTOMER!");
			LOGGER.info("Please choose you want need.");
			LOGGER.info("1.View category.");
			LOGGER.info("2.View product.");
			LOGGER.info("3.Make Installation request.");
			LOGGER.info("4.View Installation request.");
			LOGGER.info("5.Search.");
			LOGGER.info("6.View Shopping cart");
			LOGGER.info("7.Edit your profile");
			LOGGER.info("8.Log OUT");

			String input=SCANN.nextLine();
			if(input.equalsIgnoreCase("1")) {// View category
				product.viewCategories();
			}

			else if(input.equalsIgnoreCase("2")){//View Product
				LOGGER.info("Enter name of category");
	              String category=SCANN.nextLine();
	              ArrayList<Product>prod;
 	             
	              prod= product.viewProduct(category);
	              String vailability;
		         for(int i=0;i<prod.size();i++) {
		        	 if(prod.get(i).getQuientity()>0) {
							vailability="avaliable";
						}
						else {
							vailability="not avaliable";
						}
		        	 LOGGER.info(String.format("id=%d\t%s\t%s\t%d$\t%s\t%d star",
		                     prod.get(i).getId(),
		                     prod.get(i).getName(),
		                     prod.get(i).getDescription(),
		                     prod.get(i).getPrice(),
		                     vailability,
		                     oldEvalProduct(prod.get(i).getId())));
		     			}
				viewBuy(user);
			}
			
			else if(input.equalsIgnoreCase("3")){//to Make Installation request
                 makeInstallation(user);
			}
			else if(input.equalsIgnoreCase("4")){//to view Installation request
				String name=getCustomerName(user);
			
				installer.customerViewInstallation(name);
			}
			else if(input.equalsIgnoreCase("5")){//
				search(user);
			}
			else if(input.equalsIgnoreCase("6")){// to View Order
				order.viewOrder(user);
				shoppingCart(user);
			}
			else if(input.equalsIgnoreCase("7")){// Edit profile
		     editCustomerProfile(user);
			}
			else if(input.equalsIgnoreCase("8")){
				
				x=1;
			} 
			else {
				LOGGER.info("Invalid choice. Please enter 1, 2, 3,4,5,6 ,7or 8.");

			}

		}

	}
	

	public void viewCategoryProduct(String category) {

		try {
			LOGGER.info("please choose the number of product you want.");
			connection();
			String sql="Select * from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			String vailability=null;
			while (rs.next()) {
				String id="id = "+rs.getInt("id");
				String name= rs.getString("name");
				String description= rs.getString("description");
				int price= rs.getInt("price");

				if(rs.getInt(QUANTITY_LITERAL)>0) {
					vailability="avaliable";
				}
				else {
					vailability="not avaliable";
				}

				LOGGER.info(String.format("%d\t%s\t%s\t%d$\t%s\t%d Stars", id, name, description, price, vailability, rs.getInt("evaluation")));
		}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void viewBuy(String customername){
		LOGGER.info("1.add product to cart");
		LOGGER.info("2.Rate the products");
		LOGGER.info("3.Back to  products");
		scann=SCANN.nextLine();
		if(scann.equalsIgnoreCase("1")) {
			LOGGER.info("please enter id product");
			scann=SCANN.nextLine();
			int productId=Integer.parseInt(scann);
			LOGGER.info("enter quntity");
			scann=SCANN.nextLine();
			int quantity=Integer.parseInt(scann);

			productAvailable(quantity,productId);//add to order table 
			if(getIsAvaliable()) {
				int customerId=getCustomerId(customername);
				String productname=product.getProductName(productId);
				int price=product.getProductPrice(productId);
				Order orderr=new Order(customername,customerId, productId,  productname,quantity,price);
				order.insertOrder(orderr);
				if(getFinsertOrder()) {
					LOGGER.info("insert order succsessfully");
					int pQuantity=product.getProductQuantity(productId);
					pQuantity-=quantity;
					product.updateProductQuantity(productId,pQuantity);
				}
				else {
					LOGGER.info("insert order unsuccsessfully");	
				}
			}
			else {
				LOGGER.info("This product is not avaliable or quantity avaliable not enough");	

			}

		}
		else if(scann.equalsIgnoreCase("2")) {
			LOGGER.info("please enter id product");
			scann=SCANN.nextLine();
			int productId=Integer.parseInt(scann);
			LOGGER.info("please enter your evaluation for product between 1-5");
			scann=SCANN.nextLine();
			int eval=Integer.parseInt(scann);
			
			while(eval<1||eval>5) {
				LOGGER.info("please enter your evaluation for product between 1-5");
				scann=SCANN.nextLine();
				 eval=Integer.parseInt(scann);	
			}
			int oldeval=oldEvalProduct(productId);
			int numberOfUser=numberOfUserEval(productId);
			updateUserEval(productId,(numberOfUser+1));
			if(oldeval!=0) {
				int avgEval=(eval+oldeval)/2;
				setEval(productId,avgEval);	
			}
			else {
				setEval(productId,eval);	
			}
		}
	}
	public void makeInstallation(String email) { 
		installer.viewInstaller();
		 LOGGER.info("Enter the id of installer you want ");
		 String installerId=SCANN.nextLine();
		 int instalerIdd=Integer.parseInt(installerId);
		 LOGGER.info("Enter the day of installation you want ");
		 String day=SCANN.nextLine();
		 
			LOGGER.info("Enter your request");
			String request=SCANN.nextLine();
			
			LOGGER.info("Enter your car model");
			String carModel=SCANN.nextLine();
			 
			LOGGER.info("Enter your phone number");
			this.phone=SCANN.nextLine();
			
			LOGGER.info("Enter your address");
			LOGGER.info("Enter your city");
			this.city=SCANN.nextLine();
			
			LOGGER.info("Enter your street");
			 this.street=SCANN.nextLine();
			 this.customername=getCustomerName(email);
			 String installerName=installer.getInstallerName(instalerIdd);
		 installationReq(carModel,request,installerName,day);
		 installer.editDay(day,instalerIdd,true);
		 
		 EMAIL emaill=new EMAIL();
			String body="Dear installer , \n you are have anew installation rwquest , please check your installation request table .";
         		
			String subject="Customer installation request";
			emaill.sendEmail("nadoosh.jamal.aj@gmail.com", subject, body);
	}
	
	
	
public String getCustomerName(String email) {
	String name=null;
	try {
		connection();
		String sql=SELECT_USERS_QUERY +email+"' and user_type='customer' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			name=rs.getString("name");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return name ;
}
public boolean installationReq(String carmodel,String request,String installerNamme,String day) {
	int num=0;
	try {
		connection();
		String sql="INSERT INTO installation_req (customername,customerphone,customerreq,carmodel,city,street,day,installer_name) values(?,?,?,?,?,?,?,?)";
		stm=con.prepareStatement(sql);

				stm.setString(1,customername);
		    	stm.setString(2,phone);
		    	stm.setString(3,request);
		    	stm.setString(4,carmodel);
		    	stm.setString(5,city);
		    	stm.setString(6,street);
		    	stm.setString(7,day);
		    	stm.setString(8,installerNamme);
		 
		     num=stm.executeUpdate();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	
	return num>0;
	
}
	
	public void search(String user) {
		LOGGER.info("1.Search by name.");
		LOGGER.info("2.Search by price.");
		LOGGER.info("3.Search by category.");
		scann=SCANN.nextLine();
		ArrayList<Product>prod=new ArrayList<>();
	
		if(scann.equalsIgnoreCase("1")) {
			LOGGER.info("enter name");
			scann=SCANN.nextLine();
			prod=product.searchByName(scann);
		
		}
		else if(scann.equalsIgnoreCase("2")) {
			LOGGER.info("enter price");
			scann=SCANN.nextLine();
			int price=Integer.parseInt(scann);
			prod=product.searchByPrice(price);
		}
		else if(scann.equalsIgnoreCase("3")) {
			LOGGER.info("enter category");
			scann=SCANN.nextLine();

			prod=product.searchByCategory(scann);
		}
		if(!getFlag_search()) {
			LOGGER.info("no product to display");
		}
		else {
			for(int i=0;i<prod.size();i++) {
		LOGGER.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+prod.get(i).getCategory());
			}
			viewBuy(user);
		}
	}
	
	public void shoppingCart(String user) {
		LOGGER.info("choose betwen choices");
		LOGGER.info("1.Update Order");
		LOGGER.info("2.Delete Order");
		LOGGER.info("3.Confirm Order");
		LOGGER.info("4.Go back");
		scann=SCANN.nextLine();

		if(scann.equalsIgnoreCase("1")) {
			LOGGER.info("to update quintity the product in your order please enter id order ");
			scann=SCANN.nextLine();
			int orderid=Integer.parseInt(scann);

			LOGGER.info(" please enter new quantity ");
			scann=SCANN.nextLine();
			int quuantity=Integer.parseInt(scann);

			if(order.updateOrder(orderid,quuantity)) {
				LOGGER.info("update order succsessfuly");
			}
			else {
				LOGGER.info("update order unsuccsessfuly you enter incorrect id ");
			}
		}

		else if(scann.equalsIgnoreCase("2")){
			LOGGER.info("to delete order please enter id order");
			scann=SCANN.nextLine();
			int idd=Integer.parseInt(scann);
			order.deleteOrder(idd);
			if(getFlagDeleteO()) {
				LOGGER.info("Delete Order successfuly");	
			}
			else {
				LOGGER.info("Delete Order unsuccessfuly, incorrect order Id");	
			}
		}
		else if(scann.equalsIgnoreCase("3")){
			confirmOrder(user);
			
		}
		

	}
	public int getCustomerId(String customeremail) {
		int id=0;
		try {
			connection();
			String sql=SELECT_USERS_QUERY +customeremail+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				id=rs.getInt("id");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return id;
	}
	public void productAvailable(int quantity,int idproduct) { 

		try {

			connection();
			String sql=SELECT_PRODUCT_BY_ID_QUERY +idproduct+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			while (rs.next()) {

				if(rs.getInt(QUANTITY_LITERAL)>0 && rs.getInt(QUANTITY_LITERAL)>=quantity) {
					setIsAvaliable(true);
				}
				else {
					setIsAvaliable(false);
					}
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}

	}
	public void insertConfirmOrder(String customername,String city,String street,String phoneNumber) {

		try {
			connection();
			String sql="Update orders set Buy=?,city=?,street=?,phoneNumber=? where customername='"+customername+"'";
			stm=con.prepareStatement(sql);

			stm.setBoolean(1,true);
			stm.setString(2, city);
			stm.setString(3,street);
			stm.setString(4, phoneNumber);

			stm.executeUpdate();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}

	}
	public void confirmOrder(String customername) {
		LOGGER.info("Enter your address");
		LOGGER.info("Enter your city");
		scann=SCANN.nextLine();
		String cityy=scann;

		LOGGER.info("Enter your steet");
		String streett=SCANN.nextLine();;

		LOGGER.info("Enter your phone number");
		String phoneNumber=SCANN.nextLine();;
		int count=0;
		for(int i=0;i<phoneNumber.length();i++) {
			if(Character.isDigit(phoneNumber.charAt(i))) {
				count++;
			}
		}
		if(count==phoneNumber.length()) {
			insertConfirmOrder(customername,cityy,streett,phoneNumber);
			EMAIL email=new EMAIL();
			String body="Dear user , \n your order is ready, please pick it up from the company's delivery service ."
            		+ "\n Please contact the owner of this number: 0599516693 in case the delivery is delayed or there is an error in the order."
            		+ " \n Thank you for dealing with our company for Car Accessories.";
			String subject="Customer Order";
			email.sendEmail("ibtihajsami9@gmail.com", subject, body);
			LOGGER.info("Email sent successfully!");

		}
		else {
			LOGGER.info("should all phoneNumber is digit");
		}
	}
	public boolean editName(String user,String ename){
		boolean flagN=false;
		try {
			connection();
			String sql="Update users set name=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, ename);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagN=true;
			}
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return flagN;
	}
	public boolean editEmail(String user,String eemail){
		 boolean flagE=false;
		try {
			connection();
			String sql="Update users set email=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, eemail);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagE=true;
			}
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return flagE;
	}
	public boolean editPassword(String user,String epassword){
		boolean flagP=false;
		try {
			connection();
			String sql="Update users set password=? where email='"+user+"'";
			stm=con.prepareStatement(sql);
			stm.setString(1, epassword);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) {
				flagP=true;
			}
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return flagP;
	}
	private void connection() throws ClassNotFoundException, SQLException {
		String password = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",password);
	}
	public String getCustomerPassword(String user) {
		String oldpass=null;
		try {
			connection();
			String sql=SELECT_USERS_QUERY +user+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				oldpass=rs.getString("password");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return oldpass;
	}

	public void editCustomerProfile(String user){
		LOGGER.info("1.edit your name");
		LOGGER.info("2.edit your email");
		LOGGER.info("3.edit your password");
		String edit=SCANN.nextLine();
		if(edit.equalsIgnoreCase("1")) {
			LOGGER.info("enter your new name");
			String ename=SCANN.nextLine();
			editName(user,ename);
		}
		else if(edit.equalsIgnoreCase("2")) {
			LOGGER.info("enter your new email");
			String eemail=SCANN.nextLine();
			if(eemail.contains("@")||eemail.contains(".")) {
				editEmail(user,eemail);
			}
		}
		else if(edit.equalsIgnoreCase("3")) {
			LOGGER.info("enter your old password");
			String oldPass=SCANN.nextLine();
			LOGGER.info("enter your new password");
			String epassword=SCANN.nextLine();
			String oldpassword=getCustomerPassword(user);
			if(oldPass.equals(oldpassword)){
				editPassword(user,epassword);
			}
			else {
				LOGGER.info("enter wronge old password");
			}
		}
	}
    public void viewProduct(int id) {
	
		try {
			connection();
			String sql=SELECT_PRODUCT_BY_ID_QUERY+id+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (rs.next()) {
		
				
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
	}
    public int oldEvalProduct(int id) {
	int eval=0;
	try {
		connection();
		String sql=SELECT_PRODUCT_BY_ID_QUERY+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			eval=rs.getInt("evaluation");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return eval;
}

public void setEval(int id,int neweval)	{
	try {
		connection();
		String sql="Update product set evaluation=? where id='"+id+"'";
		stm=con.prepareStatement(sql);

	
		stm.setInt(1,neweval );
		
		stm.executeUpdate();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
}
public void updateUserEval(int id,int user ) {

	try {
		connection();
		String sql="Update product set userEval=? where id='"+id+"'";
		stm=con.prepareStatement(sql);

	
		stm.setInt(1,user );
		
		stm.executeUpdate();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
}

public int numberOfUserEval(int id) {
	int user=0;
	try {
		connection();
		String sql=SELECT_PRODUCT_BY_ID_QUERY+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			user=rs.getInt("userEval");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return user;
}
public static Boolean getFinsertOrder() {
    return finsertorder;
}


public static void setFinsertOrder(Boolean value) {
	finsertorder = value;
}

public static Boolean getFlagDeleteO() {
    return flagdeleteO;
}


public static void setFlagDeleteO(Boolean value) {
	flagdeleteO = value;
}

public static Boolean getFlag_search() {
    return flagSearch;
}


public static void setFlag_search(Boolean value) {
	flagSearch = value;
}

public static Boolean getIsAvaliable() {
    return isavaliable;
}


public static void setIsAvaliable(Boolean value) {
	isavaliable = value;
}
}
