package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
   	public  static Logger logger=Logger.getLogger(Login.class.getName());
   	
   	
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
		   			Class.forName("com.mysql.jdbc.Driver");
		   			String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   			String sql="Select * from Category ";
		   			stm=con.prepareStatement(sql);
					rs=stm.executeQuery();
		   			while (rs.next()) {
		   			
		   				logger.info(rs.getString("category"));
		   
		   			}

		   			stm.close();
		   			rs.close();
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
			}   
	     
		   public void insertProduct(Product p){	     	   
	    	   try {
	    			Class.forName("com.mysql.jdbc.Driver");
	    			String url="jdbc:mysql://localhost/caracc";
	    			con=DriverManager.getConnection(url,"root","");
	    			String sql="INSERT INTO product (name,description,price,quantity,category) values(?,?,?,?,?)";
	    			stm=con.prepareStatement(sql);
	    		
	    	stm.setString(1,p.getName());
	    	stm.setString(2,p.getDescription());
	    	stm.setInt(3,p.getPrice());
	    	stm.setInt(4,p.getQuientity());
	    	stm.setString(5,p.getCategory());
	    int num=stm.executeUpdate();
	    if (num!=0)  Admin.flaginsertP=true;
		else Admin.flaginsertP=false;
		
	    	stm.close();
	    		}
	    		catch(Exception e) {
	    			e.printStackTrace();
	    		} 
	    	   
	    	   
	       }
		   
		   public boolean updateProduct(String id,String name,String value) {
			   int num=0;
	    	   try {
	    		   Class.forName("com.mysql.jdbc.Driver");
		   			String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   			String sql=null;
		   			if(name.equalsIgnoreCase("name")) {
		   		     sql="Update product set name='"+ value+"' where id='"+id+"'";
		   			}
		   			else if(name.equalsIgnoreCase("description")	) {
		   				sql="Update product set description='"+ value+"' where id='"+id+"'";
		   			}
		   			else if(name.equalsIgnoreCase("price")	) {
		   				sql="Update product set price='"+ value+"' where id='"+id+"'";
		   			}
		   			else if(name.equalsIgnoreCase("quantity")	) {
		   				sql="Update product set quantity='"+ value+"' where id='"+id+"'";
		   			}
		            stm=con.prepareStatement(sql);
//		            stm.setString(1, name);
//		            stm.setString(2, desc);
//		            stm.setInt(3, price);
//		            stm.setInt(4, quintity);
		            
		           num= stm.executeUpdate();
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
		   public void removeProdct(int id ,String category) {
	    	   try {
		   			Class.forName("com.mysql.jdbc.Driver");
					String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   			String sql="Delete from product where ID='" +id+"'and category='"+category+"' ";
		   			stm=con.prepareStatement(sql);
		   			int num =stm.executeUpdate();
		   					if (num!=0)  Admin.flagdeleteP=true;
					else Admin.flagdeleteP=false;
		   			stm.close();
		   			
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
	       }
		
		
		public String getProductName(int productId) {
			String name=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +productId+"' ";
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
		return name;
	}
		
		public int getProductPrice(int productId) {
			int price=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
		   price=rs.getInt("price");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return price;
	}
		public int getProductQuantity(int productId) {
			int quantity=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where id='" +productId+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			 if(rs.next()) {
				 quantity=rs.getInt("quantity");
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return quantity;
	}	
  
		public int getProductId(String name) {
			int id=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from product where name='" +name+"' ";
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
		
		public void updateProductQuantity(int productId,int p_quantity){
			 try {
	  		   Class.forName("com.mysql.jdbc.Driver");
		   			String url="jdbc:mysql://localhost/caracc";
		   			con=DriverManager.getConnection(url,"root","");
		   		    String sql="Update product set quantity=? where id='"+productId+"'";
		            stm=con.prepareStatement(sql);
		           
		            stm.setInt(1, p_quantity);
		            
		            stm.executeUpdate();
		            stm.close();
		   		}
		   		catch(Exception e) {
		   			e.printStackTrace();
		   		}
		}

		public ArrayList<Product> searchByName(String name) {
		ArrayList<Product>product=new ArrayList<>();
					try {
				Class.forName("com.mysql.jdbc.Driver");
				String url="jdbc:mysql://localhost/caracc";
				con=DriverManager.getConnection(url,"root","");
				String sql="Select*from product where name='" +name+"' ";
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				if(rs.next()) {
					Customer.flag_search=true;
					Product p=new Product(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("price"),rs.getString("category"));
					product.add(p);
//				logger.info("id    name      description       price     category"+"\n"+
//				rs.getInt("id")+" "+rs.getString("name")+" "+rs.getString("description")+" "+rs.getInt("price")+"$"+rs.getString("category"));//print order
//					
				}
				else {
					Customer.flag_search=false;
				}
			
				rs.close();
				stm.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}	
					if(Customer.flag_search) {return product;		}
					
					else {
						return null;
					}
		}
	public ArrayList<Product> searchByPrice( int price) {
		ArrayList<Product>product=new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select*from product where price='" +price+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			if(rs.next()) {
				Customer.flag_search=true;
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				while (rs.next()) {
					Product p=new Product(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("price"),rs.getString("category"));
					product.add(p);				}
			}
			else {
				Customer.flag_search=false;
			}

			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
if(Customer.flag_search) {return product;		}
		
		else {
			return null;
		}

		}

	public ArrayList<Product> searchByCategory(String category) {
		ArrayList<Product>product=new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select*from product where category='" +category+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			if(rs.next()) {
				
				Customer.flag_search=true;
				
				stm=con.prepareStatement(sql);
				rs=stm.executeQuery();
				while (rs.next()) {
					Product p=new Product(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("price"),rs.getString("category"));
					product.add(p);					}
			}
			else {
				Customer.flag_search=false;
			}

			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		if(Customer.flag_search) {return product;		}
		
		else {
			return null;
		}
	}
	public ArrayList<Product> viewProduct(String category){
   		ArrayList<Product>product=new ArrayList<>();

    	   try {
    		   Class.forName("com.mysql.jdbc.Driver");
    		   String url="jdbc:mysql://localhost/caracc";
    		   con=DriverManager.getConnection(url,"root","");
    		   String sql="Select * from product where category='" +category+"' ";
    		   stm=con.prepareStatement(sql);
    		   rs=stm.executeQuery();
    		   while (rs.next()) {
    			   Product pro=new Product();
    			   int id=rs.getInt("id");
    			   String name= rs.getString("name");
    			   String description= rs.getString("description");
    			   int price= rs.getInt("price");
    			   Integer q=rs.getInt("quantity");
    			   pro.setId(id);
    			   pro.setName(name);
    			   pro.setDescription(description);
    			   pro.setPrice(price);
    			   pro.setquantity(q);
    			   product.add(pro);
    			  
    		   }

    		   stm.close();
    		   rs.close();
    	   }
    	   catch(Exception e) {
    		   e.printStackTrace();
    	   }
    	   return product;	
       }

}