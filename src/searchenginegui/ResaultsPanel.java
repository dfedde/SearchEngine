/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchenginegui;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;

/**
 *
 * @author duncan
 */
public class ResaultsPanel extends Panel{
    TextArea sentanceResults = new TextArea();
    
    public ResaultsPanel() {
        setLayout(new GridLayout(0, 1));
        add(sentanceResults);
        setVisible(true);
    }
    public void drawresults( String results ){
            sentanceResults.setText(results);
            System.out.print(results);
            
    }
    
    
    
    
}
