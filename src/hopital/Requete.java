package hopital;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Cette classe permet de gérer les requête ainsi que la progress bar
 *
 */
public class Requete {
	private String requete="";
	private ResultSet result;
	private ArrayList<String> selections;
	private ArrayList<String> froms;
	private ArrayList<String> wheres;


	/**
	 *  Creation de la requete
	 */
	public Requete(){
		// Traitement de la requête (mise en String)
		this.selections=new ArrayList<String>();		
		this.froms=new ArrayList<String>();
		this.wheres=new ArrayList<String>();

	}


	/** Requete Update pour JTable constructor 
	 * (Ne servira à rien après l'implémentation correcte de cette classe) 
	 */
	public Requete(String nomTable){
		this.requete =" Select * from " + nomTable;
	}
	public void requetUpdate(String requeteUpdate){
		this.requete = requeteUpdate;
	}


	/**
	 * Envoi la requete de recherche et recupére le résultat en gérant la progress bar
	 * @param connection
	 * @param progress
	 * @throws SQLException
	 */
	public void SendRequete(ConnectionBDD connection, ProgressingConnection progress) throws SQLException{
		progress.Retrieving();
		//Correction prob sur ancienne version mySQL ECE tolowercase
		this.result=connection.executeQuery(this.requete.toLowerCase());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progress.Close();
	}


	/**
	 * Envoi la requete de mise à jour en gérant la progress bar
	 * @param connection
	 * @param progress
	 * @throws SQLException
	 */
	public void SendUpdate(ConnectionBDD connection, ProgressingConnection progress) throws SQLException{
		progress.Sending();
		//Correction prob sur ancienne version mySQL ECE tolowercase
		connection.executeUpdate(this.requete.toLowerCase());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progress.Close();
	}

	/**
	 * Récupére le résultat de la requete
	 * @return ResultSet de la requete
	 */
	public ResultSet getResult(){
		return this.result;
	}

	/**
	 * Calcul de la requete...
	 * @param listPanel
	 * @throws Exception 
	 */
	public void performeRequete(ArrayList<String> tables,ArrayList<RecherchePanelCondition> listPanel,ArrayList<String> specialCondition) throws BDDException{
		this.froms=tables;
		this.wheres=new ArrayList<String>();
		this.wheres.addAll(specialCondition);
		String paramSpecial = null;

		//On vérifie que la requête est pertinente
		if(!this.tableLinked())
			throw new BDDException("Cette recherche ne peut être effectuée car les tables n'ont pas de rapport entre elles");

		Iterator<RecherchePanelCondition> it = listPanel.iterator();
		while(it.hasNext()){
			RecherchePanelCondition panel=it.next();
			ArrayList<RechercheChamp> selections=panel.getSelections();
			ArrayList<MyParameter> param=panel.getParameters();
			Iterator<MyParameter> itParam = param.iterator();

			for(RechercheChamp myChamp:selections){
				MyParameter myParam=itParam.next();
				if(myChamp.isChecked()){
					if(!panel.getTitle().equals("Chambre")&&!panel.getTitle().equals("Special"))
						this.selections.add(myParam.getSQLValue()+" AS \""+myParam.getValue()+" "+panel.getTitle()+"\"");
					else
						this.selections.add(myParam.getSQLValue()+" AS \""+myParam.getValue()+"\"");
					if(panel.getTitle().equals("Special"))
						if(paramSpecial==null)
							paramSpecial=myParam.getSpecialParam();
						else
							throw new BDDException("Veuillez ne sélectionner qu'une recherche complexe");
				}
				if(!myChamp.getText().equals(""))
					this.wheres.add(myParam.getSQLValue()+myChamp.getComparator()+"\""+myChamp.getText()+"\"");
			}
			while(itParam.hasNext()){
				this.wheres.add(itParam.next().getSQLValue());
			}
		}
		if(this.selections.isEmpty())
			throw new BDDException("Veuillez sélectionner au moins un champ à afficher !");
		addRequete("SELECT",this.selections,",");
		addRequete("FROM",this.froms,",");
		addRequeteCondition("WHERE",this.wheres,"AND");
		if(paramSpecial!=null)
			this.requete+=paramSpecial;
		System.out.println(requete);
	}

	/**
	 * Return true si les tables ont un lien entre elles
	 * @return bool
	 */
	private boolean tableLinked(){
		int i=0;
		if(this.froms.size()>3)
			return true;
		if(this.froms.contains("Service")&&this.froms.contains("Chambre")&&this.froms.contains("Infirmier"))
			i++;
		else if(this.froms.contains("Malade")&&this.froms.contains("Service")&&this.froms.contains("Chambre"))
			i++;
		else if(this.froms.contains("Docteur")&&this.froms.contains("Infirmier"))
			i++;

		if(this.wheres.size()-i==this.froms.size()-1)
			return true;
		else
			return false;
	}

	/**
	 * Méthode ajoutant une partie de la requête
	 * @param value
	 * @param list
	 * @param separator
	 */
	public void addRequete(String value,ArrayList<String> list,String separator){
		this.requete+=" "+value+" ";
		if(list.size()!=0)
			concatRequete(list,separator);
		else
			this.requete+="*";
	}

	public void addRequeteCondition(String value,ArrayList<String> list,String separator){
		if(list.size()!=0){
			this.requete+=" "+value+" ";
			concatRequete(list,separator);
		}
	}

	/**
	 * Méthode permettant de concaténer une chaine de charactères avec un séparator entre chaque valeur
	 * @param list
	 * @param separator
	 */
	private void concatRequete(ArrayList<String> list, String separator){
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			this.requete+=it.next();
			if(it.hasNext())
				this.requete+=" "+separator+" ";
		}
	}
}
