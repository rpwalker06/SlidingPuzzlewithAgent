package feedBackTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Robert Walker
 */
public class puzzleSQLWriter {
    
    static Connection con = null;
    
    puzzleSQLWriter()
    {
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/puzzlerecord";
        String user = "puzzleuser";
        String password = "Howard";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {puzzleApplet.agentOutput.setText("Not even the intro " + ex.getMessage());
        } 
        
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {}
        }
    }
    
    public static String implode(List<String> fields, String delim) 
        {String out = "";
            for (int i=0; i<fields.size(); i++) {if(i!=0) { out += delim; }out += fields.get(i);}
         return out;}
    
    public static String delimitedPlaceholder(String placeholder,String delim,int count)
        {String out = "";
            for (int i=0; i<count; i++) {if(i!=0) { out += delim; }out += placeholder;}
         return out;}
    
    public void writeGameMove(MoveData dataMove)
    {
        PreparedStatement updateGameMove = null;
        
        String updateString = "";
        String insertFields = implode(MoveData.fieldList, ",");
        String placeholders = delimitedPlaceholder("?",",",MoveData.fieldList.size());
        
        updateString +=  "insert into gamemoves (";
        updateString +=  insertFields;
        updateString +=  ") values (";
        updateString +=  placeholders;
        updateString +=  ");";
        
        try 
        {
            updateGameMove = con.prepareStatement(updateString);
            
            fillMoveStrings(updateGameMove, dataMove);            
            
            updateGameMove.executeUpdate();
        }
            catch (SQLException e) {puzzleApplet.agentOutput.setText("Failed write: " + e.getMessage());
        }
        
        //now we implode the fields
    }
    
    private void fillMoveStrings(PreparedStatement p, MoveData dataMove)
    {
        int i = 0;
        try {
                p.setInt(++i, puzzleApplet.participantID );
                p.setInt(++i, dataMove.getPuzzlesequencenum() );
                p.setInt(++i, dataMove.getPuzzlesequencenum() );
                p.setString(++i, dataMove.getPuzzlestate() );
                p.setInt(++i, dataMove.getMoveno());
                p.setLong(++i, dataMove.getMovetime());
                p.setInt(++i, dataMove.getTileclicked());
                p.setInt(++i, dataMove.getAgentresponse());
                p.setBoolean(++i, dataMove.getPuzzlesolved());
            
        } catch (SQLException ex) {}
        
    }
}