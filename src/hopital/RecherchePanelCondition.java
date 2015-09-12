package hopital;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Panel de recherche indiquant les conditions
 *
 */
@SuppressWarnings("serial")
public class RecherchePanelCondition extends RecherchePanel {
	ArrayList<MyParameter> myList;

	public RecherchePanelCondition(String title, ArrayList<MyParameter> myList) {
		super(title);
		this.myList=new ArrayList<MyParameter>();
		this.myList.addAll(myList);
		this.setPreferredSize(new Dimension(300, 470));


		for(MyParameter param:myList){
			RechercheChamp myChamp=new RechercheChamp(param.getValue(),param.iString());
			this.selections.add(myChamp);
			this.panel.add(myChamp);
		}
	}

	public ArrayList<MyParameter> getParameters(){
		return myList;
	}
}
