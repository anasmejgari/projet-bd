import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Connectivity {

	public static void main(String[] args) throws SQLException {
			
			Scanner keyboard = new Scanner(System.in);
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("driver ok !");
			String url = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
			String user = "benallaa";
			String passwd = "anasbenalla";
			Connection connection = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connection ..");
			Statement stmt = connection.createStatement ();
			System.out.println("Connected successfully ! ");
			System.out.println("Bienvenue et Merci de choisir Klex.");
		//	connection.setTransactionIsolation(connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			/*
			ResultSet resultats = null;
			resultats = stmt.executeQuery("SELECT COLUMN_NAME FROM all_tab_columns\n" + 
					"WHERE TABLE_NAME= 'UTILISATEUR'");
			ResultSetMetaData rsmd = resultats.getMetaData();
			int nbCols = rsmd.getColumnCount();
			String entree = null;
		    String insertCommand = "INSERT INTO UTILISATEUR (";
		    String val = "(";
		    while (resultats.next()) {
		     	System.out.print("Entrez "+resultats.getString(1)+": ");
	   	        entree = keyboard.nextLine();
			    val += "'"+entree+"', ";
			    insertCommand += resultats.getString(1)+", ";
		    }
		    insertCommand = insertCommand.substring(0, insertCommand.length()-2)+")"+" VALUES "+val.substring(0, val.length()-2)+")";
		    stmt.executeQuery(insertCommand);
		    resultats.close();
		    */
			String email = null;
		try {
			email = Authentification.acceuil(stmt, keyboard);
			connection.commit();
			//	AddAlbum.ajouterAlbum(stmt, email, keyboard);
			boolean continuite = true;
			while (continuite) {
				String choix = Authentification.choixDepart(stmt, keyboard, email);
				if (choix == "Selection") {
					Selection.selection(stmt, email, keyboard);
				} else if (choix == "Ajout"){
					Ajout.ajout(stmt, keyboard, email);
				} else if (choix == "Se déconnecter") {
					System.out.println("Deconnexion ..");
				    System.out.println("Vous vous etes déconneté avec succes. On espere te voir très prochainement.");
				    continuite = false;   
				} else {
					Suppression.supprimer(stmt, email, keyboard);
				}
				connection.commit();
			}
		    connection.close();
		    stmt.close();
		    keyboard.close();
		    System.out.println("Succes");
	    } catch (SQLException e) {
	    	connection.rollback();
		    connection.close();
		    keyboard.close();
			e.printStackTrace();
			System.out.println("request failed");
		}
	}
}