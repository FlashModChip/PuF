package server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Client.SaveState;
import entities.Entity;
import level.LevelData;


public class Server {
    
    private ServerSocket serverSocket; 

    private static final String xmlFilePath = "./???";

    public static void saveXMLtoFile(String save)
    {
        try
        {
            //
            // Example code
            //
            
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("root");
            document.appendChild(root);

            // leaf
            Element leaf = document.createElement("leaf");

            root.appendChild(leaf);

            // attribute
            leaf.setAttribute("id", "1");        

            // name element
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode("Bla"));
            leaf.appendChild(name);      

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging 

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }  
    }    
    
    
    /**
     * @param save
     */
    public static void savetoDatabase(String save)
    {  
    	try {
         serverData.saveToDatabase(File.createTempFile(ServerSetting.path, save));            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    //new thread
    void ServerThread() 
    {
        try 
        {
        serverSocket = new ServerSocket(8080);

        } catch (Exception e) 
        {           
          
        }       
    }

    //after thread ends
    public void finalize() {
        try 
        {
            serverSocket.close();

        } catch (Exception e) 
        {
            serverSocket = null;
        }
    } 
    public void run() {
        
        while (serverSocket != null) 
        {
            
            Socket socket = null;
            
            try 
            {
                socket = serverSocket.accept();
               
            } catch (Exception e) 
            {
               
            }
            //open input and output streams
            try {
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
                //DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                PrintStream printStream = new PrintStream(new BufferedOutputStream(socket.getOutputStream(), 1024), false);
                String inputLine = null, outputLine = "\n";
                String command = "Test";
				/*
				 * while (!command.startsWith("bye")) { StringTokenizer st1 = null; inputLine =
				 * inputStream.readLine().trim(); System.out.println("Got: " + inputLine); st1 =
				 * new StringTokenizer(inputLine, ":");
				 * 
				 * if (st1.hasMoreTokens()) { command = st1.nextToken(); if
				 * (command.startsWith("request")) { if (st1.hasMoreTokens() &&
				 * Long.parseLong(st1.nextToken()) < lastChange) //outputLine =
				 * scoreList.toDataString(); else outputLine = "none";
				 * printStream.println(outputLine); printStream.flush(); } if
				 * (command.startsWith("update")) { if (scoreList.tryScore(st1.nextToken()) !=
				 * null) } } }
				 */
                printStream.close();
                inputStream.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}