package de.unibayreuth.bayeos.connection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;

public class JPanelEditCon extends JDialog {
	protected boolean okPressed;
	private JTextField textFieldName;
	private JTextField textFieldUrl;
	private JTextField textFieldUser;
	private JPasswordField passwordField;

	private final ValidationResultModel validationResultModel = new DefaultValidationResultModel();

	/**
	 * Create the dialog.
	 */
	public JPanelEditCon() {
		setTitle("Edit Connection");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JPanelEditCon.class.getResource("/de/unibayreuth/bayeos/goat/Goat.gif")));
		setBounds(100, 100, 409, 238);
		addWindowListener(new WindowAdapter(){
            // Invoked when user clicks the close box
            public void windowClosing(WindowEvent evt){
              okPressed = false;
            }
        });
		
		
		
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {												
						validationResultModel.setResult(getValidationResult());
						if (validationResultModel.hasErrors()) return;
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
		{
			JPanel panelForm = new JPanel();
			getContentPane().add(panelForm, BorderLayout.NORTH);
			panelForm.setLayout(new BorderLayout(0, 0));
			{
				JPanel panelEdit = new JPanel();
				panelForm.add(panelEdit);
				panelEdit.setBorder(new EmptyBorder(10, 10, 10, 10));
				GridBagLayout gbl_panelEdit = new GridBagLayout();
				gbl_panelEdit.columnWidths = new int[] {70, 0, 0};
				gbl_panelEdit.rowHeights = new int[] {0, 14, 14, 14};
				gbl_panelEdit.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
				gbl_panelEdit.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
				
				
				panelEdit.setLayout(gbl_panelEdit);
				{
					JLabel label = new JLabel("Name:");
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.anchor = GridBagConstraints.NORTHEAST;
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 0;
					gbc_label.gridy = 0;
					panelEdit.add(label, gbc_label);
				}
				{
					textFieldName = new JTextField();
					textFieldName.setColumns(20);
					GridBagConstraints gbc_textFieldName = new GridBagConstraints();
					gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
					gbc_textFieldName.insets = new Insets(0, 0, 5, 0);
					gbc_textFieldName.gridx = 1;
					gbc_textFieldName.gridy = 0;
					panelEdit.add(textFieldName, gbc_textFieldName);
				}
				{
					JLabel label = new JLabel("URL:");
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.anchor = GridBagConstraints.NORTHEAST;
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 0;
					gbc_label.gridy = 1;
					panelEdit.add(label, gbc_label);
				}
				{
					textFieldUrl = new JTextField();
					textFieldUrl.setColumns(40);
					GridBagConstraints gbc_textFieldUrl = new GridBagConstraints();
					gbc_textFieldUrl.fill = GridBagConstraints.HORIZONTAL;
					gbc_textFieldUrl.insets = new Insets(0, 0, 5, 0);
					gbc_textFieldUrl.gridx = 1;
					gbc_textFieldUrl.gridy = 1;
					panelEdit.add(textFieldUrl, gbc_textFieldUrl);
				}
				{
					JLabel label = new JLabel("User:");
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.anchor = GridBagConstraints.NORTHEAST;
					gbc_label.insets = new Insets(0, 0, 5, 5);
					gbc_label.gridx = 0;
					gbc_label.gridy = 2;
					panelEdit.add(label, gbc_label);
				}
				{
					textFieldUser = new JTextField();
					textFieldUser.setColumns(20);
					GridBagConstraints gbc_textFieldUser = new GridBagConstraints();
					gbc_textFieldUser.fill = GridBagConstraints.HORIZONTAL;
					gbc_textFieldUser.insets = new Insets(0, 0, 5, 0);
					gbc_textFieldUser.gridx = 1;
					gbc_textFieldUser.gridy = 2;
					panelEdit.add(textFieldUser, gbc_textFieldUser);
				}
				{
					JLabel label = new JLabel("Password:");
					GridBagConstraints gbc_label = new GridBagConstraints();
					gbc_label.anchor = GridBagConstraints.NORTHEAST;
					gbc_label.insets = new Insets(0, 0, 0, 5);
					gbc_label.gridx = 0;
					gbc_label.gridy = 3;
					panelEdit.add(label, gbc_label);
				}
				{
					passwordField = new JPasswordField();
					passwordField.setColumns(20);
					GridBagConstraints gbc_passwordField = new GridBagConstraints();
					gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
					gbc_passwordField.gridx = 1;
					gbc_passwordField.gridy = 3;
					panelEdit.add(passwordField, gbc_passwordField);
				}
			}
			{
				JPanel panelValidation = new JPanel();
				
				panelValidation.setLayout(new BorderLayout(0, 0));
				JComponent valComp = ValidationResultViewFactory.createReportIconAndTextPane(this.validationResultModel);				
				panelValidation.add(valComp, BorderLayout.CENTER);
				panelForm.add(panelValidation, BorderLayout.SOUTH);
			}
		}
	}

	public void init(Connection con) {
		textFieldName.setText(con.getName());
		textFieldUrl.setText(con.getURL());
		textFieldUser.setText(con.getUserName());
		passwordField.setText(con.getPassword());						
	}
	
	public String getName(){
		return textFieldName.getText();
	}
	
	public String getURL() {
		return textFieldUrl.getText();
	}
	
	public void  setURL(String url){
		textFieldUrl.setText(url);
	}
	
	public String getUser() {
		return textFieldUser.getText();
	}
	
	public String getPassword() {
		return String.valueOf(passwordField.getPassword());
	}
		
	private ValidationResult getValidationResult() {
	        ValidationResult validationResult = new ValidationResult();
	        if (this.textFieldName.getText().isEmpty()) {
	            validationResult.addError("The Name field can not be blank.");
	        }
	        if (this.textFieldUrl.getText().isEmpty()) {
	            validationResult.addError("The URL field can not be blank.");
	        }
	        
	        return validationResult;
	 }

}
