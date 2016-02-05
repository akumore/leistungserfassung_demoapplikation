import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class loginView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldEmail;
	private JPasswordField textFieldPassword;



	public loginView(Rest restfunction) {
		
		setResizable(false);
		setModal(true);
		
		setLocationRelativeTo(null);
		setUndecorated(true);
		setBounds(100, 100, 402, 231);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel labelLoginTitle = new JLabel("Login");
		labelLoginTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		labelLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelLoginTitle.setBounds(6, 6, 390, 43);
		contentPanel.add(labelLoginTitle);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldEmail.setBounds(94, 71, 223, 26);
		contentPanel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel labelEmail = new JLabel("Skywalk E-Mail Adresse");
		labelEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		labelEmail.setHorizontalAlignment(SwingConstants.CENTER);
		labelEmail.setBounds(94, 57, 223, 16);
		contentPanel.add(labelEmail);
		
		textFieldPassword = new JPasswordField();
		textFieldPassword.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPassword.setBounds(94, 124, 223, 26);
		contentPanel.add(textFieldPassword);
		
		JLabel labelPasswort = new JLabel("Passwort");
		labelPasswort.setHorizontalAlignment(SwingConstants.CENTER);
		labelPasswort.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		labelPasswort.setBounds(94, 109, 223, 16);
		contentPanel.add(labelPasswort);
		
		JButton buttonLogin = new JButton("Anmelden");
		buttonLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					buttonLogin.doClick();
				}
			}
		});
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txtFieldEmail = textFieldEmail.getText();
				@SuppressWarnings("deprecation")
				String txtFieldPassword = textFieldPassword.getText();
				
	            if(!txtFieldEmail.isEmpty() && !txtFieldPassword.isEmpty()) {
	                    restfunction.getUser().setUsername(txtFieldEmail);
	                    restfunction.getUser().setPassword(txtFieldPassword);
	                    restfunction.setupRest();
                } else {
                    JOptionPane.showMessageDialog(loginView.this, "Invalid username or password", "Login", JOptionPane.ERROR_MESSAGE);
                }
	            dispose();
			}
		});
		buttonLogin.setBounds(143, 162, 123, 34);
		contentPanel.add(buttonLogin);
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		buttonCancel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		buttonCancel.setBounds(316, 199, 80, 26);
		contentPanel.add(buttonCancel);
	}
}
