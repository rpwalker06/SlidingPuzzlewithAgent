package puzzleAgent;

import java.applet.AudioClip;
import java.util.HashMap;
import javax.swing.ImageIcon;


public class textAgentPanel extends agentPanel {
    
    ImageIcon neutralFace;
    ImageIcon correctFace;
    ImageIcon incorrectFace;
    
    public textAgentPanel() {
        initComponents();
        setTextPanel(lbl_AgentFeedback);
        this.setVisible(true);
    }
    
    public textAgentPanel(HashMap<String, AudioClip> words) {
        super(words);
        initComponents();
        setTextPanel(lbl_AgentFeedback);
        this.setVisible(true);
    }
    
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_AgentFeedback = new javax.swing.JLabel();

        lbl_AgentFeedback.setBackground(new java.awt.Color(204, 255, 102));
        lbl_AgentFeedback.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_AgentFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_AgentFeedback, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_AgentFeedback;
    // End of variables declaration//GEN-END:variables
}