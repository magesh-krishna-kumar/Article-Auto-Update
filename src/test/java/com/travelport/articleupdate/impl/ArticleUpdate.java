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

import com.travelport.articleupdate.model.ArticleStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ArticleUpdate {
  
  public static String updateArticle(String articleUri){
    String status="";
    WebDriver driver = null;
    WebDriverManager.chromedriver().setup();
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
    WebElement table =  driver.findElement(By.xpath("/html/body/div[1]/div[1]/span/div/div[5]/table/tbody/tr/td/div/table/tbody"))/*.click()*/;
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
          String article= driver.findElement(By.xpath("/html/body/div[2]/form/span[1]/span/div[5]/div[1]/div[1]/div[1]/div[2]/input[1]")).getAttribute("value");
          System.out.println("article"+article);
          
          if(true){
            /*next Checkout*/
            try {
              driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/span[2]/span/button[2]")).isDisplayed();
              checkoutFlag=true;
              System.out.println("Checkout Button Available");
            } catch (Exception e) {
              checkoutFlag=false;
              System.out.println("Checkout Button Not Available");
            }
          if(checkoutFlag&&driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/span[2]/span/button[2]")).getText().contains("checkkout")){
          driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/span[2]/span/button[2]")).click();
          driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
          /*next update*/
          if(driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/span/span/button[1]")).isDisplayed()){
          driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/span/span/button[1]")).click();
          
          articleStatusList.get(elementCount).setArticleStatus("Updated-"+today.getDate());
          driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
          rowsList.get(elementCount).findElements(By.tagName("td")).get(2).click();


          }
          }else{
            try {
              driver.findElement(By.xpath("/html/body/div[2]/form/span[1]/span/div[4]/div/div/span[2]/a")).isDisplayed();
              updateVersionLink=true;
              System.out.println("updateVersionLink Available");

            } catch (Exception e) {
              updateVersionLink=false;
              System.out.println("updateVersionLink Not Available");
            }
            /*updated version avail*/
            if(updateVersionLink){
            driver.findElement(By.xpath("/html/body/div[2]/form/span[1]/span/div[4]/div/div/span[2]/a")).click();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            try {
            WebElement  submitForReview =driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/span[2]/span/button[2]"));
              
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
              driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/span[2]/span/button[2]")).click();
              driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
              System.out.println("submitReview Clicked");
              /*Back Button
              if(driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[1]/button[1]")).isDisplayed()){
                driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[1]/button[1]")).click();
              }*/
              //rowsList.get(elementCount).findElements(By.tagName("td")).get(2).click();
            }else{
            /*Back Button*/
            if(driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[1]/button[1]")).isDisplayed()){
              driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[1]/button[1]")).click();
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
          if(driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/div[2]/button[2]")).isEnabled()){
           
          driver.findElement(By.xpath("/html/body/div[1]/span/span/nav/div/div[2]/span[1]/div[2]/button[2]")).click();
          driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          }
          elementCount++;
        }
    
   // driver.close();

   

    return status;
  }

}
