import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddPiste {
	
	public static void ajoutPiste(Statement currentStatement, int idAlbum, int numPiste, Scanner keyboard, String email) throws SQLException {
		
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		
		System.out.println("Entrez le nom de la piste: ");
		String nom = keyboard.nextLine();
		
		System.out.println("Quel est la durée [en min] ?");
		int duree = keyboard.nextInt();
		keyboard.nextLine();
		
		System.out.println();
		System.out.println("Ajout du fichier de la piste"+numPiste+"..");
		
		currentStatement.executeQuery("INSERT INTO PISTEMUSICALE (idAlbum, NumPiste, Titre, Duree)\n"
				+"VALUES ('"+idAlbum+"', '"+numPiste+"', '"+nom+"' , '"+duree+"')");
		
		AddFile.ajoutFichier(currentStatement, keyboard, 2, email);
		
		ResultSet idFichierReq = currentStatement.executeQuery("SELECT max(idFichier) FROM Fichier");
		idFichierReq.next();
		int idFichier = idFichierReq.getInt(1);
		currentStatement.executeQuery("INSERT INTO FichierPiste (idAlbum, NumPiste, IDFICHIER)\n"
				+"VALUES ('"+idAlbum+"', '"+numPiste+"' , '"+idFichier+"')");
		
		
		System.out.println("A combien de catégories appartient cette piste ?: ");
		int nbrCat = keyboard.nextInt();
		keyboard.nextLine();
		String Categorie = null;
		for (int i=1; i<=nbrCat; i++) {
			System.out.println("Veuillez choisir la catégorie n°"+i+" [une fenetre s'affiche].");
			ArrayList<String> catMusique = donneesDefault.getCatMusique();
			Liste c = new Liste ("Choix de la catégorie n°"+i, catMusique);
		    String choice = c.getChoice();
			System.out.println("Vous avez choisi "+choice+" comme catégorie n°"+i+" de cette piste.");
			currentStatement.executeQuery("INSERT INTO ASSOCPISTECAT (idAlbum, NumPiste, Categorie)\n"
					+"VALUES ('"+idAlbum+"', '"+numPiste+"', '"+choice+"')");
		
		}
		
		System.out.print("Voulez vous ajouter un fichier de paroles pour cette piste? [Tapez 0 pour NON ou 1 pour OUI]: ");
		int choixSubtitles = keyboard.nextInt();
		keyboard.nextLine();	
		if (choixSubtitles ==1) {
			AddFile.ajoutFichier(currentStatement, keyboard, 3, email);
			idFichierReq = currentStatement.executeQuery("SELECT max(idFichier) FROM Fichier");
			idFichierReq.next();
			idFichier = idFichierReq.getInt(1);
			currentStatement.executeQuery("INSERT INTO FichierPiste (idAlbum, NumPiste, IDFICHIER)\n"
					+"VALUES ('"+idAlbum+"', '"+numPiste+"' , '"+idFichier+"')");
		}
		System.out.println("Piste ajouté avec Succes.");
	
	}
}