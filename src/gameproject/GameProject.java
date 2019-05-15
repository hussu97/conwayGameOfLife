package gameproject;

import controllers.OptionPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import models.Cell;
import models.CellsList;
import views.GamePanel;
/**
 *
 * @author hussain
 */
public class GameProject {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame=new GameFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
        });
    }
}

