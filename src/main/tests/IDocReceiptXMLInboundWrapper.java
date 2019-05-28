package com.idoc.sender;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.idoc.helper.EDI_DC40;
import com.idoc.helper.salesdocs.E1WPB01;
import com.idoc.helper.salesdocs.E1WPB02;
import com.idoc.helper.salesdocs.E1WPB03;
import com.idoc.helper.salesdocs.E1WPB04;
import com.idoc.helper.salesdocs.E1WPB05;
import com.idoc.helper.salesdocs.E1WPB06;
import com.idoc.helper.salesdocs.E1WPB07;
import com.idoc.helper.salesdocs.E1WXX01;
import com.idoc.helper.salesdocs.IDOC;
import com.idoc.helper.salesdocs.WPUBON01;
import com.sap.conn.idoc.IDocMetaDataUnavailableException;
import com.sap.conn.idoc.IDocParseException;
import com.sap.conn.idoc.IDocSyntaxException;
import com.sap.conn.jco.JCoException;
/**
 * Wrapper Class for Sending the Sales Docs (Receipts) to the ERP System
 * This class Creates the XML Format for the Sales Docs (Receipts)
 * It stores the XML Format and then sends the stored XML to the ERP System
 *
 * @author Saxena, Anish - mailto:anish.saxena@gmail.com
 *
 */
