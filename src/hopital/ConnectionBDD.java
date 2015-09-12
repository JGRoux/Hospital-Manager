package hopital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * Connexion à une BDD avec les infos user fournis en paramètre
 * 
 * @author Inspirated by Mr Segado class
 */
public class ConnectionBDD {

	private Connection conn;
	private Statement stmt;
	private String serverBDD,serverSSH,bdd,usernameSSH,passwordSSH,loginDatabase,passwordDatabase;
	private boolean isSSH;

	/**
	 * Constructeur
	 * @param serverBDD
	 * @param bdd
	 * @param loginDatabase
	 * @param passwordDatabase
	 * @param serverSSH
	 * @param usernameSSH
	 * @param passwordSSH
	 * @param isSSH
	 */
	public ConnectionBDD(String serverBDD, String bdd, String loginDatabase, String passwordDatabase, String serverSSH, String usernameSSH, String passwordSSH, boolean isSSH){
		this.serverBDD=serverBDD;
		this.bdd=bdd;
		this.loginDatabase=loginDatabase;
		this.passwordDatabase=passwordDatabase;

		this.serverSSH=serverSSH;
		this.usernameSSH=usernameSSH;
		this.passwordSSH=passwordSSH;
		this.isSSH=isSSH;
	}

	/**
	 * Démarrer la connection
	 */
	public void Connect() throws SQLException, ClassNotFoundException{
		String address=serverBDD+"/"+bdd;

		// chargement driver "com.mysql.jdbc.Driver"
		Class.forName("com.mysql.jdbc.Driver");
		if(isSSH){
			address="localhost:3305/" + bdd;
			// Connexion via le tunnel SSH avec le username et le password ECE
			SSHTunnel ssh = new SSHTunnel(serverBDD,serverSSH,usernameSSH, passwordSSH);
			ssh.connect();
		}


		System.out.println("Connexion reussie");

		// url de connexion "jdbc:mysql://localhost:3305/usernameSSH"
		String urlDatabase = "jdbc:mysql://"+address;

		//création d'une connexion JDBC à la base
		this.conn = DriverManager.getConnection(urlDatabase, loginDatabase, passwordDatabase);

		// création d'un ordre SQL (statement)
		this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	}

	/**
	 * Methode qui execute une requete de recherche en paramètre
	 */
	public ResultSet executeQuery(String requete) throws SQLException {
		return stmt.executeQuery(requete);
	}

	/**
	 * Méthode qui execute une requete de mise à jour en parametre
	 */
	public void executeUpdate(String requeteMaj) throws SQLException {
		stmt.executeUpdate(requeteMaj);
	}

	/**
	 * Methode qui retourne la connection du driver manager
	 */
	public Connection getConnection(){
		return this.conn;
	}

	/**
	 * Méthode qui ferme la connection à la BDD
	 */
	public void closeConnection(){
		try {
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
