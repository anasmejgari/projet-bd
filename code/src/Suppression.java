import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Suppression {

public static void supprimer(Statement currentStatement, String email, Scanner keyboard) throws SQLException {
		
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		ArrayList<String> choixSuppression = new ArrayList<String>();
		choixSuppression.add("Album");
		choixSuppression.add("Film");
		Liste c = new Liste ("Choix des donnees", choixSuppression);
	    String choice = c.getChoice();
	    if (choice.equals("Film")) {
	    	ResultSet fichiersFilm = currentStatement.executeQuery("Select DISTINCT Titre,anneeSortie FROM Fichier NATURAL JOIN FichierFilm\n"+
	    "WHERE Fichier.Mail = '"+email+"'");
	    	ArrayList<String> fichiers = new ArrayList<String>();
	    	while (fichiersFilm.next()) {
		    	fichiers.add(fichiersFilm.getString(1) + " ("+fichiersFilm.getString(2)+")");
		    }
	    	Liste listFichiers = new Liste ("Choix deq fichiers", fichiers);
		    String choiceFichier = listFichiers.getChoice();
		    currentStatement.executeQuery("Delete FROM Fichier WHERE IdFichier IN (Select idFichier FROM Fichier NATURAL JOIN FichierFilm\n" +
		    "WHERE FichierFilm.Titre  = '"+choiceFichier.substring(0, choiceFichier.length()-7)+"' and anneeSortie ='"+choiceFichier.substring(choiceFichier.length()-5, choiceFichier.length()-1)+"')");
		    
		    System.out.println("Vos fichiers liés au film "+choiceFichier.substring(0, choiceFichier.length()-7)+"ont été supprimé correctement.");
	    	
	    }
	    if (choice.equals("Album")){
	    	ResultSet albums = currentStatement.executeQuery("Select DISTINCT Titre,DATESORTIE FROM Album\n"+
	    			"Where IdAlbum In(Select DISTINCT idAlbum FROM Fichier NATURAL JOIN FichierPiste\n"+
	    		    "WHERE Fichier.Mail = '"+email+"')");
	    	
	    	ArrayList<String> mesAlbums = new ArrayList<String>();
	    	while(albums.next()) {
	    		mesAlbums.add(albums.getString(1)+" ("+albums.getString(2).substring(0, 10)+")");
	    	}
	    	Liste listAlbums = new Liste ("Choix de l'album à supprimer", mesAlbums);
	    	String choiceAlbum = listAlbums.getChoice();
		    String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
		    String jour = choiceAlbum.substring(choiceAlbum.length()-3, choiceAlbum.length()-1);
		    String mois =  months[Integer.parseInt(choiceAlbum.substring(choiceAlbum.length()-6, choiceAlbum.length()-4))-1];
		    String annee = choiceAlbum.substring(choiceAlbum.length()-9, choiceAlbum.length()-7);
		    String date = jour+"-"+mois+"-"+annee;
		    ResultSet idAlbumReq = currentStatement.executeQuery("SELECT idAlbum FROM Album \n"+
	    			"WHERE Titre = '"+choiceAlbum.substring(0, choiceAlbum.length()-13)+"' and DATESORTIE = '"+date+"'");
	    	idAlbumReq.next();
	    	int idAlbum = idAlbumReq.getInt(1);
	    	ResultSet pistes = currentStatement.executeQuery("SELECT NumPiste, idFichier FROM Fichierpiste\n"+
	    			"WHERE idAlbum = '"+idAlbum+"'");
	    	ArrayList<Integer> ghd = new ArrayList<Integer>();
	    	while (pistes.next()) {
	    		ghd.add(pistes.getInt(1));
	    		ghd.add(pistes.getInt(2));
	    	}
	    	for (int i=0; i<ghd.size(); i += 2) {
	    		currentStatement.executeQuery("Delete FROM Fichier Where IdFichier = '"+ghd.get(i+1)+"'");
	    		currentStatement.executeQuery("Delete FROM PISTEMUSICALE WHERE idAlbum='"+idAlbum+"' and numPiste ='"+ghd.get(i)+"'");
	    	}
	    	currentStatement.executeQuery("Delete FROM Album WHERE idAlbum ='"+idAlbum+"'");
	    	System.out.println("Album supprimé avec succès.");
	    }
	    
       }
}