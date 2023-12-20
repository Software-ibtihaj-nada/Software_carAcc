package car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Installer {
	public  Logger logger=Logger.getLogger(Login.class.getName());
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String phone;
	public  static Boolean test=false;

	public  Scanner scanner=new Scanner(System.in);
	public  String scann;
	public Installer() {
	
	}
	public Installer(String fname,String lname,String email, String password,String phone) {
		this.fname=fname;
		this.lname=lname;
		this.email=email;
		this.password=password;
		this.phone=phone;
		

	}
	public void setfname(String fname) {
		this.fname=fname;
	}
	public void setlname(String lname) {
		this.lname=lname;
	}
	public void setemail(String email) {
		this.email=email;
	}
	public void setphone(String password) {
		this.password=password;
	}
	public void setpassword(String phone) {
		this.phone=phone;
	}
	
	
	public String getfname() {
		return fname;
		}
	public String getlname() {
		return lname;
		}
	public String getemail() {
		return email;
		}
	public String getpassword() {
		return password;
		}
	public String getphone() {
		return phone;
		}
	

	public void installerDashboard(String email) {
		int x=0;
		while(x!=1) {
			logger.info("Welcome, INSTALLER!");
			logger.info("Please choose you want need.");
			logger.info("1.View Instllation request.");
			logger.info("2.Done Instllation request.");
			logger.info("3.Change the installation day.");
			logger.info("4.Log OUT");

			String input=scanner.nextLine();
			if(input.equalsIgnoreCase("1")) {
				int id=getInstallerId(email);
				String name=getInstallerName(id);
				
				viewInstallationReq(name);
			}

			else if(input.equalsIgnoreCase("2")){
				logger.info("Enter the id of installation to make it done");
				String id=scanner.nextLine();
				int installaion_id=Integer.parseInt(id);
				int installer_id=getInstallerId(email);
				String day=getInstallationDay(installaion_id);
				editDay(day,installer_id,false);
				if(removeInstallation(installaion_id)) {
					logger.info("Done installation");
					
				}
				else {
					logger.info("you enter wrong id");
				}
			}
			
			else if(input.equalsIgnoreCase("3")){
				int id_installer=getInstallerId(email);
				String name=getInstallerName(id_installer);
				viewInstallationReq(name);
				
				logger.info("Enter the id of installation to change the day");
				String id_installation=scanner.nextLine();
				int id_inst=Integer.parseInt(id_installation);
				logger.info("Enter the new day");
				String new_day=scanner.nextLine();
				String oldDay =getInstallationDay(id_inst);
				this.editDay(oldDay, id_installer, false);
				this.editDay(new_day, id_installer, true);
				updateDayforcustomer(id_inst,new_day);
				EMAIL emaill=new EMAIL();
				String body="Dear customer, \n We would like to inform you that there has been a change in the installation calculations, please check your account \n"
						+ "if there is any problem  please contact us of this number: 0599516693.";
	         		
				String subject="Customer installation request";
				emaill.sendEmail("ibtihajsami9@gmail.com", subject, body);
				

			
			} 
			else if(input.equalsIgnoreCase("4")){
				
				x=1;
			} 
			else {
				logger.info("Invalid choice. Please enter 1, 2 or 3");

			}

		}

	}
	
	public boolean insertInstaller(Installer installer) {
		int num=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="INSERT INTO installer (first_name,last_name,email,password,phone_num,saturday,sunday,monday,tuesday,Wensday,Thuersday) values(?,?,?,?,?,?,?,?,?,?,?)";
			stm=con.prepareStatement(sql);
	
					stm.setString(1,installer.getfname());
			    	stm.setString(2,installer.getlname());
			    	stm.setString(3,installer.getemail());
			    	stm.setString(4,installer.getpassword());
			    	stm.setString(5,installer.getphone());
			    	stm.setBoolean(6,false);
			    	stm.setBoolean(7,false);
			    	stm.setBoolean(8,false);
			    	stm.setBoolean(9,false);
			    	stm.setBoolean(10,false);
			    	stm.setBoolean(11,false);
			    	
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
	
public void viewInstaller() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select * from installer";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			
			while (rs.next()) {
				String day="";
			        
				if(rs.getBoolean("Saturday")==false) {
					day+="  Saturday";
				}
				if(rs.getBoolean("Sunday")==false) {
					day+="  Sunday";
				}
				if(rs.getBoolean("Monday")==false) {
					day+="  Monday";
				}
				if(rs.getBoolean("Tuesday")==false) {
					day+="  Tuesday";
				}
				if(rs.getBoolean("Wensday")==false) {
					day+="  Wensday";
				}
				if(rs.getBoolean("Thuersday")==false) {
					day+="  Thuersday";
				}
				logger.info("id= "+rs.getInt("id")+" "+rs.getString("first_name")+" "+rs.getString("last_name")+"  Phone Number is:"+rs.getString("phone_num")+"\n"+"Avaliable ON:"+day);
				
		}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}

public int getInstallerId(String email) {
	int id=0;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from installer where email='"+email+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if (rs.next()) {
		id=rs.getInt("id");
			
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
		e.printStackTrace();
	}
	return id;
}


