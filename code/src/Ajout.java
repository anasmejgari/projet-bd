import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Ajout {
	
	public static void ajout(Statement currentStatement, Scanner keyboard, String email) throws SQLException {
		
		ArrayList<String> choixSelection = new ArrayList<String>();
		choixSelection.add("Film");
		choixSelection.add("Album");
		Liste c = new Liste ("Choix des données à ajouter", choixSelection);
	    String choice = c.getChoice();
		if (choice.equals("Film")) {
			addFilmFiles.ajoutFichiersFilm(currentStatement, email, keyboard);
		} else {
			AddAlbum.ajouterAlbum(currentStatement, email, keyboard);
		}
		
	}
	
	
}
