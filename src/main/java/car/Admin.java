package car;
import java.util.*;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Admin {
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	public  static Logger logger=Logger.getLogger(Login.class.getName());
	public  Scanner scann=new Scanner(System.in);
	public   String scan;
	public  Boolean checkprod=false;
	public static Boolean flaginsertP=false;
	public  static Boolean flagdeleteP=false;


	public Boolean flaginsertC=false;
	public Boolean flagdeleteC=false;
	public Product product=new Product();
	public Installer installer;
	public Order order=new Order();
       public Admin() {
    	   
       }
       
       
       public void adminDashboard() {
    	   int x=0;
    	   while(x!=1) {
    	   logger.info("chose the fnction you want: ");
    	   logger.info("1 Veiw Product Category");
    	   logger.info("2 Add Category");
    	   logger.info("3 Delete Category");
    	   logger.info("4 Veiw product.");
    	   logger.info("5 add new product.");
    	   logger.info("6 Update product.");
    	   logger.info("7 Delete Product");
    	   logger.info("8 View customer Account .");
    	   logger.info("9 View all order.");
    	   logger.info("10 View installation Request  .");
    	   logger.info("11 Add new installer.");
    	   logger.info("12 Remove installer.");
    	   logger.info("13 Sales Report");
    	   logger.info("14 View customer reviews");
    	   logger.info("15 log out .");
    	   
    	   scan=scann.nextLine();

    	   switch(scan) {
    	   case "1": product.viewCategories();
    	    	      break;
    	   case "2":logger.info("Enter name of category");
           String category=scann.nextLine();
          if( !cheackCategory(category)) {
        	  addCategory(category); 
   		   if(flaginsertC) {
		        	  logger.info("Insert category succssesfuly");  
		          }
		          else {
		        	  logger.info("Insert category unsuccssesfuly");  
		          } 
          }
          else {
        	  logger.info("this category is already exist");  
          }
           break;
    	   case "3":logger.info("Enter name of category");
           String categoryy=scann.nextLine();
    		   deleteCategory(categoryy); 
    		   deleteProductCategory(categoryy);
    		   if(flagdeleteC) {
		        	  logger.info("delete category succssesfuly");  
		          }
		          else {
		        	  logger.info("delete category unsuccssesfuly");  
		          }
           break;
    	   case "4":  logger.info("Enter name of category");
    	              String cat=scann.nextLine();
    	              ArrayList<Product>prod=new ArrayList<>();
    	             
    	              prod= product.viewProduct(cat);
			printProduct(prod);
   
    	              break;

    	   case "5":addProduct();
           break;
    	   case "6":updateProduct();
           break;
    	   case "7":deleteProduct();
           break;
    	   case "8":  ArrayList<Customer>cust=new ArrayList<>();
                      cust= veiwCustomerAccount();
                      for(int i=0;i<cust.size();i++) {
    		 logger.info("id= "+cust.get(i).getid()+"\t"+cust.get(i).getemail()+"\t"+cust.get(i).getname());
                      }
           break;
    	   case "9":order.adminViewOrder();
           break;
    	   case "10":installer=new Installer();
    		   installer.veiwInstallationRequestAdmin();
           break;
    	   case "11":addInstaller();
           break;
    	   case "12":installer.viewInstallerAdmin();
    	   logger.info("Enter id of installer that you want to remove");
           String id_installer=scann.nextLine();
           int id=Integer.parseInt(id_installer);
           installer.removeInstaller(id);
           break;
    	   case "13":reportAdmin();
           break;
    	   case "14":ViewCustomerReviews();
           break;
    	   case "15": x=1;//veiwInstallationRequest();
               break;
    	    default: logger.info("please chose one of the availabe choises");
    	    adminDashboard();
    	   }
    	   }
       }


	private void printProduct(ArrayList<Product> prod) {
		for(int i=0;i<prod.size();i++) {
			logger.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+prod.get(i).getQuientity());
				}
	}
       
    public boolean cheackCategory(String category) {
    	boolean flag=false;
    	  try {
   		   Class.forName("com.mysql.jdbc.Driver");
   		   String url="jdbc:mysql://localhost/caracc";
   		   con=DriverManager.getConnection(url,"root","");
   		   String sql="Select * from category where category='" +category+"' ";
   		   stm=con.prepareStatement(sql);
   		   rs=stm.executeQuery();
   	if(rs.next()){
   		flag=true;
   	}
   	else {
   		flag=false;
   	}

   		   stm.close();
   		   rs.close();
   	   }
   	   catch(Exception e) {
   		   e.printStackTrace();
   	   }
    	  return flag;
    }
   
    public void  addCategory(String category){
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="INSERT INTO Category (category) values(?)";
    		   stm=con.prepareStatement(sql);

    		   stm.setString(1,category);

    		   int num=stm.executeUpdate();
    		   if (num!=0) flaginsertC=true;
    		   else flaginsertC=false;

    		   stm.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   } 
       }
       public void deleteCategory(String categoryy){
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Delete from Category where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   int num =stm.executeUpdate();
    		   if (num!=0) flagdeleteC=true;
    		   else flagdeleteC=false;
    		   stm.close();

    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }  

       }

       public void deleteProductCategory(String categoryy) {
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Delete from product where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   stm.executeUpdate();

    		   stm.close();

    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }    
       }
     
       public void addProduct() {
    	   logger.info("Enter category:");
    	   String category=scann.nextLine();

    	   logger.info("Enter product name:");
    	   String pname=scann.nextLine();

    	   logger.info("Enter product description:");
    	   String pdescription=scann.nextLine();

    	   logger.info("Enter product price:");
    	   String pprice=scann.nextLine();

    	   logger.info("Enter product quientity:");
    	   String pquientity=scann.nextLine();

    	   checkProduct(pname);
    	   if(!checkprod) { // product doesn't found
    		   Product p=new Product( pname, pdescription, Integer.parseInt(pprice),Integer.parseInt(pquientity),category);
    		   product.insertProduct(p);
    		   if(flaginsertP)
    			   logger.info("product added successfuly");

    	   }
    	   else {
    		   logger.info("this product already found");


    	   }

       }

       public void checkProduct(String name) {
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select name from product where name='" +name+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   if (rs.next()) {
    			   checkprod=true;
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }

       }


       public void updateProduct() {

    	   product.viewCategories();
    	   
    	   logger.info("Please enter the name of category you want to update ");
    	   String category=scann.nextLine();
    	   ArrayList<Product>prod=new ArrayList<>();
           
           prod= product.viewProduct(category);
	       printProduct(prod);
           logger.info("enter Id of product you wnat to update ");
    	   String id=scann.nextLine();
    	   logger.info("1- update name of product");
		   logger.info("2- update description of product");
		   logger.info("3- update price of product");
		   logger.info("4- update quantity of product");
		   logger.info("5- Go back");
		   logger.info("Choose that you want to update ");
    	   String input=scann.nextLine();

		   if(input.equalsIgnoreCase("1")) {
    	   logger.info("enter new name of product  ");
    	   String name=scann.nextLine();
    	   product.updateProduct(id,"name",name);

		   }
		   else if(input.equalsIgnoreCase("2")) {
			   logger.info("enter new description of product  ");
	    	   String desc=scann.nextLine(); 
	    	   product.updateProduct(id,"description",desc);
		   }
		   else if(input.equalsIgnoreCase("3")) {
			   logger.info("enter new price of product  ");
	    	   String p=scann.nextLine();
	    	   product.updateProduct(id,"price",p);
		   }
		  
		   else if(input.equalsIgnoreCase("4")) {
			   logger.info("enter new quantity of product  ");
			   String quantity=scann.nextLine();
	    	 //  int quantity=Integer.parseInt(q);
	    	   product.updateProduct(id,"quantity",quantity);
		   }
		   else if(input.equalsIgnoreCase("5")) {
	    	    adminDashboard();
  
		   }
		   else {
			   logger.info("enter wronge input ,please try again");
			   updateProduct();
			   
		   }
		  
       }


       public void deleteProduct() {

    	   product.viewCategories();
    	   logger.info("Please enter the name of category you want to delete ");
    	   String category=scann.nextLine();

 ArrayList<Product>prod=new ArrayList<>();
           
           prod= product.viewProduct(category);
	       printProduct(prod);

    	   logger.info("enter Id of product you wnat to delete ");
    	   scan=scann.nextLine();  
    	   product.removeProdct(Integer.parseInt(scan),category);

    	   if(flagdeleteP)  logger.info("product delete successfuly");

    	   else {logger.info("you enter wrong product id");
    	   //p.viewProduct(category);
    	   }

       }
       

       public void addInstaller() {
    	   logger.info("Enter first name installer");
    	String fname=scann.nextLine();
    	
    	  logger.info("Enter last name installer");
      	String lname=scann.nextLine();
      	
        logger.info("Enter email installer");
    	String email=scann.nextLine();
    	
        logger.info("Enter password installer");
    	String password=scann.nextLine();
    	
    	  logger.info("Enter phone number installer");
      	String phone=scann.nextLine();
      	
      	
      	if(!email.contains("@")||!email.contains(".")) {
			logger.info("syntex error in email");
			addInstaller();
    	}
      	else {
      		if(!(Login.checkName(fname)&&Login.checkName(lname))) {
      			logger.info("Name should start with character and contains character");
      			addInstaller();
      			
      		}
      		else {
      			int count=0;
      			for(int i=0;i<phone.length();i++) {
      				if(Character.isDigit(phone.charAt(i))) {
      					count++;
      				}
      			}
      			if(count==phone.length()) {
      				//all phoneNumber is digit
      				installer=new Installer(fname,lname,email,password,phone);
      				if(installer.insertInstaller(installer)) {
      					insertInstallerUser(installer);
      					logger.info("Add intaller succseefully");
      				}
      				
      				
      			}
      		}
      		
      	}
      	
       }
       public boolean insertInstallerUser(Installer installer) {
    	   int num=0;
    	   try {
    			Class.forName("com.mysql.jdbc.Driver");
    			String url="jdbc:mysql://localhost/caracc";
    			con=DriverManager.getConnection(url,"root","");
    			String sql="INSERT INTO users (name,email,password,user_type) values(?,?,?,?)";
    			stm=con.prepareStatement(sql);
    	stm.setString(1,installer.getfname()+installer.getlname());
    	stm.setString(2,installer.getemail());
    	stm.setString(3,installer.getpassword());
    	stm.setString(4,"installer");
    	stm.executeUpdate();
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
      
     
 public ArrayList<Customer> veiwCustomerAccount() {
	 ArrayList<Customer>customer=new ArrayList<>();
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";

    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select * from users where user_type='customer'";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();

    		   while (rs.next()) {
    			   Customer costomers=new Customer();
    			   int id=rs.getInt("id");
    			   String name=rs.getString("name");
    			   String email=rs.getString("email");
    			   costomers.setid(id);
    			   costomers.setName(name);
    			   costomers.setEmail(email);
    			   customer.add(costomers);
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
    	   return customer;
       }
       
       
       public void reportAdmin() {
    	   int price=0;
    	   System.out.println("Product Name"+"\t\t"+"Product Quantity"+"\t"+"Product Price"+"\n");
    	   
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select*from orders where Buy=true";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {

    			   System.out.println(rs.getString("productname")+"\t\t\t"+rs.getInt("productquantity")+"\t\t\t"+rs.getInt("productprice")+"$");//print order
    			   price+=rs.getInt("productprice");
    		   }
    		   System.out.print("\n");
    		   System.out.println("Total income="+price+"$");
    		   rs.close();
    		   stm.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
    	   
    	   
    	   
       }
       public void ViewCustomerReviews() {
    	  // System.out.println("Product Name"+"\t\t"+"Customer Reviews"+"\t"+"# of customers rate products"+"\n");
    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select*from product";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {

    			   System.out.print(rs.getString("name")+"\t\t\t");
    			   for(int i=0;i<rs.getInt("evaluation");i++) {
    				   System.out.print("* ");
    			   }
    			   System.out.print("\t\t\t\t"+rs.getInt("userEval"));
    			   System.out.print("\n");
    			   
    		   }
    		  
    		   rs.close();
    		   stm.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
    	 
       }

}