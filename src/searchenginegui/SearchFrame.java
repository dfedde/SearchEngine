/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchenginegui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionListener;

/**
 * 
 * @author duncan
 */
public class SearchFrame extends Frame{// implements ActionListener{
    private ResaultsPanel resultsPanel = new ResaultsPanel();
    private ActionListener actionHandler;
    //the construtor 
    public SearchFrame(){
        //set up the window
        setTitle("webpage search");
        setLayout(new BorderLayout(3, 3));
       
        //make the button and textfeild
        Panel inputPannel = new Panel(new FlowLayout(1, 3, 3));
        
        //make the buttons
        Button go = new Button("go");
        Button exit = new Button("exit");
        
        //make the text buttons
        Label locationLable = new Label("url:");
        Label keywordsLable = new Label("keywords:");
        TextField keywords = new TextField("",20);
        TextField location = new TextField("", 20); 
        
        //make the handaler
        actionHandler = new ActionHandler(location,keywords,resultsPanel);
        
        //add the text feilds
        inputPannel.add(locationLable);
        inputPannel.add(location);
        inputPannel.add(keywordsLable);
        inputPannel.add(keywords);
        
        //add the buttons
        inputPannel.add(go);
        inputPannel.add(exit);
        
        //add the pannels to the frames
        add(inputPannel, BorderLayout.NORTH);
        add(resultsPanel, BorderLayout.SOUTH);
        
        // add the actin listner
        go.addActionListener(actionHandler);
        exit.addActionListener(actionHandler);
        //add(new ResaultsPanel(results));
        //set the size then set it visable
        pack();
        setVisible(true);
        
    }
    public static void main(String[] args){
        new SearchFrame();
    }

//    @Override
//    public void actionPerformed(ActionEvent ae) {
//        //resultsPanel.drawresults(linksearcher.findLinks());
//        System.out.print(ae.getActionCommand());
//        
//    }
    
}