public String getInstallerName(int id) {
	
	String name=null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from installer where id='"+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if (rs.next()) {
		name=rs.getString("first_name")+" "+rs.getString("last_name");
			
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
		e.printStackTrace();
	}
	return name;
}
public void viewInstallationReq(String name) {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from installation_req where installer_name='"+name+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		while (rs.next()) {
		int idd=rs.getInt("id");
		String c_name=rs.getString("customername");
		String req=rs.getString("customerreq");
		String carmodel=rs.getString("carmodel");
		String day=rs.getString("day");
		String phone=rs.getString("customerphone");
		String city=rs.getString("city");
		String street=rs.getString("street");
		
		logger.info("id= "+idd+"\t"+c_name+"\t"+req+"\t"+carmodel+"\t"+ day+"\t"+phone+"\t"+city+"\t"+street);	
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
		e.printStackTrace();
	}	
	
}

public boolean editDay(String day,int id,boolean validDay) {
	boolean flag=false;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql=null;
		if(validDay) {
		 sql="Update installer set "+day+"=true where id='"+id+"'";
		}
		else {
			sql="Update installer set "+day+"=false where id='"+id+"'";
		}
		stm=con.prepareStatement(sql);
		//stm.setBoolean(1, true);
		int num=stm.executeUpdate();
		stm.close();
		if(num>0) {
			flag=true;
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return flag;

}

public boolean removeInstallation(int Id) {
	int num =0;
	 try {
 			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
 			con=DriverManager.getConnection(url,"root","");
 			String sql="Delete from installation_req where ID='" +Id+"' ";
 			stm=con.prepareStatement(sql);
 			 num =stm.executeUpdate();
 					
 			stm.close();
 			
 		}
 		catch(Exception e) {
 			e.printStackTrace();
 		}
	 if (num!=0)return true;
		else  return false;

}
public String getInstallationDay(int installation_id) {
	String day=null;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from installation_req where id='"+installation_id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if(rs.next()) {
		day=rs.getString("day");
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
		e.printStackTrace();
	}	
	return day;
}
public boolean updateDayforcustomer(int installation_id,String day){
	boolean flagUpdate=false;
	 try {
		   Class.forName("com.mysql.jdbc.Driver");
	   			String url="jdbc:mysql://localhost/caracc";
	   			con=DriverManager.getConnection(url,"root","");
	   		    String sql="Update installation_req set day=? where id='"+installation_id+"'";
	            stm=con.prepareStatement(sql);
	           
	            stm.setString(1, day);
	            
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

public boolean veiwInstallationRequestAdmin() {
	boolean flag=false;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from installation_req  ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
	
		while (rs.next()) {
		if(!test) {
		int idd=rs.getInt("id");
		String c_name=rs.getString("customername");
		String req=rs.getString("customerreq");
		String carmodel=rs.getString("carmodel");
		String day=rs.getString("day");
		String phone=rs.getString("customerphone");
		String city=rs.getString("city");
		String street=rs.getString("street");
		String installername=rs.getString("installer_name");
		logger.info("id= "+idd+"\t"+installername+"\t"+c_name+"\t"+req+"\t"+carmodel+"\t"+ day+"\t"+phone+"\t"+city+"\t"+street);	
	}
		else {
			flag=true;
		}
	}
		stm.close();
		rs.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return flag;
	
}
public void viewInstallerAdmin() {
	   try {
		   Class.forName("com.mysql.jdbc.Driver");
		   String url="jdbc:mysql://localhost/caracc";

		   con=DriverManager.getConnection(url,"root","");
		   String sql="Select * from installer ";
		   stm=con.prepareStatement(sql);
		   rs=stm.executeQuery();

		   while (rs.next()) {
			   Integer id=rs.getInt("id");
			   logger.info(id+"\t"+rs.getString("first_name")+"\t"+rs.getString("last_name")+"\t"+rs.getString("phone_num"));
		   }
		   stm.close();
		   rs.close();
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
}
public boolean removeInstaller(int id) {
	int num=0;
	   try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Delete from installer where ID='" +id+"' ";
			stm=con.prepareStatement(sql);
			 num =stm.executeUpdate();
					
			stm.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
	   
	   if (num>0) {
			return true;
		}
	   else {
		   return false;
	   }
	
}

public boolean customerViewInstallation(String name) {
	boolean flag=false;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="Select * from installation_req where customername='"+name+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		while (rs.next()) {
			if(!test) {
		int idd=rs.getInt("id");
		String c_name=rs.getString("customername");
		String req=rs.getString("customerreq");
		String carmodel=rs.getString("carmodel");
		String day=rs.getString("day");
		String phone=rs.getString("customerphone");
		String city=rs.getString("city");
		String street=rs.getString("street");
		String installer_name=rs.getString("installer_name");

		logger.info("id= "+idd+"\t"+c_name+"\t"+req+"\t"+carmodel+"\t"+ day+"\t"+installer_name);	
	}
			else {
				flag=true;
			}
		}
		
		stm.close();
		rs.close();
	}

	catch(Exception e) {
		e.printStackTrace();
	}	
	return flag;
}

}
