package hopital;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;


/**
 * 
 * Classe h�rit�e de ActionListener r�cup�rant les entr�es user du panel de recherche
 *
 */
public class Listener implements ActionListener {
	private ConnectionBDD connection;
	private JPanel globalPanel;
	private ArrayList<RecherchePanelCondition> listPanel;
	private ArrayList<String> tables;
	private ArrayList<String> specialCondition;
	ArrayList<MyParameter> paramList=new ArrayList<MyParameter>();

	/**
	 * Constructeur prenant en param�tre la connection et le panel parent
	 * @param connection
	 * @param globalPanel
	 */
	public Listener(ConnectionBDD connection, JPanel globalPanel) {
		this.connection=connection;
		this.globalPanel=globalPanel;
		this.listPanel=new ArrayList<RecherchePanelCondition>();
		this.tables=new ArrayList<String>();
		this.specialCondition=new ArrayList<String>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Lancement de la requ�te
		if (e.getSource().getClass()==JButton.class)
		{
			final Requete requete=new Requete();
			final ProgressingConnection progress=new ProgressingConnection();

			new Thread(new Runnable() {
				public void run() {
					try {
						requete.performeRequete(tables,listPanel,specialCondition);
						requete.SendRequete(connection,progress);
						new ResultsFrame(requete);
					} catch (SQLException | BDDException e) {
						e.printStackTrace();
						progress.Error();
					}
				}
			}).start();

		}
		else{
			// r�cup�ration de la checkbox et add/remove panel
			JCheckBox checkbox=(JCheckBox)e.getSource();
			if(checkbox.isSelected()){
				this.addRecherche(checkbox.getText());
			}else{
				this.removeRecherche(checkbox.getText());
				this.resetTables();
			}
			this.globalPanel.revalidate();
			this.globalPanel.repaint();
		}
	}

	/**
	 * M�thode appel�e lorsqu'une table concern�e est coch� (elle s'occupe de tout...)
	 * @param table
	 */
	private void addRecherche(String table){
		this.addParameters(table);
		RecherchePanelCondition newPanel=new RecherchePanelCondition(table,this.paramList);
		this.listPanel.add(newPanel);
		this.globalPanel.add(newPanel);
		this.paramList.clear();
		this.resetTables();
	}

	/**
	 * M�thode permettant de joindre plusieurs tables en ajoutant des conditions automatiquement
	 */
	private void addParametersPlus(){
		if(this.tables.contains("Docteur")||this.tables.contains("Infirmier"))
			this.tables.add("Employe");
		if(this.tables.contains("Docteur")){
			this.specialCondition.add("Docteur.numero=Employe.numero");
		}
		if(this.tables.contains("Infirmier")){
			this.specialCondition.add("Infirmier.numero=employe.numero");
		}
		if(this.tables.contains("Service")){
			this.specialPanel(4);
		}
		if(this.tables.contains("Service")&&this.tables.contains("Chambre")){
			this.specialCondition.add("Service.code=Chambre.code_service");
			this.specialPanel(1);
		}
		if(this.tables.contains("Service")&&this.tables.contains("Infirmier")){
			this.specialCondition.add("Service.code=Infirmier.code_service");
			this.specialPanel(0);
		}
		if(this.tables.contains("Service")&&this.tables.contains("Docteur"))
			this.specialCondition.add("Service.directeur=Docteur.numero");

		if(this.tables.contains("Service")&&this.tables.contains("Hospitalisation"))
			this.specialCondition.add("Service.code=Hospitalisation.code_service");

		if(this.tables.contains("Chambre")&&this.tables.contains("Infirmier"))
			this.specialCondition.add("Chambre.surveillant=Infirmier.numero");

		if(this.tables.contains("Chambre")&&this.tables.contains("Hospitalisation"))
			this.specialCondition.add("Chambre.no_chambre=Hospitalisation.no_chambre");

		if(this.tables.contains("Docteur")&&this.tables.contains("Malade")){
			this.tables.add("Soigne");
			this.specialCondition.add("Docteur.numero=Soigne.no_docteur");
			this.specialCondition.add("Malade.numero=Soigne.no_malade");
			this.specialPanel(2);
		}
		if(this.tables.contains("Malade")&&this.tables.contains("Service")&&this.tables.contains("Chambre")){
			this.tables.add("Hospitalisation");
			this.specialCondition.add("Malade.numero=Hospitalisation.no_malade");
			this.specialCondition.add("Service.code=Hospitalisation.code_service");
			this.specialCondition.add("Chambre.no_chambre=Hospitalisation.no_chambre");
			this.specialPanel(3);
		}
		this.addSpecialPanel();
	}

