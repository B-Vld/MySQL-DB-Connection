package ex1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class PersoanaFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField tb_id2;
	public JTextField tb_nume2;
	public JTextField tb_varsta2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersoanaFrame frame = new PersoanaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static MainFrame m;
	public static boolean isDone;
	
	
	public boolean isDone() {
		if(isDone)
			return true;
		else
			return false;
	}
	
	/**
	 * Create the frame.
	 */
	public PersoanaFrame() {
		   
		setTitle("Adauga o persoana");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tb_id2 = new JTextField();
		tb_id2.setBounds(140, 79, 96, 19);
		contentPane.add(tb_id2);
		tb_id2.setColumns(10);
		
		tb_nume2 = new JTextField();
		tb_nume2.setColumns(10);
		tb_nume2.setBounds(140, 108, 96, 19);
		contentPane.add(tb_nume2);
		
		tb_varsta2 = new JTextField();
		tb_varsta2.setColumns(10);
		tb_varsta2.setBounds(140, 137, 96, 19);
		contentPane.add(tb_varsta2);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(85, 82, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNume = new JLabel("Nume");
		lblNume.setBounds(85, 111, 45, 13);
		contentPane.add(lblNume);
		
		JLabel lblVarsta = new JLabel("Varsta");
		lblVarsta.setBounds(85, 140, 45, 13);
		contentPane.add(lblVarsta);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id=tb_id2.getText();
				String nume=tb_nume2.getText();
				String varsta=tb_varsta2.getText();
				try {
					Persoana p=new Persoana(id,nume,varsta);
					MainFrame.list_pers.add(p);
					m=new MainFrame(p);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,"Invalid SQL Query");
					e.printStackTrace();
				}
				dispose();
				m.dispatchEvent(new WindowEvent(m, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnNewButton.setBounds(151, 165, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(151, 192, 85, 21);
		contentPane.add(btnCancel);
	}

}
