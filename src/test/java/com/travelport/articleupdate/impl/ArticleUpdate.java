package com.travelport.articleupdate.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.travelport.articleupdate.constant.ArticleUpdateConstant;
import com.travelport.articleupdate.model.ArticleStatus;
import com.travelport.articleupdate.model.ArticleUpdateData;
import com.travelport.articleupdate.util.ArticleUpdateUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ArticleUpdate {
  
  public static String updateArticle(String articleUri) throws ClassNotFoundException{
    String status="";
    WebDriver driver = null;
    WebDriverManager.chromedriver().setup();
    ArticleUpdateData articleUpdateData=new ArticleUpdateData();
    List<ArticleStatus> articleStatusList=new ArrayList<ArticleStatus>();
    ChromeOptions chromeOptions = new ChromeOptions();
    driver = new ChromeDriver(chromeOptions);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.get(
        articleUri);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    int size = driver.findElements(By.tagName("iframe")).size();
    driver.switchTo().frame("gsft_main");
    WebElement table =  driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_WEBELEMENT_TABLE));
    List<WebElement> rowsList = table.findElements(By.tagName("tr"));
   System.out.println("Size:"+size);
   System.out.println("Row Size:"+rowsList.size());
        List<WebElement> columnsList = null;
        for (WebElement row : rowsList) {
          if(row!=null){
          System.out.println();
          columnsList = row.findElements(By.tagName("td"));
          if(columnsList!=null&&columnsList.size()>3){
           // WebElement link=columnsList.get(2).findElement(By.tagName("a"));
            articleStatusList.add(new ArticleStatus(columnsList.get(2).getText(),columnsList.get(3).getText(),columnsList.get(2).findElement(By.tagName("a")).getAttribute("href"),null));
            
         /*   System.out.println("Column size:"+columnsList.size());
          System.out.println(columnsList.get(2).getText() + ",");*/
          }
          }
          /* for (WebElement column : columnsList) {
                  System.out.print(column.getText() + ",");
          }*/

}
        status="Articles Updated";
        rowsList.get(0).findElements(By.tagName("td")).get(2).click();
        
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        int elementCount=0;
        Date today = new Date();
        LocalDateTime now = LocalDateTime.now();
        boolean checkoutFlag=false,updateVersionLink=false,submitReviewFlag=false;
        while(elementCount<4/*=articleStatusList.size()*/){
          String article= driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_ARTICLE_VALUE)).getAttribute("value");
          System.out.println("article"+article);
          
          if(true){
            /*next Checkout*/
            try {
              driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_CHECKOUT_BUTTON)).isDisplayed();
              checkoutFlag=true;
              System.out.println("Checkout Button Available");
            } catch (Exception e) {
              checkoutFlag=false;
              System.out.println("Checkout Button Not Available");
            }
          if(checkoutFlag&&driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_CHECKOUT_BUTTON)).getText().contains("checkkout")){
          driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_CHECKOUT_BUTTON)).click();
          driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
          /*next update*/
          if(driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_CHECKOUT_UPDATE_BUTTON)).isDisplayed()){
          driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_CHECKOUT_UPDATE_BUTTON)).click();
          
          articleStatusList.get(elementCount).setArticleStatus("Updated-"+today.getDate());
          driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
          rowsList.get(elementCount).findElements(By.tagName("td")).get(2).click();


          }
          }else{
            try {
              driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_UPDATE_VERSION_LINK)).isDisplayed();
              updateVersionLink=true;
              System.out.println("updateVersionLink Available");

            } catch (Exception e) {
              updateVersionLink=false;
              System.out.println("updateVersionLink Not Available");
            }
            /*updated version avail*/
            if(updateVersionLink){
            driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_UPDATE_VERSION_LINK)).click();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            try {
            WebElement  submitForReview =driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_SUBMIT_REVIEW_BUTTON));
              
              if(submitForReview.getAttribute("value").contains("publish")){
                submitReviewFlag=true;
                System.out.println("submitReview button Available");
              }else{
                submitReviewFlag=false;
                System.out.println("submitReview button Not Available");
              }
            } catch (Exception e) {
              submitReviewFlag=false;
              System.out.println("submitReview button Not Available");
            }
            /*Submit For Review*/
            if(submitReviewFlag){
              driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_SUBMIT_REVIEW_BUTTON)).click();
              driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
              System.out.println("submitReview Clicked");
              /*Back Button
              if(driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[1]/button[1]")).isDisplayed()){
                driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[1]/button[1]")).click();
              }*/
              //rowsList.get(elementCount).findElements(By.tagName("td")).get(2).click();
            }else{
            /*Back Button*/
            if(driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_BACK_BUTTON)).isDisplayed()){
              driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_BACK_BUTTON)).click();
            }
            articleStatusList.get(elementCount).setArticleStatus("Article Submited For Review");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
            }else{
            articleStatusList.get(elementCount).setArticleStatus("Check out Button not visible");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }

          }
        }
          /*next Record*/
          if(driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_NEXTRECORD_BUTTON)).isEnabled()){
           
          driver.findElement(By.xpath(ArticleUpdateConstant.XPATH_NEXTRECORD_BUTTON)).click();
          driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          }
          elementCount++;
        }
        articleUpdateData.setArticleStatusList(articleStatusList);
        ArticleUpdateUtility.jaxbObjectToXML(articleUpdateData,ArticleUpdateData.class);
    driver.close();

   

    return status;
  }

}
