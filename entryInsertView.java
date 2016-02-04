import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

	private final JPanel contentPanel = new JPanel();
	private Rest restfunction;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
			JLabel lblBearbeiten = new JLabel("Leistungserfassung");
			lblBearbeiten.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
			lblBearbeiten.setBounds(18, 6, 272, 44);
			contentPanel.add(lblBearbeiten);
		}
		{
			JButton btnErfassen = new JButton("Erfassen");
			btnErfassen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btnErfassen.setBounds(6, 207, 117, 29);
			contentPanel.add(btnErfassen);
		}
		{
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnAbbrechen.setBounds(285, 207, 117, 29);
			contentPanel.add(btnAbbrechen);
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(18, 53, 170, 29);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNa = new JLabel("N/A");
		lblNa.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		lblNa.setHorizontalAlignment(SwingConstants.CENTER);
		lblNa.setBounds(6, 6, 158, 16);
		panel.add(lblNa);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_1.setBounds(222, 53, 170, 29);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("N/A");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		label.setBounds(6, 6, 158, 16);
		panel_1.add(label);
		
		textField = new JTextField();
		textField.setBounds(176, 111, 62, 26);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(236, 111, 156, 26);
		contentPanel.add(dateChooser);
		
		JLabel lblStartzeitDatum = new JLabel("Startzeit / Datum");
		lblStartzeitDatum.setBounds(18, 116, 117, 16);
		contentPanel.add(lblStartzeitDatum);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(176, 138, 62, 26);
		contentPanel.add(textField_1);
		
		JLabel lblEndzeit = new JLabel("Endzeit");
		lblEndzeit.setBounds(18, 143, 117, 16);
		contentPanel.add(lblEndzeit);
		
		JLabel lblBezeichnung = new JLabel("Bezeichnung");
		lblBezeichnung.setBounds(18, 171, 84, 16);
		contentPanel.add(lblBezeichnung);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(176, 166, 217, 26);
		contentPanel.add(textField_2);
	}
}
