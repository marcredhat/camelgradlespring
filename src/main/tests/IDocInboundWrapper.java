package com.idoc.sender;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.sap.conn.idoc.IDocDocument;
import com.sap.conn.idoc.IDocDocumentList;
import com.sap.conn.idoc.IDocFactory;
import com.sap.conn.idoc.IDocIllegalTypeException;
import com.sap.conn.idoc.IDocMetaDataUnavailableException;
import com.sap.conn.idoc.IDocParseException;
import com.sap.conn.idoc.IDocSegment;
import com.sap.conn.idoc.IDocSyntaxException;
import com.sap.conn.idoc.IDocXMLProcessor;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoException;
/**
 * Class to Send the IDoc to the ERP System
 * It is a GENERIC Class for ALL Types of IDocs
 * This class implements 2 Ways of sending IDoc to ERP System
 * Option1: Create IDoc Document and Send to ERP System. A new IDocDocument can also be fetched from this class
 * Option2: Read IDoc XML and Send to ERP System  
 * When the class is initialized connection to the ERP System is created.
 * This Class Throws JCO Exception
 *
 * @author Saxena, Anish - mailto:anish.saxena@gmail.com
 *
 */
public class IDocInboundWrapper extends BaseIDocInboundWrapper{
          /**
           * This method initializes the class and also connects to the ERP System
           * @throws JCoException
           */
          public IDocInboundWrapper() throws JCoException {
                    super();
          }
          /**
           * Creates an Empty IDocDocument for the connected Repository/ERP and returns the IDocDocument
           * The value has to be stored in this IDocDocument 
           * @param p_sIDocType
           * @return IDocDocument
           * @throws IDocMetaDataUnavailableException
           */
          public IDocDocument getNewIDocDocument(String p_sIDocType) throws IDocMetaDataUnavailableException{
                    IDocDocument l_oIDocDocument = IDOC_FACTORY.createIDocDocument(IDOC_REPOSITORY, p_sIDocType);
                    return l_oIDocDocument;
          }
          /**
           * This method takes the IDocDocument as the input parameter
           * and sends it to the ERP System
           * On Success, it returns true else throws corresponding Exception
           * @see getNewIDocDocument() for getting an Empty IDoc for setting the Data.
           * @param p_oIDocDocument
           * @return boolean
           * @throws IDocSyntaxException
           * @throws IDocMetaDataUnavailableException
           * @throws JCoException
           */
          public boolean sendIDocAsIDocDocument(IDocDocument p_oIDocDocument) throws IDocSyntaxException, IDocMetaDataUnavailableException, JCoException{
                    p_oIDocDocument.checkSyntax();
                    JCoIDoc.send(p_oIDocDocument, IDocFactory.IDOC_VERSION_DEFAULT, JCO_DESTINATION, TID);  
                    JCO_DESTINATION.confirmTID(TID);
                    return true;
          }
          /**
           * This method takes the XML Location and XML FileName as the input parameter
           * Reads the XML File and sends it to the ERP System
           * On Success, it returns true else throws corresponding Exception
           * p_sFileName is the name of the file with the extension
           * @param p_sLocation
           * @param p_sFileName
           * @return boolean
           * @throws IOException
           * @throws IDocParseException
           * @throws JCoException
           * @throws IDocSyntaxException
           * @throws IDocMetaDataUnavailableException
           */
          public boolean sendIDocAsIDocXML(String p_sLocation, String p_sFileName) throws IOException, IDocParseException, JCoException, IDocSyntaxException, IDocMetaDataUnavailableException{
                    String l_sIDocXML = this.readIDocXML(p_sLocation+"\\"+p_sFileName);
              IDocXMLProcessor l_oIDocXMLProcessor = IDOC_FACTORY.getIDocXMLProcessor();
        IDocDocumentList l_oIDocDocumentList = l_oIDocXMLProcessor.parse(IDOC_REPOSITORY, l_sIDocXML);
        for(int i=0; i<l_oIDocDocumentList.size(); i++){
                  IDocDocument l_oIDocDocument = l_oIDocDocumentList.get(i);
                  l_oIDocDocument.checkSyntax();
        }
        JCoIDoc.send(l_oIDocDocumentList, IDocFactory.IDOC_VERSION_DEFAULT, JCO_DESTINATION, TID);
        JCO_DESTINATION.confirmTID(TID);
        return true;
          }
          /**
           * This method takes Fully Qualified FileName along with Path as the input parameter.
           * It reads the file from the Location and returns the string value of the XML
           * @param p_sFullQualifiedFileName
           * @return String
           * @throws IOException
           */
          private String readIDocXML(String p_sFullQualifiedFileName) throws IOException{
                    String l_sIDocXML = null;
                    FileReader l_oFileReader;
                    l_oFileReader = new FileReader(p_sFullQualifiedFileName);
                    BufferedReader l_oBufferedReader = new BufferedReader(l_oFileReader);
                    StringBuffer l_oStringBuffer = new StringBuffer();
                    String l_sLine;
                    while ((l_sLine = l_oBufferedReader.readLine()) != null){
                              l_oStringBuffer.append(l_sLine);
                    }
                    l_sIDocXML = l_oStringBuffer.toString();
                    l_oBufferedReader.close();
                    l_oFileReader.close();
                    return l_sIDocXML;
          }
          /**
           * Main Method
           * Sample Method shows how to test this class
           * @param args
           */
          public static void main(String[] args){
                    /*
                     * WAY 1 SENDING AN IDOC AS IDOCDOCUMENT
                     */
                    try {
                              IDocInboundWrapper l_oIDocInboundReceiptWrapper = new IDocInboundWrapper();
                              IDocDocument l_oIDocDocument = l_oIDocInboundReceiptWrapper.getNewIDocDocument("WPUBON01");
                              IDocSegment l_oIDocSegment = l_oIDocDocument.getRootSegment();
                              l_oIDocSegment = l_oIDocSegment.addChild("E1WPB01");
                              // and so on. User can refer the IDoc Specification for creating the IDoc Structure
                              @SuppressWarnings("unused")
                              boolean l_oResponse = l_oIDocInboundReceiptWrapper.sendIDocAsIDocDocument(l_oIDocDocument);
                              //IF l_oResponse is true then the send was successful
                    } catch (IDocIllegalTypeException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocIllegalTypeException has occured :: "+ oX.getMessage());
                    } catch (IDocSyntaxException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocSyntaxException has occured :: "+ oX.getMessage());
                    } catch (IDocMetaDataUnavailableException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocMetaDataUnavailableException has occured :: "+ oX.getMessage());
                    } catch (JCoException oX) {
                              oX.printStackTrace();
                              System.out.println("A JCoException has occured :: "+ oX.getMessage());
                    }
                    /*
                     * WAY 2 SENDING AN IDOC AS IDOCXML
                     */
                    try {
                              IDocInboundWrapper l_oIDocInboundReceiptWrapper = new IDocInboundWrapper();
               String l_sLocation = "C:\\Anish_Documents\\SAMPLE_IDOC_XML\\INBOUND";
                              @SuppressWarnings("unused")
                              boolean l_oResponse = l_oIDocInboundReceiptWrapper.sendIDocAsIDocXML(l_sLocation, "TestSalesOrder.xml");
                              //IF l_oResponse is true then the send was successful
                    } catch (IDocSyntaxException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocSyntaxException has occured :: "+ oX.getMessage());
                    } catch (IDocParseException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocParseException has occured :: "+ oX.getMessage());
                    } catch (IDocMetaDataUnavailableException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocMetaDataUnavailableException has occured :: "+ oX.getMessage());
                    } catch (IOException oX) {
                              oX.printStackTrace();
                              System.out.println("An IOException has occured :: "+ oX.getMessage());
                    } catch (JCoException oX) {
                              oX.printStackTrace();
                              System.out.println("A JCoException has occured :: "+ oX.getMessage());
                    }
          }
}
