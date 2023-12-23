package car;
import java.util.*;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
    public static final Logger LOGGER = Logger.getLogger(Login.class.getName());
    public static final Scanner SCANN = new Scanner(System.in);
    private static final String ENTER_CATEGORY_MESSAGE = "Enter name of category";
    private static final String TAB_SPACING = "\t\t\t";
    private  String scan ;
    private static Boolean checkprod = false;
    private static Boolean flaginsertP=false;
	private static Boolean flagdeleteP=false;


	private static Boolean flaginsertC=false;
	private static Boolean flagdeleteC=false;

	private Product product=new Product();
	private Installer installer;
	private Order order=new Order();
       public Admin() {
    	   
       }
       
       
       public void adminDashboard() {
    	   int x=0;
    	   while(x!=1) {
    		   LOGGER.info("chose the fnction you want: ");
    		   LOGGER.info("1 Veiw Product Category");
    		   LOGGER.info("2 Add Category");
    	   LOGGER.info("3 Delete Category");
    	   LOGGER.info("4 Veiw product.");
    	   LOGGER.info("5 add new product.");
    	   LOGGER.info("6 Update product.");
    	   LOGGER.info("7 Delete Product");
    	   LOGGER.info("8 View customer Account .");
    	   LOGGER.info("9 View all order.");
    	   LOGGER.info("10 View installation Request  .");
    	   LOGGER.info("11 Add new installer.");
    	   LOGGER.info("12 Remove installer.");
    	   LOGGER.info("13 Sales Report");
    	   LOGGER.info("14 View customer reviews");
    	   LOGGER.info("15 log out .");
    	   
    	   scan=SCANN.nextLine();

    	   switch(scan) {
    	   case "1": product.viewCategories();
    	    	      break;
    	   case "2":LOGGER.info(ENTER_CATEGORY_MESSAGE);
           String category=SCANN.nextLine();
          if( !cheackCategory(category)) {
        	  addCategory(category); 
   		   if(flaginsertC) {
   			LOGGER.info("Insert category succssesfuly");  
		          }
		          else {
		        	  LOGGER.info("Insert category unsuccssesfuly");  
		          } 
          }
          else {
        	  LOGGER.info("this category is already exist");  
          }
           break;
    	   case "3":LOGGER.info(ENTER_CATEGORY_MESSAGE);
           String categoryy=SCANN.nextLine();
    		   deleteCategory(categoryy); 
    		   deleteProductCategory(categoryy);
    		   if(getFlagdeleteC()) {
    			   LOGGER.info("delete category succssesfuly");  
		          }
		          else {
		        	  LOGGER.info("delete category unsuccssesfuly");  
		          }
           break;
    	   case "4":  LOGGER.info(ENTER_CATEGORY_MESSAGE);
    	              String cat=SCANN.nextLine();
    	              ArrayList<Product>prod;
    	             
    	              prod= product.viewProduct(cat);
			printProduct(prod);
   
    	              break;

    	   case "5":addProduct();
           break;
    	   case "6":updateProduct();
           break;
    	   case "7":deleteProduct();
           break;
    	   case "8":  List<Customer>cust;
                      cust= veiwCustomerAccount();
                      for(int i=0;i<cust.size();i++) {
                    	  LOGGER.info("id= "+cust.get(i).getid()+"\t"+cust.get(i).getemail()+"\t"+cust.get(i).getname());
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
    	   LOGGER.info("Enter id of installer that you want to remove");
           String idInstaller=SCANN.nextLine();
           int id=Integer.parseInt(idInstaller);
           installer.removeInstaller(id);
           break;
    	   case "13":reportAdmin();
           break;
    	   case "14":viewCustomerReviews();
           break;
    	   case "15": x=1;
               break;
    	    default: LOGGER.info("please chose one of the availabe choises");
    	    adminDashboard();
    	   }
    	   }
       }


	private void printProduct(ArrayList<Product> prod) {
		for(int i=0;i<prod.size();i++) {
			LOGGER.info("id="+prod.get(i).getId()+"\t"+prod.get(i).getName()+"\t"+prod.get(i).getDescription()+"\t"+prod.get(i).getPrice()+"$"+"\t"+prod.get(i).getQuientity());
				}
	}
       
    public boolean cheackCategory(String category) {
    	boolean flag=false;
    	  try {
   		   connection();
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
           LOGGER.severe("An error occurred: " + e.getMessage());
   		 }
    	  return flag;
    }


	private void connection() throws ClassNotFoundException, SQLException {
		String password = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",password);
	}
   
    public void  addCategory(String category){
    	   try {
    		   connection();
    		   String sql="INSERT INTO Category (category) values(?)";
    		   stm=con.prepareStatement(sql);

    		   stm.setString(1,category);

    		   int num=stm.executeUpdate();
    		   if (num!=0) setFlaginsertC(true);
    		   else setFlaginsertC(false);

    		   stm.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   } 
       }
       public void deleteCategory(String categoryy){
    	   try {
    		   connection();
    		   String sql="Delete from Category where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   int num =stm.executeUpdate();
    		   if (num!=0) setFlagdeleteC(true);
    		   else setFlagdeleteC(false);
    		   stm.close();

    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }  

       }

       public void deleteProductCategory(String categoryy) {
    	   try {
    		   connection();
    		   String sql="Delete from product where category='" +categoryy+"' ";
    		   stm=con.prepareStatement(sql);
    		   stm.executeUpdate();

    		   stm.close();

    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }    
       }
     
       public void addProduct() {
    	   LOGGER.info("Enter category:");
    	   String category=SCANN.nextLine();

    	   LOGGER.info("Enter product name:");
    	   String pname=SCANN.nextLine();

    	   LOGGER.info("Enter product description:");
    	   String pdescription=SCANN.nextLine();

    	   LOGGER.info("Enter product price:");
    	   String pprice=SCANN.nextLine();

    	   LOGGER.info("Enter product quientity:");
    	   String pquientity=SCANN.nextLine();

    	   checkProduct(pname);
    	   if(!getCheckprod()) { 
    		   Product p=new Product( pname, pdescription, Integer.parseInt(pprice),Integer.parseInt(pquientity),category);
    		   product.insertProduct(p);
    		   if(getFlaginsertP())
    			   LOGGER.info("product added successfuly");

    	   }
    	   else {
    		   LOGGER.info("this product already found");


    	   }

       }

       public void checkProduct(String name) {
    	   try {
    		   connection();
    		   String sql="Select name from product where name='" +name+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   if (rs.next()) {
    			   setCheckprod(true);
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }

       }


       public void updateProduct() {

    	   product.viewCategories();
    	   
    	   LOGGER.info("Please enter the name of category you want to update ");
    	   String category=SCANN.nextLine();
    	   ArrayList<Product>prod;
           
           prod= product.viewProduct(category);
	       printProduct(prod);
	       LOGGER.info("enter Id of product you wnat to update ");
    	   String id=SCANN.nextLine();
    	   LOGGER.info("1- update name of product");
    	   LOGGER.info("2- update description of product");
    	   LOGGER.info("3- update price of product");
    	   LOGGER.info("4- update quantity of product");
    	   LOGGER.info("5- Go back");
    	   LOGGER.info("Choose that you want to update ");
    	   String input=SCANN.nextLine();

		   if(input.equalsIgnoreCase("1")) {
			   LOGGER.info("enter new name of product  ");
    	   String name=SCANN.nextLine();
    	   product.updateProduct(id,"name",name);

		   }
		   else if(input.equalsIgnoreCase("2")) {
			   LOGGER.info("enter new description of product  ");
	    	   String desc=SCANN.nextLine(); 
	    	   product.updateProduct(id,"description",desc);
		   }
		   else if(input.equalsIgnoreCase("3")) {
			   LOGGER.info("enter new price of product  ");
	    	   String p=SCANN.nextLine();
	    	   product.updateProduct(id,"price",p);
		   }
		  
		   else if(input.equalsIgnoreCase("4")) {
			   LOGGER.info("enter new quantity of product  ");
			   String quantity=SCANN.nextLine();
	    	   product.updateProduct(id,"quantity",quantity);
		   }
		   else if(input.equalsIgnoreCase("5")) {
	    	    adminDashboard();
  
		   }
		   else {
			   LOGGER.info("enter wronge input ,please try again");
			   updateProduct();
			   
		   }
		  
       }


       public void deleteProduct() {

    	   product.viewCategories();
    	   LOGGER.info("Please enter the name of category you want to delete ");
    	   String category=SCANN.nextLine();

 ArrayList<Product>prod;
           
           prod= product.viewProduct(category);
	       printProduct(prod);

	       LOGGER.info("enter Id of product you wnat to delete ");
    	   scan=SCANN.nextLine();  
    	   product.removeProdct(Integer.parseInt(scan),category);

    	   if(getFlagdeleteP())  LOGGER.info("product delete successfuly");

    	   else {LOGGER.info("you enter wrong product id");
    	   
    	   }

       }
       

       public void addInstaller() {
    	   LOGGER.info("Enter first name installer");
    	String fname=SCANN.nextLine();
    	
    	LOGGER.info("Enter last name installer");
      	String lname=SCANN.nextLine();
      	
      	LOGGER.info("Enter email installer");
    	String email=SCANN.nextLine();
    	
    	LOGGER.info("Enter password installer");
    	String password=SCANN.nextLine();
    	
    	LOGGER.info("Enter phone number installer");
      	String phone=SCANN.nextLine();
      	
      	
      	if(!email.contains("@")||!email.contains(".")) {
      		LOGGER.info("syntex error in email");
			addInstaller();
    	}
      	else {
      		if(!(Login.checkName(fname)&&Login.checkName(lname))) {
      			LOGGER.info("Name should start with character and contains character");
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
      				installer=new Installer(fname,lname,email,password,phone);
      				if(installer.insertInstaller(installer)) {
      					insertInstallerUser(installer);
      					LOGGER.info("Add intaller succseefully");
      				}
      				
      				
      			}
      		}
      		
      	}
      	
       }
       public boolean insertInstallerUser(Installer installer) {
    	   int num=0;
    	   try {
    			connection();
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
    	   return num > 0;

       }
      
     
 public List<Customer> veiwCustomerAccount() {
	 ArrayList<Customer>customer=new ArrayList<>();
    	   try {
    		   connection();
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
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }
    	   return customer;
       }
       
       
       public void reportAdmin() {
    	   int price=0;
    	  
    	   LOGGER.info("Product Name" + "\t\t" + "Product Quantity" + "\t" + "Product Price" + "\n");
    	   try {
    		   connection();
    		   String sql="Select*from orders where Buy=true";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {
    			   LOGGER.info(rs.getString("productname") + TAB_SPACING+ rs.getInt("productquantity") + TAB_SPACING + rs.getInt("productprice") + "$");

    			   price+=rs.getInt("productprice");
    		   }
    		   
    		   LOGGER.info("Total income="+price+"$");
    		   rs.close();
    		   stm.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }
    	   
    	   
    	   
       }
       public void viewCustomerReviews() {
    	   try {
    		   connection();
    		   String sql="Select*from product";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {

    			   LOGGER.info(rs.getString("name") + TAB_SPACING);

    	            for (int i = 0; i < rs.getInt("evaluation"); i++) {
    	            	LOGGER.info("* ");
    	            }

    	            LOGGER.info(TAB_SPACING + rs.getInt("userEval"));
    	            LOGGER.info("\n");
    			   
    		   }
    		  
    		   rs.close();
    		   stm.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }
    	 
       }
       public static Boolean getCheckprod() {
           return checkprod;
       }

    
       public static void setCheckprod(Boolean value) {
           checkprod = value;
       }
       
       public static Boolean getFlaginsertP() {
           return flaginsertP;
       }

    
       public static void setFlaginsertP(Boolean value) {
    	   flaginsertP = value;
       }
       
       public static Boolean getFlagdeleteP() {
           return flagdeleteP;
       }

    
       public static void setFlagdeleteP(Boolean value) {
    	   flagdeleteP = value;
       }
       
       public static Boolean getFlaginsertC() {
           return flaginsertC;
       }

    
       public static void setFlaginsertC(Boolean value) {
    	   flaginsertC = value;
       }
       
       public static Boolean getFlagdeleteC() {
           return flagdeleteC;
       }

    
       public static void setFlagdeleteC(Boolean value) {
    	   flagdeleteC = value;
       }
}