public class IDocReceiptXMLInboundWrapper{
          //FIXME:READ GLOBAL ATTRIBUTES FROM PROPERTIES FILE
          private static final String LOCATION = "C:\\Anish_Documents\\SAMPLE_IDOC_XML\\INBOUND";
          //FIXME:REMOVE FILE_NAME AND MAKE IT DYNAMIC BASED ON RECEIPT ID OR SO
          private static final String FILE_NAME = "RECEIPT_TEST.XML";
          /**
           * This method will create the IDoc Structure as Object
           * It will internally call corresponding objects to form the IDoc Object
           * @return WPUBON01
           */
          private WPUBON01 getWPUBON01(){
                    WPUBON01 l_oWPUBON01 = new WPUBON01();
                    l_oWPUBON01.setIDOC(getIDOC());
                    return l_oWPUBON01;
          }
          /**
           * @return IDOC
           */
          private IDOC getIDOC(){
                    IDOC l_oIDOC = new IDOC();
                    l_oIDOC.setBEGIN("1");
                    l_oIDOC.setEDI_DC40(getEDI_DC40());
                    l_oIDOC.setE1WPB01s(getE1WPB01s());
                    return l_oIDOC;
          }
          /**
           * This will return the Header Information of the IDoc as Object
           * This is common for all the IDocs. Setters with setSND* can be ignored
        * Direct shows its incoming or outgoing
           * as it is filled when Idoc is sent from ERP.
           * @return EDI_DC40
           */
          private EDI_DC40 getEDI_DC40(){
                    EDI_DC40 l_oEDI_DC40 = new EDI_DC40();
                    l_oEDI_DC40.setSEGMENT("1");
                    l_oEDI_DC40.setTABNAM("EDI_DC40");
                    l_oEDI_DC40.setMANDT("");
                    l_oEDI_DC40.setDOCNUM("");
                    l_oEDI_DC40.setDOCREL("");
                    l_oEDI_DC40.setSTATUS("");
                    l_oEDI_DC40.setDIRECT("");
                    l_oEDI_DC40.setOUTMOD("");
                    l_oEDI_DC40.setIDOCTYP("WPUBON01");
                    l_oEDI_DC40.setMESTYP("WPUBON");
                    l_oEDI_DC40.setSNDPOR("");
                    l_oEDI_DC40.setSNDPRT("");
                    l_oEDI_DC40.setSNDPRN("");
                    l_oEDI_DC40.setRCVPOR("");
                    l_oEDI_DC40.setRCVPRT("");
                    l_oEDI_DC40.setRCVPRN("");
                    l_oEDI_DC40.setCREDAT("");
                    l_oEDI_DC40.setCRETIM("");
                    l_oEDI_DC40.setSERIAL("");
                    return l_oEDI_DC40;
          }
          /**
           * List of Sales Documents / Receipts
           * @return List<E1WPB01>
           */
          private List<E1WPB01> getE1WPB01s(){
                    List<E1WPB01> l_oE1WPB01s = new ArrayList<E1WPB01>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB01 l_oE1WPB01 = new E1WPB01();
                    l_oE1WPB01.setSEGMENT("1");
                    l_oE1WPB01.setPOSKREIS("");
                    l_oE1WPB01.setKASSID("");
                    l_oE1WPB01.setVORGDATUM("");
                    l_oE1WPB01.setVORGZEIT("");
                    l_oE1WPB01.setBONNUMMER("");
                    l_oE1WPB01.setQUALKDNR("");
                    l_oE1WPB01.setKUNDNR("");
                    l_oE1WPB01.setKASSIERER("");
                    l_oE1WPB01.setCSHNAME("");
                    l_oE1WPB01.setBELEGWAERS("");
                    l_oE1WPB01.setE1WPB02s(getE1WPB02());
                    l_oE1WPB01.setE1WPB05s(getE1WPB05s());
                    l_oE1WPB01.setE1WPB07s(getE1WPB07s());
                    l_oE1WPB01.setE1WPB06s(getE1WPB06s());
                    l_oE1WPB01s.add(l_oE1WPB01);
                    return l_oE1WPB01s;
          }
          /**
           * List of Materials in Receipts
           * @return
           */
          private List<E1WPB02> getE1WPB02(){
                    List<E1WPB02> l_oE1WPB02s = new ArrayList<E1WPB02>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB02 l_oE1WPB02 = new E1WPB02();
                    l_oE1WPB02.setSEGMENT("1");
                    l_oE1WPB02.setVORGANGART("");
                    l_oE1WPB02.setQUALARTNR("");
                    l_oE1WPB02.setARTNR("");
                    l_oE1WPB02.setSERIENNR("");
                    l_oE1WPB02.setVORZEICHEN("-");
                    l_oE1WPB02.setMENGE("");
                    l_oE1WPB02.setVERKAEUFER("");
                    l_oE1WPB02.setAKTIONSNR("");
                    l_oE1WPB02.setREFBONNR("");
                    l_oE1WPB02.setE1WPB03s(getE1WPB03s());
                    l_oE1WPB02.setE1WPB04s(getE1WPB04s());
                    l_oE1WPB02.setE1WXX01s(getE1WXX01s());
                    l_oE1WPB02s.add(l_oE1WPB02);
                    return l_oE1WPB02s;
          }
          /**
           * Information about Material
           * @return List<E1WPB03>
           */
          private List<E1WPB03> getE1WPB03s(){
                    List<E1WPB03> l_oE1WPB03s = new ArrayList<E1WPB03>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB03 l_oE1WPB03 = new E1WPB03();
                    l_oE1WPB03.setSEGMENT("1");
                    l_oE1WPB03.setVORZEICHEN("");
                    l_oE1WPB03.setKONDITION("");
                    l_oE1WPB03.setKONDVALUE("");
                    l_oE1WPB03.setCONDID("");
                    l_oE1WPB03.setCONDID20("");
                    l_oE1WPB03.setQUALCONDID("");
                    l_oE1WPB03.setBBYNR("");
                    l_oE1WPB03s.add(l_oE1WPB03);
                    return l_oE1WPB03s;
          }
          /**
           *
           * @return List<E1WPB04>
           */
          private List<E1WPB04> getE1WPB04s(){
                    List<E1WPB04> l_oE1WPB04s = new ArrayList<E1WPB04>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB04 l_oE1WPB04 = new E1WPB04();
                    l_oE1WPB04.setSEGMENT("1");
                    l_oE1WPB04.setMWSKZ("");
                    l_oE1WPB04.setMWSBT("");
                    l_oE1WPB04s.add(l_oE1WPB04);
                    return l_oE1WPB04s;
          }
          /**
           *
           * @return List<E1WXX01>
           */
          private List<E1WXX01> getE1WXX01s(){
                    List<E1WXX01> l_oE1WXX01s = new ArrayList<E1WXX01>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WXX01 l_oE1WXX01 = new E1WXX01();
                    l_oE1WXX01.setSEGMENT("1");
                    l_oE1WXX01.setFLDGRP("");
                    l_oE1WXX01.setFLDNAME("");
                    l_oE1WXX01.setFLDVAL("");
                    l_oE1WXX01s.add(l_oE1WXX01);
                    return l_oE1WXX01s;
          }
          /**
           * Discount Information
           * @return List<E1WPB05>
           */
          private List<E1WPB05> getE1WPB05s(){
                    List<E1WPB05> l_oE1WPB05s = new ArrayList<E1WPB05>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB05 l_oE1WPB05 = new E1WPB05();
                    l_oE1WPB05.setSEGMENT("1");
                    l_oE1WPB05.setVORZEICHEN("");
                    l_oE1WPB05.setRABATTART("");
                    l_oE1WPB05.setRABSATZ("");
                    l_oE1WPB05.setRABVALUE("");
                    l_oE1WPB05.setCONDID("");
                    l_oE1WPB05.setCONDID20("");
                    l_oE1WPB05.setQUALCONDID("");
                    l_oE1WPB05.setBBYNR("");
                    l_oE1WPB05s.add(l_oE1WPB05);
                    return l_oE1WPB05s;
          }
          /**
           * Tax Data for Receipt
           * @return List<E1WPB07>
           */
          private List<E1WPB07> getE1WPB07s(){
                    List<E1WPB07> l_oE1WPB07s = new ArrayList<E1WPB07>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB07 l_oE1WPB07 = new E1WPB07();
                    l_oE1WPB07.setSEGMENT("1");
                    l_oE1WPB07.setTAXCODE("");
                    l_oE1WPB07.setTAXVALUE("");
                    l_oE1WPB07s.add(l_oE1WPB07);
                    return l_oE1WPB07s;
          }
          /**
           * Payment For the Receipt
           * @return List<E1WPB06>
           */
          private List<E1WPB06> getE1WPB06s(){
                    List<E1WPB06> l_oE1WPB06s = new ArrayList<E1WPB06>();
                    //FIXME:ADD THE FOR LOOP HERE
                    E1WPB06 l_oE1WPB06 = new E1WPB06();
                    l_oE1WPB06.setSEGMENT("1");
                    l_oE1WPB06.setVORZEICHEN("");
                    l_oE1WPB06.setZAHLART("");
                    l_oE1WPB06.setSUMME("");
                    l_oE1WPB06.setCCINS("");
                    l_oE1WPB06.setWAEHRUNG("");
                    l_oE1WPB06.setKARTENNR("");
                    l_oE1WPB06.setKARTENFNR("");
                    l_oE1WPB06.setGUELTAB("");
                    l_oE1WPB06.setGUELTBIS("");
                    l_oE1WPB06.setKONTOINH("");
                    l_oE1WPB06.setBANKLZ("");
                    l_oE1WPB06.setKONTONR("");
                    l_oE1WPB06.setAUTORINR("");
                    l_oE1WPB06.setTERMID("");
                    l_oE1WPB06.setTRTIME("");
                    l_oE1WPB06.setZUONR("");
                    l_oE1WPB06.setREFERENZ1("");
                    l_oE1WPB06.setREFERENZ2("");
                    l_oE1WPB06.setCCBEG("");
                    l_oE1WPB06.setCSOUR("");
                    l_oE1WPB06.setSETTL("");
                    l_oE1WPB06.setAUTRA("");
                    l_oE1WPB06.setLOCID("");
                    l_oE1WPB06.setREACT("");
                    l_oE1WPB06.setFLGAU("");
                    l_oE1WPB06.setCONDID("");
                    l_oE1WPB06.setCONDID20("");
                    l_oE1WPB06.setCARDGUID("");
                    l_oE1WPB06.setENCTYPE("");
                    l_oE1WPB06s.add(l_oE1WPB06);
                    return l_oE1WPB06s;
          }
          /**
           * Create Receipt IDoc XML and Stores it at the location with the file name
           * @param p_sFileName
           * @throws JAXBException
           */
          private void createReceiptIDocXML(String p_sFileName) throws JAXBException{
                    File l_oFile = new File(LOCATION+"\\"+p_sFileName);
                    JAXBContext l_oJAXBContext = JAXBContext.newInstance(WPUBON01.class);
                    Marshaller l_oJAXBMarshaller = l_oJAXBContext.createMarshaller();
                    l_oJAXBMarshaller.marshal(getWPUBON01(), l_oFile);
                    //TO PRETTY PRINT OUTPUT
                    l_oJAXBMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    //FIXME: TO REMOVE THIS l_oJAXBMarshaller.marshal(getWPUBON01(), System.out);
                    l_oJAXBMarshaller.marshal(getWPUBON01(), System.out);
          }
          /**
           * Send the Receipt to ERP. On Success returns true else throws exception
           * @return boolean
           * @throws JAXBException
           * @throws JCoException
           * @throws IDocSyntaxException
           * @throws IDocParseException
           * @throws IDocMetaDataUnavailableException
           * @throws IOException
           */
          public boolean sendReceipt() throws JAXBException, JCoException, IDocSyntaxException, IDocParseException, IDocMetaDataUnavailableException, IOException{
                    //FIXME: ADD A RECEIPT LOOP HERE
                    this.createReceiptIDocXML(FILE_NAME);
                    IDocInboundWrapper l_oIDocInboundReceiptWrapper = new IDocInboundWrapper();
                    boolean l_oResponse = l_oIDocInboundReceiptWrapper.sendIDocAsIDocXML(LOCATION, FILE_NAME);
                    return l_oResponse;
          }
          /**
           * Main Method
           * Sample Method shows how to use this class
           * @param args
           */
          public static void main(String[] args){
                    try {
                              IDocReceiptXMLInboundWrapper l_oIDocReceiptXMLInboundWrapper = new IDocReceiptXMLInboundWrapper();
                              boolean l_oResponse = l_oIDocReceiptXMLInboundWrapper.sendReceipt();
                              if(l_oResponse)
                                        System.out.println("Sales Doc (Receipt) Successfully Sent");
                    } catch (IDocSyntaxException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocSyntaxException has occured :: "+ oX.getMessage());
                    } catch (IDocParseException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocParseException has occured :: "+ oX.getMessage());
                    } catch (IDocMetaDataUnavailableException oX) {
                              oX.printStackTrace();
                              System.out.println("An IDocMetaDataUnavailableException has occured :: "+ oX.getMessage());
                    } catch (JAXBException oX) {
                              oX.printStackTrace();
                              System.out.println("A JAXBException has occured :: "+ oX.getMessage());
                    } catch (JCoException oX) {
                              oX.printStackTrace();
                              System.out.println("A JCoException has occured :: "+ oX.getMessage());
                    } catch (IOException oX) {
                              oX.printStackTrace();
                              System.out.println("An IOException has occured :: "+ oX.getMessage());
                    }
          }
}
