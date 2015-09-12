package hopital;

import java.awt.Dimension;

/**
 * Premier panel de recherche indiquant les tables
 *
 */
@SuppressWarnings("serial")
public class RecherchePanelBase extends RecherchePanel {

	public RecherchePanelBase(String title, String selections_str[], Listener myListener) {
		super(title);
		this.setPreferredSize(new Dimension(150, 470));

		for(int i=0;i<selections_str.length;i++){
			RechercheChamp myChamp=new RechercheChamp(selections_str[i]);
			this.selections.add(myChamp);
			myChamp.getCheckBox().addActionListener(myListener);
			this.panel.add(myChamp);
		}
	}
}
