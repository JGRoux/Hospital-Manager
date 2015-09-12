package hopital;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Classe contenant les deux arrayList contenant les lignes supprimer et la requete de restauration
 *
 */
public class Storage {
	
	private ArrayList<String> affichage;
	private ArrayList<String> restauration;
	private String supp;
	private String rest_req;
	
	/**
	 * Constructeur
	 */
	public Storage(ConnectionBDD connection){
		this.affichage = new ArrayList<String>();
		this.restauration=new ArrayList<String>();
	}
	
	/**
	 * retourne le tableau d'affichage de la corbeille
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getArrayAff(){
	return this.affichage;
	}
	
	/**
	 * retourne le tableau de requete
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getArrayReq(){
		return this.restauration;
	}
	
	/**
	 * Ajoute dans les arraylist les suppression 
	 * @param NomTable
	 * @param champs
	 * @param colonnes
	 */
	public void addAff(String NomTable, Object[] champs, String[] colonnes){
		String temp;
		String temp2;
		temp = "Supprimé de la Table "+NomTable +" ";
		for (int i =0; i<champs.length;i++){
			temp = temp.concat(champs[i].toString() +" ");
		}
		System.out.println(temp);
		this.supp = temp;
		this.affichage.add(this.supp);
		
		
		
		
		// création de la string requete insert en fonction des champs séléctionnés récuperés
		    temp2 = "INSERT "+NomTable + " (";
		

			for (int i = 0;i<champs.length;i++){

				temp2 =  temp2.concat("" + colonnes[i] +"," );
				//System.out.println(this.champs[i]);


			}
			temp2 =  temp2.substring(0, temp2.length()-1);
			temp2 = temp2.concat(") VALUES (");

			for (int i = 0; i<champs.length;i++){

				temp2= temp2.concat("'"+ champs[i] + "',");
			}
			temp2 = temp2.substring(0, temp2.length()-1);
			temp2 = temp2.concat(")");
		this.rest_req = temp2;
		System.out.println(this.rest_req);
		this.restauration.add(this.rest_req);
		
	}
	
	public void restaurer (ConnectionBDD connexion, int index){
		final Requete r = new Requete();
		
		final String requete = this.restauration.get(index);
		final ProgressingConnection progress=new ProgressingConnection();
		final ConnectionBDD connection = connexion;
		// Lancement de la progress bar dans un nouveau Thread car le Thread swing est bloqué sur l'action button pendant la connexion
		new Thread(new Runnable() {
			public void run() {
				try {
					// On envoit la reqûete à la BDD
					r.requetUpdate(requete);
					r.SendUpdate(connection, progress);
					new JOptionPane();
					JOptionPane.showMessageDialog(null, "Les données ont été restaurées !", "Information", JOptionPane.INFORMATION_MESSAGE);
										
					
				} catch (SQLException e) {
					e.printStackTrace();
					progress.Error();
				}
			}
		}).start();
		this.affichage.remove(index);
		this.restauration.remove(index);
	}
}

