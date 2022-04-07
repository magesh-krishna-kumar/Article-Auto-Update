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

import com.travelport.articleupdate.constant.ArticleUpdateConstant;
import com.travelport.articleupdate.impl.ArticleUpdate;

public class ArticleUpdateTest {
 
  @Rule
  public TestName testMethodName = new TestName();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {    
          
  }
  
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }
  
  @Test
  public void articleTest() throws Exception {
    String articleUri=ArticleUpdateConstant.FILTER_URL;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("config/articledata");
     String[] teamData = resourceBundle.getString("Travelperformance").split("-");

     String emailDistribution = resourceBundle.getString(teamData[0]);
     String[] emailData =resourceBundle.getString(teamData[1]).split("-");
     String spocEmail = emailData[0];
     String spocPwd = emailData[1];
    String response=ArticleUpdate.updateArticle(articleUri,emailDistribution,spocEmail,spocPwd);
    System.out.println("response:"+response);
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
