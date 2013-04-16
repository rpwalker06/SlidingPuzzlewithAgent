package feedBackTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert Walker
 */
public class MoveData
{
    public static List<String> fieldList = new ArrayList<String>();
    public List<String> entryData = new ArrayList<String>();
    private puzzleSQLWriter writer = new puzzleSQLWriter();
    
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
        fieldList.add("puzzlesequencenum");
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
    
    public int getPuzzlesequencenum() {
        return puzzlesequencenum;
    }

    public void setPuzzlesequencenum(int puzzlesequencenum) {
        this.puzzlesequencenum = puzzlesequencenum;
    }
    
    public void writeCurrentData()
        {writer.writeGameMove(this);}
    
    public void dummyData()
        {for (int i=0;i<fieldList.size();i++)   entryData.add("0.0");}
}