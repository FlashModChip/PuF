package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import settings.Settings;


/**
 * Class for Cloud save client access
 * @author Roman
 *
 */
public class Client {    

	
    /**
     * @param state
     * @throws IOException
     */
    public void sendCloudSave(SaveState state) throws IOException {

        Socket socket = new java.net.Socket(Settings.SERVER, Settings.PORT); 
        
        String message = convertGameStateToXML(state);        
        
        writeSave(socket, message);
       
    }    
    
    
    /**
     * @return
     * @throws IOException
     */
    public SaveState  readCloudSave() throws IOException {

        Socket socket = new java.net.Socket(Settings.SERVER, Settings.PORT); 
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        
        char[] buffer = new char[2000];

        int countSigns = bufferedReader.read(buffer, 0, 2000);
       
        
        String message = new String(buffer, 0, countSigns);
        

        return convertXMLToGameState(message);
      
    }    
    
    
    /**
     * @param socket
     * @param message
     * @throws IOException
     */
    void writeSave(Socket socket, String message) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.print(message);
        printWriter.flush();

    }

    
    public String convertGameStateToXML(SaveState state) {
        try {
            // Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(SaveState.class);

            // Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Print XML String to Console
            StringWriter sw = new StringWriter();

            // Write XML to StringWriter
            jaxbMarshaller.marshal(state, sw);

            // Verify XML Content
            String xmlContent = sw.toString();

            // DEBUG
            System.out.println(xmlContent);

            return xmlContent;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
		return null;
    }
    

	
	  /**
	 * @param xml
	 * @return
	 */
	public static SaveState convertXMLToGameState(String xml) {
	  
//	  try {
//	  
//	  JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);
//	  
//	  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); Question
//	  que = (Question) jaxbUnmarshaller.unmarshal(file);
//	  
//	  System.out.println(que.getId() + " " + que.getQuestionname());
//	  System.out.println("Answers:"); List<Answer> list = que.getAnswers(); for
//	  (Answer ans : list) System.out.println(ans.getId() + " " +
//	  ans.getAnswername() + "  " + ans.getPostedby());
//	  
//	  } catch (JAXBException e) { e.printStackTrace(); }
		  
		  return null;
	  
	  }
	 

}




