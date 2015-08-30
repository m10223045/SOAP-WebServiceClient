package cimes.ares;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.rpc.ServiceException;

import org.json.JSONArray;
import org.json.JSONException;

import cimes.ares.webserviceclient.WebServiceClient;

public class YulonGetBIList {
    private WebServiceClient WSClient;
    
	public YulonGetBIList() throws MalformedURLException, ServiceException, ClassNotFoundException, SQLException{
		ConnectDB conn = new ConnectDB();
		PreparedStatement pst = conn.getConnection().prepareStatement("SELECT value FROM `system_parameter` WHERE `name`='進車清單來源'"); 
    	ResultSet rs = pst.executeQuery();
    	rs.next();
    	String str[]=rs.getString("value").split(","); //index 0 is URL, 1 is NameSpace
		
		WSClient = new WebServiceClient();
		
		//Set Web Service Source
		//WSClient.createWebServiceClient(strEndpoint, strNameSpace);
		WSClient.createWebServiceClient(str[0], str[1]); 
		
		//Set Service Name
		WSClient.setOperationName("GetBIList");
		
		conn.getConnection().close();
		pst.close();
	}
	/**
	 * Transform string to json if the return data is json formal.
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws JSONException
	 */
	public JSONArray GetBIList() throws RemoteException, MalformedURLException, JSONException{
		String result=WSClient.getService();
		if(result.equals("Error_GetBIList")){
			result= "[Error_GetBIList]";
		}
		
		JSONArray jsona =  new JSONArray(result);
		return jsona;
	}
	/**
	 * Getting the web service.
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public String GetBIListString() throws RemoteException, MalformedURLException{
		return WSClient.getService();		
	}
}
