package com.travelport.articleupdate.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ArticleUpdateUtility {
  public static <T> void jaxbObjectToXML(Object obj,Class<T> t) throws ClassNotFoundException 
  {
  T t1=(T)obj;
  System.out.println(t1.getClass().getName());
      try
      {
          //Create JAXB Context
          JAXBContext jaxbContext = JAXBContext.newInstance(Class.forName(t1.getClass().getName())/*ValidationParameters.class*/);
           
          //Create Marshaller
          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

          //Required formatting??
          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

         //Store XML to File
          File file = new File("target/"+t1.getClass().getSimpleName()+".xml");
           
          //Writes XML file to file-system
          jaxbMarshaller.marshal(t1, file); 
      } 
      catch (JAXBException e) 
      {
          e.printStackTrace();
      }
  }
}
