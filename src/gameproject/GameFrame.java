/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameproject;

import controllers.OptionPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import models.CellsList;
import views.GamePanel;

/**
 *
 * @author hp
 */
//====================FRAME CLASS====================
public class GameFrame extends JFrame{
    public GameFrame(){
        setTitle("Game of Life");
        setLocationByPlatform(true);
    //creating the model view and controller
        CellsList cells=new CellsList();
        GamePanel gamePanel=new GamePanel(cells);
        OptionPanel optionPanel=new OptionPanel(cells,gamePanel);
    //adding the view and controller to the frame
        add(gamePanel,BorderLayout.CENTER);
        add(optionPanel,BorderLayout.SOUTH);
    }
}
