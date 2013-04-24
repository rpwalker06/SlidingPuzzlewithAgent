package feedBackTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import puzzleAgent.PuzzleCanvasObservable;

/**
 * Class to handle writing to the dB from the puzzle game
 * @author Robert Walker
 */
public class puzzleSQLWriter {
    
    static Connection con = null;
    
    public static String implode(List<String> fields, String delim) 
        {String out = "";
            for (int i=0; i<fields.size(); i++) {if(i!=0) { out += delim; }out += fields.get(i);}
         return out;}
    
    public static String delimitedPlaceholder(String placeholder,String delim,int count)
        {String out = "";
            for (int i=0; i<count; i++) {if(i!=0) { out += delim; }out += placeholder;}
         return out;}
    
    
    //NOTE: For the purpose of an applet, it assumes that the db is on the same server
    //that the applet is served from.  Network requests can not be cross domain at all.
    public void writeGameMove(MoveData dataMove, PuzzleCanvasObservable p) throws IOException
    {
        String writeURL;
        URL dbWriteRequest;
        HttpURLConnection conn;
        String getRequest = "";
        String phpFile = "puzzleappletread.php";
        
        for(int i=0;i<dataMove.fieldMap.size();i++)
        {
            if (i!=0){getRequest+="&";}
                getRequest+=MoveData.fieldList.get(i) + "=" + dataMove.fieldMap.get(MoveData.fieldList.get(i));
        }

       writeURL = "http://" + puzzleApplet.appletHostName +"/puzzle/";
       writeURL+=phpFile;
       writeURL+="?"+getRequest;
       
        try {
                 dbWriteRequest = new URL( writeURL );
                 conn = (HttpURLConnection) dbWriteRequest.openConnection();
                 //TODO:  Look into why this is needed to send url params
                 conn.getResponseCode();
            }
        catch (MalformedURLException ex) {System.out.println("Bad url");}
    }
}