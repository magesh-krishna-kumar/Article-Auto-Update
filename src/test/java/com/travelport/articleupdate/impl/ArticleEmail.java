package com.travelport.articleupdate.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.travelport.articleupdate.constant.ArticleUpdateConstant;




public class ArticleEmail {
  private static final Logger logger = LogManager.getLogger(ArticleEmail.class);

  public static String mailSetup(String emailIds, String travelport_email, String travelport_password) throws MessagingException{
    String status="";
    Session session = null;
    final String from=travelport_email;
   // final String from="ppm-tracker@travelport365.onmicrosoft.com";
    final String pass=travelport_password;
    logger.info("Email-Distribution:"+emailIds);
    String host = "smtp.office365.com";
    int port = 587;
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", "587");
    session = Session.getInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, pass);
      }
    });
  
    String subject=ArticleUpdateConstant.EMAIL_SUBJECT;


    String msg=ArticleUpdateConstant.EMAIL_MESSAGE;
    String[] emailArray =getEmailIDs(emailIds);
    status=sendAttachmentEmail(session,subject,msg,getReciepientList(emailArray));
    return status;
  }
public static  String[] getEmailIDs(String emailIds) {
    

    String[] emailArray = emailIds.trim().split(";");
    return emailArray;
  }
  public static InternetAddress[] getReciepientList(String[] mailIds) throws AddressException {
    InternetAddress[] receipientLists = new InternetAddress[mailIds.length];
    for (int i = 0; i < mailIds.length; i++) {
      InternetAddress internetAddress=new InternetAddress();
      internetAddress.setAddress(mailIds[i]);
      receipientLists[i] = internetAddress/*InternetAddress.parse(mailIds[i])*/;
    }
    for (int i = 0; i < receipientLists.length; i++) {
      logger.info("receipientLists:" + receipientLists[i]);
    }
    return receipientLists;
  }
  public static String sendAttachmentEmail(Session session, String subject, String body,InternetAddress[] receipientLists){
    try{
         MimeMessage msg = new MimeMessage(session);
         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
         msg.addHeader("format", "flowed");
         msg.addHeader("Content-Transfer-Encoding", "8bit");
          
         msg.setFrom(new InternetAddress(/*"ppm-tracker@travelport365.onmicrosoft.com"*/"magesh.kumar@travelport.com", "NoReply-JD"));

         msg.setReplyTo(InternetAddress.parse("magesh.kumar@travelport.com", false));

         msg.setSubject(subject, "UTF-8");

         msg.setSentDate(new Date());

         msg.setRecipients(Message.RecipientType.TO, receipientLists);
          
         // Create the message body part
         BodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         messageBodyPart.setText(body);
         
         // Create a multipart message for attachment
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Second part is attachment
         messageBodyPart = new MimeBodyPart();
         /*String[] mangerArray =PpmDefaulterSheetDowload.getManagerArray(manager);
         for(int i=0;i<mangerArray.length;i++){*/
          // String[] mangerNameSplit =PpmDefaulterSheetDowload.getManagerName(mangerArray[i]);
         String fileName="ArticleUpdateData"+".xml";
           String fullFilename ="target/"+fileName/* PpmApiConstants.fileLocation+"-"+mangerNameSplit[1].trim()+"_"+mangerNameSplit[0].trim()+".xlsx"*/;
           addAttachment(multipart, fullFilename,fileName);

        // }
        //String filename = "C:/Users/magesh.kumar/Downloads/"+"Unapproved Timesheets"+".xlsx";
         //String filename = "C:/Users/magesh.kumar/Downloads/"+"TimesheetStatus"+".xlsx";
         //String filename2 = "C:/Users/magesh.kumar/Downloads/"+"India_Holiday_List_-_2020"+".pdf";

         //addAttachment(multipart, filename2);

        /* DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName("Unapproved Timesheets"+".xlsx");
         multipart.addBodyPart(messageBodyPart);
*/

         // Send the complete message parts
         msg.setContent(multipart);

         // Send message
         Transport.send(msg);
         logger.info("EMail Sent Successfully with attachment!!");
      }catch (MessagingException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
    }
    return "EMail Sent Successfully with attachment!!";

}
  private static void addAttachment(Multipart multipart, String fullFilename,String fileName) throws MessagingException
  {
      DataSource source = new FileDataSource(fullFilename);
      BodyPart messageBodyPart = new MimeBodyPart();        
      messageBodyPart.setDataHandler(new DataHandler(source));
     // String[] fileNameArray=PpmDefaulterSheetDowload.getFilenameFromPath(filename);
      messageBodyPart.setFileName(fileName);
      multipart.addBodyPart(messageBodyPart);
  }
}
