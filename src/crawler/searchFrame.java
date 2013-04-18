/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import com.its.util.Stringer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

/**
 * 
 * @author duncan
 */
public class searchFrame extends javax.swing.JFrame {

	/**
	 * Creates new form searchFrame
	 */
	public searchFrame() {
		initComponents();
	}

	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        keywordInput = new javax.swing.JTextField();
        goButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        keywordInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keywordInputActionPerformed(evt);
            }
        });

        goButton.setText("go");
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });

        resultsPane.setEditable(false);
        resultsPane.setContentType("text/html"); // NOI18N
        resultsPane.setText("<html>\n  <head>\n\n  </head>\n  <body>\n    <p style=\"margin-top: 0\">\n    </p>\n  </body>\n</html>\n");
        resultsPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(resultsPane);
        //set the editor to allow the vewing of hypr links
        HTMLEditorKit kit = new HTMLEditorKit();
        resultsPane.setEditorKit(kit);
        //add a lister to hndle the clicks on a hyperlink
        HyperlinkListener l = new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED == e.getEventType()) {
                    try {
                        resultsPane.setPage(e.getURL());
                    } catch (IOException e1) {
                        System.out.print("can't read location");
                    }
                }

            }

        };
        resultsPane.addHyperlinkListener(l);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(keywordInput, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goButton)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keywordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(goButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void keywordInputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_keywordInputActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_keywordInputActionPerformed

	private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_goButtonActionPerformed
		
		String[] keywords = Stringer.split(" ", keywordInput.getText());
                KeywordDB searchDB = new KeywordDB();
                searchDB.MakeDBConnection();
                String[][] urlID_Results = searchDB.searchKeyword_GUI(keywords);
                String[][] urlSearchResults = searchDB.getUrlByID(urlID_Results);
                String finalResults = " ";
                
                for (int i = 0; i < urlSearchResults.length; i ++){
                    finalResults = "Link: " + urlSearchResults[i][0] + "\n";
                }
                                	
		resultsPane.setText(finalResults);

	}// GEN-LAST:event_goButtonActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(searchFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(searchFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(searchFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(searchFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new searchFrame().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField keywordInput;
    private javax.swing.JTextPane resultsPane;
    // End of variables declaration//GEN-END:variables
}
