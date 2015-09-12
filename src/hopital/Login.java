package hopital;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.SQLException;


/**
 * 
 * Cette classe permet de logger l'utilisateur
 */
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener {        
	private JButton login;
	private JPasswordField pwdSSH, pwdBDD;
	private JTextField serverBDD, serverSSH, bdd, usernameSSH, usernameBDD;
	private JLabel serverBDD_lbl, serverSSH_lbl, bdd_lbl, pwdSSH_lbl, pwdBDD_lbl, usernameSSH_lbl, usernameBDD_lbl;
	private JCheckBox ssh;

	/**
	 * Constructeur de la fenêtre de login
	 */
	public Login()
	{
		super("Authentification");

		// Set window icon
		this.setIconImage(new ImageIcon(this.getClass().getResource("/images/hopital_logo.png")).getImage());

		/**
		 * Identifiants
		 */
		this.serverBDD=new JTextField();
		this.serverSSH=new JTextField();
		this.bdd=new JTextField();
		this.usernameSSH=new JTextField();
		this.usernameBDD=new JTextField();
		this.pwdSSH = new JPasswordField(20);
		this.pwdBDD = new JPasswordField(20);
		this.login=new JButton("Login");
		this.login.addActionListener(this);

		this.serverBDD_lbl=new JLabel("Serveur BDD : ");
		this.serverBDD_lbl.setHorizontalAlignment(JLabel.RIGHT);
		this.bdd_lbl=new JLabel("Nom BDD : ");
		this.bdd_lbl.setHorizontalAlignment(JLabel.RIGHT);
		this.usernameBDD_lbl=new JLabel("Username BDD : ");
		this.usernameBDD_lbl.setHorizontalAlignment(JLabel.RIGHT);
		this.pwdBDD_lbl=new JLabel("Password BDD : ");
		this.pwdBDD_lbl.setHorizontalAlignment(JLabel.RIGHT);

		this.serverSSH_lbl=new JLabel("Serveur SSH : ");
		this.serverSSH_lbl.setHorizontalAlignment(JLabel.RIGHT);
		this.usernameSSH_lbl=new JLabel("Username SSH : ");
		this.usernameSSH_lbl.setHorizontalAlignment(JLabel.RIGHT);
		this.pwdSSH_lbl=new JLabel("Password SSH : ");
		this.pwdSSH_lbl.setHorizontalAlignment(JLabel.RIGHT);



		/*this.serverBDD.setText("");
		this.bdd.setText("");
		this.usernameBDD.setText("");
		this.pwdBDD.setText("");

		this.serverSSH.setText("");
		this.usernameSSH.setText("");*/

		/**
		 * Ajout d'un bouton checkbox pour savoir si on utilise le ssh 
		 */
		this.ssh = new JCheckBox("SSH");
		this.ssh.setSelected(true);
		this.ssh.addActionListener(this);


		JPanel p = new JPanel(new GridLayout(8,2));
		/**
		 * Ajout des identifiants au panel
		 */
		p.add(this.serverBDD_lbl);
		p.add(this.serverBDD);
		p.add(this.bdd_lbl);
		p.add(this.bdd);
		p.add(this.usernameBDD_lbl);
		p.add(this.usernameBDD);
		p.add(this.pwdBDD_lbl);
		p.add(this.pwdBDD);

		p.add(this.serverSSH_lbl);
		p.add(this.serverSSH);
		p.add(this.usernameSSH_lbl);
		p.add(this.usernameSSH);
		p.add(this.pwdSSH_lbl);
		p.add(this.pwdSSH);
		p.add(ssh);

		this.getContentPane().add(p,BorderLayout.CENTER);
		this.getContentPane().add(this.login,BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 250);
		this.setLocationRelativeTo(null); // center on the screen
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		/**
		 * Active ou desactive les infos ssh lors de l'activation ou desactivation ssh
		 */
		if (e.getSource().equals(this.ssh)){
			if(this.ssh.isSelected()){
				this.serverSSH.setEditable(true);
				this.usernameSSH.setEditable(true);
				this.pwdSSH.setEditable(true);
			}else{
				this.serverSSH.setEditable(false);
				this.usernameSSH.setEditable(false);
				this.pwdSSH.setEditable(false);
			}
		}
		/**
		 * Tentative de connexion avec les infos user
		 */
		if ( e.getSource().equals(this.login))
		{
			final ConnectionBDD connection=new ConnectionBDD(serverBDD.getText(),bdd.getText(),usernameBDD.getText(),new String(pwdBDD.getPassword()),serverSSH.getText(),usernameSSH.getText(),new String(pwdSSH.getPassword()),ssh.isSelected());
			final ProgressingConnection progress=new ProgressingConnection();
			// Lancement de la progress bar dans un nouveau Thread car le Thread swing est bloqué sur l'action button pendant la connexion
			new Thread(new Runnable() {
				public void run() {
					try {
						connection.Connect();
						progress.Close();
						new Menu(connection);
						dispose();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
						progress.Error();
					}
				}
			}).start();
		}
	}

	public static void main(String[] args){
		new Login();
	}
}
