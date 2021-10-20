import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Selection {

	public static void selection (Statement currentStatement, String email, Scanner keyboard) throws SQLException {
		
		ArrayList<String> choixSelection = new ArrayList<String>();
		choixSelection.add("Film");
		choixSelection.add("Piste musicale");
		Liste c = new Liste ("Choix des données", choixSelection);
	    String choice = c.getChoice();
	    if (choice.equals("Film")) {
	    	selectFilm(currentStatement, email, keyboard);
	    } else  if (choice.equals("Piste musicale")){
	    	selectPiste(currentStatement, email, keyboard);
	    }
	}
	
	public static ArrayList<String> selectionFilmCat(Statement currentStatement, String email, String cat) throws SQLException {
		ResultSet filmCat = currentStatement.executeQuery("SELECT Titre, anneeSortie\n" + 
				"FROM (SELECT * FROM AssocFilmCat\n" + 
				"Natural Join Film \n" + 
				"WHERE Categorie = '"+cat+"') \"C\", Utilisateur\n" + 
				"WHERE (Utilisateur.mail='"+email+"') and (Utilisateur.age> C.ageMin)" );
		ArrayList<String> listFilm = new ArrayList<String>();
		while(filmCat.next()) {
			listFilm.add(filmCat.getString(1)+ " ("+filmCat.getString(2)+")");
		}
		
		return listFilm;
	}
	
	public static void selectFilm(Statement currentStatement, String email, Scanner keyboard) throws SQLException {
		System.out.println("Veuillez choisir une catégorie.");
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
    	ArrayList<String> catFilm = donneesDefault.getCatFilm();
		Liste listCat = new Liste ("Choix de la catégorie de recherche", catFilm);
	    String choiceCat = listCat.getChoice();
	    
	    // Les films de cette catégorie;
	    ArrayList<String> selcFilm = selectionFilmCat(currentStatement, email, choiceCat);
	    Liste listFilm = new Liste ("Choix du film de la catégorie "+choiceCat, selcFilm);
	    String choiceFilm = listFilm.getChoice();
	    String nomFilm = choiceFilm.substring(0, choiceFilm.length()-7);
	    String anneeSortie = choiceFilm.substring(choiceFilm.length()-5, choiceFilm.length()-1);
	    	    
	    ArrayList<String> selcFichier = new ArrayList<String>();
	    ResultSet fichiersFilm = currentStatement.executeQuery("SELECT idFichier FROM FICHIERFILM\n"+
	    		"WHERE titre = '"+nomFilm+"' and anneeSortie ='"+anneeSortie+"'");		 
	    
	    ArrayList<Integer> ids = new ArrayList<Integer>();
	    while (fichiersFilm.next()) {
	    	ids.add(fichiersFilm.getInt(1));
	    }
	    System.out.println("Vous avez choisi "+choiceFilm+".");
	    
	    ArrayList<Integer> lesVideos = new ArrayList<Integer>();
	    ArrayList<Integer> lesTextes = new ArrayList<Integer>();

	    
	    for (Integer id: ids) {
	    	ResultSet reqVidTexte = currentStatement.executeQuery("SELECT idFichier FROM FluxTexte\n"+
	    			"JOIN Flux On Flux.NumFlux = FluxTexte.NumFluxTexte \n"+
	    			"Where idFichier = '"+id+"'");
	    	if (reqVidTexte.next()) {
	    		lesTextes.add(id);
	    	} else {
	    		lesVideos.add(id);
	    	}
	    }
	    ArrayList<String> choice = new ArrayList<String>();
	    for (Integer video:lesVideos) {
	    	String caracFlux = null;
	    	ResultSet reqVidTexte = currentStatement.executeQuery("SELECT NumFlux, Hauteur, Largeur FROM FluxVideo\n"+
	    			"JOIN Flux On Flux.NumFlux = FluxVideo.NumFluxVideo \n"+
	    			"Where idFichier = '"+video+"'");
	    	reqVidTexte.next();
	    	caracFlux = "("+reqVidTexte.getInt(2)+"x"+reqVidTexte.getInt(3)+")"+" en ";
	    	reqVidTexte = currentStatement.executeQuery("SELECT NumFlux, LangueA FROM FluxAudio\n"+
	    			"JOIN Flux On Flux.NumFlux = FluxAudio.NumFluxAudio \n"+
	    			"Where idFichier = '"+video+"'");
	    	reqVidTexte.next();
	    	caracFlux += reqVidTexte.getString(2);
	    	choice.add(caracFlux);
	    	for (Integer txt:lesTextes) {
	    		reqVidTexte = currentStatement.executeQuery("SELECT NumFlux, LangueTxt FROM FluxTexte\n"+
		    			"JOIN Flux On Flux.NumFlux = FluxTexte.NumFluxTexte \n"+
		    			"Where idFichier = '"+txt+"'");
	    		reqVidTexte.next();
	    		choice.add(caracFlux+" sous titré en "+reqVidTexte.getString(2));
		    }
	    }
	    Liste choice_b = new Liste("choix d'options", choice);
    	String c = choice_b.getChoice();
    	nomFilm = choiceFilm.substring(0, choiceFilm.length()-7);
	    anneeSortie = choiceFilm.substring(choiceFilm.length()-5, choiceFilm.length()-1);
	    ResultSet req = currentStatement.executeQuery("SELECT * FROM Film\n"+
	    		"WHERE titre = '"+nomFilm+"' and anneeSortie ='"+anneeSortie+"'");
	    req.next();
	    System.out.println("Vous avez choisi le film "+nomFilm+" ("+anneeSortie+"), résolution "+c+".");
    	System.out.println("Résumé du film: "+req.getString(3)+".");
    	System.out.println("Ce film est interdit au moins de "+req.getString(4)+" ans.");
	    
	}
	
	public static void selectPiste(Statement currentStatement, String email, Scanner keyboard) throws SQLException {
		int choice = 1;		
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		if (choice == 1)  {
			ArrayList<String> catMusique = donneesDefault.getCatMusique();
			Liste listCat = new Liste ("Choix de la catégorie de l'album", catMusique);
		    String choiceCat = listCat.getChoice();
		    ResultSet choiceAlbum = currentStatement.executeQuery("SELECT idAlbum, Titre, Nom FROM ASSOCALBUMCAT\n"+
		    		"NATURAL JOIN Album, Artiste\n"+
		    		"WHERE Categorie='"+choiceCat+"'");
		    System.out.println(1111111);
		    ArrayList<String> listAlbum = new ArrayList<String>();
		    while (choiceAlbum.next()) {
		    	listAlbum.add(choiceAlbum.getString(2));
		    }
		    Liste listeAlbum = new Liste("Choix d'album", listAlbum);
		    String choix = listeAlbum.getChoice();
		    choiceAlbum = currentStatement.executeQuery("SELECT idAlbum FROM Album\n"+
		    		"WHERE Titre='"+choix+"'");
		    choiceAlbum.next();
		    int idAlbum = choiceAlbum.getInt(1);
		    choiceAlbum = currentStatement.executeQuery("SELECT * FROM PisteMusicale\n"+
		    		"WHERE idAlbum='"+idAlbum+"'");
		    ArrayList<String> listPiste = new ArrayList<String>();
		    while(choiceAlbum.next()) {
		    	listPiste.add(choiceAlbum.getInt(2)+". "+choiceAlbum.getString(3));
		    }
		    Liste listePiste = new Liste("Choix de piste", listPiste);
		    String choicePiste = listePiste.getChoice();
		    ResultSet reqs = currentStatement.executeQuery("SELECT Nom FROM ARtiste\n"+
		    		"WHERE idArtiste IN (SELECT idArtiste FROM Album\n"+
		    							"WHERE idAlbum = '"+idAlbum+"')");
		    reqs.next();
		    System.out.println("Vous avez choisi d'écouter "+choicePiste.substring(3)+" de "+reqs.getString(1));
		}
	   
	}
	
}
