package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
public class Customer {
	public  Logger logger=Logger.getLogger(Login.class.getName());
	public  Scanner scanner=new Scanner(System.in);
	public  String scann;
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	public static boolean isavaliable=false;
	public static boolean finsertorder=false;
	public static boolean flag_search=false;
public static boolean flagdeleteO=false;
public Product product=new Product();
public Order order=new Order();
public Installer installer=new Installer();
public String customername, phone, city, street,customeremail,password;
public int customerid;
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
			logger.info("Welcome, CUSTOMER!");
			logger.info("Please choose you want need.");
			logger.info("1.View category.");
			logger.info("2.View product.");
			logger.info("3.Make Installation request.");
			logger.info("4.View Installation request.");
			logger.info("5.Search.");
			logger.info("6.View Shopping cart");
			logger.info("7.Edit your profile");
			logger.info("8.Log OUT");

			String input=scanner.nextLine();
			if(input.equalsIgnoreCase("1")) {// View category
				product.viewCategories();
			}

			else if(input.equalsIgnoreCase("2")){//View Product
				logger.info("Enter name of category");
	              String category=scanner.nextLine();
	              ArrayList<Product>prod=new ArrayList<>();
 	             
	              prod= product.viewProduct(category);
	              String vailability;
		         for(int i=0;i<prod.size();i++) {
		        	 if(prod.get(i).getQuientity()>0) {
							vailability="avaliable";
						}
						else {
							vailability="not avaliable";
						}
		     		logger.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+vailability+"\t"+oldEvalProduct(prod.get(i).getId())+" star");
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
				logger.info("Invalid choice. Please enter 1, 2, 3,4,5,6 ,7or 8.");

			}

		}

	}
	

	public void viewCategoryProduct(String category) {

		try {
			logger.info("please choose the number of product you want.");
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			String vailability=null;
			while (rs.next()) {
				String id="id = "+rs.getInt("id");
				String name= rs.getString("name");
				String description= rs.getString("description");
				int price= rs.getInt("price");

				if(rs.getInt("quantity")>0) {
					vailability="avaliable";
				}
				else {
					vailability="not avaliable";
				}

				logger.info(id+"\t"+name+"\t"+description+"\t"+price+"$ \t"+vailability+"\t"+rs.getInt("evaluation")+" Stars");
		}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void viewBuy(String customername){
		logger.info("1.add product to cart");
		logger.info("2.Rate the products");
		logger.info("3.Back to  products");
		scann=scanner.nextLine();
		if(scann.equalsIgnoreCase("1")) {//to buy product
			logger.info("please enter id product");
			scann=scanner.nextLine();
			int productId=Integer.parseInt(scann);
			logger.info("enter quntity");
			scann=scanner.nextLine();//prodect quntity
			int quantity=Integer.parseInt(scann);

			productAvailable(quantity,productId);//add to order table 
			if(isavaliable) {
				int customerId=getCustomerId(customername);
				String productname=product.getProductName(productId);
				int price=product.getProductPrice(productId);
				Order orderr=new Order(customername,customerId, productId,  productname,quantity,price);
				order.insertOrder(orderr);
				if(finsertorder) {
					logger.info("insert order succsessfully");
					int p_quantity=product.getProductQuantity(productId);
					p_quantity-=quantity;
					product.updateProductQuantity(productId,p_quantity);
				}
				else {
					logger.info("insert order unsuccsessfully");	
				}
			}
			else {
				logger.info("This product is not avaliable or quantity avaliable not enough");	

			}

		}
		else if(scann.equalsIgnoreCase("2")) {
			logger.info("please enter id product");
			scann=scanner.nextLine();
			int productId=Integer.parseInt(scann);
			//viewProduct(productId);
			logger.info("please enter your evaluation for product between 1-5");
			scann=scanner.nextLine();
			int eval=Integer.parseInt(scann);
			
			while(eval<1||eval>5) {
				logger.info("please enter your evaluation for product between 1-5");
				scann=scanner.nextLine();
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
		 logger.info("Enter the id of installer you want ");
		 String installer_id=scanner.nextLine();
		 int instaler_id=Integer.parseInt(installer_id);
		 logger.info("Enter the day of installation you want ");
		 String day=scanner.nextLine();
		 
			logger.info("Enter your request");
			String request=scanner.nextLine();
			
			logger.info("Enter your car model");
			String carModel=scanner.nextLine();
			 
			logger.info("Enter your phone number");
			this.phone=scanner.nextLine();
			
			logger.info("Enter your address");
			logger.info("Enter your city");
			this.city=scanner.nextLine();
			
			logger.info("Enter your street");
			 this.street=scanner.nextLine();
			 this.customername=getCustomerName(email);
			 String installer_name=installer.getInstallerName(instaler_id);
		 installationReq(carModel,request,installer_name,day);
		 installer.editDay(day,instaler_id,true);
		 //send email to installer
		 EMAIL emaill=new EMAIL();
			String body="Dear installer , \n you are have anew installation rwquest , please check your installation request table .";
         		
			String subject="Customer installation request";
			emaill.sendEmail("nadoosh.jamal.aj@gmail.com", subject, body);
			 //System.out.println("Email sent successfully!");

	}
	
	
	
public String getCustomerName(String email) {
	String name=null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from users where email='" +email+"' and user_type='customer' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			name=rs.getString("name");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return name ;
}
public boolean installationReq(String carmodel,String request,String installer_name,String day) {
	int num=0;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="INSERT INTO installation_req (customername,customerphone,customerreq,carmodel,city,street,day,installer_name) values(?,?,?,?,?,?,?,?)";
		stm=con.prepareStatement(sql);

				stm.setString(1,customername);
		    	stm.setString(2,phone);
		    	stm.setString(3,request);
		    	stm.setString(4,carmodel);
		    	stm.setString(5,city);
		    	stm.setString(6,street);
		    	stm.setString(7,day);
		    	stm.setString(8,installer_name);
		 
		     num=stm.executeUpdate();
	
		
		stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	if(num>0) {
		return true;
	}
	else {
	return false;
	}
}
	
	public void search(String user) {
		logger.info("1.Search by name.");
		logger.info("2.Search by price.");
		logger.info("3.Search by category.");
		scann=scanner.nextLine();
		ArrayList<Product>prod=new ArrayList<>();
	
		if(scann.equalsIgnoreCase("1")) {
			logger.info("enter name");
			scann=scanner.nextLine();
			prod=product.searchByName(scann);
		
		}
		else if(scann.equalsIgnoreCase("2")) {
			logger.info("enter price");
			scann=scanner.nextLine();
			int price=Integer.parseInt(scann);
			prod=product.searchByPrice(price);
		}
		else if(scann.equalsIgnoreCase("3")) {
			logger.info("enter category");
			scann=scanner.nextLine();

			prod=product.searchByCategory(scann);
		}
		if(!flag_search) {
			logger.info("no product to display");
		}
		else {
			for(int i=0;i<prod.size();i++) {
		logger.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+prod.get(i).getCategory());
			}
			viewBuy(user);
		}
	}
	
	public void shoppingCart(String user) {
		logger.info("choose betwen choices");
		logger.info("1.Update Order");
		logger.info("2.Delete Order");
		logger.info("3.Confirm Order");
		logger.info("4.Go back");
		scann=scanner.nextLine();

		if(scann.equalsIgnoreCase("1")) {
			logger.info("to update quintity the product in your order please enter id order ");
			scann=scanner.nextLine();
			int orderid=Integer.parseInt(scann);

			logger.info(" please enter new quantity ");
			scann=scanner.nextLine();
			int Quantity=Integer.parseInt(scann);

			if(order.updateOrder(orderid,Quantity)) {
				logger.info("update order succsessfuly");
			}
			else {
				logger.info("update order unsuccsessfuly you enter incorrect id ");
			}
		}

		else if(scann.equalsIgnoreCase("2")){
			logger.info("to delete order please enter id order");
			scann=scanner.nextLine();
			int idd=Integer.parseInt(scann);
			order.deleteOrder(idd);
			if(flagdeleteO) {
				logger.info("Delete Order successfuly");	
			}
			else {
				logger.info("Delete Order unsuccessfuly, incorrect order Id");	
			}
		}
		else if(scann.equalsIgnoreCase("3")){
			confirmOrder(user);
			
		}
		

	}
	public int getCustomerId(String customeremail) {
		int id=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from users where email='" +customeremail+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				id=rs.getInt("id");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	public void productAvailable(int quantity,int idproduct) { 

		try {

			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +idproduct+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			while (rs.next()) {
				int price=rs.getInt("price"); 
				if(rs.getInt("quantity")>0 && rs.getInt("quantity")>=quantity) {
					//product is avaliable
					isavaliable=true;
				}
				else {
					isavaliable=false;
				}
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void insertConfirmOrder(String customername,String city,String street,String phoneNumber) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
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
			e.printStackTrace();
		}

	}
	public void confirmOrder(String customername) {
		logger.info("Enter your address");
		logger.info("Enter your city");
		scann=scanner.nextLine();
		String city=scann;

		logger.info("Enter your steet");
		scann=scanner.nextLine();
		String street=scann;

		logger.info("Enter your phone number");
		scann=scanner.nextLine();
		String phoneNumber=scann;
		int count=0;
		for(int i=0;i<phoneNumber.length();i++) {
			if(Character.isDigit(phoneNumber.charAt(i))) {
				count++;
			}
		}
		if(count==phoneNumber.length()) {
			//all phoneNumber is digit
			insertConfirmOrder(customername,city,street,phoneNumber);
			EMAIL email=new EMAIL();
			String body="Dear user , \n your order is ready, please pick it up from the company's delivery service ."
            		+ "\n Please contact the owner of this number: 0599516693 in case the delivery is delayed or there is an error in the order."
            		+ " \n Thank you for dealing with our company for Car Accessories.";
			String subject="Customer Order";
			email.sendEmail("ibtihajsami9@gmail.com", subject, body);
			 System.out.println("Email sent successfully!");

		}
		else {
			logger.info("should all phoneNumber is digit");
		}
	}
	public boolean editName(String user,String ename){
		boolean flagN=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
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
			e.printStackTrace();
		}
		return flagN;
	}
	public boolean editEmail(String user,String eemail){
		 boolean flagE=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
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
			e.printStackTrace();
		}
		return flagE;
	}
	public boolean editPassword(String user,String epassword){
		boolean flagP=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
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
			e.printStackTrace();
		}
		return flagP;
	}
	public String getCustomerPassword(String user) {
		String oldpass=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from users where email='" +user+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if(rs.next()) {
				oldpass=rs.getString("password");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return oldpass;
	}

	public void editCustomerProfile(String user){
		logger.info("1.edit your name");
		logger.info("2.edit your email");
		logger.info("3.edit your password");
		String edit=scanner.nextLine();
		if(edit.equalsIgnoreCase("1")) {
			logger.info("enter your new name");
			String ename=scanner.nextLine();
			editName(user,ename);
		}
		else if(edit.equalsIgnoreCase("2")) {
			logger.info("enter your new email");
			String eemail=scanner.nextLine();
			if(eemail.contains("@")||eemail.contains(".")) {
				editEmail(user,eemail);
			}
		}
		else if(edit.equalsIgnoreCase("3")) {
			logger.info("enter your old password");
			String oldPass=scanner.nextLine();
			logger.info("enter your new password");
			String epassword=scanner.nextLine();
			String oldpassword=getCustomerPassword(user);
			if(oldPass.equals(oldpassword)){
				editPassword(user,epassword);
			}
			else {
				logger.info("enter wronge old password");
			}
		}
	}
    public void viewProduct(int id) {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select*from product where id='" +id+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (rs.next()) {
				//logger.info(rs.getString("name")+" "+rs.getString("description")+" "+rs.getInt("price")+"$"+" "+rs.getInt("evaluation")+"Stars");//print order
		
				
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    public int oldEvalProduct(int id) {
	int eval=0;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from product where id='" +id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			eval=rs.getInt("evaluation");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return eval;
}

public void setEval(int id,int neweval)	{
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Update product set evaluation=? where id='"+id+"'";
		stm=con.prepareStatement(sql);

	
		stm.setInt(1,neweval );
		
		stm.executeUpdate();
		stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
public void updateUserEval(int id,int user ) {

	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Update product set userEval=? where id='"+id+"'";
		stm=con.prepareStatement(sql);

	
		stm.setInt(1,user );
		
		stm.executeUpdate();
		stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}

public int numberOfUserEval(int id) {
	int user=0;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from product where id='" +id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		if(rs.next()) {
			user=rs.getInt("userEval");
		}
		rs.close();
		stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return user;
}
}