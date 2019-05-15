/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JLabel;
import models.Cell;
import models.CellsList;
import views.GamePanel;

/**
 *
 * @author hp
 */
public class GameLogic {
    private CellsList cells;
    private Cell[] cellsList;
    private JLabel generationsLabel;
    private GamePanel gamePanel;
    private int generations=0;
    public GameLogic(CellsList cells,Cell[] cellsList,JLabel generationsLabel, GamePanel gamePanel){
        this.cells = cells;
        this.cellsList = cellsList;
        this.generationsLabel = generationsLabel;
        this.gamePanel = gamePanel;
    }
    public void resetGenerations(){generations=0;}
    public void run(){
        int colnum=0;
        for(int i=0;i<cells.getNumCells();i++){
            boolean l=false,r=false,u=false,d=false;
            int liveNeighbours=0;
        //----------left boundary----------
            if(i==cells.getNumColumns()*colnum){
                l=true;
                if(cellsList[i+cells.getNumColumns()-1].isAlive())
                    liveNeighbours++;
                if(i-1>0&&cellsList[i-1].isAlive())
                    liveNeighbours++;
                if(i+cells.getNumColumns()<cells.getNumCells()&&cellsList[i+(cells.getNumColumns()*2)-1].isAlive())
                    liveNeighbours++;
                colnum++;
            }
        //----------right boundary----------
            if(i+1==cells.getNumColumns()*colnum){
                r=true;
                if(cellsList[i-cells.getNumColumns()+1].isAlive())
                    liveNeighbours++;
                if(i+1<cells.getNumCells()&&cellsList[i+1].isAlive())
                    liveNeighbours++;
                if(i-cells.getNumColumns()>0&&cellsList[i-(cells.getNumColumns()*2)+1].isAlive())
                    liveNeighbours++;
                
            }
        //----------upper boundary----------
            if(i-cells.getNumColumns()<0){
                u=true;
                if(cellsList[cells.getNumCells()-(cells.getNumColumns()-i)].isAlive())
                    liveNeighbours++;
                if(i>0&&cellsList[cells.getNumCells()-(cells.getNumColumns()-i)-1].isAlive())
                    liveNeighbours++;
                if(i+1<cells.getNumColumns()&&cellsList[cells.getNumCells()-(cells.getNumColumns()-i)+1].isAlive())
                    liveNeighbours++;
            }
        //----------down boundary----------
            if(i+cells.getNumColumns()>cells.getNumCells()-1){
                d=true;
                if(cellsList[cells.getNumColumns()-(cells.getNumCells()-i)].isAlive())
                    liveNeighbours++;
                if(i+1<cells.getNumCells()&&cellsList[cells.getNumColumns()-(cells.getNumCells()-i)+1].isAlive())
                    liveNeighbours++;
                if(i>cells.getNumCells()-cells.getNumColumns()&&cellsList[cells.getNumColumns()-(cells.getNumCells()-i)-1].isAlive())
                    liveNeighbours++;
            }
        //----------checking for each of the 8 neighbours----------
            if(!l&&cellsList[i-1].isAlive())
                liveNeighbours++;
            if(!r&&cellsList[i+1].isAlive())
                liveNeighbours++;
            if(!u&&cellsList[i-cells.getNumColumns()].isAlive())
                liveNeighbours++;
            if(!d&&cellsList[i+cells.getNumColumns()].isAlive())
                liveNeighbours++;
            if(!l&&!u&&cellsList[i-cells.getNumColumns()-1].isAlive())
                liveNeighbours++;
            if(!r&&!u&&cellsList[i-cells.getNumColumns()+1].isAlive())
                liveNeighbours++;
            if(!l&&!d&&cellsList[i+cells.getNumColumns()-1].isAlive())
                liveNeighbours++;
            if(!r&&!d&&cellsList[i+cells.getNumColumns()+1].isAlive())
                liveNeighbours++;
            
        //----------setting the cells' next state----------
            if(cellsList[i].isAlive() && (liveNeighbours == 2 || liveNeighbours == 3))
                cellsList[i].setNextState(true);
            else if(!cellsList[i].isAlive() && (liveNeighbours == 3 ))
                cellsList[i].setNextState(true);
        }
    
    //----------updating the label----------
        generationsLabel.setText("Generations: "+ (++generations));
        cells.update();
        gamePanel.repaint();
    }  
}
