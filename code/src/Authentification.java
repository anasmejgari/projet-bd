import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.Color;


public class Authentification {
	
	
	public static String connexion(Statement currentStatement, Scanner keyboard) throws SQLException {
		String email = null;
		String accessCode = null;
		int nombreTentatives = 1;
		do {
			email = "";
			accessCode = "";
			if (nombreTentatives > 1) {
				System.out.println(nombreTentatives+"eme tentative de connexion.");
				System.out.println("Attention votre compte sera bloquée après 5 tentatives");
			}
			System.out.println("Veuillez entrer votre email:");
			email += keyboard.nextLine();
			
			System.out.println("Veuillez entrer votre code d'accès:");
			accessCode += keyboard.nextLine();
			System.out.println();
			ResultSet verifDonnes = currentStatement.executeQuery("SELECT * from Utilisateur WHERE Mail ='"+email+"' and codeAcces='"+accessCode+"'");
			if (verifDonnes.next()) {			
				System.out.println("Connecté avec succès");
				System.out.println("Ravi de vous voir une autre fois "+verifDonnes.getString(3)+" "+verifDonnes.getString(2));
				return email;
			}
			nombreTentatives++;
		} while (nombreTentatives <=5);
		if (nombreTentatives == 6) {
			System.out.println("Compte bloqué temporairement. Un email a été envoyé à votre adresse email pour plus d'informations.");
		}
		
		return null;
	}
	
	public static String acceuil(Statement currentStatement, Scanner keyboard) throws SQLException {
		
		
		ArrayList<String> lesChoixAcceuil = new ArrayList<String>(Arrays.asList("Se connecter", "S'inscrire"));
		Liste listCon = new Liste ("Acceuil", lesChoixAcceuil);
		String choixAcceuil = listCon.getChoice();
		System.out.println(choixAcceuil);
		String email = null;
		if (choixAcceuil.equals("Se connecter")) {
			email = connexion(currentStatement, keyboard);
		} else if (choixAcceuil.equals("S'inscrire")){
			email = AddUser.ajouterUtlisateur(currentStatement, keyboard);
		}
		return email;
	}
	
	public static String choixDepart(Statement currentStatement, Scanner keyboard, String email) throws SQLException {
		
		if (email != null) {
			
		}
		ArrayList<String> lesChoixAcceuil = new ArrayList<String>(Arrays.asList("Selection", "Ajout", "Supression", "Se déconnecter"));
		Liste listCon = new Liste ("Que voulez vous faire", lesChoixAcceuil);
		String choixAcceuil = listCon.getChoice();
		return choixAcceuil;
	}
}
