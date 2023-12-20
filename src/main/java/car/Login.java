package car;

import java.sql.Statement;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login {
	public  Logger logger=Logger.getLogger(Login.class.getName());
	public  Scanner scanner=new Scanner(System.in);
	public  String scan;
	public boolean flaglogin=false;
	public static boolean flagemail=false;
	public boolean flagpass=false;
	public static Connection con=null;
	public static PreparedStatement stm=null;
	public static ResultSet rs=null;
    public boolean isLoginPage=false;
    public  boolean flagname=false;
    public boolean flagconfpass=false;
    Installer installer=new Installer();
	public Login() {
		isLoginPage=true;
	}
	public static void checkEmail(String email,String usertype) {
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select email from users where email='" +email+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (rs.next()) {
				flagemail=true;

			}
			else {
				flagemail=false;
			}
			stm.close();
			rs.close();

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void checkpassword(String email,String pass,String usertype) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost/caracc";
			con=DriverManager.getConnection(url,"root","");
			String sql="Select email from users where email='" +email+"'and password='" +pass+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (!rs.next()) {
				flagpass=false;
			}
			else{
				flagpass=true;
			}
stm.close();
rs.close();

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	public void logIn(String usertype,String email,String password) {

		if(!email.contains("@")||!email.contains(".")) {
			logger.info("syntex error in email");
			flaglogin=true;
			start(usertype);
		}
		else {
			checkEmail(email,usertype);
			if(!flagemail) {//doesnt exist email
				logger.info("user email doesnt exist"); //
				flaglogin=true;
				start(usertype);

			}
			else{ //user inter correct email
				checkpassword(email,password,usertype);
				
				if(flagpass) {
				

					if(usertype.equalsIgnoreCase("admin")) {
						Admin admin=new Admin();
                     admin.adminDashboard();
					}
					else if(usertype.equalsIgnoreCase("customer")) {//customer log in sucsessfully
						Customer customer=new Customer();
						customer.customerDashboard(email);
					}
					else  {//installer log in sucsessfully
						installer.installerDashboard(email);
					}
				}
				else {
					logger.info("you enter incorrect password"); //
					flaglogin=true;
					start(usertype);
				}
			}

		}
	}
	
public void insertuser(String email,String name,String password,String usertype) {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root","");
		String sql="INSERT INTO users (name,email,password,user_type) values(?,?,?,?)";
		stm=con.prepareStatement(sql);
stm.setString(1,name);
stm.setString(2,email);
stm.setString(3, password);
stm.setString(4, usertype);
stm.executeUpdate();
stm.close();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}
	public void regesterUser(String email,String username,String password,String confirmPassword,String usertype) {
		
		if(!email.contains("@")||!email.contains(".")) {
			logger.info("syntex error in email");
			signup(usertype);
		}
		else {
			checkEmail(email,usertype);
			if(flagemail) {//already exist email
				logger.info("this email already exist"); //
				signup(usertype);
			}
			else{ //user inter correct email
		
				if(checkName(username)) {
					
						flagname=true;
				if(confirmPassword.equals(password)) {
					flagconfpass=true;
					
					insertuser(email,username,password,usertype);
				logger.info("you sign up sucessfulley");
				logIn(usertype,email,password);
				}
				else {
					logger.info("your password doesnt match your confirm password");
					signup(usertype);
				}
				
					
				}
				else {
					logger.info("your name should contain character ");
					signup(usertype);

				}
			}

		}

		
		
	}
	public static boolean checkName(String name) {
		int count=0;
		for(int i=0;i<name.length();i++) {
			if(Character.isDigit(name.charAt(i))) {
				count++;
			}
		}
		if(count!=name.length() &&  !Character.isDigit(name.charAt(0))) {

		return true;}
		else {
			return false;
		}
	}
	public void signup(String usertype) {
		logger.info(" Enter your email :");
		String email=scanner.nextLine();


		logger.info(" Enter your username :");
		String username=scanner.nextLine();

		logger.info(" Enter your password :");
		String password=scanner.nextLine();

		logger.info(" Confirm your password :");
		String confirmPassword=scanner.nextLine();
		
		regesterUser( email,username,password,confirmPassword,usertype);
		

	}

	public void start(String usertype) {
		if(!flaglogin) {
			if(!usertype.equalsIgnoreCase("admin")&&!usertype.equalsIgnoreCase("installer")){
				logger.info("1- sign up");
			}
			logger.info("2- login");
			logger.info("3- go back");
			scan=scanner.nextLine();
		}
		if(scan.equalsIgnoreCase("1")&&!usertype.equalsIgnoreCase("admin")&&!usertype.equalsIgnoreCase("installer")) {
			signup(usertype);
		}
		else if(scan.equalsIgnoreCase("2")) {
			logger.info("to login please enter your email and password");
			logger.info(" email: ");
			String email=scanner.nextLine();
			logger.info("password: ");
			String pass=scanner.nextLine();

			logIn(usertype,email,pass);

		}
		else if(scan.equalsIgnoreCase("3")) {
			mainMenue();
		}

	}

	public void mainMenue() {
		logger.info("Welcome to Carr Accessories company");
		while(true) {
			logger.info("Please choose between the specific users");
			logger.info("1-Admin");
			logger.info("2-Customer");
			logger.info("3-Installer");
			logger.info("4-exit");
			scan=scanner.nextLine();

			if(scan.equalsIgnoreCase("1")) {// user is admin
				start("admin");

			}

			else if(scan.equalsIgnoreCase("2")){//user is Customer
				start("customer");
			}

			else if(scan.equalsIgnoreCase("3")){//user is installer
				start("installer");
			}
			else if(scan.equalsIgnoreCase("4")){//user is installer
				logger.info("you log out succesfully");

				System.exit(0);
			}
			else {
				logger.info("please make sure to enter the right user");

			}

		}
	}


	public static void main(String[] args) {
		Login l=new Login();
		l.mainMenue();



	}


}