package cimes.ares.webserviceclient;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class WebServiceClient {
	private String strEndpoint="";
	private String strNameSpace="";
	private String SOAPActionURI="";
	private String OperationName="";
	private ArrayList<String> Parameters;
	private Call call;
	private Service service;
	
	public WebServiceClient(){
		service = new Service();
		Parameters = new ArrayList();
	}
	/**
	 * Setting Web Service Source, strEndpint is asmx URL and strNameSpace is namespace URL.
	 * @param strEndpoint
	 * @param strNameSpace
	 * @throws ServiceException
	 * @throws MalformedURLException
	 */
	public void createWebServiceClient(String strEndpoint,String strNameSpace) throws ServiceException, MalformedURLException{
		try{
			this.strEndpoint = strEndpoint;
			this.strNameSpace = strNameSpace;
			
			call = (Call)service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(strEndpoint));
			call.setUseSOAPAction(true);		
		}catch(Exception e){
			System.out.println("createWebServiceError: "+e);
		}
	}
	/**
	 * Setting used Web Service Name.
	 * @param OperationName
	 */
	public void setOperationName(String OperationName){
		this.OperationName = OperationName;
		call.setSOAPActionURI(strNameSpace+OperationName);
		call.setOperationName(new QName(strNameSpace,OperationName));
	}
	/**
	 * Setting name and value of parameters.
	 * @param ParametersName
	 * @param ParametersValue
	 */
	public void setParameters(String ParametersName,String ParametersValue){
		call.addParameter(new QName(strNameSpace,ParametersName), org.apache.axis.encoding.XMLType.XSD_STRING,
				javax.xml.rpc.ParameterMode.IN);
		Parameters.add(ParametersValue);
	}
	/**
	 * Clean parameters setting.
	 */
	public void removeAllParameters(){
		call.removeAllParameters();
		Parameters.clear();
	}
	/**
	 * Getting the Web Service.
	 * @return
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public String getService() throws RemoteException, MalformedURLException{
		try{
			String result;
			Object para[] = Parameters.toArray();
					
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			call.setTargetEndpointAddress(new java.net.URL(strEndpoint));	
			result=(String) call.invoke(para);
			
			return result;			
		}catch(Exception e){
			System.out.println("Error: can't get Service. "+ OperationName);
			
			return "Error_" + OperationName;
		}		
	}
}
