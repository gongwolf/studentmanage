package edu.nmsu.nmamp.student.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@PropertySource("classpath:database_portal.properties")
public class PortalData {
	// private Environment env;
	@Value("${ds.database-driver}")
	private String driver;

	

	@Value("${ds.url}")
	private String url;
	
	@Value("${ds.username}")
	private String uname;

	@Value("${ds.password}")
	private String password;
	
	@Value("${ds.connectPorperties}")
	private String connectProperties;

	private Connection con;

	public void SynPortalStudenData() {
		// System.out.println("I am SynPortalStudenData function "+
		// env.getProperty("ds.username"));
		System.out.println("bbbbbbbbbbb" + this.driver + " \n" + this.url+"&amp;"+this.connectProperties + " \n" + this.uname + " " + this.password);
		this.connectDB();
		if(this.con!=null)
		{
			System.out.println("Connected Success!!!");
			this.connectClose();
		}
	}

	private void connectClose() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void connectDB() {
		try {
			Class.forName(this.driver);
			Properties ps = new Properties();
			ps.setProperty("user", this.uname);
			ps.setProperty("password", this.password);
			ps.setProperty("autoReconnect", "true");
			ps.setProperty("useSSL","false");
			ps.setProperty("useUnicode", "true");
			ps.setProperty("characterEncoding", "UTF-8");
			ps.setProperty("serverTimezone", "UTC");
			
			this.con = DriverManager.getConnection(this.url,ps);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
