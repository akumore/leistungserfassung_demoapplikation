import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class entryInsertView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Rest restfunction;
	private JTextField textFieldStartTime;
	private JTextField textFieldEndTime;
	private JTextField textFieldSubject;

	public entryInsertView(Rest restfunction) {
		setUndecorated(true);
		
		this.restfunction = restfunction;
		
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
		
		JLabel labelChosenProject = new JLabel("N/A");
		labelChosenProject.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		labelChosenProject.setHorizontalAlignment(SwingConstants.CENTER);
		labelChosenProject.setBounds(6, 6, 158, 16);
		panelChosenProjectBox.add(labelChosenProject);
		
		JPanel panelChosenWorkPackageBox = new JPanel();
		panelChosenWorkPackageBox.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelChosenWorkPackageBox.setBounds(222, 53, 170, 29);
		contentPanel.add(panelChosenWorkPackageBox);
		panelChosenWorkPackageBox.setLayout(null);
		
		JLabel labelChosenWorkpackage = new JLabel("N/A");
		labelChosenWorkpackage.setHorizontalAlignment(SwingConstants.CENTER);
		labelChosenWorkpackage.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		labelChosenWorkpackage.setBounds(6, 6, 158, 16);
		panelChosenWorkPackageBox.add(labelChosenWorkpackage);
		
		textFieldStartTime = new JTextField();
		textFieldStartTime.setBounds(176, 111, 62, 26);
		contentPanel.add(textFieldStartTime);
		textFieldStartTime.setColumns(10);
		
		JDateChooser datePicker = new JDateChooser();
		datePicker.setBounds(236, 111, 156, 26);
		contentPanel.add(datePicker);
		
		JLabel labelStartTimeDate = new JLabel("Startzeit / Datum");
		labelStartTimeDate.setBounds(18, 116, 117, 16);
		contentPanel.add(labelStartTimeDate);
		
		textFieldEndTime = new JTextField();
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
}
