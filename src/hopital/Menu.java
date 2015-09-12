package hopital;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Fenetre menu contenant tous les panels du programme géré par une JToolbar
 * @author louis
 * @author Alexandre
 * @author Jean Guillaume
 *
 */
@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {

	private ConnectionBDD connection;
	private JToolBar toolbar;
	private JButton update, seek, historic, historic2;
	private Storage store;


	/** 
	 * Constructeur
	 */
	public Menu(ConnectionBDD connection){
		super("Gestion Bdd Hopital");

		// Set window icon
		this.setIconImage(new ImageIcon(this.getClass().getResource("/images/hopital_logo.png")).getImage());

		this.connection=connection;
		this.store = new Storage(this.connection);


		//On ajoute les attributs boutons toolbar et checkbox
		this.toolbar = new JToolBar("Still draggable");
		this.seek = new JButton("Recherche");
		this.seek.addActionListener(this);
		this.update = new JButton ("Mise à jour");
		this.update.addActionListener(this);
		this.historic = new JButton("Génération");
		this.historic.addActionListener(this);
		this.historic2 = new JButton("Corbeille");
		this.historic2.addActionListener(this);

		toolbar.add(seek);
		toolbar.add(update);
		toolbar.add(historic);
		toolbar.add(historic2);

		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
		this.getContentPane().add(new Recherche(connection));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		this.pack();
		this.setSize(800,600);
		this.setLocationRelativeTo(null); // center on the screen
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.getContentPane().remove(1);
		if (e.getSource().equals(seek))
		{
			this.getContentPane().add(new Recherche(connection));
		}
		else if(e.getSource().equals(update)){
			this.getContentPane().add("Center",new Update(connection,this.store));
		}
		else if(e.getSource().equals(historic)){
			this.getContentPane().add(new HistorisationChoix(connection));
		}
		else if(e.getSource().equals(historic2)){
			this.getContentPane().add(new HistoriquePanel(this.store, connection));
		}
		this.revalidate();
	}
}
