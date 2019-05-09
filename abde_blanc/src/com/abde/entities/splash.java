package com.abde.entities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class splash extends JFrame
{
	private Thread t;
	  private JProgressBar bar;
	  private JButton launch ;
	  private int val;
	  
	  
	  public splash(){  
		  
	    this.setSize(300, 80);
	    this.setTitle("Chargement");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);      
	    t = new Thread(new Traitement());
	    bar  = new JProgressBar();
	    bar.setMaximum(100);
	    bar.setMinimum(0);
	    bar.setStringPainted(true);
	    this.getContentPane().add(bar, BorderLayout.CENTER);
	    
	    launch = new JButton("Lancer");
	    launch.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent event){
	    	
	    	 
	        framePrincipale f1 = null;
			try {
				f1 = new framePrincipale("bdsplash");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        f1.setVisible(true);
	        f1.getContentPane().revalidate();
	        f1.getContentPane().repaint();
	      }
	        
	    });      
	    this.getContentPane().add(launch, BorderLayout.SOUTH);      
	    t.start();      
	    this.setVisible(true);    
	   
	  }
	  
	  
	  class Traitement implements Runnable{   
	    public void run(){
	      launch.setEnabled(false);
	      for(val = 0; val <= 100; val++){
	        bar.setValue(val);
	        try {
	          t.sleep(10);
	        } catch (InterruptedException e) {
	        e.printStackTrace();
	        }
	      }
	      launch.setEnabled(true);
	    } 
	  }   
}
