import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class AddArtiste {

	public static int ajouterArtiste(Statement currentStatement, Scanner keyboard) throws SQLException{
		System.out.println("Merci de bien donner les informations nécessaires pour ajouter votre artiste.");
		System.out.println("Veuillez entrer le nom de votre artiste:");
		String nom = keyboard.nextLine();
		if (!currentStatement.executeQuery("SELECT idArtiste FROM Artiste WHERE Nom='"+nom+"'").next())   {
			System.out.println("Veuillez entrer sa spécialité :");
			String specialite = keyboard.nextLine();
			System.out.println("Veuillez entrer un URL de sa photo :");
			String URLartiste = keyboard.nextLine();
			String command = "INSERT INTO Artiste (Nom, URL_photo, Specialite)\n"
					+"Values ('"+nom+"', '"+URLartiste+"', '"+specialite+"')";
			currentStatement.executeQuery(command);
			ResultSet reqId = currentStatement.executeQuery("SELECT max(idArtiste) FROM Artiste");
			reqId.next();
			int idArtiste = reqId.getInt(1);
			System.out.print("Connaissez-vous sa date de naissance ? [Tapez 0 pour NON ou 1 pour OUI]: ");
			int choixNaissance = keyboard.nextInt();
			keyboard.nextLine();
			System.out.println();
			if (choixNaissance == 1) {
				System.out.println("Veuillez Entrez la date de naissance de l'artiste [forme jj/MM/yy]: ");
				String date = keyboard.nextLine();
			    String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
			    date = date.substring(0, 2)+"-"+months[Integer.parseInt(date.substring(3, 5))-1]+"-"+date.substring(6);
			    currentStatement.executeQuery("INSERT INTO DateNaissance (DateDeNaissance) VALUES ('"+date+"')");
			    currentStatement.executeQuery("INSERT INTO aPourDateDeNaissance (IdArtiste, DateDeNaissance)\n"+
			    "VALUES ('"+idArtiste+"', '"+date+"')");
			}
		    
			System.out.println("Voulez-vous entrez une courte biographie ? [Tapez 0 pour NON ou 1 pour OUI]:");
			int choixBio = keyboard.nextInt();
			keyboard.nextLine();
			System.out.println();
			if (choixBio == 1) {
				System.out.println("Veuillez Entrez la biographie [sans retour à la ligne]: ");
				String biographie = keyboard.nextLine();
			    currentStatement.executeQuery("INSERT INTO Biographie (IdArtiste, contenueBio)\n"+
			    "VALUES ('"+idArtiste+"', '"+biographie+"')");		    
			}
			
			System.out.println("Artiste ajouté avec succes");
			ResultSet idArtisteReq = currentStatement.executeQuery("SELECT max(idArtiste) FROM Artiste");
			idArtisteReq.next();
			return idArtisteReq.getInt(1);
			
		} else {
			System.out.println("Cet artiste existe déjà sur la base.");
			ResultSet idArtisteReq_2 = currentStatement.executeQuery("SELECT idArtiste FROM Artiste WHERE Nom='"+nom+"'");
			idArtisteReq_2.next();
			return idArtisteReq_2.getInt(1);
		}
		
		
		
	}
	
}
