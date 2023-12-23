package car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Installer {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
	Connection con=null;
	PreparedStatement stm=null;
	ResultSet rs=null;
	
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String phone;
	private  static Boolean test=false;
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String CUSTOMER_NAME_COLUMN = "customername";
    private static final String CUSTOMER_REQ_COLUMN = "customerreq";
    private static final String CAR_MODEL_COLUMN = "carmodel";
    private static final String CUSTOMER_PHONE_COLUMN = "customerphone";
    private static final String STREET_COLUMN = "street";
    private static final Scanner SCANNER = new Scanner(System.in);
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
	public void setphone(String pho) {
		this.password=pho;
	}
	public void setpassword(String pa) {
		this.phone=pa;
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
			LOGGER.info("Welcome, INSTALLER!");
			LOGGER.info("Please choose you want need.");
			LOGGER.info("1.View Instllation request.");
			LOGGER.info("2.Done Instllation request.");
			LOGGER.info("3.Change the installation day.");
			LOGGER.info("4.Log OUT");

			String input=SCANNER.nextLine();
			if(input.equalsIgnoreCase("1")) {
				int id=getInstallerId(email);
				String name=getInstallerName(id);
				
				viewInstallationReq(name);
			}

			else if(input.equalsIgnoreCase("2")){
				LOGGER.info("Enter the id of installation to make it done");
				String id=SCANNER.nextLine();
				int installaionId=Integer.parseInt(id);
				int installerId=getInstallerId(email);
				String day=getInstallationDay(installaionId);
				editDay(day,installerId,false);
				if(removeInstallation(installaionId)) {
					LOGGER.info("Done installation");
					
				}
				else {
					LOGGER.info("you enter wrong id");
				}
			}
			
			else if(input.equalsIgnoreCase("3")){
				int idinstaller=getInstallerId(email);
				String name=getInstallerName(idinstaller);
				viewInstallationReq(name);
				
				LOGGER.info("Enter the id of installation to change the day");
				String iddinstallation=SCANNER.nextLine();
				int idinst=Integer.parseInt(iddinstallation);
				LOGGER.info("Enter the new day");
				String newday=SCANNER.nextLine();
				String oldDay =getInstallationDay(idinst);
				this.editDay(oldDay, idinstaller, false);
				this.editDay(newday, idinstaller, true);
				updateDayforcustomer(idinst,newday);
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
				LOGGER.info("Invalid choice. Please enter 1, 2 or 3");

			}

		}

	}
	
	public boolean insertInstaller(Installer installer) {
		int num=0;
		try {
			connection();
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
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
   return num>0;

	}
	private void connection() throws ClassNotFoundException, SQLException {
		String password = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",password);
	}
	
public void viewInstaller() {
		
		try {
			connection();
			String sql="Select * from installer";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			
			
			while (rs.next()) {
				String day="";
			        
				if(!rs.getBoolean("Saturday")) {
					day+="  Saturday";
				}
				if(!rs.getBoolean("Sunday")) {
					day+="  Sunday";
				}
				if(!rs.getBoolean("Monday")) {
					day+="  Monday";
				}
				if(!rs.getBoolean("Tuesday")) {
					day+="  Tuesday";
				}
				if(!rs.getBoolean("Wensday")) {
					day+="  Wensday";
				}
				if(!rs.getBoolean("Thuersday")) {
					day+="  Thuersday";
				}
				LOGGER.info(String.format("id= %d %s %s  Phone Number is:%s%nAvaliable ON:%s",
				        rs.getInt("id"),
				        rs.getString(FIRST_NAME_COLUMN),
				        rs.getString(LAST_NAME_COLUMN),
				        rs.getString("phone_num"),
				        day));

		}
			stm.close();
			rs.close();
		}

		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}
	}

