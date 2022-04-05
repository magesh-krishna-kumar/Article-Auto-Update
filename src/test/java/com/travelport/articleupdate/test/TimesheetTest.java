package com.travelport.articleupdate.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class TimesheetTest {
 /* private static ResourceBundle resourceBundle = ResourceBundle.getBundle("config/ppmdata");
  public static String emailDistribution = resourceBundle.getString("TravelPerformanceLeadsEmail");
  public static String managers = resourceBundle.getString("TravelPerformanceManager");
  public static String spocEmail = resourceBundle.getString("TravelPerformanceSpocEmail");
  public static String spocPwd = resourceBundle.getString("TravelPerformanceSpocEmailPwd");
*/
  @Rule
  public TestName testMethodName = new TestName();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {    
          
  }
  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }
  
  
  @Test
  public void weeklyDefaulterListTripservices() throws Exception {
    String response=null;
    String manager="kakar, roopali";
    System.setProperty("http.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");  
    // HTTPS
    System.setProperty("https.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("https.proxyPort", "8080"); 
     response=getServiceInfo("weekly","Tripservices");
    
    System.out.println("response:"+response);
  }
  
  @Test
  public void weeklyTravelPerformanceDefaulterList() throws Exception {
    String response=null;
    String manager="kakar, roopali";
    System.setProperty("http.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");  
    // HTTPS
    System.setProperty("https.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("https.proxyPort", "8080"); 
     response=getServiceInfo("weekly","Travelperformance");
    
    System.out.println("response:"+response);
  }
  
  @Test
  public void monthlyDefaulterListTripservices() throws Exception {
    String response=null;
    String manager="kakar, roopali";
    System.setProperty("http.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");  
    // HTTPS
    System.setProperty("https.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("https.proxyPort", "8080"); 
     response=getServiceInfo("monthly","Tripservices");
    
    System.out.println("response:"+response);
  }
  @Test
  public void monthlyDefaulterListTravelperformane() throws Exception {
    String response=null;
    String manager="kakar, roopali";
    System.setProperty("http.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");  
    // HTTPS
    System.setProperty("https.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("https.proxyPort", "8080"); 
     response=getServiceInfo("monthly","Travelperformance");
    
    System.out.println("response:"+response);
  }

/*
  @Test
  public void defaulterList2() throws Exception {
    String response=null;
    String manager="hill, don";
    System.setProperty("http.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");  
    // HTTPS
    System.setProperty("https.proxyHost", "denwsproxy.galileo.corp.lcl");
    System.setProperty("https.proxyPort", "8080"); 
     response=getServiceInfo(manager);
    
    System.out.println("response:"+response);
  }
*/  
public static String getServiceInfo(String timeline,String team) throws ClientProtocolException, IOException{
        System.out.println("Thread awake");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config/ppmdata");
       // String teamDetails=resourceBundle.getString(team);
        String[] teamData = resourceBundle.getString(team).split("-");

        String emailDistribution = resourceBundle.getString(teamData[0]);
        String managers = resourceBundle.getString(teamData[1]);
        String[] emailData =resourceBundle.getString(teamData[2]).split("-");
        String spocEmail = emailData[0];
        String spocPwd = emailData[1];

    String responseString;
    
  //String url = "http://10.4.120.76:9090/PPMdefaulter-jersey-Api/ppm/defaulterslist";
      String url = "http://172.31.64.124:9090/PPMdefaulter-jersey-Api/ppm/defaulterslist";
      //String emailIds="magesh.kumar@travelport.com"+";"+"vignesh.kumar@travelport.com";
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(url);
      request.setHeader("email-ids",emailDistribution);
      request.setHeader("manager",managers );
      request.setHeader("timeline",timeline );
      request.setHeader("travelport_email",spocEmail );
      request.setHeader("travelport_password",spocPwd );
      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();
       responseString = EntityUtils.toString(entity, "UTF-8");
       System.out.println(responseString);
       
   
    return responseString;
  }
  
public static String getServiceInfo() throws ClientProtocolException, IOException{
        System.out.println("Thread awake");
    String responseString;
      String url = "http://172.31.64.55:9090/PPMdefaulter-jersey-Api/ppmdefaulters";
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(url);
      /*request.setHeader("Authorization", "Bearer " + token);
      request.setHeader("Accept", "application/json ");
      request.setHeader("Content-Type", "application/json ");
*/      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();
       responseString = EntityUtils.toString(entity, "UTF-8");
       System.out.println(responseString);
       
   
    return responseString;
  }

public static void generateTextFile(String emailList)  {
  File file = new File("target/emaillist/email.txt");
  file.getParentFile().mkdirs();
  FileWriter fileWriter = null;
  
  try {
   fileWriter=new FileWriter(file,true);
   fileWriter.write(emailList);
   fileWriter.close();
 } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
 }
  
  
}
public static void createProperties(String defaulter,String emails) {


  try  {
    OutputStream output = new FileOutputStream("target/emaillist/ppmdefaulter.properties");
      Properties prop = new Properties();

      // set the properties value
      prop.setProperty(defaulter, emails);
            // save properties to project root folder
      prop.store(output, null);

      System.out.println(prop);

  } catch (IOException io) {
      io.printStackTrace();
  }

}

public void setDataInProperty(String lable,String val) throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException{
  //OutputStream output = new FileOutputStream("src/test/resources/config/InvokeConfig.properties");
 // InputStream input = new FileInputStream("src/test/resources/config/InvokeConfig.properties");
 /* Properties prop = new Properties();
  //prop.load(input);
  prop.setProperty(lable, val);
  File file = new File("src/test/resources/config/InvokeConfig.properties");
  FileOutputStream fileOut = new FileOutputStream(file);
  // save properties to project root folder
  prop.store(fileOut, null);
*/
//  
//  Properties configProperty = new Properties();
//  FileOutputStream fileOut = null;
//  FileInputStream fileIn = null;
    File file = new File("target/emaillist/ppmdefaulter.properties");
    file.getParentFile().mkdirs();

//  fileIn = new FileInputStream(file);
//  configProperty.load(fileIn);
//  configProperty.setProperty(lable, val);
//  fileOut = new FileOutputStream(file);
//  configProperty.store(fileOut, "sample properties");
//  System.out.println(configProperty);
//
  PropertiesConfiguration config = new PropertiesConfiguration();
  PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
  layout.load(new InputStreamReader(new FileInputStream(file)));

  config.setProperty(lable, val);
  layout.save(new FileWriter("target/emaillist/ppmdefaulter.properties", false));
  
}
}
