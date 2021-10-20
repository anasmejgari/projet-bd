import java.util.*;
import java.sql.*;


public class DonnesParDefault {
	
	Statement currentStatement;
	
	
	public DonnesParDefault(Statement currentStatement) throws SQLException {
		this.currentStatement = currentStatement;
	}
	
	public ArrayList<String> getLangues() throws SQLException{
		ArrayList<String> langues = new ArrayList<String>();
		ResultSet languesRes = currentStatement.executeQuery("SELECT * FROM Langue");
		while(languesRes.next()) {
			langues.add(languesRes.getString(1));
		}
		return langues;
	}
	
	public ArrayList<String> getCatFilm() throws SQLException{
		ArrayList<String> catFilm = new ArrayList<String>();
		ResultSet catRes = currentStatement.executeQuery("SELECT * FROM CategorieFilm");
		while(catRes.next()) {
			catFilm.add(catRes.getString(1));
		}
		return catFilm;
	}
	
	public ArrayList<String> getCatMusique() throws SQLException{
		ArrayList<String> catMusique = new ArrayList<String>();
		ResultSet catRes = currentStatement.executeQuery("SELECT * FROM CategorieMusique");
		while(catRes.next()) {
			catMusique.add(catRes.getString(1));
		}
		return catMusique;
	}
	
	public ArrayList<String> getCodecAudio() throws SQLException{
		ArrayList<String> codcAudio = new ArrayList<String>();
		ResultSet coRes = currentStatement.executeQuery("SELECT * FROM CodecAudio");
		while(coRes.next()) {
			codcAudio.add(coRes.getString(1));
		}
		return codcAudio;
	}
	
	public ArrayList<String> getCodecVideo() throws SQLException{
		ArrayList<String> codcVideo = new ArrayList<String>();
		ResultSet coRes = currentStatement.executeQuery("SELECT * FROM CodecVideo");
		while(coRes.next()) {
			codcVideo.add(coRes.getString(1));
		}
		return codcVideo;
	}
	
	public ArrayList<String> getEchantillonage() throws SQLException{
		ArrayList<String> echantillonage = new ArrayList<String>();
		echantillonage.add("8");
		echantillonage.add("16");
		echantillonage.add("32");
		return echantillonage;
	}
	
	public ArrayList<String> getCodecTexte() throws SQLException{
		ArrayList<String> codecTexte = new ArrayList<String>();
		ResultSet coRes = currentStatement.executeQuery("SELECT * FROM CodecTexte");
		while(coRes.next()) {
			codecTexte.add(coRes.getString(1));
		}
		return codecTexte;
	}
	
}
