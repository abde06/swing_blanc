package com.abde.entities;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MyConnection 
{  
	public static Connection connect;
	public static Statement state=null;
	public static Properties props = new Properties();
	public static PreparedStatement preparedStatement =null;
	public static ResultSet r;

	public static void seConnecterProps() throws IOException, ClassNotFoundException, FileNotFoundException, SQLException
	{
		try(FileInputStream file = new FileInputStream("props.properties"))
		{
			props.load(file);
			Class.forName(props.getProperty("jdbc.driver.class"));
			System.out.println("Driver OK");
			framePrincipale.txtArea.setText("Driver OK");
			String username = props.getProperty("jdbc.user");
			String password = props.getProperty("jdbc.password");
			String url = props.getProperty("jdbc.url");
			connect = DriverManager.getConnection(url, username, password);
			System.out.println("Connexion ok");
			framePrincipale.txtArea.setText("Driver OK\nConnexion OK");
			String b =connect.getCatalog();
			framePrincipale.txtDB.setText(connect.getCatalog());

			framePrincipale.txtArea.setText(connect.getCatalog());
			state = connect.createStatement();
		}
	}

	public static void seConnecter(String username, String password, String driver, String url) throws SQLException, ClassNotFoundException
	{
		Class.forName(driver);
		System.out.println("Driver OK");
		framePrincipale.txtArea.setText("Driver OK");
		connect = DriverManager.getConnection(url, username, password);
		System.out.println("Connexion ok");
		framePrincipale.txtArea.setText("Driver OK\nConnexion OK");
		String b =connect.getCatalog();
		framePrincipale.txtDB.setText(b);

		DatabaseMetaData md = connect.getMetaData();
		ResultSet rs = md.getTables("world", null, "%", null);
		while(rs.next())
		{

			framePrincipale.comboTable.addItem(rs.getString("TABLE_NAME"));
			System.out.println(rs.getString("TABLE_NAME"));
		}

		state = connect.createStatement();
	}

	public static Connection getConnect() 
	{
		return connect;
	}

	public static void seDeconnecter() throws SQLException 
	{	
		connect.close();
		framePrincipale.txtArea.setText("Disconnect");

	}	

	public static void Request(String a) throws SQLException
	{
		connect.createStatement().executeUpdate(a);
	
	}
}