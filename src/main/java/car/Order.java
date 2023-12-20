package car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
public class Order {
	public  Logger logger=Logger.getLogger(Login.class.getName());

	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	private String customername;
	private int customerId;
	private int productId;
	private String productname;
	private int productquntity;
	private int productprice;
	public  static Boolean test=false;
	public  static Boolean test1=false;

	public Order() {
	
	}
	public Order(String customername,int customerId,int productId, String productname,int productquntity,int productprice) {
		this.customername=customername;
		this.customerId=customerId;
		this.productId=productId;
		this.productname=productname;
		this.productquntity=productquntity;
		this.productprice=productprice;

	}
	public void setcustomername(String customername) {
		this.customername=customername;
	}
	public void setcustomerId(int customerId) {
		this.customerId=customerId;
	}
	public void setproductId(int productId) {
		this.productId=productId;
	}
	public void setproductname(String productname) {
		this.productname=productname;
	}

	public void setproductquntity(int productquntity) {
		this.productquntity=productquntity;
	}
	public void setproductprice( int productprice) {
		this.productprice=productprice;
	}

	
	
	public String getcustomername() {
	return customername;
	}
	public int getcustomerId() {
		return customerId;
	}
	public int getproductId() {
		return productId;
	}
	public String getproductname() {
		return productname;
	}

	public int getproductquntity() {
		return productquntity;
	}
	public int getproductprice( ) {
		return productprice;
	}

	
	public boolean adminViewOrder() {
 	   boolean flag=false;
 	   try {
 		   Class.forName("com.mysql.jdbc.Driver");
 		   String url="jdbc:mysql://localhost/caracc";
 		   con=DriverManager.getConnection(url,"root","");
 		   String sql="Select*from orders where Buy=true";
 		   stm=con.prepareStatement(sql);
 		   rs=stm.executeQuery();
 		   while (rs.next()) {
 			   if(!test) {
 				   logger.info(rs.getInt("id")+" "+rs.getString("customername")+" "+rs.getString("productname")+" "+rs.getInt("productquantity")+" "+
     					   rs.getInt("productprice")+"$"+" "+rs.getString("city")+" "+rs.getString("street")+" "+rs.getString("phoneNumber"));//print order 
 			   }
 			   else {
 				 flag=true;  
 			   }
 		   }
 		   rs.close();
 		   stm.close();
 	   }
 	   catch(Exception e) {
 		   e.printStackTrace();
 	   }
 	   return flag;
    }
 public void insertOrder(Order order) {//add order to order table 
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="INSERT INTO orders (customername,customerId,productId,productname,productquantity,productprice,Buy) values(?,?,?,?,?,?,?)";
			stm=con.prepareStatement(sql);
	
					stm.setString(1,order.getcustomername());
			    	stm.setInt(2,order.getcustomerId());
			    	stm.setInt(3,order.getproductId());
			    	stm.setString(4,order.getproductname());
			    	stm.setInt(5,order.getproductquntity());
			    	int price=(order.getproductquntity())*( order.getproductprice());
			    
			    	stm.setInt(6,price);
			    	
			    	stm.setBoolean(7,false);
			    int num=stm.executeUpdate();
		if(num>0) {
			Customer.finsertorder=true;
		}
		else {
			Customer.finsertorder=false;
		}
			
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public int getOrderId(Order order) {
		
		int idd=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select* from orders where customername='"+order.getcustomername()+"'and customerId='"+order.getcustomerId()+"' "
					+"and productId='"+order.getproductId()+"'and productname='"+order.getproductname()+"' "
							+" and productquantity='"+order.getproductquntity()+"'and productprice='"+order.getproductprice()+"' and Buy=false";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (rs.next()) {
				idd=rs.getInt("id");
			
			}
			rs.close();
			stm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return idd;
	}
	
public boolean updateOrder(int orderId,int Quantity ) {
	boolean flagUpdate=false;
	 try {
 		   Class.forName("com.mysql.jdbc.Driver");
	   			String url="jdbc:mysql://localhost/caracc";
	   			con=DriverManager.getConnection(url,"root","");
	   		    String sql="Update orders set productquantity=? where id='"+orderId+"'";
	            stm=con.prepareStatement(sql);
	           
	            stm.setInt(1, Quantity);
	            
	            int num=stm.executeUpdate();
	            stm.close();
	            if(num>0) {
	            	flagUpdate=true;	
	            	
	            }
	            else {
	            	flagUpdate=false;	
	            }
	            
	   		}
	   		catch(Exception e) {
	   			e.printStackTrace();
	   		}
	 return flagUpdate;
}
public void deleteOrder(int orderId) {

	 try {
  			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
  			con=DriverManager.getConnection(url,"root","");
  			String sql="Delete from orders where ID='" +orderId+"' ";
  			stm=con.prepareStatement(sql);
  			int num =stm.executeUpdate();
  					if (num!=0) Customer.flagdeleteO=true;
			else Customer.flagdeleteO=false;
  			stm.close();
  			
  		}
  		catch(Exception e) {
  			e.printStackTrace();
  		}


}
public boolean viewOrder(String customername) {
	boolean flag1 =false;
	int price=0;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select*from orders where customername='" +customername+"' and Buy=false";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		while (rs.next()) {
			if(!test1) {
			price+=rs.getInt("productprice");
			logger.info(rs.getInt("id")+" "+rs.getString("productname")+" "+rs.getInt("productquantity")+" "+rs.getInt("productprice")+"$");//print order
		}
			else {
				
				flag1=true;
			}
		}
		logger.info("Total Price= "+ price +" $");
		rs.close();
		stm.close();
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return flag1;
}

}
