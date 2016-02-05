import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MainView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableTodayEntry;
	private JTable tableWeekEntry;
	
	private DefaultTableModel tModel;
	
	private loginView loginVw;
	
	private JLabel labelUserEmail;
	private JLabel labelProjectBoxDisplay;
	private JLabel labelWorkpackageBoxDisplay;
	
	private JButton buttonChooseWorkpackage;
	private JButton buttonCreateEntry;
	
	private Rest restfunction = new Rest();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MainView() throws ParseException {
		
		loginVw = new loginView(restfunction);
		loginVw.setLocationRelativeTo(null);
		loginSequence();
		
		setTitle("Brand Leadership Circle - Time Tracking");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1246, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelCompanyTitle = new JLabel("Brand Leadership Circle");
		labelCompanyTitle.setFont(new Font("Lucida Grande", Font.BOLD, 23));
		labelCompanyTitle.setBounds(6, 6, 302, 44);
		contentPane.add(labelCompanyTitle);
		
		JPanel panelUserInformation = new JPanel();
		panelUserInformation.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelUserInformation.setBounds(914, 6, 326, 99);
		contentPane.add(panelUserInformation);
		panelUserInformation.setLayout(null);
		
		JLabel labelUserIcon = new JLabel("");
		labelUserIcon.setIcon(new ImageIcon("/Users/u-ak/Desktop/IPA 2016/Bilder : Screenshots/default-user-avatar.png"));
		labelUserIcon.setBounds(6, 6, 90, 87);
		panelUserInformation.add(labelUserIcon);
		
		JLabel labelLoggedInAs = new JLabel("Angemeldet als");
		labelLoggedInAs.setHorizontalAlignment(SwingConstants.CENTER);
		labelLoggedInAs.setBounds(97, 16, 223, 16);
		panelUserInformation.add(labelLoggedInAs);
		
		labelUserEmail = new JLabel(restfunction.getUser().getUsername());
		labelUserEmail.setHorizontalAlignment(SwingConstants.CENTER);
		labelUserEmail.setBounds(97, 53, 223, 16);
		panelUserInformation.add(labelUserEmail);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 106, 1234, 472);
		contentPane.add(tabbedPane);
		
		JPanel panelTagesAnsicht = new JPanel();
		tabbedPane.addTab("Tagesansicht", null, panelTagesAnsicht, null);
		panelTagesAnsicht.setLayout(null);
		
		JPanel panelProjectBox = new JPanel();
		panelProjectBox.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelProjectBox.setBounds(40, 23, 219, 34);
		panelTagesAnsicht.add(panelProjectBox);
		panelProjectBox.setLayout(null);
		
		labelProjectBoxDisplay = new JLabel("Bitte auswählen");
		labelProjectBoxDisplay.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		labelProjectBoxDisplay.setBounds(6, 6, 207, 22);
		panelProjectBox.add(labelProjectBoxDisplay);
		labelProjectBoxDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelWorkpackgeBox = new JPanel();
		panelWorkpackgeBox.setLayout(null);
		panelWorkpackgeBox.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelWorkpackgeBox.setBounds(307, 23, 219, 34);
		panelTagesAnsicht.add(panelWorkpackgeBox);
		
		labelWorkpackageBoxDisplay = new JLabel("- Bitte auswählen -");
		labelWorkpackageBoxDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		labelWorkpackageBoxDisplay.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		labelWorkpackageBoxDisplay.setBounds(6, 6, 207, 22);
		panelWorkpackgeBox.add(labelWorkpackageBoxDisplay);
		
		JLabel lblProjekt = new JLabel("Projekt");
		lblProjekt.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblProjekt.setHorizontalAlignment(SwingConstants.LEFT);
		lblProjekt.setBounds(40, 6, 94, 16);
		panelTagesAnsicht.add(lblProjekt);
		
		JLabel labelWorkpackagBox = new JLabel("Workpackage");
		labelWorkpackagBox.setHorizontalAlignment(SwingConstants.LEFT);
		labelWorkpackagBox.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		labelWorkpackagBox.setBounds(307, 5, 94, 16);
		panelTagesAnsicht.add(labelWorkpackagBox);
		
		JButton buttonChooseProject = new JButton("Projekt Auswählen");
		buttonChooseProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openProjectView();
			}
		});
		buttonChooseProject.setBounds(1011, 26, 196, 45);
		panelTagesAnsicht.add(buttonChooseProject);
		
		buttonChooseWorkpackage = new JButton("Workpackage Auswählen");
		buttonChooseWorkpackage.setEnabled(false);
		buttonChooseWorkpackage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openWorkpackageView();
			}
		});
		buttonChooseWorkpackage.setBounds(1011, 83, 196, 45);
		panelTagesAnsicht.add(buttonChooseWorkpackage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 61, 993, 359);
		panelTagesAnsicht.add(scrollPane);
		
		tableTodayEntry = new JTable();
		tableTodayEntry.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		"T-ID", "P-ID","WP-ID", "P-Nr","Projekt Name", "Workpackage Name", "Bezeichnung", "Datum", "Startzeit", "Endzeit", "Stunden"
	            }
	        ) {
				private static final long serialVersionUID = -5060001140943749394L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	            };
	            boolean[] canEdit = new boolean [] {
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false
	            };

	            public Class<?> getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });		
		
		TableColumnModel columnModel = tableTodayEntry.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(10);
		columnModel.getColumn(1).setPreferredWidth(10);
		columnModel.getColumn(2).setPreferredWidth(10);
		columnModel.getColumn(3).setPreferredWidth(15);
		columnModel.getColumn(4).setPreferredWidth(150);
		columnModel.getColumn(5).setPreferredWidth(150);
		columnModel.getColumn(6).setPreferredWidth(150);
		
		scrollPane.setViewportView(tableTodayEntry);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.window);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(1011, 249, 196, 152);
		panelTagesAnsicht.add(panel);
		panel.setLayout(null);
		
		buttonCreateEntry = new JButton("Erfassen");
		buttonCreateEntry.setEnabled(false);
		buttonCreateEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openEntryView();
			}
		});
		buttonCreateEntry.setBounds(6, 6, 184, 39);
		panel.add(buttonCreateEntry);
		
		JButton buttonUpdateEntry = new JButton("Bearbeiten");
		buttonUpdateEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonUpdateEntry.setBounds(6, 57, 184, 39);
		panel.add(buttonUpdateEntry);
		
		JButton buttonDeleteEntry = new JButton("Löschen");
		buttonDeleteEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonDeleteEntry.setBounds(6, 108, 184, 39);
		panel.add(buttonDeleteEntry);
		
		JPanel panelWochenAnsicht = new JPanel();
		tabbedPane.addTab("Wochenansicht", null, panelWochenAnsicht, null);
		panelWochenAnsicht.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 6, 1201, 414);
		panelWochenAnsicht.add(scrollPane_1);
		
		tableWeekEntry = new JTable();
		tableWeekEntry.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            		"T-ID", "P-ID","WP-ID", "P-Nr","Projekt Name", "Workpackage Name", "Bezeichnung", "Datum", "Startzeit", "Endzeit", "Stunden"
	            }
	        ) {
				private static final long serialVersionUID = -5060001140943749394L;
				@SuppressWarnings("rawtypes")
				Class[] types = new Class [] {
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class,
	                java.lang.String.class
	            };
	            boolean[] canEdit = new boolean [] {
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false,
	                false
	            };

	            public Class<?> getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        });
		scrollPane_1.setViewportView(tableWeekEntry);
		
		
		restfunction.queryTracks();
		updateTable(tableTodayEntry);
	}
	
	
	private void loginSequence() {
		loginVw.setVisible(true);
		if(!restfunction.isAuthSuccess()) {
			JOptionPane.showMessageDialog(loginVw, "Invalid username or password", "Login", JOptionPane.ERROR_MESSAGE);
			loginSequence();
		}
	}

    private String castTableValues(JTable table, int i) {
        return (String)table.getModel().getValueAt(getSelectedRow(), i);
    }

    private boolean isRowSelected() {
        boolean isSelected = false;
        if(getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(MainView.this, "Bitte wählen Sie einen Datensatz von der Tabelle!", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {
            isSelected = true;
        }
        return isSelected;
    }
    
    private int getSelectedRow() { return tableTodayEntry.getSelectedRow(); }

    private void updateTable(JTable table) {
        tModel = (DefaultTableModel)table.getModel();
        int rowCount = tModel.getRowCount();
        
        if(rowCount != 0) { //Check if any rows are present
            for(int i = rowCount - 1; i >= 0; i--) {
                tModel.removeRow(i);
            }
        }
        restfunction.getEntryList().stream().forEach((a) -> {
            tModel.addRow(new String[]{ a.getEntryId(), a.getEntryProjectId(), a.getEntryWorkPackageId(), a.getEntryProjectNr(), a.getEntryProjectName(), a.getEntryWorkPackageName(), a.getEntrySubject(), a.getEntryDate(), a.getEntryStartTime(), a.getEntryEndTime(), a.getEntryHours()});
        });
        table.setModel(tModel);
    }
    
    private void openProjectView() {
    	String chosenProject;
    	
    	projectView projectVw = new projectView(restfunction);
    	projectVw.setLocationRelativeTo(null);
    	projectVw.setVisible(true);
    	
    	if(restfunction.isProjectChosen()) {
    		chosenProject = restfunction.getChosenProject().getProjectNr() + " " + restfunction.getChosenProject().getProjectName();
    		labelProjectBoxDisplay.setText(chosenProject);
    		buttonChooseWorkpackage.setEnabled(true);
	    }
    }
    
    private void openWorkpackageView() {
    	String chosenWorkPackage;
    	
    	workpackageView workpackageView = new workpackageView(restfunction);
    	workpackageView.setLocationRelativeTo(null);
    	workpackageView.setVisible(true);
    	
    	if(restfunction.isWorkpackageChosen()) {
    		chosenWorkPackage = restfunction.getChosenWorkPackage().getWorkPackageName();
    		labelWorkpackageBoxDisplay.setText(chosenWorkPackage);
    		buttonCreateEntry.setEnabled(true);
    	}
    }
    
    private void openEntryView() {
    	entryInsertView entryVw = new entryInsertView(restfunction);
    	entryVw.setLocationRelativeTo(null);
    	entryVw.setVisible(true);
    }
}
