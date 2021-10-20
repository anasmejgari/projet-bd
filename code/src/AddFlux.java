import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;


public class AddFlux {
	
	public static void ajoutFlux(Statement currentStatement, int idFichier, int natureFichier, Scanner keyboard) throws SQLException{
		DonnesParDefault donneesDefault = new DonnesParDefault(currentStatement);
		System.out.println("Entrez le débit: ");
		int debit = keyboard.nextInt();
		System.out.println();
		String command, command_2;
		switch (natureFichier) {
			case(1):
				System.out.println("Choisissez le codec utilisé pour la video.");
				ArrayList<String> mesCodecVideo = donneesDefault.getCodecVideo();
				Liste c = new Liste ("Choix du codec Video", mesCodecVideo);
				String codecVideo = c.getChoice();
				
				System.out.println("Entrez la hauteur de la video: ");
				int hauteur = keyboard.nextInt();
				System.out.println();
				System.out.print("Entrez la largeur de la video: ");
				int largeur = keyboard.nextInt();
				System.out.println();
				currentStatement.executeQuery("INSERT INTO Flux (Debit, idFichier) values ('"+debit+"', '"+idFichier+"')");
				ResultSet lastID = currentStatement.executeQuery("SELECT max(NumFlux) FROM Flux");
				lastID.next();					
				command = "INSERT INTO FluxVideo (NumFluxVideo, Codec, Largeur, Hauteur) values ('"+lastID.getInt(1)+"', '"+codecVideo+"','"+largeur+"', '"+hauteur+"')";
				currentStatement.executeQuery(command);
				break;
			case(2):
				keyboard.nextLine();
				System.out.println("Choisissez le codec utilisé pour l'audio: ");
				ArrayList<String> mesCodecAudio = donneesDefault.getCodecAudio();
				Liste c_2 = new Liste ("Choix du codec Audio", mesCodecAudio);
				String codecAudio = c_2.getChoice();
				
				System.out.println("Choisissez la valeur d'echantillonage: ");
				ArrayList<String> valEchantillonage = donneesDefault.getEchantillonage();
				Liste c_e = new Liste ("Choix de la valeur d'echantillonage", valEchantillonage);
			    String echantillonage = c_e.getChoice();
			    System.out.println();
				System.out.println("Veuillez choisir votre langue [une fenetre s'affiche]."); 
				ArrayList<String> langues = donneesDefault.getLangues();
				Liste c_l = new Liste ("Choix de langue", langues);
			    String langue = c_l.getChoice();
				currentStatement.executeQuery("INSERT INTO Flux (Debit, idFichier) values ('"+debit+"', '"+idFichier+"')");
				lastID = currentStatement.executeQuery("SELECT max(NumFlux) FROM Flux");
				lastID.next();					
				command = "INSERT INTO FluxAudio (NumFluxAudio, Echantillonage, LangueA, Codec) values ('"+lastID.getInt(1)+"', '"+echantillonage+"','"+langue+"', '"+codecAudio+"')";
				currentStatement.executeQuery(command);
				break;
				
			case (3):
				keyboard.nextLine();
				System.out.print("Entrez le codec utilisé pour le fichier texte: ");
				ArrayList<String> mesCodecTexte = donneesDefault.getCodecTexte();
				Liste c_2t = new Liste ("Choix du codec Texte", mesCodecTexte);
				String codecTexte = c_2t.getChoice();
				
				System.out.println("Veuillez choisir la langue [une fenetre s'affiche]."); 
				ArrayList<String> languesText = donneesDefault.getLangues();
				Liste c_l_3 = new Liste ("Choix de langue", languesText);
			    String langueTexte = c_l_3.getChoice();
				
				currentStatement.executeQuery("INSERT INTO Flux (Debit, idFichier) values ('"+debit+"', '"+idFichier+"')");
				lastID = currentStatement.executeQuery("SELECT max(NumFlux) FROM Flux");
				lastID.next();					
				command = "INSERT INTO FluxTexte (NumFluxTexte, LangueTxt, Codec) values ('"+lastID.getInt(1)+"', '"+langueTexte+"', '"+codecTexte+"')";
				currentStatement.executeQuery(command);
			default:
				break;
		}
						
		//return chaineFlux;
		
	}
}
