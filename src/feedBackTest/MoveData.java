package feedBackTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import puzzleAgent.PuzzleCanvasObservable;

/**
 *
 * @author Robert Walker
 */
public class MoveData
{
    public static List<String> fieldList = new ArrayList<String>();
    public List<String> entryData = new ArrayList<String>();
    private puzzleSQLWriter writer = new puzzleSQLWriter();
    public HashMap<String,String> fieldMap = new HashMap<String,String>();
    
    SimpleDateFormat sqlDateFormatter = new SimpleDateFormat("yyyyMMdd_HH:mm:ss:SS");
    private int puzzlesequencenum;
    private int sessionpuzzleno;

    private String puzzlestate;
    private int moveno=0;
    private long movetime;
    private int tileclicked;
    private int agentresponse;
    private boolean puzzlesolved;

    public MoveData()
    {
        fieldList.add("idparticipant");
        fieldList.add("sessionpuzzleno");
        fieldList.add("puzzlestate");
        fieldList.add("moveno");
        fieldList.add("movetime");
        fieldList.add("tileclicked");
        fieldList.add("agentresponse");
        fieldList.add("puzzlesolved");
    }
    
    public int getSessionpuzzleno() {
        return sessionpuzzleno;
    }

    public void setSessionpuzzleno(int sessionpuzzleno) {
        this.sessionpuzzleno = sessionpuzzleno;
    }
    
    public void setPuzzlestate(String puzzlestate) {
        this.puzzlestate = puzzlestate;
    }

    public void incrementMoveno() {
        this.moveno++;
    }

    public void setMovetime(long movetime) {
        this.movetime = movetime;
    }

    public void setTileclicked(int tileclicked) {
        this.tileclicked = tileclicked;
    }

    public void setAgentresponse(int agentresponse) {
        this.agentresponse = agentresponse;
    }

    public void setPuzzlesolved(boolean puzzlesolved) {
        this.puzzlesolved = puzzlesolved;
    }
    
    public String getPuzzlestate() {
        return puzzlestate;
    }

    public int getMoveno() {
        return moveno;
    }

    public long getMovetime() {
        return movetime;
    }

    public int getTileclicked() {
        return tileclicked;
    }

    public int getAgentresponse() {
        return agentresponse;
    }

    public boolean getPuzzlesolved() {
        return puzzlesolved;
    }
    
     public int getPuzzlesolvedInt() {
        return puzzlesolved ? 1:0;
    }
    
    public int getPuzzlesequencenum() {
        return puzzlesequencenum;
    }

    public void setPuzzlesequencenum(int puzzlesequencenum) {
        this.puzzlesequencenum = puzzlesequencenum;
    }
    
    public void writeCurrentData(PuzzleCanvasObservable p)
        {   //fillFieldMap();
            try{ writer.writeGameMove(this, p);} catch (IOException e) {System.out.println("Error on write move!" + e.getMessage());}}
    
    public void fillFieldMap()
    {
        int i=-1;
        
        fieldMap.put(fieldList.get(++i),puzzleApplet.participantID + "" );
        fieldMap.put(fieldList.get(++i), puzzleApplet.sessionPuzzleNo +"");
        try {fieldMap.put(fieldList.get(++i), URLEncoder.encode(this.puzzlestate,"UTF-8"));} 
        catch (UnsupportedEncodingException ex) {fieldMap.put(fieldList.get(++i), "Error");}
        fieldMap.put(fieldList.get(++i), this.moveno +"");
        fieldMap.put(fieldList.get(++i), this.movetime+"");
        fieldMap.put(fieldList.get(++i), this.tileclicked+"");
        fieldMap.put(fieldList.get(++i), this.agentresponse+"");
        fieldMap.put(fieldList.get(++i), this.getPuzzlesolvedInt()+"");
    }
    
    public void dummyData()
        {
            fieldMap.put("idparticipant", 78 + "" );
            fieldMap.put("sessionpuzzleno", 99 +"");
            fieldMap.put("puzzlesequencenum", 15 +"");
            fieldMap.put("puzzlestate", "TESTTEST");
            fieldMap.put("moveno", 8+"");
            fieldMap.put("movetime", 123456789+"");
            fieldMap.put("tileclicked", 8+"");
            fieldMap.put("agentresponse", 1+"");
            fieldMap.put("puzzlesolved", 0+"");
        }
}