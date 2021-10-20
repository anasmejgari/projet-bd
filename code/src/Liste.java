import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Liste 
{
  static String choice = null;
  static boolean closed = false;
  public String title;
  public ArrayList<String> choices;
  JFrame cadre;
  public Liste(String title, ArrayList<String> choices) {
	  this.title = title;
	  this.choices = choices;
	  Dimension  uneDimension = new Dimension();
	  uneDimension.setSize(300, 25);
	  
    // Créer et ajouter des objets au comboBox
	  String[] listeChoix = new String[choices.size()];
	  for (int i=0; i<choices.size(); i++) {
		  listeChoix[i] = choices.get(i);
	  }
	  JComboBox<String> cb = new JComboBox<String>(listeChoix);
	  cb.setSelectedItem(null);
	  cb.setFont(new Font("TimesRoman", Font.PLAIN, 12));
	  cb.setPreferredSize(uneDimension);
	
	
    // Ajouter Listener au comboBox
    cb.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	String varName = (String)cb.getSelectedItem();
        	String value = cb.getSelectedItem().toString();
        	choice = value;
        }
    });
    JButton b = new JButton("Choisir");
    // créer un frame
    Dimension size = b.getPreferredSize();
    b.setBounds(450, 230, size.width, size.height);
    JFrame cadre = new javax.swing.JFrame(title);
	JPanel panneau = new JPanel();
	panneau.setPreferredSize(new Dimension(450, 150));
	JLabel l = new JLabel("Veuilleuz choisr:  "); 
	cadre.setContentPane(panneau);
	cadre.add(cb);
	panneau.add(b);
	panneau.setBackground(Color.blue);
	cadre.setBounds(400, 200, 450, 150);
	cadre.pack();
	cadre.setVisible(true);
	cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	cadre.addWindowListener(new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            closed = true;
        }
    });
	b.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        cadre.dispose();
	        closed = true;
	    }
	});
  }
  
  public String getChoice() {
	  choice = "x";
	  closed = false;
	  while (!closed) {
		  //System.out.print("\033[H\033[2J");
		  System.out.print("");
	  }
	  return choice;
  }
    
}

