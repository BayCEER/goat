/**
 * 
 */
package de.unibayreuth.bayeos.goat.panels.frame;

import javax.swing.JPanel;

import de.unibayreuth.bayceer.bayeos.client.Client;
import de.unibayreuth.bayeos.goat.panels.LoadableForm;
import de.unibayreuth.bayeos.goat.tree.ObjektNode;

/**
 * @author oliver
 *
 */
public class JFramePanel extends JPanel implements LoadableForm {
	
	
	// Frame API 
	// file:///C:/Users/oliver/Documents/Subversion/bayeos/bayeos-common/target/apidocs/index.html?index-all.html
	
	ObjektNode n;
	String borderText;	
	Boolean editable;

	public JFramePanel(ObjektNode n) {
		this.n = n;
		this.borderText = n.getDe();		
	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#loadData()
	 */
	@Override
	public boolean loadData() {
		// Client.getXmlRpcClient().execute(method, params)
		return true;
	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#setBorderText(java.lang.String)
	 */
	@Override
	public void setBorderText(String Bezeichung) {			
		this.borderText = Bezeichung;
	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#getBorderText()
	 */
	@Override
	public String getBorderText() {
		return borderText;
	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#getObjektNode()
	 */
	@Override
	public ObjektNode getObjektNode() {
		return n;
	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#setObjektNode(de.unibayreuth.bayeos.goat.tree.ObjektNode)
	 */
	@Override
	public void setObjektNode(ObjektNode node) {
		this.n = node;
	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#setEditable(boolean)
	 */
	@Override
	public void setEditable(boolean enabled) {
			editable = enabled;

	}

	/* (non-Javadoc)
	 * @see de.unibayreuth.bayeos.goat.panels.LoadableForm#getEditable()
	 */
	@Override
	public boolean getEditable() {
		// TODO Auto-generated method stub
		return editable;
	}

}
