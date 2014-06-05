package de.unibayreuth.bayeos.connection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import de.unibayreuth.bayeos.goat.JMainFrame;
import de.unibayreuth.bayeos.utils.MsgBox;

public class JDialogConnect extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox comboBoxCon;
	private JButton btnConEdit;
	private JLabel lblNewLabel;
	
	protected final static Logger logger = Logger.getLogger(JDialogConnect.class);
	
	public ConnectionFileAdapter cf;
	
	
	protected boolean okPressed;
	
	public ComboModel comboModel;
	
	private JPanelListCon jPanelListCon;
	
	


	public boolean isOkPressed() {
		return okPressed;
	}

	private final static Preferences pref = Preferences.userNodeForPackage(JMainFrame.class);
	private JButton okButton;
	
	
	
	
	/**
	 * Create the dialog.
	 */
	
	
	public JDialogConnect(JMainFrame frame) {
		super(frame,true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogConnect.class.getResource("/de/unibayreuth/bayeos/goat/Goat.gif")));
		setTitle("Connect");
		setBounds(100, 100, 315, 174);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(frame);
		cf  = new ConnectionFileAdapter(new File( new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath()));
		
		
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
			lblNewLabel = new JLabel("Connection:");
		
		
			comboBoxCon = new JComboBox();
			
			comboBoxCon.setPreferredSize(new Dimension(29, 20));
		
		
			btnConEdit = new JButton("");
			btnConEdit.setToolTipText("Manage connections.");
			btnConEdit.addActionListener(new ActionListener() {
				

				public void actionPerformed(ActionEvent e) {					
						jPanelListCon = new JPanelListCon();						
						jPanelListCon.setLocationRelativeTo(jPanelListCon.getParent());
						jPanelListCon.setModal(true);
						
						try {
						jPanelListCon.init(cf.read());
						} catch (IOException ex){
							MsgBox.error(JDialogConnect.this,"Failed to read connections.");
							return;
						}
						jPanelListCon.setVisible(true);
						
						if (jPanelListCon.okPressed){							
							try {
								List<Connection> cons = jPanelListCon.getConnections();								
								cf.writeEncrypted(cons);								
								init();
								
								int i = jPanelListCon.getSelectedRow(); 
								if (i != -1) {									
									comboBoxCon.setSelectedIndex(i);
								}
								
								
							} catch (IOException ex) {
								logger.error(ex.getMessage());
								MsgBox.error("Failed to read connections.");
							}
						}
						
						
				}
			});
			btnConEdit.setPreferredSize(new Dimension(29, 9));
			btnConEdit.setIcon(new ImageIcon(JDialogConnect.class.getResource("/de/unibayreuth/bayeos/goat/Edit16.gif")));
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxCon, 0, 159, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConEdit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnConEdit, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel)
							.addComponent(comboBoxCon, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 		okPressed = true;
			                    setVisible(false);
			             
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						okPressed = false;
						setVisible(false);					
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		addWindowListener(new WindowAdapter(){
            // Invoked when user clicks the close box
            public void windowClosing(WindowEvent evt){
              okPressed = false;
            }
        });
	}
	
	
	public void init() {
		List<Connection> cons;
		try {
			cons = cf.read();
			if (cons.size()==0){
				cons.add(new Connection("gast@bayceer","http://bayeos.bayceer.uni-bayreuth.de/BayEOS-Server/XMLServlet","gast","gast"));
				cf.writeEncrypted(cons);
			}
		} catch (IOException e) {
			MsgBox.error(JDialogConnect.this,"Failed to read connection definitions.");
			return;
		}
		comboModel = new ComboModel(cons);		
		comboBoxCon.setModel(comboModel);
		
		comboBoxCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				okButton.setEnabled(cb.getSelectedIndex()!=-1);
				
			}
		});
		if (pref.getBoolean("rememberlastconnection",true)){
			String conName = pref.get("lastconnection",null);
			if (conName!=null){
				Integer i = getIndex(cons, conName);				
				if ((i!=null)&&(i+1<=cons.size())) comboBoxCon.setSelectedIndex(i);
			}
		}		
	}
	
	private Integer getIndex(List<Connection> cons, String name){		
		for(int i=0;i<cons.size();i++){			
			if (cons.get(i).getName().equals(name)) return i;			
		}
		return null;
	}
	
	public Connection getSelectedConnection(){
		int i =comboBoxCon.getSelectedIndex(); 
		if (i==-1){
			return null;
		} else {
			return comboModel.getConnections().get(i);
		}
	}

	
	
	
	public class ComboModel extends DefaultComboBoxModel {
		
		List<Connection> cons;
		
		public ComboModel(List<Connection> cons){
			super();
			this.cons = cons;
		}
		
		public List<Connection> getConnections(){
			return cons;
		}
						
		
		public int getSize() {
			return cons.size();
		}

		public Object getElementAt(int index) {
			return cons.get(index).getName();
		}


		

		
	}
	
	
	

}
