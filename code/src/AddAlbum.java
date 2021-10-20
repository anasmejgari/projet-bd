import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddAlbum {
	
	public static void ajouterAlbum(Statement currentStatement, String email, Scanner keyboard) throws SQLException{
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		System.out.println("Merci de bien donner les informations nÃ©cessaires pour votre album.");
		System.out.println("Veuillez entrer le titre de l'album:");
		String titre = keyboard.nextLine();
		System.out.println("Veuillez entrer le nom de l'artiste de cet album:");
		String nomArtiste = keyboard.nextLine();
		ResultSet artistQuery = currentStatement.executeQuery("SELECT idArtiste FROM Artiste\n"
				+"WHERE nom ='"+nomArtiste+"'");
		int idArtiste = 0;
		if (!artistQuery.next()) {
			System.out.println("Artiste non enregistré sur notre base.\n"+""
					+ "Voulez-vous l'ajouter et continuer après l'ajout de l'album ? [Tapez 0 pour NON ou 1 pour OUI]");
			int choixContinuation = keyboard.nextInt();
			keyboard.nextLine();
			System.out.println();
			if (choixContinuation == 1) {
				AddArtiste.ajouterArtiste(currentStatement, keyboard);
				ResultSet idArtisteReq = currentStatement.executeQuery("SELECT max(idArtiste) FROM Artiste");
				idArtisteReq.next();
				idArtiste = idArtisteReq.getInt(1);
				System.out.println("Artiste ajouté avec succès. On continue l'insertion de l'abum.");
			} else {
				System.out.println("Impossible de continuer l'ajout de l'album sans l'ajout de l'artiste.");
				System.out.println("Termination de l'execution ...");
				System.exit(1);
			}
		} else {
			idArtiste = artistQuery.getInt(1);
		}
		System.out.println("Veuillez Entrez la date de sortie de l'album [forme jj/MM/yyyy]: ");
	    String date = keyboard.nextLine();
	    String[] months = {"JAN", "FEB", "MAR", "APR", "MAI", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	    date = date.substring(0, 2)+"-"+months[Integer.parseInt(date.substring(3, 5))-1]+"-"+date.substring(6);
		System.out.println("Veuillez Entrez l'url de l'album: ");
		String url = keyboard.nextLine();
		String command = "INSERT INTO Album (Titre, idArtiste, dateSortie, urlAlbum)\n"
				+"Values ('"+titre+"', '"+idArtiste+"', '"+date+"', '"+url+"')";
		currentStatement.executeQuery(command);
		
		ResultSet idAlbumReq = currentStatement.executeQuery("SELECT max(idAlbum) FROM Album");
		idAlbumReq.next();
		int idAlbum = idAlbumReq.getInt(1);
		
		System.out.print("Quel est le nombre de catégories pour lesquelles cet album appertient ?: ");
		int nbrCat = keyboard.nextInt();
		keyboard.nextLine();
		String Categorie = null;
		for (int i=1; i<=nbrCat; i++) {
			System.out.println("Veuillez choisir la catégorie n°"+i+" [une fenetre s'affiche].");
			ArrayList<String> catMusique = donneesDefault.getCatMusique();
			Liste c = new Liste ("Choix de la catégorie n°"+i, catMusique);
		    String choice = c.getChoice();
			System.out.println("Vous avez choisi "+choice+" comme catégorie n°"+i+" de l'album.");
			currentStatement.executeQuery("INSERT INTO ASSOCALBUMCAT (idALbum, Categorie)\n"
					+"VALUES ('"+idAlbum+"', '"+choice+"')");
		}
		
		System.out.println("Quel est le nombre de pistes dans cet album ? : ");
		int nombrePistes = keyboard.nextInt();
		keyboard.nextLine();
		System.out.println();
		for (int numPiste=1; numPiste<= nombrePistes; numPiste++) {
			System.out.println("Piste n°"+numPiste);
			AddPiste.ajoutPiste(currentStatement, idAlbum, numPiste, keyboard, email);
			System.out.println();
		}
		
		System.out.println("Album ajouté avec succès.");
	}

}