package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import settings.Settings;

public class Client {    

    void sendCloudSave(SaveState state) throws IOException {

        java.net.Socket socket = new java.net.Socket(Settings.SERVER, Settings.PORT); // verbindet
                                                                                                        // sich mit
                                                                                                        // Server
        String zuSendendeNachricht = convertGameStateToXML(state);

        schreibeNachricht(socket, zuSendendeNachricht);

        String empfangeneNachricht = leseNachricht(socket);

        System.out.println(empfangeneNachricht);
    }

    void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.print(nachricht);
        printWriter.flush();

    }

    String leseNachricht(java.net.Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        char[] buffer = new char[200];

        int anzahlZeichen = bufferedReader.read(buffer, 0, 200);
        // blockiert bis Nachricht empfangen
        String nachricht = new String(buffer, 0, anzahlZeichen);

        return nachricht;
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

	/*
	 * public static convertXMLToGameState(String xml) {
	 * 
	 * try {
	 * 
	 * JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);
	 * 
	 * Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); Question
	 * que = (Question) jaxbUnmarshaller.unmarshal(file);
	 * 
	 * System.out.println(que.getId() + " " + que.getQuestionname());
	 * System.out.println("Answers:"); List<Answer> list = que.getAnswers(); for
	 * (Answer ans : list) System.out.println(ans.getId() + " " +
	 * ans.getAnswername() + "  " + ans.getPostedby());
	 * 
	 * } catch (JAXBException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}




