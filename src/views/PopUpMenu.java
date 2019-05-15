/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author hp
 */
//====================POPUP MENU====================
public class PopUpMenu extends JPopupMenu{
    private JMenuItem settingsItem;
    private JMenuItem aboutItem;
    private JDialog settingsDialog;
    private JDialog aboutDialog;

    public PopUpMenu(){
        settingsItem=new JMenuItem("Settings");
        aboutItem = new JMenuItem("About");
        settingsItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(settingsDialog==null)
                    settingsDialog=new SettingsDialog((JFrame)null);
                settingsDialog.setVisible(true);
            }  
        });
        aboutItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(aboutDialog==null)
                    aboutDialog=new AboutDialog((JFrame)null);
                aboutDialog.setVisible(true);
            }
        });
        add(settingsItem);
        add(aboutItem);
    }
}
