package views;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import models.Cell;
import models.CellsList;

//====================VIEW PANEL====================
public class GamePanel extends JPanel{
    private int xOffset,yOffset;
    private int xInitial;
    private int yInitial;
    CellsList cells;
    Cell[] cellsList;
    boolean edit;
    JPopupMenu settingsMenu;

    public GamePanel(CellsList cells){
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 10));
        addMouseListener(new Mouse());
        addMouseMotionListener(new MouseMotion());
        
        //----------initializing data members----------
        settingsMenu=new PopUpMenu();
        edit=true;
        this.cells=cells;
        this.cellsList=cells.getList();
    }
    
//============================================================ 
    public void setEditing(boolean edit){this.edit=edit;}
    public boolean getEditing(){return this.edit;}
    
//============================================================  
    @Override 
    public void paintComponent(Graphics g){              
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        int k=0;       
        for(int i=0;i<cells.getNumRows();i++){
            for(int j=0;j<cells.getNumColumns();j++){
                cellsList[k].setPoint(j, i);
                Rectangle2D r=new Rectangle2D.Double(Cell.SIDE*j,Cell.SIDE*i,Cell.SIDE,Cell.SIDE);                        
                g2.setColor(Color.LIGHT_GRAY);
                g2.draw(r);
                if(cellsList[k].isAlive())
                    g2.setColor(Color.BLUE);
                else
                    g2.setColor(Color.WHITE);
                g2.fill(r);
                k++;
            }
        }
    }
    
//====================MOUSE MOTION LISTENER====================    
    private class MouseMotion extends MouseMotionAdapter{
        @Override 
        public void mouseDragged(MouseEvent e){
            int x=e.getX();
            int y=e.getY();
            xOffset=(x-xInitial)/(Cell.SIDE-2);
            yOffset=(y-yInitial)/(Cell.SIDE-2);
            GamePanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
        //----------dragging functionality----------
            int distance=xOffset+yOffset*cells.getNumColumns();
            for(int i=0;i<cells.getNumCells();i++){
                if(cellsList[i].isAlive()){
                    cellsList[i].setAlive(false);
                    if(i+distance<0){
                        distance=xOffset+(cells.getNumRows()+yOffset-1)*cells.getNumColumns();
                        cellsList[i+distance].setNextState(true);
                    }
                    else if(i+distance>=cells.getNumCells()){
                        cellsList[(cells.getNumColumns()-cells.getNumCells()+i)+distance].setNextState(true);
                    }
                    else
                        cellsList[i+distance].setNextState(true);
                }
            } 
            
        //----------update all the cells on the grid and repaint----------
            cells.update();
            xInitial=x;
            yInitial=y;
            repaint();
        }
    }
        
//====================MOUSE LISTENER====================    
    private class Mouse extends MouseAdapter{
        @Override 
        public void mousePressed(MouseEvent e){
            xInitial=e.getX();
            yInitial=e.getY();
        }    
        
    //============================================================    
        @Override 
        public void mouseClicked(MouseEvent e){
        //----------can only edit when it's allowed----------
            if(edit){
                int x=e.getX()/Cell.SIDE;int y=e.getY()/Cell.SIDE;
                for(Cell c:cellsList){
                    if(c.getPoint().getX()==x && c.getPoint().getY()==y)
                        c.setAlive(!c.isAlive());
                }
            }
            repaint();
        }    
        
    //============================================================
        @Override 
        public void mouseReleased(MouseEvent e){
            GamePanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if(e.getButton()==MouseEvent.BUTTON3){
                settingsMenu.show(e.getComponent(),e.getX(),e.getY());
            }
        }
    }
}