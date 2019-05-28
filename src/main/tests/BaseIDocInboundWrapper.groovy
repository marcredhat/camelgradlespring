/*
  Automatically Converted from Java Source 
  
  by java2groovy v0.0.1   Copyright Jeremy Rayner 2007
  
  !! NOT FIT FOR ANY PURPOSE !! 
  'java2groovy' cannot be used to convert one working program into another  */

package com.idoc.sender
import java.io.File
import java.io.FileOutputStream
import java.util.Properties
import com.sap.conn.idoc.IDocFactory
import com.sap.conn.idoc.IDocRepository
import com.sap.conn.idoc.jco.JCoIDoc
import com.sap.conn.jco.JCoDestination
import com.sap.conn.jco.JCoDestinationManager
import com.sap.conn.jco.JCoException
import com.sap.conn.jco.ext.DestinationDataProvider
import BaseIDocInboundWrapperBaseIDocInboundWrapper








class BaseIDocInboundWrapper 
    {private static final String DESTINATION_NAME = "SampleDestination"

    private static final String JCO_AS_HOST_NAME = "HOST_NAME"
    private static final String JCO_SYSTEM_NR = "SYSTEM_NR"
    private static final String JCO_CLIENT_NR = "CLIENT_NR"
    private static final String JCO_USER_NAME = "USER_NAME"
    private static final String JCO_PASSWORD = "PASSWORD"
    private static final String JCO_LANGUAGE = "LANGUAGE"
    protected static JCoDestination JCO_DESTINATION
    protected static IDocRepository IDOC_REPOSITORY
    protected static String TID
    protected static IDocFactory IDOC_FACTORY




    def BaseIDocInboundWrapperBaseIDocInboundWrapper() throws JCoException{
        connectionProperties()
        prepareERPForIDoc()
}







    private static void createDestinationDataFile(final Properties l_oConnectionProperties) {
        File destCfg = new File(DESTINATION_NAME + ".jcoDestination")
        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false)
            l_oConnectionProperties.store(fos, "for tests only !")
            fos.close()
}



         catch (Exception) {
            throw new RuntimeException("Unable to create the destination files", e)
}
}













    private static void connectionProperties() {
        Properties connectionProperties = new Properties()
        connectionProperties.setProperty(DestinationDataProvider.JCO_ASHOST, JCO_AS_HOST_NAME)
        connectionProperties.setProperty(DestinationDataProvider.JCO_SYSNR, JCO_SYSTEM_NR)
        connectionProperties.setProperty(DestinationDataProvider.JCO_CLIENT, JCO_CLIENT_NR)
        connectionProperties.setProperty(DestinationDataProvider.JCO_USER, JCO_USER_NAME)
        connectionProperties.setProperty(DestinationDataProvider.JCO_PASSWD, JCO_PASSWORD)
        connectionProperties.setProperty(DestinationDataProvider.JCO_LANG, JCO_LANGUAGE)
        createDestinationDataFile(connectionProperties)
}














    private static void prepareERPForIDoc() throws JCoException{

        JCO_DESTINATION = JCoDestinationManager.getDestination(DESTINATION_NAME)

        IDOC_REPOSITORY = JCoIDoc.getIDocRepository(JCO_DESTINATION)
        TID = JCO_DESTINATION.createTID()
        IDOC_FACTORY = JCoIDoc.getIDocFactory()
}
}
