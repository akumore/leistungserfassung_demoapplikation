import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class entryInsertView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textFieldStartTime;
	private JTextField textFieldEndTime;
	private JTextField textFieldSubject;
	
	private boolean fieldsEmpty;
	
	private JDateChooser datePicker;
	
	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	Date date = new Date();

	public boolean areFieldsEmpty() { return fieldsEmpty; }
	
	public void setFieldsEmpty(boolean b) { this.fieldsEmpty = b; }
	
	public entryInsertView(Rest restfunction) {
		
		this.fieldsEmpty = true;
		
		setUndecorated(true);
		setResizable(false);
		setModal(true);

		setLocationRelativeTo(null);
		
		setBounds(100, 100, 429, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel labelDialogTitle = new JLabel("Leistungserfassung");
			labelDialogTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
			labelDialogTitle.setBounds(18, 6, 272, 44);
			contentPanel.add(labelDialogTitle);
		}
		{
			JButton buttonCreate = new JButton("Erfassen");
			buttonCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkFieldValues(textFieldStartTime, textFieldEndTime, textFieldSubject);
					if(!areFieldsEmpty()) {
						try {
							String startTime = formatTimeToString(textFieldStartTime.getText());
							String endTime = formatTimeToString(textFieldEndTime.getText());
							String entryDate = formatDateToString(datePicker.getDate());
							String subject = textFieldSubject.getText();
							
							restfunction.createEntry(subject, entryDate, startTime, endTime);
							restfunction.setCreatedEntry(true);
							dispose();
							
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(entryInsertView.this, "Bitte fülle alle Felder aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			buttonCreate.setBounds(6, 207, 117, 29);
			contentPanel.add(buttonCreate);
		}
		{
			JButton buttonCancel = new JButton("Abbrechen");
			buttonCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonCancel.setBounds(285, 207, 117, 29);
			contentPanel.add(buttonCancel);
		}
		
		JPanel panelChosenProjectBox = new JPanel();
		panelChosenProjectBox.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelChosenProjectBox.setBounds(18, 53, 170, 29);
		contentPanel.add(panelChosenProjectBox);
		panelChosenProjectBox.setLayout(null);
		
		JLabel labelChosenProject = new JLabel(restfunction.getChosenProject().getProjectNr() + " " + restfunction.getChosenProject().getProjectName());
		labelChosenProject.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		labelChosenProject.setHorizontalAlignment(SwingConstants.CENTER);
		labelChosenProject.setBounds(6, 6, 158, 16);
		panelChosenProjectBox.add(labelChosenProject);
		
		JPanel panelChosenWorkPackageBox = new JPanel();
		panelChosenWorkPackageBox.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelChosenWorkPackageBox.setBounds(222, 53, 170, 29);
		contentPanel.add(panelChosenWorkPackageBox);
		panelChosenWorkPackageBox.setLayout(null);
		
		JLabel labelChosenWorkpackage = new JLabel(restfunction.getChosenWorkPackage().getWorkPackageName());
		labelChosenWorkpackage.setHorizontalAlignment(SwingConstants.CENTER);
		labelChosenWorkpackage.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		labelChosenWorkpackage.setBounds(6, 6, 158, 16);
		panelChosenWorkPackageBox.add(labelChosenWorkpackage);
		
		textFieldStartTime = new JTextField();
		textFieldStartTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldStartTime.setText(null);
			}
		});
		textFieldStartTime.setFocusTraversalKeysEnabled(false);
		textFieldStartTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_TAB) {
			        formatTime(textFieldStartTime);
			        textFieldEndTime.requestFocus();
			    }
			    else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			    	formatTime(textFieldStartTime);
			    	textFieldEndTime.requestFocus();
			    }
			}
		});
		textFieldStartTime.setBounds(176, 111, 62, 26);
		contentPanel.add(textFieldStartTime);
		textFieldStartTime.setColumns(10);
		
		datePicker = new JDateChooser();
		datePicker.setBounds(236, 111, 156, 26);
		contentPanel.add(datePicker);
		
		Date date = new Date();
		datePicker.setDate(date);
		
		JLabel labelStartTimeDate = new JLabel("Startzeit / Datum");
		labelStartTimeDate.setBounds(18, 116, 117, 16);
		contentPanel.add(labelStartTimeDate);
		
		textFieldEndTime = new JTextField();
		textFieldEndTime.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textFieldEndTime.setText(null);
			}
		});
		textFieldEndTime.setFocusTraversalKeysEnabled(false);
		textFieldEndTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
			    if (key == KeyEvent.VK_TAB) {
			        formatTime(textFieldEndTime);
			        textFieldSubject.requestFocus();
			    }
			    else if(key == KeyEvent.VK_ENTER){
			    	formatTime(textFieldEndTime);
			    	textFieldSubject.requestFocus();
			    }
			}
		});
		textFieldEndTime.setColumns(10);
		textFieldEndTime.setBounds(176, 138, 62, 26);
		contentPanel.add(textFieldEndTime);
		
		JLabel labelEndTime = new JLabel("Endzeit");
		labelEndTime.setBounds(18, 143, 117, 16);
		contentPanel.add(labelEndTime);
		
		JLabel labelSubject = new JLabel("Bezeichnung");
		labelSubject.setBounds(18, 171, 84, 16);
		contentPanel.add(labelSubject);
		
		textFieldSubject = new JTextField();
		textFieldSubject.setColumns(10);
		textFieldSubject.setBounds(176, 166, 217, 26);
		contentPanel.add(textFieldSubject);
	}
	
	private boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	
	private String formatTimeToString(String d) throws ParseException {
		String formattedDate;
		String oldFormat = "HH:mm";
		String newFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'+'SSSS";
		
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date b = sdf.parse(d);
		
		sdf.applyPattern(newFormat);
		formattedDate = sdf.format(b);
		
		return formattedDate;
	}
	
	private String formatDateToString(Date d) {
		String formattedDate;
		String oldFormat = "dd.MM.yyyy";
		String newFormat = "yyyy-MM-dd";
		
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date b = d;
		
		sdf.applyPattern(newFormat);
		formattedDate = sdf.format(b);
		
		return formattedDate;
	}
	
	private void formatTime(JTextField textField){
		String inputText = textField.getText();
		
		if(!inputText.isEmpty()) {
			if(isNumeric(inputText)) {
			
				String oldFormat = "HHmm";
				String newFormat = "HH:mm";
				
				SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
				Date d;
				try {
					d = sdf.parse(inputText);
					sdf.applyPattern(newFormat);
					String formattedTime = sdf.format(d);
					textField.setText(formattedTime);
				} catch (ParseException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(entryInsertView.this, "Ein Fehler bei der Formatierung der Zeit is aufgetreten!", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(entryInsertView.this, "Bitte gib eine gültige Zeit ein!", "Fehler", JOptionPane.ERROR_MESSAGE);
				textField.setText("");
			}
		}		
	}

	private void checkFieldValues(JTextField txtStartTime, JTextField txtEndTime, JTextField txtSubject) {
		if(!txtStartTime.getText().isEmpty() && !txtEndTime.getText().isEmpty() && !txtSubject.getText().isEmpty()){
			setFieldsEmpty(false);
		}
	}

}