	/**
	 * M�thode indiquant les champs � afficher dans le nouveau panel de recherche
	 * @param table
	 */
	private void addParameters(String table){
		if(table.equals("Service")){
			paramList.add(new MyParameter("Service.nom","Nom",true));
			paramList.add(new MyParameter("Service.batiment","B�timent",true));
		}
		else if(table.equals("Chambre")){
			paramList.add(new MyParameter("Chambre.no_chambre","N� Chambre",false));
			paramList.add(new MyParameter("Chambre.nb_lits","Nb lits",false));
		}
		else if(table.equals("Docteur")){
			paramList.add(new MyParameter("Employe.nom","Nom",true));
			paramList.add(new MyParameter("Employe.prenom","Pr�nom",true));
			paramList.add(new MyParameter("Employe.adresse","Adresse",true));	
			paramList.add(new MyParameter("Employe.tel","T�l�phone",true));
			paramList.add(new MyParameter("Docteur.specialite","Sp�cialit�",true));	
		}
		else if(table.equals("Infirmier")){			
			paramList.add(new MyParameter("Employe.nom","Nom",true));
			paramList.add(new MyParameter("Employe.prenom","Pr�nom",true));
			paramList.add(new MyParameter("Employe.adresse","Adresse",true));	
			paramList.add(new MyParameter("Employe.tel","T�l�phone",true));
			paramList.add(new MyParameter("Infirmier.rotation","Rotation",true));
			paramList.add(new MyParameter("Infirmier.salaire","Salaire",false));
		}
		else if(table.equals("Malade")){
			paramList.add(new MyParameter("Malade.nom","Nom",true));
			paramList.add(new MyParameter("Malade.prenom","Pr�nom",true));
			paramList.add(new MyParameter("Malade.adresse","Adresse",true));
			paramList.add(new MyParameter("Malade.tel","T�l�phone",true));
			paramList.add(new MyParameter("Malade.mutuelle","Mutuelle",true));
		}
	}

	/**
	 * Ajout d'un panel sp�cial (recherche pr�d�finies complexes)
	 * @param i
	 */
	private void specialPanel(int i){
		if(i==0){
			paramList.add(new MyParameter("cast(avg(Infirmier.salaire) AS decimal(10,2))","<html>Salaire moyen<br>infirmier par service</html>"," GROUP BY Service.code",false));
		}
		else if(i==1){
			paramList.add(new MyParameter("cast(avg(Chambre.nb_lits) AS decimal(10,2))","<html>Nb moyen de lits <br>par service</html>"," GROUP BY Service.code",false));
		}
		else if(i==2){
			paramList.add(new MyParameter("count(distinct Docteur.specialite) AS \"Nb sp�cialit�s\",count(*) ","<html>Malades<br>soign�s par plus de<br>3 m�decins</html>"," GROUP BY Malade.nom , Malade.prenom HAVING count(*)>3",false));
		}
		else if(i==3){
			paramList.add(new MyParameter("Hospitalisation.lit","N� lit",false));
		}
		else if(i==4){
			paramList.add(new MyParameter("(SELECT count(*) FROM Infirmier where Infirmier.code_service = Service.code )/(SELECT count(*) FROM Hospitalisation WHERE Hospitalisation.code_service = Service.code)","Nb Infirmier/Nb Malade",false));
		}
	}

	/**
	 * Ajout du panel sp�cial pour requetes complexes
	 */
	private void addSpecialPanel(){
		if(!this.paramList.isEmpty()){
			RecherchePanelCondition newPanel=new RecherchePanelCondition("Special",this.paramList);
			this.listPanel.add(newPanel);
			this.globalPanel.add(newPanel);
			this.paramList.clear();
		}
	}

	/**
	 * M�thode r�initialisant les tables concern�es et les conditions sp�ciales
	 */
	private void resetTables(){
		this.removeRecherche("Special");
		this.getTables();
		this.flushSpecialCondition();
	}

	/**
	 * M�thode permettant de r�cup�rer les tables concern�es
	 */
	private void getTables(){
		this.tables.clear();
		RecherchePanelBase panel=(RecherchePanelBase)this.globalPanel.getComponent(0);
		ArrayList<RechercheChamp> listChamp=panel.getSelections();
		for(RechercheChamp champ:listChamp){
			if(champ.isChecked())
				this.tables.add(champ.getName());
		}
	}

	/**
	 * M�thode permettant d'enlever un panel de recherche lorsqu'il est d�coch�
	 * @param table
	 */
	private void removeRecherche(String table){
		for(RecherchePanel panel:this.listPanel)
			if(panel.getTitle().equals(table)){
				this.globalPanel.remove(panel);
				this.listPanel.remove(panel);
				break;
			}
	}

	/**
	 * M�thode permettant de r�initialiser les conditions sp�ciales
	 */
	private void flushSpecialCondition(){
		this.specialCondition.clear();
		this.addParametersPlus();
	}
}
