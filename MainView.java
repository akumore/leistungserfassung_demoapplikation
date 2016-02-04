import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MainView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setTitle("B");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1040, 503);
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
		panelUserInformation.setBounds(696, 6, 326, 99);
		contentPane.add(panelUserInformation);
		panelUserInformation.setLayout(null);
		
		JLabel labelUserIcon = new JLabel("New label");
		labelUserIcon.setBounds(6, 6, 79, 87);
		panelUserInformation.add(labelUserIcon);
		
		JLabel labelLoggedInAs = new JLabel("Angemeldet als");
		labelLoggedInAs.setHorizontalAlignment(SwingConstants.CENTER);
		labelLoggedInAs.setBounds(97, 16, 223, 16);
		panelUserInformation.add(labelLoggedInAs);
		
		JLabel labelUserEmail = new JLabel("N/A");
		labelUserEmail.setHorizontalAlignment(SwingConstants.CENTER);
		labelUserEmail.setBounds(97, 41, 223, 16);
		panelUserInformation.add(labelUserEmail);
	}

}
