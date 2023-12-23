package car;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;
public class Login {
	private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
	private static final Scanner SCANNER = new Scanner(System.in);
    public static final String ADMIN_ROLE = "admin";
    private static final String INSTALLER = "installer";
	private String scan; 
	private static boolean flaglogin=false;
	private static boolean flagemail=false;
	private static boolean flagpass=false;
	private static Connection con=null;
	private static PreparedStatement stm=null;
	private static ResultSet rs=null;
    private static boolean isLoginPage=false;
    private static  boolean flagname=false;
    private static boolean flagconfpass=false;
    Installer installer=new Installer();
	public Login() {
		setIsLoginPage(true);
	}
	public static void checkEmail(String email,String usertype) {
		try {
		 connection();
			String sql="Select email from users where email='" +email+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (rs.next()) {
				setFlagemail(true);

			}
			else {
				setFlagemail(true);
			}
			stm.close();
			rs.close();

		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}

	}

	public void checkpassword(String email,String pass,String usertype) {

		try {
			connection();
			String sql="Select email from users where email='" +email+"'and password='" +pass+"' ";
			stm=con.prepareStatement(sql);
			rs=stm.executeQuery();
			if (!rs.next()) {
				setFlagPass(false);
			}
			else{
				setFlagPass(true);
			}
stm.close();
rs.close();

		}
		catch(Exception e) {
	        LOGGER.severe("An error occurred: " + e.getMessage());
		}

	}
	private static void connection() throws ClassNotFoundException, SQLException {
		String password = System.getProperty("database.password");
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost/caracc";
		con=DriverManager.getConnection(url,"root",password);
	}
	public void logIn(String usertype,String email,String password) {

		if(!email.contains("@")||!email.contains(".")) {
			LOGGER.info("syntex error in email");
			setFlaglogin(true);
			start(usertype);
		}
		else {
			checkEmail(email,usertype);
			if(!getFlagemail()) {
				LOGGER.info("user email doesnt exist"); 
				setFlaglogin(true);
				start(usertype);

			}
			else{ 
				checkpassword(email,password,usertype);
				
				if(getFlagPass()) {
				

					if(usertype.equalsIgnoreCase(ADMIN_ROLE)) {
						Admin admin=new Admin();
                     admin.adminDashboard();
					}
					else if(usertype.equalsIgnoreCase("customer")) {
						Customer customer=new Customer();
						customer.customerDashboard(email);
					}
					else  {
						installer.installerDashboard(email);
					}
				}
				else {
					LOGGER.info("you enter incorrect password"); 
					setFlaglogin(true);
					start(usertype);
				}
			}

		}
	}
	
public void insertuser(String email,String name,String password,String usertype) {
	try {
		connection();
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
        LOGGER.severe("An error occurred: " + e.getMessage());
	}
}
	public void regesterUser(String email,String username,String password,String confirmPassword,String usertype) {
		
		if(!email.contains("@")||!email.contains(".")) {
			LOGGER.info("syntex error in email");
			signup(usertype);
		}
		else {
			checkEmail(email,usertype);
			if(getFlagemail()) {
				LOGGER.info("this email already exist"); 
				signup(usertype);
			}
			else{ 
		
				if(checkName(username)) {
					
					setFlagName(true);
				if(confirmPassword.equals(password)) {
					setFlagConfPass(true);
					
					insertuser(email,username,password,usertype);
				LOGGER.info("you sign up sucessfulley");
				logIn(usertype,email,password);
				}
				else {
					LOGGER.info("your password doesnt match your confirm password");
					signup(usertype);
				}
				
					
				}
				else {
					LOGGER.info("your name should contain character ");
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
		return (count != name.length() && !Character.isDigit(name.charAt(0)));
	}
	public void signup(String usertype) {
		LOGGER.info(" Enter your email :");
		String email=SCANNER.nextLine();


		LOGGER.info(" Enter your username :");
		String username=SCANNER.nextLine();

		LOGGER.info(" Enter your password :");
		String password=SCANNER.nextLine();

		LOGGER.info(" Confirm your password :");
		String confirmPassword=SCANNER.nextLine();
		
		regesterUser( email,username,password,confirmPassword,usertype);
		

	}

	public void start(String usertype) {
		if(!getFlaglogin()) {
			if(!usertype.equalsIgnoreCase(ADMIN_ROLE)&&!usertype.equalsIgnoreCase(INSTALLER)){
				LOGGER.info("1- sign up");
			}
			LOGGER.info("2- login");
			LOGGER.info("3- go back");
			scan=SCANNER.nextLine();
		}
		if(scan.equalsIgnoreCase("1")&&!usertype.equalsIgnoreCase(ADMIN_ROLE)&&!usertype.equalsIgnoreCase(INSTALLER)) {
			signup(usertype);
		}
		else if(scan.equalsIgnoreCase("2")) {
			LOGGER.info("to login please enter your email and password");
			LOGGER.info(" email: ");
			String email=SCANNER.nextLine();
			LOGGER.info("password: ");
			String pass=SCANNER.nextLine();

			logIn(usertype,email,pass);

		}
		else if(scan.equalsIgnoreCase("3")) {
			mainMenue();
		}

	}

	public void mainMenue() {
		LOGGER.info("Welcome to Carr Accessories company");
		while(true) {
			LOGGER.info("Please choose between the specific users");
			LOGGER.info("1-Admin");
			LOGGER.info("2-Customer");
			LOGGER.info("3-Installer");
			LOGGER.info("4-exit");
			scan=SCANNER.nextLine();

			if(scan.equalsIgnoreCase("1")) {
				start(ADMIN_ROLE);

			}

			else if(scan.equalsIgnoreCase("2")){
				start("customer");
			}

			else if(scan.equalsIgnoreCase("3")){
				start(INSTALLER);
			}
			else if(scan.equalsIgnoreCase("4")){
				LOGGER.info("you log out succesfully");

				System.exit(0);
			}
			else {
				LOGGER.info("please make sure to enter the right user");

			}

		}
	}
	public static Boolean getFlaglogin() {
        return flaglogin;
    }

 
    public static void setFlaglogin(Boolean value) {
    	flaglogin = value;
    }
    public static Boolean getFlagemail() {
        return flagemail;
    }

 
    public static void setFlagemail(Boolean value) {
    	flagemail = value;
    }
    public static Boolean getFlagPass() {
        return flagpass;
    }

 
    public static void setFlagPass(Boolean value) {
    	flagpass = value;
    }
    public static Boolean getFlagConfPass() {
        return flagconfpass;
    }

 
    public static void setFlagConfPass(Boolean value) {
    	flagconfpass = value;
    }
    public static Boolean getIsLoginPage() {
        return isLoginPage;
    }

 
    public static void setIsLoginPage(Boolean value) {
    	isLoginPage = value;
    }
    public static Boolean getFlagName() {
        return flagname;
    }

 
    public static void setFlagName(Boolean value) {
    	flagname = value;
    }
	public static void main(String[] args) {
		Login l=new Login();
		l.mainMenue();



	}


}
