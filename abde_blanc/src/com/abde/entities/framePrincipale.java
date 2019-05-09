package com.abde.entities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import com.abde.entities.Fichier;

public class framePrincipale extends JFrame
{
	private static final long serialVersionUID = 1L;
	static JTextArea txtArea;
	JTextField txtRequest;
	JTextField txtLogin;
	JTextField txtPass;
	static JTextField txtTable;
	JComboBox comboDriver;
	static JComboBox comboTable;
	JTextField txtUrl;
	static JTextField txtDB;
	JButton boutSend;
	JButton boutCon;
	JButton boutADD;
	JButton boutDEL;
	JButton boutUPD;
	JMenuItem menuOuvrir;
	JMenuItem menuEnregistrer;
	JMenuItem menuConnect;
	JMenuItem menuDisConnect;
	JMenuBar toolBar;
	JMenuBar toolBar2;
	JPanel contentPane;
	private JSplitPane split;
	JScrollPane scroll2; 
	JScrollPane scroll;
	JTable tab;

	public framePrincipale(String Titre) throws SQLException // constructors interface graphique
	{
		super(Titre);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		this.setLocationRelativeTo(null); // centrer la fenetre

		this.setJMenuBar(createMenuBar());

		contentPane = (JPanel) this.getContentPane();

		contentPane.add(connectToolBar(), BorderLayout.NORTH);

		txtArea=new JTextArea();
		scroll = new JScrollPane(txtArea);
		contentPane.add(scroll, BorderLayout.WEST);

		contentPane.add(requestToolBar(),BorderLayout.SOUTH);

		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll, scroll2);
		contentPane.add(split, BorderLayout.CENTER);
	}

	public JMenuBar createMenuBar() // methode construction barre de menu
	{
		// BARRE DE MENU
		JMenuBar menuBar = new JMenuBar();
		//menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ELEMENT DU MENU
		JMenu menuFichier = new JMenu("Fichier");
		menuBar.add(menuFichier);

		JMenu menuBD = new JMenu("BD");
		menuBar.add(menuBD);


		// ITEM DE CHAQUE ELEMENTS

		menuOuvrir = new JMenuItem("Ouvrir");
		menuOuvrir.addActionListener(this::menuOuvrirListener);
		menuFichier.add(menuOuvrir);		

		JMenuItem menuFermer = new JMenuItem("Fermer");
		menuFermer.addActionListener(this::menuFermerListener);
		menuFichier.add(menuFermer);

		menuEnregistrer = new JMenuItem("Enregistrer");
		menuEnregistrer.addActionListener(this::menuEnregistrerListener);
		menuFichier.add(menuEnregistrer);

		JMenuItem menuEnregistrerSous = new JMenuItem("Enregistrer Sous");
		menuEnregistrerSous.addActionListener(this::menuEnregistrerListener);
		menuFichier.add(menuEnregistrerSous);

		menuConnect = new JMenuItem("Connect");
		menuConnect.addActionListener(e -> {
			try {
				menuConnectListener(e);
			} catch (ClassNotFoundException | IOException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		menuBD.add(menuConnect);		

		menuDisConnect = new JMenuItem("DisConnect");
		menuDisConnect.addActionListener(e -> {
			try {
				menuDisConnectListener(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		menuBD.add(menuDisConnect);		

		return menuBar;
	}

	private JMenuBar connectToolBar()
	{
		toolBar2= new JMenuBar();

		JLabel label1 = new JLabel("login : ");
		txtLogin = new JTextField("root");
		txtLogin.setPreferredSize(new Dimension(200,10));

		JLabel label2 = new JLabel("pass : ");
		txtPass = new JTextField("root");
		txtPass.setPreferredSize(new Dimension(200,10));

		JLabel label3 = new JLabel("driver : ");
		comboDriver = new JComboBox();
		comboDriver.setPreferredSize(new Dimension(200,10));

		String urlSQL ="com.mysql.cj.jdbc.Driver";
		String urlMARIADB ="com.mariadb.Driver";
		String urlORACLE ="com.oracle.Driver";

		comboDriver.addItem(urlSQL);
		comboDriver.addItem(urlMARIADB);
		comboDriver.addItem(urlORACLE);

		JLabel label4 = new JLabel("url : ");
		txtUrl = new JTextField("jdbc:mysql://localhost:3306/world?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC");
		txtUrl.setPreferredSize(new Dimension(200,10));

		boutCon = new JButton("connect");
		boutCon.addActionListener(arg0 -> {
			try {
				menuConnectListener(arg0);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		toolBar2.add(label1);
		toolBar2.add(txtLogin);
		toolBar2.add(label2);
		toolBar2.add(txtPass);
		toolBar2.add(label3);
		toolBar2.add(comboDriver);
		toolBar2.add(label4);
		toolBar2.add(txtUrl);
		toolBar2.add(boutCon);
		return toolBar2;
	}
	private JMenuBar requestToolBar() throws SQLException
	{

		toolBar = new JMenuBar();

		JLabel label1 = new JLabel("DB : ");
		txtDB = new JTextField();
		txtDB.setPreferredSize(new Dimension(200,10));

		JLabel label2 = new JLabel("table : ");
		comboTable= new JComboBox();
		comboTable.setPreferredSize(new Dimension(200,10));
		comboTable.addActionListener(this::comboTableActionListener);

		boutADD= new JButton("ADD");
		boutADD.addActionListener(arg0 -> {
			try {
				boutAddActionListener(arg0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		boutDEL= new JButton("DEL");
		boutDEL.addActionListener(e -> {
			try {
				boutDelActionListener(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		boutUPD= new JButton("UPD");
		boutUPD.addActionListener(e -> {
			try {
				boutUpdActionListener(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		toolBar.add(label1);
		toolBar.add(txtDB);
		toolBar.add(label2);
		toolBar.add(comboTable);
		toolBar.add(boutADD);
		toolBar.add(boutDEL);
		toolBar.add(boutUPD);

		return toolBar;
	}

	// GESTION EVENEMENT DES ITEMS

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

	private void menuOuvrirListener(ActionEvent e)
	{	
		if(e.getSource()==menuOuvrir)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(filter);

			int returnVal = chooser.showOpenDialog(toolBar);

			if(returnVal==JFileChooser.APPROVE_OPTION){
				String s = readFile(chooser.getSelectedFile().getPath());
				txtArea.setText(s);				
			}
		}
	}

	public String readFile(String file)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String ligne;
			StringBuffer fichier = new StringBuffer();

			while((ligne = reader.readLine()) != null){
				fichier.append(ligne);
				fichier.append("\n");			
			}
			reader.close();

			return fichier.toString();		
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	private void menuEnregistrerListener(ActionEvent e)
	{
		if(e.getSource()==menuEnregistrer){
			Fichier fichier = new Fichier("");
			fichier.setContenu(txtArea, toolBar);
		}
	}

	private void menuFermerListener(ActionEvent event)
	{	
		txtArea.setText(null);
	}

	private void menuConnectListener(ActionEvent event) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException
	{	

		MyConnection.seConnecter(txtLogin.getText(), txtPass.getText(), comboDriver.getSelectedItem().toString(), txtUrl.getText());
	}

	private void menuDisConnectListener(ActionEvent event) throws SQLException
	{	
		MyConnection.seDeconnecter();
	}

	private void boutAddActionListener(ActionEvent event) throws SQLException
	{
		MyConnection.Request("SELECT * FROM "+comboTable.getSelectedItem().toString());
	}

	private void boutUpdActionListener(ActionEvent event) throws SQLException
	{
		MyConnection.Request("SELECT * FROM "+comboTable.getSelectedItem().toString());
	}

	
	private void boutDelActionListener(ActionEvent event) throws SQLException
	{
		System.out.println(tab.getValueAt(tab.getSelectedRow(), tab.getSelectedColumn()).toString());
		String id=tab.getValueAt(tab.getSelectedRow(), tab.getSelectedColumn()).toString();
		String colonne = tab.getColumnName(tab.getSelectedColumn());
		System.out.println("Requete : DELETE FROM "+comboTable.getSelectedItem().toString()+" WHERE "+ colonne +" = "+id);
		try {
		MyConnection.Request("DELETE FROM "+comboTable.getSelectedItem().toString()+" WHERE "+ colonne +"="+id);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	private void comboTableActionListener(ActionEvent event)
	{
		initTable("SELECT * FROM "+comboTable.getSelectedItem().toString());
	}
	
	public void initTable(String query)
	{
		try {
			//On crée un statement

			Statement state = MyConnection.getConnect().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			//On exécute la requête
			ResultSet res = state.executeQuery(query);
			//Temps d'exécution

			//On récupère les meta afin de récupérer le nom des colonnes
			ResultSetMetaData meta = res.getMetaData();
			//On initialise un tableau d'Object pour les en-têtes du tableau
			Object[] column = new Object[meta.getColumnCount()];

			for(int i = 1 ; i <= meta.getColumnCount(); i++)
				column[i-1] = meta.getColumnName(i);

			//Petite manipulation pour obtenir le nombre de lignes
			res.last();
			int rowCount = res.getRow();
			Object[][] data = new Object[res.getRow()][meta.getColumnCount()];

			//On revient au départ
			res.beforeFirst();
			int j = 1;

			//On remplit le tableau d'Object[][]
			while(res.next())
			{
				for(int i = 1 ; i <= meta.getColumnCount(); i++)
					data[j-1][i-1] = res.getObject(i);

				j++;
			}
			//On ferme le tout                                     
			res.close();
			state.close();

			//On enlève le contenu de notre conteneur
			//contentPane.remove(txtArea);
			//On y ajoute un JTable

			contentPane.add(scroll2=new JScrollPane(tab=new JTable(data, column)), BorderLayout.EAST);
			
			//On force la mise à jour de l'affichage
			contentPane.revalidate();

		} catch (SQLException e) {
			//Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
			contentPane.removeAll();
			contentPane.add(new JScrollPane(new JTable()), BorderLayout.CENTER);
			contentPane.revalidate();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
}