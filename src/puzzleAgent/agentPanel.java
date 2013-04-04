package puzzleAgent;

import javax.swing.JLabel;

public class agentPanel extends javax.swing.JPanel {

    public agentPanel() {
        initComponents();
        this.setVisible(true);
    }
    
    public JLabel getTextPanel()
    {return lbl_AgentFeedback;}
    
    public void sendMessageToUser(String message)
    {lbl_AgentFeedback.setText(message);}
    
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_AgentAvatar = new javax.swing.JLabel();
        lbl_AgentFeedback = new javax.swing.JLabel();

        lbl_AgentAvatar.setBackground(new java.awt.Color(102, 102, 255));
        lbl_AgentAvatar.setText("Insert Avatar");
        lbl_AgentAvatar.setOpaque(true);

        lbl_AgentFeedback.setBackground(new java.awt.Color(204, 255, 102));
        lbl_AgentFeedback.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_AgentAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_AgentFeedback, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_AgentFeedback, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(lbl_AgentAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_AgentAvatar;
    private javax.swing.JLabel lbl_AgentFeedback;
    // End of variables declaration//GEN-END:variables
}
