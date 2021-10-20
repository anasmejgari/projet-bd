import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddFile {
		
	public static void ajoutFichier(Statement currentStatement, Scanner keyboard, int choix, String email) throws SQLException{
			System.out.println("Entrez la taille du fichier: ");
			int taille = keyboard.nextInt();
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = new Date();
			String dateUpload = format.format(date).toString();
			
			String command = "INSERT INTO Fichier (Taille, dateDepot, Mail)\n"
					+"Values ('"+taille+"', '"+dateUpload+"', '"+email+"')";
			currentStatement.executeQuery(command);
			ResultSet idFichier = currentStatement.executeQuery("SELECT max(idFichier) FROM Fichier");
			idFichier.next();
			int idFichierCourrant = idFichier.getInt(1);
			switch(choix) {
				case(1):
					System.out.println("On commence par le contenue Video.");
					AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 1, keyboard);
					System.out.println("Passons maintenant au contenue Audio.");
					AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 2, keyboard);
					break;
				case(2):
					System.out.println("Ajout du contenu Audio.");
					AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 2, keyboard);
					break;
				case(3):
					System.out.println("Ajout du contenu texte.");
					AddFlux.ajoutFlux(currentStatement, idFichierCourrant, 3, keyboard);
					break;
				default:
					break;
					
			}
		
		
	}
}
