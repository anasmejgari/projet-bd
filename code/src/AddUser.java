import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class AddUser {
	
	public static String ajouterUtlisateur(Statement currentStatement, Scanner keyboard) throws SQLException {
		
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		System.out.println("Merci de bien entrez vos informations personelles .");
		System.out.println("Entrez votre mail :");
		String email = keyboard.nextLine();
		System.out.println("Entrez votre nom :");
		String nom = keyboard.nextLine();
		System.out.println("Entrez votre prenom :");
		String prenom = keyboard.nextLine();
		System.out.println("Entrez votre age :");
		int age = keyboard.nextInt();
		System.out.println("Entrez votre code d'accès [compsé de 4 chiffres]: ");
		int codeAcces = keyboard.nextInt();
		keyboard.nextLine();
		while (String.valueOf(codeAcces).length() != 4){
			System.out.println("Code invalide. Entre un code d'accès de 4 chiffres.");
			codeAcces = keyboard.nextInt();
			keyboard.nextLine();
		}
		System.out.println("Veuillez choisir votre langue [une fenetre s'affiche]."); 
		ArrayList<String> langues = donneesDefault.getLangues();
		Liste c = new Liste ("Choix de langue", langues);
	    String choice = c.getChoice();
	    
		System.out.println("Vous avez choisi "+choice+" comme langue.");		
		String command = "INSERT INTO Utilisateur (Mail, Nom, Prenom, Age, CodeAcces, LangueUtilisateur)\n"
				+"Values ('"+email+"', '"+nom+"', '"+prenom+"', '"+age+"', '"+codeAcces+"', '"+choice+"')";
		currentStatement.executeUpdate(command);
		System.out.println("Vous etes bien enregistré sur notre base de données.");
		
		return email; 
	}

}

	
	



