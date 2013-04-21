package feedBackTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import puzzleAgent.PuzzleCanvasObservable;

/**
 *
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
    
    public void writeGameMove(MoveData dataMove, PuzzleCanvasObservable p) throws IOException
    {
        String writeURL;
        URL dbWriteRequest;
        HttpURLConnection conn;
        String getRequest = "";
        String phpFile = "puzzleAppletRead.php";
        
        for(int i=0;i<dataMove.fieldMap.size();i++)
        {
            if (i!=0){getRequest+="&";}
                getRequest+=MoveData.fieldList.get(i) + "=" + dataMove.fieldMap.get(MoveData.fieldList.get(i));
        }

       writeURL = "http://" + puzzleApplet.appletHostName +"/PuzzleExperiment/";
       writeURL+=phpFile;
       writeURL+="?"+getRequest;
       
        try {
                 dbWriteRequest = new URL( writeURL );
                 conn = (HttpURLConnection) dbWriteRequest.openConnection();
                 conn.getResponseCode();
            }
        catch (MalformedURLException ex) {System.out.println("Bad url");}
    }
}