/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author hp
 */
//====================MODEL====================
public class CellsList{
    private final Cell[] list;
    private final Dimension dimension;
    private final int numCells;
    private final int minCellSide;
    private final int rows;
    private final int columns;
    
    public CellsList(){
        dimension=Toolkit.getDefaultToolkit().getScreenSize();
        minCellSide=5;
        rows=dimension.height/minCellSide;
        columns=dimension.width/minCellSide;
        numCells=(dimension.height/minCellSide)*(dimension.width/minCellSide);
        list=new Cell[numCells];       
        for(int i=0;i<numCells;i++){
            list[i]=new Cell();
        }
    }
    public int getNumCells(){return numCells;}
    public int getNumRows(){return rows;}
    public int getNumColumns(){return columns;}
    public Cell[] getList(){return list;}
    public void update(){
        for(Cell c:list)
            c.updateState();
    }
    public void clear(){
        for(Cell c:list)
            c.setAlive(false);
    }
}
