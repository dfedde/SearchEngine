/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchenginegui;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import searchengine.SearchKeyWords;
import searchengine.SearchLinks;
/**
 *
 * @author duncan
 */
public class ActionHandler implements ActionListener, WindowListener{
    private TextField location;
    private TextField keywords;
    private ResaultsPanel  resaultsPanel;
    
    //constructors
    public ActionHandler(TextField location, TextField keywords, ResaultsPanel resaultsPanel) {
        this.location       = location;
        this.keywords       = keywords;
        this.resaultsPanel  = resaultsPanel;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.print(ae.getActionCommand());
        String keyWordsInput = keywords.getText();
        String locationInput = location.getText();
        System.out.print(locationInput);
        System.out.print(keyWordsInput);
        
        switch(ae.getActionCommand()){
            case "go":
                resaultsPanel.drawresults(getResaults(locationInput, keyWordsInput));
                break;
            case "exit":
                System.exit(1);
                break;
            
        }
    }

    @Override
    public void windowOpened(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowClosing(WindowEvent we) {
            System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowIconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowActivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public String getResaults(String location,String keywords){
       String results = "";
       //make the objects
       SearchLinks linksearcher = new SearchLinks(location);
       SearchKeyWords pageSearcher = new SearchKeyWords(location);
       //set the keywords to search for
       pageSearcher.setKeywords(keywords);
       linksearcher.setKeywords(keywords);
       //search the pages
       results += pageSearcher.searchTextSentences();
       results += linksearcher.findLinks();
       return results;
       
    }
    
}
