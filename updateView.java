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
import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class updateView extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textFieldStartTime;
	private JTextField textFieldEndTime;
	private JTextField textFieldSubject;
	private JLabel labelChosenWorkpackage;
	private JLabel labelChosenProject;
	private JLabel labelEntryId;
	
	private JDateChooser datePicker;
	
	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	Date date = new Date();
	
	private boolean fieldsEmpty;
	
	// Getter
	public boolean areFieldsEmpty() { return fieldsEmpty; }
	
	// Setter
	public void setFieldsEmpty(boolean b) { this.fieldsEmpty = b; }
	
	
	// Constructor
	public updateView(Rest restfunction, String entryId, String Starttime, String date, String endTime, String subject, String pNr, String pName, String wp) throws ParseException {
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
			JLabel labelDialogTitle = new JLabel("Bearbeiten");
			labelDialogTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
			labelDialogTitle.setBounds(18, 5, 127, 44);
			contentPanel.add(labelDialogTitle);
		}
		{
			JButton buttonSave = new JButton("Speichern");
			buttonSave.requestFocus();
			buttonSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!areFieldsEmpty()) {
						try {
							String startTime = formatTimeToString(textFieldStartTime.getText());
							String endTime = formatTimeToString(textFieldEndTime.getText());
							String entryDate = formatDateToString(datePicker.getDate());
							String subject = textFieldSubject.getText();
							
							restfunction.updateEntry(labelEntryId.getText(), subject, entryDate, startTime, endTime);
							dispose();
							
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(updateView.this, "Bitte füllen Sie alle Felder aus!", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			buttonSave.setBounds(6, 207, 117, 29);
			contentPanel.add(buttonSave);
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
		
		labelChosenProject = new JLabel(pNr + " " + pName);
		labelChosenProject.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		labelChosenProject.setHorizontalAlignment(SwingConstants.CENTER);
		labelChosenProject.setBounds(6, 6, 158, 16);
		panelChosenProjectBox.add(labelChosenProject);
		
		JPanel panelChosenWorkpackageBox = new JPanel();
		panelChosenWorkpackageBox.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelChosenWorkpackageBox.setBounds(222, 53, 170, 29);
		contentPanel.add(panelChosenWorkpackageBox);
		panelChosenWorkpackageBox.setLayout(null);
		
		labelChosenWorkpackage = new JLabel(wp);
		labelChosenWorkpackage.setHorizontalAlignment(SwingConstants.CENTER);
		labelChosenWorkpackage.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		labelChosenWorkpackage.setBounds(6, 6, 158, 16);
		panelChosenWorkpackageBox.add(labelChosenWorkpackage);
		
		textFieldStartTime = new JTextField();
		textFieldStartTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
			    if (key == KeyEvent.VK_TAB) {
			        formatTime(textFieldStartTime);
			    }
			    else if(key == KeyEvent.VK_ENTER){
			    	formatTime(textFieldStartTime);
			    }
			}
		});
		textFieldStartTime.setBounds(176, 111, 62, 26);
		contentPanel.add(textFieldStartTime);
		textFieldStartTime.setColumns(10);
		textFieldStartTime.setText(Starttime);
		
		datePicker = new JDateChooser();
		datePicker.setBounds(236, 111, 156, 26);
		contentPanel.add(datePicker);
		datePicker.setDate(stringToDate(date));
		
		JTextFieldDateEditor editor = (JTextFieldDateEditor) datePicker.getDateEditor();
		editor.setEditable(false);
		
		JLabel labelStartTimeDate = new JLabel("Startzeit / Datum");
		labelStartTimeDate.setBounds(18, 116, 117, 16);
		contentPanel.add(labelStartTimeDate);
		
		textFieldEndTime = new JTextField();
		textFieldEndTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
			    if (key == KeyEvent.VK_TAB) {
			        formatTime(textFieldStartTime);
			    }
			    else if(key == KeyEvent.VK_ENTER){
			    	formatTime(textFieldStartTime);
			    }
			}
		});
		textFieldEndTime.setColumns(10);
		textFieldEndTime.setBounds(176, 138, 62, 26);
		textFieldEndTime.setText(endTime);
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
		textFieldSubject.setText(subject);
		contentPanel.add(textFieldSubject);
		
		labelEntryId = new JLabel(entryId);
		labelEntryId.setFont(new Font("Lucida Grande", Font.ITALIC, 10));
		labelEntryId.setBounds(157, 24, 61, 16);
		contentPanel.add(labelEntryId);
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
	
	private Date stringToDate(String sDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date d;
		d = sdf.parse(sDate);
	    return d;
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(updateView.this, "Bitte geben Sie eine gültige Zeit ein!", "Fehler", JOptionPane.ERROR_MESSAGE);
				textField.setText("");
			}
		}
	}
}
