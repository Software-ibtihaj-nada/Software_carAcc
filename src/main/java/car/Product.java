package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
public class Product {
       private String name;
       private int id;
       private String description;
       private int price;
       private String category;
       int quantity;
       Connection con=null;
       PreparedStatement stm=null;
   	   ResultSet rs=null;
   	private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
   	public static final String CATEGORY_LITERAL = "category";
   	public static final String ID_WHERE_CLAUSE = "' where id='";
   	public static final String DESCRIPTION_LITERAL = "description";
   	public static final String PRICE_LITERAL = "price";
   	public static final String QUANTITY_LITERAL = "quantity";
   	public static final String SELECT_PRODUCT_BY_ID_QUERY = "Select * from product where id='";





    public Product() {
    	
    }
    public Product(String name,String description,int price,int quantity,String category) {
 	  
 	   this.name=name;
 	   this.description=description;
 	   this.price=price;
 	   this.category=category;
 	   this.quantity=quantity;
    }
    
       public Product(int id,String name,String description,int price,String category) {
    	   this.id=id;
    	   this.name=name;
    	   this.description=description;
    	   this.price=price;
    	   this.category=category;
    	   
       }
       public int getId() {
			return id;
		}
       public String getName() {
			return name;
		}

       public void setId(int id) {
			this.id = id;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}

		public int getPrice() {
			return price;
		}


		public void setPrice(int price) {
			this.price = price;
		}
		
		public String getCategory() {
			return category;
		}


		public void setCategory(String category) {
			this.category = category;
		}
		public void setquantity(int quantity) {
			this.quantity = quantity;
		}
		public int getQuientity() {
			return quantity;
		}
		   public void viewCategories() {
	    		try {
		   			connection();
		   			String sql="Select * from Category ";
		   			stm=con.prepareStatement(sql);
					rs=stm.executeQuery();
		   			while (rs.next()) {
		   				LOGGER.info(rs.getString(CATEGORY_LITERAL));
		   			}

		   			stm.close();
		   			rs.close();
		   		}
		   		catch(Exception e) {
		   	        LOGGER.severe("An error occurred: " + e.getMessage());
		   		}
			}   
	     
		   public void insertProduct(Product p){	     	   
	    	   try {
	    			connection();
	    			String sql="INSERT INTO product (name,description,price,quantity,category) values(?,?,?,?,?)";
	    			stm=con.prepareStatement(sql);
	    		
	    	stm.setString(1,p.getName());
	    	stm.setString(2,p.getDescription());
	    	stm.setInt(3,p.getPrice());
	    	stm.setInt(4,p.getQuientity());
	    	stm.setString(5,p.getCategory());
	    int num=stm.executeUpdate();
	    if (num!=0)  Admin.setFlaginsertP(true);
		else Admin.setFlaginsertP(false);
		
	    	stm.close();
	    		}
	    		catch(Exception e) {
	    	        LOGGER.severe("An error occurred: " + e.getMessage());
	    		} 
	    	   
	    	   
	       }
		private void connection() throws ClassNotFoundException, SQLException {
			String password = System.getProperty("database.password");
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root",password);
		}
		   
