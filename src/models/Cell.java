/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.geom.Point2D;

/**
 *
 * @author hp
 */
//====================CELL====================
public class Cell{
    public static int SIDE=7;
    private int xCoord,yCoord;
    private boolean alive=false;
    private boolean nextState=false;
    
    public Cell(){}
    public void setPoint(int x,int y){
        xCoord=x;
        yCoord=y;
    }
    public Point2D getPoint(){return new Point2D.Double(xCoord,yCoord);}
    public void setAlive(boolean a){alive=a;}
    public boolean isAlive(){return alive;}
    public void setNextState(boolean state){nextState=state;}
    public boolean getNextState(){return nextState;}
    public void updateState(){
        alive=nextState;
        nextState=false;
    }  
}
