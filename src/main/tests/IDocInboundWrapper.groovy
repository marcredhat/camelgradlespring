/*
  Automatically Converted from Java Source 
  
  by java2groovy v0.0.1   Copyright Jeremy Rayner 2007
  
  !! NOT FIT FOR ANY PURPOSE !! 
  'java2groovy' cannot be used to convert one working program into another  */

package com.idoc.sender
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import com.sap.conn.idoc.IDocDocument
import com.sap.conn.idoc.IDocDocumentList
import com.sap.conn.idoc.IDocFactory
import com.sap.conn.idoc.IDocIllegalTypeException
import com.sap.conn.idoc.IDocMetaDataUnavailableException
import com.sap.conn.idoc.IDocParseException
import com.sap.conn.idoc.IDocSegment
import com.sap.conn.idoc.IDocSyntaxException
import com.sap.conn.idoc.IDocXMLProcessor
import com.sap.conn.idoc.jco.JCoIDoc
import com.sap.conn.jco.JCoException
import IDocInboundWrapperIDocInboundWrapper











def class IDocInboundWrapper extends BaseIDocInboundWrapper 
    { super()
  public IDocInboundWrapperIDocInboundWrapper() throws JCoException{
       super();
}





    IDocDocument getNewIDocDocument(String p_sIDocType) throws IDocMetaDataUnavailableException{
        super()
	IDocDocument l_oIDocDocument = IDOC_FACTORY.createIDocDocument(IDOC_REPOSITORY, p_sIDocType)
        return l_oIDocDocument
}














    boolean sendIDocAsIDocDocument(IDocDocument p_oIDocDocument) throws IDocSyntaxException{
        p_oIDocDocument.checkSyntax()
        JCoIDoc.send(p_oIDocDocument, IDocFactory.IDOC_VERSION_DEFAULT, JCO_DESTINATION, TID)
        JCO_DESTINATION.confirmTID(TID)
        return true
}



















    boolean sendIDocAsIDocXML(String p_sLocation, String p_sFileName) throws IOException{
        String l_sIDocXML = this.readIDocXML(p_sLocation + "\\" + p_sFileName)
        IDocXMLProcessor l_oIDocXMLProcessor = IDOC_FACTORY.getIDocXMLProcessor()
        IDocDocumentList l_oIDocDocumentList = l_oIDocXMLProcessor.parse(IDOC_REPOSITORY, l_sIDocXML)
        for (int i = 0 ; i < l_oIDocDocumentList.size() ; i++){
            IDocDocument l_oIDocDocument = l_oIDocDocumentList.get(i)
            l_oIDocDocument.checkSyntax()
}



        JCoIDoc.send(l_oIDocDocumentList, IDocFactory.IDOC_VERSION_DEFAULT, JCO_DESTINATION, TID)
        JCO_DESTINATION.confirmTID(TID)
        return true
}


















    private String readIDocXML(String p_sFullQualifiedFileName) throws IOException{
        String l_sIDocXML = null
        FileReader l_oFileReader
        l_oFileReader = new FileReader(p_sFullQualifiedFileName)
        BufferedReader l_oBufferedReader = new BufferedReader(l_oFileReader)
        StringBuffer l_oStringBuffer = new StringBuffer()
        String l_sLine
        while (l_sLine = l_oBufferedReader.readLine() != null) {
            l_oStringBuffer.append(l_sLine)
}


        l_sIDocXML = l_oStringBuffer.toString()
        l_oBufferedReader.close()
        l_oFileReader.close()
        return l_sIDocXML
}



















    static void main(String[] args) {



        try {
            IDocInboundWrapper l_oIDocInboundReceiptWrapper = new IDocInboundWrapper()
            IDocDocument l_oIDocDocument = l_oIDocInboundReceiptWrapper.getNewIDocDocument("WPUBON01")
            IDocSegment l_oIDocSegment = l_oIDocDocument.getRootSegment()
            l_oIDocSegment = l_oIDocSegment.addChild("E1WPB01")

            @SuppressWarnings("unused") 
            boolean l_oResponse = l_oIDocInboundReceiptWrapper.sendIDocAsIDocDocument(l_oIDocDocument)
}








         catch (IDocIllegalTypeException) {
            oX.printStackTrace()
            System.out.println("An IDocIllegalTypeException has occured :: " + oX.getMessage())
}


         catch (IDocSyntaxException) {
            oX.printStackTrace()
            System.out.println("An IDocSyntaxException has occured :: " + oX.getMessage())
}


         catch (IDocMetaDataUnavailableException) {
            oX.printStackTrace()
            System.out.println("An IDocMetaDataUnavailableException has occured :: " + oX.getMessage())
}


         catch (JCoException) {
            oX.printStackTrace()
            System.out.println("A JCoException has occured :: " + oX.getMessage())
}






        try {
            IDocInboundWrapper l_oIDocInboundReceiptWrapper = new IDocInboundWrapper()
            String l_sLocation = "C:\\Anish_Documents\\SAMPLE_IDOC_XML\\INBOUND"
            @SuppressWarnings("unused") 
            boolean l_oResponse = l_oIDocInboundReceiptWrapper.sendIDocAsIDocXML(l_sLocation, "TestSalesOrder.xml")
}





         catch (IDocSyntaxException) {
            oX.printStackTrace()
            System.out.println("An IDocSyntaxException has occured :: " + oX.getMessage())
}


         catch (IDocParseException) {
            oX.printStackTrace()
            System.out.println("An IDocParseException has occured :: " + oX.getMessage())
}


         catch (IDocMetaDataUnavailableException) {
            oX.printStackTrace()
            System.out.println("An IDocMetaDataUnavailableException has occured :: " + oX.getMessage())
}


         catch (IOException) {
            oX.printStackTrace()
            System.out.println("An IOException has occured :: " + oX.getMessage())
}


         catch (JCoException) {
            oX.printStackTrace()
            System.out.println("A JCoException has occured :: " + oX.getMessage())
}
}
}