		   public boolean updateProduct(String id,String name,String value) {
			   int num=0;
	    	   try {
	    		   connection();
		   			String sql=null;
		   			if(name.equalsIgnoreCase("name")) {
		   		     sql="Update product set name='"+ value+ID_WHERE_CLAUSE+id+"'";
		   			}
		   			else if(name.equalsIgnoreCase(DESCRIPTION_LITERAL)	) {
		   				sql="Update product set description='"+ value+ID_WHERE_CLAUSE+id+"'";
		   			}
		   			else if(name.equalsIgnoreCase(PRICE_LITERAL)	) {
		   				sql="Update product set price='"+ value+ID_WHERE_CLAUSE+id+"'";
		   			}
		   			else if(name.equalsIgnoreCase(QUANTITY_LITERAL)	) {
		   				sql="Update product set quantity='"+ value+ID_WHERE_CLAUSE
		   						+id+"'";
		   			}
		            stm=con.prepareStatement(sql);	            
		           num= stm.executeUpdate();
		            stm.close();
		   		}
		   		catch(Exception e) {
		   	        LOGGER.severe("An error occurred: " + e.getMessage());
		   		}	    	
	    	   return num>0;
    	   
	       }
		   public void removeProdct(int id ,String category) {
	    	   try {
		   			connection();
		   			String sql="Delete from product where ID='" +id+"'and category='"+category+"' ";
		   			stm=con.prepareStatement(sql);
		   			int num =stm.executeUpdate();
		   					if (num!=0)  Admin.setFlagdeleteP(true);
					else Admin.setFlagdeleteP(false);
		   			stm.close();
		   			
		   		}
		   		catch(Exception e) {
		   	        LOGGER.severe("An error occurred: " + e.getMessage());
		   		}
	       }
		
		
		public String getProductName(int productId) {
			String nname=null;
		try {
			connection();
			String sql=SELECT_PRODUCT_BY_ID_QUERY +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
		   nname=rs.getString("name");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return nname;
	}
		
		public int getProductPrice(int productId) {
			int pricee=0;
		try {
			connection();
			String sql=SELECT_PRODUCT_BY_ID_QUERY +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
		   pricee=rs.getInt(PRICE_LITERAL);
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return pricee;
	}
		public int getProductQuantity(int productId) {
			int quantityy=0;
		try {
			connection();
			String sql=SELECT_PRODUCT_BY_ID_QUERY +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
				 quantityy=rs.getInt(QUANTITY_LITERAL);
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return quantityy;
	}	
  
		public int getProductId(String name) {
			int iid=0;
		try {
			connection();
			String sql="Select * from product where name='" +name+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
				 iid=rs.getInt("id");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
		return iid;
	}	
		
		public void updateProductQuantity(int productId,int pQuantity){
			 try {
	  		   connection();
		   		    String sql="Update product set quantity=? where id='"+productId+"'";
		            stm=con.prepareStatement(sql);
		           
		            stm.setInt(1, pQuantity);
		            
		            stm.executeUpdate();
		            stm.close();
		   		}
		   		catch(Exception e) {
		   	        LOGGER.severe("An error occurred: " + e.getMessage());
		   		}
		}

		public ArrayList<Product> searchByName(String name) {
		ArrayList<Product>product=new ArrayList<>();
					try {
				connection();
				String sql="Select*from product where name='" +name+"' ";
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				if(rs.next()) {
					Customer.setFlag_search(true);
					Product p=new Product(rs.getInt("id"),rs.getString("name"),rs.getString(DESCRIPTION_LITERAL),rs.getInt(PRICE_LITERAL),rs.getString(CATEGORY_LITERAL));
					product.add(p);					
				}
				else {
					Customer.setFlag_search(false);;
				}
			
				rs.close();
				stm.close();
			}
			catch(Exception e) {
		        LOGGER.severe("An error occurred: " + e.getMessage());
			}	
					if(Customer.getFlag_search()) {return product;		}
					
					else {
						return null;
					}
		}
	public ArrayList<Product> searchByPrice( int price) {
		ArrayList<Product>product=new ArrayList<>();

		try {
			connection();
			String sql="Select*from product where price='" +price+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			if(rs.next()) {
				Customer.setFlag_search(true);
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				while (rs.next()) {
					Product p=new Product(rs.getInt("id"),rs.getString("name"),rs.getString(DESCRIPTION_LITERAL),rs.getInt(PRICE_LITERAL),rs.getString(CATEGORY_LITERAL));
					product.add(p);				}
			}
			else {
				Customer.setFlag_search(false);
			}

			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}	
if(Customer.getFlag_search()) {return product;		}
		
		else {
			 return new ArrayList<>();
		}

		}

	public ArrayList<Product> searchByCategory(String category) {
		ArrayList<Product>product=new ArrayList<>();

		try {
			connection();
			String sql="Select*from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			if(rs.next()) {
				
				Customer.setFlag_search(true);
				
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				while (rs.next()) {
					Product p=new Product(rs.getInt("id"),rs.getString("name"),rs.getString(DESCRIPTION_LITERAL),rs.getInt(PRICE_LITERAL),rs.getString(CATEGORY_LITERAL));
					product.add(p);					}
			}
			else {
				Customer.setFlag_search(false);
			}

			rs.close();
			stm.close();
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}	
		if(Customer.getFlag_search()) {
			return product;	
		}
		
		else {
			return new ArrayList<>();
			}
	}
	public ArrayList<Product> viewProduct(String category){
   		ArrayList<Product>product=new ArrayList<>();

    	   try {
    		   connection();
    		   String sql="Select * from product where category='" +category+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {
    			   Product pro=new Product();
    			   int iddd=rs.getInt("id");
    			   String namme= rs.getString("name");
    			   String descriptionnn= rs.getString(DESCRIPTION_LITERAL);
    			   int priccce= rs.getInt(PRICE_LITERAL);
    			   Integer q=rs.getInt(QUANTITY_LITERAL);
    			   pro.setId(iddd);
    			   pro.setName(namme);
    			   pro.setDescription(descriptionnn);
    			   pro.setPrice(priccce);
    			   pro.setquantity(q);
    			   product.add(pro);
    			  
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    	        LOGGER.severe("An error occurred: " + e.getMessage());
    	   }
    	   return product;	
       }

}
