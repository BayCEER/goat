package de.unibayreuth.bayeos.connection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class JPanelListCon extends JDialog {

	private final JPanel contentPanel = new JPanel();
	protected boolean okPressed;
	private JTable table;
	private JScrollPane scrollPane;
	private ConnectionTableModel tableModel;
	private JButton btnNewCon;
	private JButton btnDeleteCon;
	private JButton btnEditCon;

	private JPanelEditCon jPanelEditCon;

	
	/**
	 * Create the dialog.
	 */
	public JPanelListCon() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JPanelListCon.class.getResource("/de/unibayreuth/bayeos/goat/Goat.gif")));
		setTitle("BayEOS Connections");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter(){
            // Invoked when user clicks the close box
            public void windowClosing(WindowEvent evt){
              okPressed = false;
            }
        });
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel buttonPanel = new JPanel();
			contentPanel.add(buttonPanel, BorderLayout.NORTH);
			buttonPanel.setLayout(new BorderLayout(0, 0));
			{
				JPanel buttonLeft = new JPanel();
				FlowLayout flowLayout = (FlowLayout) buttonLeft.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				buttonPanel.add(buttonLeft, BorderLayout.WEST);
				{
					btnNewCon = new JButton("");
					btnNewCon.setToolTipText("New Connection");
					btnNewCon.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							jPanelEditCon = new JPanelEditCon();	
							jPanelEditCon.setTitle("New Connection");
							jPanelEditCon.setLocationRelativeTo(jPanelEditCon.getParent());
							jPanelEditCon.setModal(true);							
							jPanelEditCon.setURL("https://<hostname>/BayEOS-Server/XMLServlet");							
							jPanelEditCon.setVisible(true);
							
							
							if (jPanelEditCon.okPressed){
								Connection con = new Connection(jPanelEditCon.getName(),jPanelEditCon.getURL(),jPanelEditCon.getUser(),jPanelEditCon.getPassword());
								tableModel.addRow(con);
							}
						}
					});
					btnNewCon.setIcon(new ImageIcon(JPanelListCon.class.getResource("/de/unibayreuth/bayeos/goat/New16.gif")));
					buttonLeft.add(btnNewCon);
				}
				{
					btnDeleteCon = new JButton("");
					btnDeleteCon.setToolTipText("Delete Connection");
					btnDeleteCon.setEnabled(false);
					btnDeleteCon.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							tableModel.removeRow(table.getSelectedRow());
						}
					});
					btnDeleteCon.setIcon(new ImageIcon(JPanelListCon.class.getResource("/de/unibayreuth/bayeos/goat/Delete16.gif")));
					buttonLeft.add(btnDeleteCon);
				}
				{
					btnEditCon = new JButton("");
					btnEditCon.setToolTipText("Edit Connection");
					btnEditCon.setEnabled(false);
					btnEditCon.addActionListener(new ActionListener() {						
						public void actionPerformed(ActionEvent e) {
							int r = table.getSelectedRow();
							if (r!=-1) editRow(table.getSelectedRow());
							
						}
					});
					btnEditCon.setIcon(new ImageIcon(JPanelListCon.class.getResource("/de/unibayreuth/bayeos/goat/Edit16.gif")));
					buttonLeft.add(btnEditCon);
				}
			}
			{
				JPanel buttonRight = new JPanel();
				FlowLayout flowLayout = (FlowLayout) buttonRight.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				buttonPanel.add(buttonRight, BorderLayout.EAST);
			}
		}
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
		}
		{
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				   public void mouseClicked(MouseEvent e) {
					      if (e.getClickCount() == 2) {
					         JTable target = (JTable)e.getSource();
					         int r = target.getSelectedRow();
					         if (r!=-1) editRow(table.getSelectedRow());
					      }
				}
			});
			
						
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
					public void actionPerformed(ActionEvent e) {
						okPressed = false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
						
		}
		
		
		
		
	}
	
	private void editRow(int row){
		jPanelEditCon = new JPanelEditCon();						
		jPanelEditCon.setLocationRelativeTo(jPanelEditCon.getParent());
		jPanelEditCon.setModal(true);		
		jPanelEditCon.init(tableModel.getConnections().get(row));
		jPanelEditCon.setVisible(true);
		if (jPanelEditCon.okPressed){
			Connection con = new Connection(jPanelEditCon.getName(),jPanelEditCon.getURL(),jPanelEditCon.getUser(),jPanelEditCon.getPassword());
			tableModel.updateRow(row, con);
		}
	}

	public void init(List<Connection> cons) {
		tableModel = new ConnectionTableModel(cons);			
		table.setModel(tableModel);		
		table.getColumnModel().getColumn( 0 ).setPreferredWidth(10);
		table.getColumnModel().getColumn( 1 ).setPreferredWidth(200);
		table.getColumnModel().getColumn( 2 ).setPreferredWidth(10);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    table.setRowSelectionAllowed( true );
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				boolean rowSelected = table.getSelectedRowCount()>0;
				btnDeleteCon.setEnabled(rowSelected);
				btnEditCon.setEnabled(rowSelected);	
			
			}
		});		
		
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		
	}
	
		
	
	public int getSelectedRow(){
		return table.getSelectedRow();			
	}
	
	public List<Connection> getConnections() {
		return tableModel.getConnections();
	}
	
	class ConnectionTableModel extends AbstractTableModel {
	    private String[] columnNames = {"Name","Url","User"};	    
	    private List<Connection> cons;
	    
	    public List<Connection> getConnections(){
	    	return cons;
	    }
	    
	    public ConnectionTableModel(List<Connection> cons) {
	    	this.cons = cons;
		}
		public int getColumnCount() {
	        return 3;
	    }
	    public int getRowCount() {
	    	if (cons == null) { 
	    		return 0;
	    	} else {
	    	    return cons.size();		
	    	}	    
	    }
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	    public Object getValueAt(int row, int col) {
	    	switch (col) {
			case 0:
				return cons.get(row).getName();				
			case 1:
				return cons.get(row).getURL();				
			case 2:
				return cons.get(row).getUserName();				
			default:
				return null;
			}
	    }
	    
	    public boolean isCellEditable(int row, int col)
        { return false; }
	    
	    
	    public void removeRow(int row) {	    	
	    	cons.remove(row);
	    	fireTableRowsDeleted(row, row);
	    }
	    
	    public void addRow(Connection con){
	    	cons.add(con);
	    	fireTableRowsInserted(cons.size()-1, cons.size()-1);
	    }
	    
	    public void updateRow(int row, Connection con){
	    	cons.set(row,con);	    	
	    	fireTableRowsUpdated(row, row);
	    }
	    	
	    
	    public Class getColumnClass(int c) {
	        return String.class;
	    }
	}

	

}

