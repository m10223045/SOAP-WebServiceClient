package cimes.ares;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.rpc.ServiceException;

import cimes.ares.webserviceclient.WebServiceClient;

public class YulonCheckUserID {
    private WebServiceClient WSClient;
	
	public YulonCheckUserID() throws MalformedURLException, ServiceException, SQLException, ClassNotFoundException{
    	ConnectDB conn = new ConnectDB();
		PreparedStatement pst = conn.getConnection().prepareStatement("SELECT value FROM `system_parameter` WHERE `name`='身分認證來源'"); 
    	ResultSet rs = pst.executeQuery();
    	rs.next();
    	String str[]=rs.getString("value").split(",");	//index 0 is URL, 1 is NameSpace
    	
    	    	
		WSClient = new WebServiceClient();
		
		//Set Web Service Source
		//WSClient.createWebServiceClient(strEndpoint, strNameSpace);
		WSClient.createWebServiceClient(str[0], str[1]); 
		
		//Set Service Name
		WSClient.setOperationName("CheckUserAD");
		
		conn.getConnection().close();
		pst.close();
	}

	public String CheckUserAD(String name,String pwd) throws RemoteException, MalformedURLException{
		//Set name and value of parameters
		WSClient.setParameters("userName", name);
		WSClient.setParameters("password", pwd);	
		return WSClient.getService();			
	}
}
