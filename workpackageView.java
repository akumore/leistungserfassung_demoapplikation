import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class workpackageView extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tablePackages;
	private JTextField textFieldSearchFilter;
	private Rest restfunction;
	DefaultTableModel tModel;
	

	public workpackageView(Rest restfunction) {
		
		this.restfunction = restfunction;
		this.restfunction.queryWorkPackages();
		
		setResizable(false);
		setModal(true);
		
		setLocationRelativeTo(null);

		setUndecorated(true);
		setBounds(100, 100, 379, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel labelDialogTitle = new JLabel("Workpackage auswählen");
		labelDialogTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		labelDialogTitle.setBounds(6, 6, 267, 43);
		contentPanel.add(labelDialogTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 61, 345, 186);
		contentPanel.add(scrollPane);
		
		tablePackages = new JTable();
		scrollPane.setViewportView(tablePackages);
		tablePackages.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	                "ID", "Bezeichnung", "Status"
	            }
	        ) {
				private static final long serialVersionUID = -5060001140943749394L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class
	            };
	            boolean[] canEdit = new boolean [] {
	                false, false, false
	            };
	            public Class<?> getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
		
		TableColumnModel columnModel = tablePackages.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(5);
		columnModel.getColumn(1).setPreferredWidth(100);
		columnModel.getColumn(2).setPreferredWidth(30);
		
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tablePackages.getModel());
		tablePackages.setRowSorter(rowSorter);
		
		textFieldSearchFilter = new JTextField();
		textFieldSearchFilter.setBounds(108, 253, 252, 26);
		contentPanel.add(textFieldSearchFilter);
		textFieldSearchFilter.setColumns(10);
		
		textFieldSearchFilter.getDocument().addDocumentListener(new DocumentListener(){	
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	            String text = textFieldSearchFilter.getText();
	
	            if (text.trim().length() == 0) {
	                rowSorter.setRowFilter(null);
	            } else {
	                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	            }
	        }
	        @Override
	        public void removeUpdate(DocumentEvent e) {
	            String text = textFieldSearchFilter.getText();
	
	            if (text.trim().length() == 0) {
	                rowSorter.setRowFilter(null);
	            } else {
	                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
	            }
	        }
	        @Override
	        public void changedUpdate(DocumentEvent e) {
	            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	        }
	    });

		
		JLabel lblSuchfilter = new JLabel("Suchfilter");
		lblSuchfilter.setBounds(26, 259, 61, 16);
		contentPanel.add(lblSuchfilter);
		
		JButton buttonChoose = new JButton("Auswahl");
		buttonChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!isRowSelected()) { return; }
				chooseWorkpackage();
				dispose();
			}
		});
		buttonChoose.setBounds(6, 294, 117, 29);
		contentPanel.add(buttonChoose);
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonCancel.setBounds(247, 290, 117, 29);
		contentPanel.add(buttonCancel);
		loadTableContent();
	}
	
	private void chooseWorkpackage() {	
    	String selectedObjectId = castTableValues(0);
        String selectedObjectName = castTableValues(1);
        String selectedObjectStatus = castTableValues(2);
        
		WorkPackage wp = new WorkPackage();
		wp.setWorkPackageId(selectedObjectId);
		wp.setWorkPackageName(selectedObjectName);
		wp.setWorkPackageStatus(selectedObjectStatus);
		
		restfunction.setChosenWorkPackage(wp);
		restfunction.setWorkpackageChosen(true);
	}
	
    private boolean isRowSelected() {
        boolean isSelected = false;
        if(tablePackages.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(workpackageView.this, "Bitte wählen Sie einen Datensatz von der Tabelle!", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            isSelected = true;
        }
        return isSelected;
    }
	
    private void loadTableContent() {
        tModel = (DefaultTableModel)tablePackages.getModel();
        restfunction.getChosenProject().getWorkPackageList().stream().forEach((a) -> {
        	tModel.addRow(new String[]{ a.getWorkPackageId(), a.getWorkPackageName(), a.getWorkPackageStatus() });
        });
        tablePackages.setModel(tModel);
    }

    private String castTableValues(int i) {
        return (String)tablePackages.getModel().getValueAt(getSelectedRow(), i);
    }
    
    private int getSelectedRow() { 
    	return tablePackages.getSelectedRow();
    }
	
}
