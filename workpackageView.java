import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
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

	private final JPanel contentPanel = new JPanel();
	private JTable tableProjects;
	private JTextField textFieldSearchFilter;
	private Rest restfunction;
	DefaultTableModel tModel;
	

	public workpackageView(Rest restfunction) {
		
		this.restfunction = restfunction;
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
		
		tableProjects = new JTable();
		scrollPane.setViewportView(tableProjects);
		tableProjects.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	                "Workpackage-ID", "Bezeichnung"
	            }
	        ) {
				private static final long serialVersionUID = -5060001140943749394L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class, java.lang.String.class
	            };
	            boolean[] canEdit = new boolean [] {
	                false, false
	            };
	            public Class<?> getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tableProjects.getModel());
		tableProjects.setRowSorter(rowSorter);
		
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
		
		JButton btnAuswahl = new JButton("Auswahl");
		btnAuswahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAuswahl.setBounds(6, 294, 117, 29);
		contentPanel.add(btnAuswahl);
		
		JButton buttonAbbrechen = new JButton("Abbrechen");
		buttonAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonAbbrechen.setBounds(247, 290, 117, 29);
		contentPanel.add(buttonAbbrechen);
	}
}
