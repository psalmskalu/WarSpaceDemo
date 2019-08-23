import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LogInFrame extends JFrame implements ActionListener, KeyListener {
	private TitledBorder titled;
	private JLabel lblPrompt = new JLabel("Enter Your name");
	private JTextField txtField = new JTextField(15);
	private JButton logButton = new JButton("Log In");
	private JPanel base = new JPanel();
	private GridBagLayout gb = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	private ImageIcon log = new ImageIcon("icons/key.gif");
	private ImageIcon logo = new ImageIcon("pictures/intro.png");
	private JLabel lblPic = new JLabel();
	private JPanel bground = new JPanel();
	
		
	public LogInFrame() {
	super("WarSpace Demo Version");
		titled = BorderFactory.createTitledBorder(null, " customise game settings...  ", 0, 0, new Font("Trebuchet MS", Font.BOLD + Font.ITALIC, 10),new Color(100,60, 90));
		logButton.setIcon(log);
		txtField.setHorizontalAlignment(JTextField.CENTER);
		lblPic.setIcon(logo);
		lblPic.setBackground(new Color(250, 250, 250));		
		bground.setBackground(new Color(250, 250, 250));
		base.setBackground(new Color(250, 250, 250));
		lblPrompt.setFont(new Font("trebuchet ms", Font.BOLD, 12));
		txtField.setFont(new Font("trebuchet ms", Font.BOLD + Font.ITALIC, 16));
		
						
				
		base.setLayout(new FlowLayout());
		base.setBorder(titled);
		
		base.add(lblPrompt);
		base.add(txtField);
		base.add(logButton);
	
		
				
		bground.setLayout(gb);
		constraints.gridx = 0;
		 constraints.gridy = 0;
		 constraints.ipady = 80;
		 constraints.gridwidth = 1;
		 constraints.gridheight = 1;
		 constraints.anchor = GridBagConstraints.NORTH;
		 constraints.fill = GridBagConstraints.BOTH;
		 gb.setConstraints(base, constraints);
		 bground.add(base);
		 
		 constraints.gridx = 0;
		 constraints.gridy = 1;
		 constraints.gridwidth = 1;
		 constraints.gridheight = 1;
		 constraints.anchor = GridBagConstraints.NORTH;
		 constraints.fill = GridBagConstraints.CENTER;
		 gb.setConstraints(lblPic, constraints);
		 bground.add(lblPic);
		
		setLayout(new GridLayout(1,1));
		add(bground);
		
		txtField.addKeyListener(this);		
		logButton.addActionListener(this);
		logButton.setMnemonic(KeyEvent.VK_C);
		setSize(400,350);
		setLocation(350,150);
		setVisible(true);
		setResizable(false);
		}
		
	protected void logIn(String keyIn){
	int i = 0;
	String word = keyIn;
				
									Toolkit kit = Toolkit.getDefaultToolkit();
									Image image = kit.getImage("shuttle.png");
									JFrame frame = new JFrame();
									Board gui = new Board(word);
									frame.add(gui);
									frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									frame.setSize(500, 500);
									frame.setLocationRelativeTo(null);
									frame.setTitle("Warship Demo Version");
									frame.setResizable(false);
									frame.setVisible(true);
									frame.setIconImage(image);
								dispose();
								
		}
		
		
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			logIn(txtField.getText());
			}
		}	
		
	public void keyReleased(KeyEvent e){
	}
	
	public void keyTyped(KeyEvent e){
	}
		
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logButton){
			logIn(txtField.getText());
		}
				
	}
}		