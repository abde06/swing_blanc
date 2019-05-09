package com.abde.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

public class Fichier
{
	String nom;

	public Fichier(String nom) 
	{
		super();
		this.nom = nom;
	}

	public Vector getlines()
	{
		File file= new File(nom);
		Vector <String> vect = new Vector();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String ligne;
			while((ligne=bufferedReader.readLine())!=null)
			{
				vect.addElement(ligne);

				System.out.println(vect.size());
			}
			System.out.println(vect);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vect;
	}

	FileFilter filter = new FileFilter()
	{ 
		@Override
		public String getDescription() 
		{
			return "Fichiers textes";
		}
		@Override
		public boolean accept(File f) 
		{
			if(f.getName().endsWith("txt"))return true;
			return false;
		}
	};

	public void setContenu(JTextArea txtArea, JMenuBar toolBar)
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);

		int returnVal = chooser.showSaveDialog(toolBar);

		if(returnVal==JFileChooser.APPROVE_OPTION){
			String s = chooser.getSelectedFile().getPath();
			s = s.endsWith(".txt")?s:s+".txt";

			try {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(s)));

				writer.write(txtArea.getText());

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getContenu(JTextArea txtArea, JMenuBar toolBar)
	{

	}
}