public int getInstallerId(String email) {
	int id=0;
	try {
		connection();
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
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return id;
}


public String getInstallerName(int id) {
	
	String name=null;
	try {
		connection();
		String sql="Select * from installer where id='"+id+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if (rs.next()) {
		name=rs.getString(FIRST_NAME_COLUMN)+" "+rs.getString(LAST_NAME_COLUMN);
			
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return name;
}
public void viewInstallationReq(String name) {
	try {
		connection();
		String sql="Select * from installation_req where installer_name='"+name+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		while (rs.next()) {
		int idd=rs.getInt("id");
		String cname=rs.getString(CUSTOMER_NAME_COLUMN);
		String req=rs.getString(CUSTOMER_REQ_COLUMN);
		String carmodel=rs.getString(CAR_MODEL_COLUMN);
		String day=rs.getString("day");
		String phonee=rs.getString(CUSTOMER_PHONE_COLUMN);
		String citty=rs.getString("city");
		String strreet=rs.getString(STREET_COLUMN);
		LOGGER.info(String.format("id= %s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", idd, cname, req, carmodel, day, phonee, citty, strreet));
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}	
	
}

public boolean editDay(String day,int id,boolean validDay) {
	boolean flag=false;
	try {
		connection();
		String sql=null;
		if(validDay) {
		 sql="Update installer set "+day+"=true where id='"+id+"'";
		}
		else {
			sql="Update installer set "+day+"=false where id='"+id+"'";
		}
		stm=con.prepareStatement(sql);
		int num=stm.executeUpdate();
		stm.close();
		if(num>0) {
			flag=true;
		}
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return flag;

}

public boolean removeInstallation(int idd) {
	int num =0;
	 try {
 			connection();
 			String sql="Delete from installation_req where ID='" +idd+"' ";
 			stm=con.prepareStatement(sql);
 			 num =stm.executeUpdate();
 					
 			stm.close();
 			
 		}
 		catch(Exception e) {
 	        LOGGER.severe("An error occurred: " + e.getMessage());
 		}
	 return num != 0;
}
public String getInstallationDay(int instid) {
	String day=null;
	
	try {
		connection();
		String sql="Select * from installation_req where id='"+instid+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		if(rs.next()) {
		day=rs.getString("day");
	}
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}	
	return day;
}
public boolean updateDayforcustomer(int instid,String day){
	boolean flagUpdate=false;
	 try {
		   connection();
	   		    String sql="Update installation_req set day=? where id='"+instid+"'";
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
	   	        LOGGER.severe("An error occurred: " + e.getMessage());
	   		}
	 return flagUpdate;
}

public boolean veiwInstallationRequestAdmin() {
	boolean flag=false;
	try {
		connection();
		String sql="Select * from installation_req  ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
	
		while (rs.next()) {
		if(!getTest()) {
		int idd=rs.getInt("id");
		String cnname=rs.getString(CUSTOMER_NAME_COLUMN);
		String req=rs.getString(CUSTOMER_REQ_COLUMN);
		String carmodel=rs.getString(CAR_MODEL_COLUMN);
		String day=rs.getString("day");
		String phonee=rs.getString(CUSTOMER_PHONE_COLUMN);
		String city1=rs.getString("city");
		String street1=rs.getString(STREET_COLUMN);
		String installername=rs.getString("installer_name");
		LOGGER.info(String.format("id= %s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", idd, installername, cnname, req, carmodel, day, phonee, city1, street1));

	}
		else {
			flag=true;
		}
	}
		stm.close();
		rs.close();
	}
	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
	return flag;
	
}
public void viewInstallerAdmin() {
	   try {
		   connection();
		   String sql="Select * from installer ";
		   stm=con.prepareStatement(sql);
		   rs=stm.executeQuery();

		   while (rs.next()) {
			   Integer id=rs.getInt("id");
			   LOGGER.info(String.format("%s\t%s\t%s\t%s", id, rs.getString(FIRST_NAME_COLUMN), rs.getString(LAST_NAME_COLUMN), rs.getString("phone_num")));
		   }
		   stm.close();
		   rs.close();
	   }
	   catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
	   }
}
public boolean removeInstaller(int id) {
	int num=0;
	   try {
			connection();
			String sql="Delete from installer where ID='" +id+"' ";
			stm=con.prepareStatement(sql);
			 num =stm.executeUpdate();
					
			stm.close();
			
		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		} 
	
		   return num>0;
	   
}

public boolean customerViewInstallation(String name) {
	boolean flag=false;
	try {
		connection();
		String sql="Select * from installation_req where customername='"+name+"' ";
		stm=con.prepareStatement(sql);
		rs=stm.executeQuery();
		
		
		while (rs.next()) {
			if(!getTest()) {
		int idd=rs.getInt("id");
		String cname=rs.getString(CUSTOMER_NAME_COLUMN);
		String req=rs.getString(CUSTOMER_REQ_COLUMN);
		String carmodel=rs.getString(CAR_MODEL_COLUMN);
		String day=rs.getString("day");
		String phonee=rs.getString(CUSTOMER_PHONE_COLUMN);
		String installername=rs.getString("installer_name");
		LOGGER.info(String.format("id= %d\t%s\t%s\t%s\t%s\t%s\t%s", idd, cname, req, carmodel, day, installername, phonee));
	}
			else {
				flag=true;
			}
		}
		
		stm.close();
		rs.close();
	}

	catch(Exception e) {
        LOGGER.severe("An error occurred: " + e.getMessage());
	}	
	return flag;
}

public static Boolean getTest() {
    return test;
}


public static void setTest(Boolean value) {
	   test = value;
}
}
