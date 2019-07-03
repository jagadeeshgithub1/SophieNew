package base;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TestBaseClass {

	// public static WebDriver driver;
	public static Properties prop;
	public String osName = null;
	public static Connection connection = null;
	public static Statement statement = null;
	FileInputStream ip = null;

	public TestBaseClass() throws Exception {

		prop = new Properties();

		osName = System.getProperty("os.name").trim();

		System.out.println("osname" + osName);

		try {
			if (osName.equalsIgnoreCase("Linux")) {

				ip = new FileInputStream("Properties/config.properties");
				System.out.println("Property file path");

			}
			// osName = osName.trim();
			else {
				ip = new FileInputStream("Properties\\config.properties");
			} // for the windows
				// ip = new FileInputStream("Properties/config.properties");// for linux

			prop.load(ip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(ip);

	}

	public static void DBConnectivity() {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;
			// TODO Auto-generated catch block

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		try {
			connection = DriverManager.getConnection(prop.getProperty("dbURL"), prop.getProperty("dbUser"),
					prop.getProperty("dbPwd"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");

		} else {
			System.out.println("Failed to make connection!");
		}

		try {
			statement = connection.createStatement();
		} catch (SQLException e) {

			System.out.println("Failed create the connection statement");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	/*
	 * public static void initialization() {
	 * 
	 * String browser = prop.getProperty("browser");
	 * 
	 * if (browser.equalsIgnoreCase("chrome")) { //
	 * 
	 * System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
	 * 
	 * // String downloadFilepath = prop.getProperty("DOWNLOADPATH"); String
	 * downloadFilepath = System.getProperty("user.dir") + "\\Downloads";
	 * HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	 * chromePrefs.put("profile.default_content_settings.popups", 0);
	 * chromePrefs.put("download.default_directory", downloadFilepath);
	 * //ChromeOptions options = new ChromeOptions();
	 * //options.setExperimentalOption("prefs", chromePrefs); //driver = new
	 * ChromeDriver(options); } else if (browser.equalsIgnoreCase("firefox")) {
	 * System.setProperty("webdriver.gecko.driver", "//Drivers//chromedriver.exe");
	 * driver = new FirefoxDriver(); }
	 * 
	 * driver.manage().window().maximize();
	 * 
	 * driver.manage().deleteAllCookies();
	 * 
	 * // driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	 * 
	 * driver.manage().timeouts().pageLoadTimeout(400, TimeUnit.SECONDS);
	 * 
	 * // loading the url
	 * 
	 * // driver.get(prop.getProperty("url"));
	 * 
	 * // prop.getProperty("url");
	 * 
	 * }
	 */

}
