package NerdHerdUtil;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Mr. Mallory
 */
public class Filer {

    private OutputStream theFile;
    private FileConnection fc;
    private String fileName;
    
    public Filer(String name){
        fileName = "file:///" + name + ".txt";
        connect();
    }
    
    private void connect(){
        try {
            fc = (FileConnection)Connector.open(fileName, Connector.WRITE);
            if(fc.exists()){
                fc.delete();
            }
            fc.create();
            theFile = Connector.openOutputStream(fileName);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void println(String line){
        byte bytes[] = (line + "\r\n").getBytes();
        try{
            theFile.write(bytes);
            theFile.flush();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void print(String line){
        byte bytes[] = (line).getBytes();
        try{
            theFile.write(bytes);
            theFile.flush();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void close(){
        try{
            theFile.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}