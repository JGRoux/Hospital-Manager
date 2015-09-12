package hopital;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe formant un champs de selection user héritée de JPanel
 *
 */
@SuppressWarnings("serial")
public class RechercheChamp extends JPanel implements ActionListener {
	private JCheckBox checkbox;
	private JTextField textfield;
	private String name;
	private JButton value;
	private String[] comparator; // tableau de String contenant =; != ; >,< 
	private int choix=0;

	public RechercheChamp(String name) {
		this.name=name;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.checkbox=new JCheckBox(this.name);
		this.add(this.checkbox);
	}

	public RechercheChamp(String name, boolean iString) {
		this.name=name;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.checkbox=new JCheckBox(this.name);
		this.add(this.checkbox);

		if(iString)
			this.comparator = new String[]{"égal à","différent de","ressemble à"};
		else
			this.comparator = new String[]{"=","!=","<","<=",">",">="};
		this.value= new JButton(this.comparator[0]);// valeur par defaut du bouton
		this.value.addActionListener(this);
		this.add(this.value);
		this.textfield=new JTextField(7);
		this.add(this.textfield);
	}

	public JTextField getTextField(){
		return this.textfield;
	}

	public String getText(){
		if(this.comparator[this.choix].equals("ressemble à"))
			return "%"+this.textfield.getText()+"%";
		else
			return this.textfield.getText();
	}

	public JCheckBox getCheckBox(){
		return this.checkbox;
	}

	public boolean isChecked(){
		return this.checkbox.isSelected();
	}

	public String getName(){
		return this.name;
	}

	/**
	 * Methode retournant le text choisi par le bouton
	 * @return String
	 */
	public String getComparator(){
		if(this.comparator[this.choix].equals("égal à"))
			return "=";
		else if(this.comparator[this.choix].equals("différent de"))
			return "!=";
		else if(this.comparator[this.choix].equals("ressemble à"))
			return " LIKE ";
		else
			return this.comparator[this.choix];
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(value)){
			this.choix++;
			if (this.choix>=this.comparator.length){
				this.choix = 0;
			}
			this.value.setText(this.comparator[this.choix]);
		}

	}
}
