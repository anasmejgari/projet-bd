import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class addFilmFiles {
	
	
	public static void ajoutFichiersFilm (Statement currentStatement, String email, Scanner keyboard) throws SQLException {
		
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		System.out.println("Merci de bien donner les informations nécessaires pour votre film.\n");
		System.out.println("Veuillez entrer le nom du film:");
		String nomFilm = keyboard.nextLine();
		System.out.println("Veuillez Entrez l'année de sortie du film: ");
		String anneeDeSortie = keyboard.nextLine();
		
		ResultSet requeteFilm = currentStatement.executeQuery("SELECT * FROM FILM \n"
				+ "WHERE Titre='"+nomFilm+"' and anneeSortie = '"+anneeDeSortie+"'");
		
		boolean exist = true;
		
		if(!requeteFilm.next()) {
			exist = false;
			System.out.print("Veuillez entrer un résumé du film (sans dépasser 255 caractère et sans retour à la ligne): ");
			String resume = keyboard.nextLine();
			System.out.print("Veuillez entrer l'age minimum pour regarder ce film: ");
			String ageMin = keyboard.nextLine();
			System.out.print("Veuillez entrer un lien de l'affiche ce film: ");
			String urlAffiche = keyboard.nextLine();
			currentStatement.executeQuery("INSERT INTO Film (Titre, anneeSortie, Resume, ageMin, URLaffiche)\n"
					+"VALUES ('"+nomFilm+"', '"+anneeDeSortie+"', '"+resume+"', '"+ageMin+"', '"+urlAffiche+"')");
			System.out.print("Quel est le nombre de catégories pour lesquelles ce film appertient ?: ");
			int nbrCat = keyboard.nextInt();
			keyboard.nextLine();
			String Categorie = null;
			for (int i=1; i<=nbrCat; i++) {
				System.out.println("Veuillez choisir la catégorie n°"+i+" [une fenetre s'affiche].");
				ArrayList<String> catFilm = donneesDefault.getCatFilm();
				Liste c = new Liste ("Choix de la catégorie n°"+i, catFilm);
			    String choice = c.getChoice();
				System.out.println("Vous avez choisi "+choice+" comme catégorie n°"+i+" du film.");
				currentStatement.executeQuery("INSERT INTO ASSOCFILMCAT (Titre, anneeSortie, Categorie)\n"
						+"VALUES ('"+nomFilm+"', '"+anneeDeSortie+"', '"+choice+"')");
			}
		}
		int choixExist = 0;
		System.out.println();
		if (exist) {
			System.out.print("Ce film existe déjà sur notre base. Voulez vous ajouter un nouveau fichier (Audio-Video) pour ce film [0 pour NON ou 1 pour OUI]:");
			choixExist = keyboard.nextInt();
			keyboard.nextLine();
		}
		if (((choixExist == 1) & exist) | !exist) {
			System.out.println("Création du fichier Audio-Video.");
			System.out.println("Combien de Ficher (Audio-Video) voulez-vous créer pour ce film ?: ");
			int nombreFluxVideo = keyboard.nextInt();
			keyboard.nextLine();
			for (int i=0; i<nombreFluxVideo; i++) {
				System.out.println("Pour le ficher Video-Audio Numero "+(i+1));
				AddFile.ajoutFichier(currentStatement, keyboard, 1, email);
				assocFilmFichier(currentStatement, nomFilm, anneeDeSortie);
			}
		}
		System.out.print("Voulez vous ajouter des sous-titres? [Tapez 0 pour NON ou 1 pour OUI]: ");
		int choixSubtitles = keyboard.nextInt();
		keyboard.nextLine();	
		if (choixSubtitles ==1) {
			System.out.print("Quel nombre de sous-titres voulez-vous proposez pour ce film: ");
			int nombreFluxText = keyboard.nextInt();
			for (int i=0; i<nombreFluxText; i++) {
				System.out.println("Pour le flux texte Numero "+(i+1));
				AddFile.ajoutFichier(currentStatement, keyboard, 3, email);
				assocFilmFichier(currentStatement, nomFilm, anneeDeSortie);
			}
		}
		addArtistsFilm(currentStatement, email, keyboard, nomFilm, anneeDeSortie);
		System.out.println("Film ajouté avec Succes.");
	}
	
	public static void assocFilmFichier(Statement currentStatement, String nomFilm, String anneeSortie) throws SQLException {
		ResultSet fichier = currentStatement.executeQuery("SELECT max(idFichier) FROM Fichier");
		fichier.next();
		int idFichier = fichier.getInt(1);
		currentStatement.executeQuery("INSERT INTO FICHIERFILM(Titre, anneeSortie, idFichier) VALUES ('"+nomFilm+"', '"+anneeSortie+"', '"+idFichier+"')");
	}
	
	public static void addArtistsFilm(Statement currentStatement, String email, Scanner keyboard, String film, String anneeDeSortie) throws SQLException {
		
		System.out.println("Combien d'artistes voulez vous associer à ce film ?");
		int nbrActeurs = keyboard.nextInt();
		keyboard.nextLine();
		for (int i=1; i<= nbrActeurs; i++) {
			AddArtiste.ajouterArtiste(currentStatement, keyboard);
			ResultSet idArtisteReq = currentStatement.executeQuery("SELECT max(idArtiste) FROM Artiste");
			idArtisteReq.next();
			int idArtiste = idArtisteReq.getInt(1);
		    System.out.println("Veuillez entrer le nom du personnage joué par cet artiste dans le film:");
		    String nomPersonnage = keyboard.nextLine();
		    currentStatement.executeQuery("INSERT INTO aPourRole (idArtiste, Titre, anneeSortie, nomDuPersonnage)\n"+
		    		"Values ('"+idArtiste+"', '"+film+"', '"+anneeDeSortie+"', '"+nomPersonnage+"')");
		}
		
	}
	
}
