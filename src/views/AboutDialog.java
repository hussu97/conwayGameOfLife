/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author hp
 */
//====================ABOUT DIALOG====================
public class AboutDialog extends JDialog{
    private JPanel mainPanel, titlePanel, descriptionPanel, rulesPanel;
    private JLabel title, descriptionTitle, description, rulesTitle, rules;

    public AboutDialog(JFrame frame){
        super(frame, "About");
        setSize(500, 550);
        setResizable(false);
        setModal(true);
        mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        titlePanel = new JPanel();
        title = new JLabel("<html><h1>Game Of Life</h1><html>", SwingConstants.CENTER);
        titlePanel.add(title);
        descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionTitle = new JLabel("<html><h2>Description</h2><html>");

        description = new JLabel("<html><p>The Game of Life is probably the most famous example of cellular automata, which are computer</p>\n"
        + "<p>simulations inspired by biological cells. It was invented by Cambridge&rsquo;s mathematician John Conway,</p>\n"
        + "<p>and popularized by Martin Gardner in a Scientific American article in 1970.</p><html>");
        description.setFont(new Font("Serif", Font.PLAIN, 18));
        descriptionPanel.add(descriptionTitle);
        descriptionPanel.add(description);

        rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));
        rulesTitle = new JLabel("<html><h2>Game Rules</h2><html>");

        rules = new JLabel("<html> <ol>\n"
            + "<li>A live cell with 1 or 0 neighbors will die (&ldquo;starvation&rdquo;)</li>\n"
            + "<li>A live cell with 4 or more neighbors will die (&ldquo;overpopulation&rdquo;)</li>\n"
            + "<li>A live cell with 2 or 3 neighbors will survive</li>\n"
            + "<li>A dead cell with exactly 3 neighbors will become live (&ldquo;reproduction&rdquo;)</li>\n"
            + "<li>In all other cases, a dead cell remains dead (either &ldquo;starvation&rdquo; or &ldquo;overpopulation&rdquo;)</li>\n"
            + "</ol><html>");
        rules.setFont(new Font("Serif", Font.PLAIN, 18));
        rulesPanel.add(rulesTitle);
        rulesPanel.add(rules);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(descriptionPanel, BorderLayout.CENTER);
        mainPanel.add(rulesPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
}
