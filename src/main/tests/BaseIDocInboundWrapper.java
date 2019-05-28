package com.idoc.sender;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import com.sap.conn.idoc.IDocFactory;
import com.sap.conn.idoc.IDocRepository;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
/**
 * Base Class to send the IDoc to the ERP System
 * This class creates the connection to the ERP System
 * This class also prepares to send the IDoc Files to the ERP System 
 * This Class Throws JCO Exception
 *
 * @author Saxena, Anish - mailto:anish.saxena@gmail.com
 *
 */
public class BaseIDocInboundWrapper {
          //FIXME:READ GLOBAL ATTRIBUTES FROM PROPERTIES FILE
          //Name of the Destination File
          private static final String DESTINATION_NAME                     = "SampleDestination";
          //Connection Attributes for SAP ERP System
          private static final String JCO_AS_HOST_NAME          = "HOST_NAME";
          private static final String JCO_SYSTEM_NR                = "SYSTEM_NR";
          private static final String JCO_CLIENT_NR                   = "CLIENT_NR";
          private static final String JCO_USER_NAME                               = "USER_NAME";
          private static final String JCO_PASSWORD                               = "PASSWORD";
          private static final String JCO_LANGUAGE                               = "LANGUAGE"; //EN OR DE etc...
          protected static JCoDestination JCO_DESTINATION;
          protected static IDocRepository IDOC_REPOSITORY;
          protected static String TID;
          protected static IDocFactory IDOC_FACTORY;
          /**
           * This method initializes the class and also connects to the ERP System
           * @throws JCoException
           */
          public BaseIDocInboundWrapper() throws JCoException {
                    connectionProperties();
                    prepareERPForIDoc();
          }
          /**
           * Create Destination Data File for the Connection
           * @param l_oConnectionProperties
           */
          private static void createDestinationDataFile(final Properties l_oConnectionProperties){
                    File destCfg = new File(DESTINATION_NAME+".jcoDestination");
                    try{
                              FileOutputStream fos = new FileOutputStream(destCfg, false);
                              l_oConnectionProperties.store(fos, "for tests only !");
                              fos.close();
                    } catch (Exception e) {
                              throw new RuntimeException("Unable to create the destination files", e);
                    }
          }
          /**
           * This method is initialized from the Constructor
           * This method creates the connection properties file and then stores is in the default location
           */
          private static void connectionProperties(){
                    Properties connectionProperties = new Properties();
                    connectionProperties.setProperty(DestinationDataProvider.JCO_ASHOST,JCO_AS_HOST_NAME);
                    connectionProperties.setProperty(DestinationDataProvider.JCO_SYSNR, JCO_SYSTEM_NR);
                    connectionProperties.setProperty(DestinationDataProvider.JCO_CLIENT, JCO_CLIENT_NR);
                    connectionProperties.setProperty(DestinationDataProvider.JCO_USER, JCO_USER_NAME);
                    connectionProperties.setProperty(DestinationDataProvider.JCO_PASSWD, JCO_PASSWORD);
                    connectionProperties.setProperty(DestinationDataProvider.JCO_LANG, JCO_LANGUAGE);
                    createDestinationDataFile(connectionProperties);
          }
          /**
           * This method is initialized from the Constructor
           * This method creates the connection to the ERP System and prepares to send IDoc to ERP System
           * @throws JCoException
           */
          private static void prepareERPForIDoc() throws JCoException{
                    // get the JCo destination
                    JCO_DESTINATION = JCoDestinationManager.getDestination(DESTINATION_NAME);
                    // Create repository
                    IDOC_REPOSITORY = JCoIDoc.getIDocRepository(JCO_DESTINATION);
                    TID = JCO_DESTINATION.createTID();
                    IDOC_FACTORY = JCoIDoc.getIDocFactory();
          }
}
