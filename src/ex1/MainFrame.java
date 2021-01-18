package ex1;


import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JToolBar;


import java.awt.ComponentOrientation;
import java.awt.Component;
import java.awt.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField tb_id;
	private JTextField tb_nume;
	private JTextField tb_varsta;
	private JTextField tb_page_no;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//STATIC VARIABLES
	public static List<Persoana> list_pers=new ArrayList<Persoana>();
	private static MyCon connection;
	private static Integer pageCounter=1;
	private static Integer numberOfPersons;
	
	
	//STATIC METHODS
	/**
	private static void closeButton(JButton button) {
		button.setEnabled(false);
	}
	private static void enableButton(JButton button) {
		button.setEnabled(true);
	}
	*/
	
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws Exception 
	 */
	
	public MainFrame(Persoana p) throws SQLException {
		list_pers.add(p);
		connection.addPerson(p);
		JOptionPane.showMessageDialog(null,"Persoana a fost adaugata cu succes !");
		this.setVisible(false);
		this.dispose();
	}
	
	
	public MainFrame() throws Exception {
		
		try {
			  connection=new MyCon();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,"Error :" + e,"Error",JOptionPane.ERROR_MESSAGE);
		}
		
		setTitle("Table Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 351, 281);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		list_pers=connection.getPersoane();
		
		PersoanaTableModel manageDB = new PersoanaTableModel(list_pers);
		
		String firstPersonName=list_pers.get(0).getNume();
		Persoana firstPerson=connection.searchPersoana(firstPersonName);
		
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 10, 326, 23);
		toolBar.setLocale(new Locale("ro", "RO"));
		toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		toolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		toolBar.setBorder(null);
		contentPane.add(toolBar);
		
		JButton b_back_begin = new JButton("");
		b_back_begin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pageCounter=1;
				tb_page_no.setText(1+"/"+numberOfPersons);
				tb_id.setText(firstPerson.getId());
				tb_nume.setText(firstPersonName);
				tb_varsta.setText(firstPerson.getVarsta());
			}
		});

		b_back_begin.setIcon(new ImageIcon("assets\\MoveFirst.png"));
		toolBar.add(b_back_begin);
		
		
		JButton b_back_one = new JButton("");
		b_back_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(pageCounter>1) {
					pageCounter--;
					tb_page_no.setText(pageCounter+"/"+numberOfPersons);
					Persoana tmp=list_pers.get(pageCounter-1);
					tb_id.setText(tmp.getId());
					tb_nume.setText(tmp.getNume());
					tb_varsta.setText(tmp.getVarsta());
				}
			}
		});
		b_back_one.setIcon(new ImageIcon("assets\\MovePrevious.png"));
		toolBar.add(b_back_one);
		
		tb_page_no = new JTextField();
		tb_page_no.setEditable(false);
		toolBar.add(tb_page_no);
		tb_page_no.setColumns(10);
		
		
		
		JButton b_advance_one = new JButton("");
		b_advance_one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(pageCounter<numberOfPersons) {
					pageCounter++;
					tb_page_no.setText(pageCounter+"/"+numberOfPersons);
					Persoana tmp=list_pers.get(pageCounter-1);
					tb_id.setText(tmp.getId());
					tb_nume.setText(tmp.getNume());
					tb_varsta.setText(tmp.getVarsta());
				}
			}
		});
		b_advance_one.setIcon(new ImageIcon("assets\\MoveNext.png"));
		toolBar.add(b_advance_one);
		
		JButton b_advance_end = new JButton("");
		b_advance_end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pageCounter=numberOfPersons;
				tb_page_no.setText(pageCounter+"/"+numberOfPersons);
				Persoana tmp=list_pers.get(pageCounter-1);
				tb_id.setText(tmp.getId());
				tb_nume.setText(tmp.getNume());
				tb_varsta.setText(tmp.getVarsta());
			}
		});
		b_advance_end.setIcon(new ImageIcon("assets\\MoveLast.png"));
		toolBar.add(b_advance_end);
		
		JButton b_add = new JButton("");
		b_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PersoanaFrame pf=new PersoanaFrame();
				pf.setVisible(true);
			}
		});
		b_add.setIcon(new ImageIcon("assets\\Add.png"));
		toolBar.add(b_add);
		
		JButton b_modify = new JButton("");
		b_modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tb_nume.setEditable(true);
				tb_varsta.setEditable(true);
				JButton saveButton=new JButton("Salveaza");
				saveButton.setVisible(true);
				saveButton.setBounds(148, 170, 85, 21);
				contentPane.add(saveButton);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Persoana p=new Persoana(tb_id.getText(),tb_nume.getText(),tb_varsta.getText());
						Persoana modificata=list_pers.get(Integer.parseInt(tb_id.getText())-1);
						modificata.setNume(tb_nume.getText());
						modificata.setVarsta(tb_varsta.getText());
						try {
							connection.modifyPerson(p);
							dispose();
							JOptionPane.showMessageDialog(null,"Persoana a fost modificata in baza de date !");
						} catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showInputDialog(this);
						}
					}
				});
				saveButton.setBounds(148, 170, 85, 21);
				contentPane.add(saveButton);
			}
		});
		b_modify.setIcon(new ImageIcon("assets\\Edit.png"));
		toolBar.add(b_modify);
		
		JButton b_delete = new JButton("");
		b_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Persoana p=new Persoana(tb_id.getText(),tb_nume.getText(),tb_varsta.getText());
				int dialogButton=JOptionPane.showConfirmDialog(null, "Esti sigur ca doresti sa-l/o stergi pe "+p.getNume()+" ?","Atentie !",JOptionPane.YES_NO_OPTION);
				if(dialogButton==JOptionPane.YES_OPTION) {
					list_pers.remove(p);
					try {
						JOptionPane.showMessageDialog(null,"Persoana a fost stearsa din baza de date !");
						connection.deletePerson(p);
						dispose();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(dialogButton==JOptionPane.NO_OPTION)
				{
					dispose();
				}
			}
		});
		b_delete.setIcon(new ImageIcon("assets\\Delete.png"));
		toolBar.add(b_delete);
		
		JButton b_search = new JButton("");
		b_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nume=JOptionPane.showInputDialog("Pe cine cautati ?");
				try {
					Persoana p=connection.searchPersoana(nume);
					if(p.getNume()!=null) {
						JOptionPane.showMessageDialog(null, "Persoana gasita pe pozitia "+p.getId());
						tb_id.setText(p.getId());
						tb_nume.setText(p.getNume());
						tb_varsta.setText(p.getVarsta());
					}
					else
						JOptionPane.showMessageDialog(null, "Persoana nu se afla in baza de date");
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Persoana nu se afla in baza de date");
					e.printStackTrace();
				}
			}
		});
		b_search.setIcon(new ImageIcon("assets\\find.JPG"));
		toolBar.add(b_search);
		
		JButton b_save = new JButton("");
		b_save.setIcon(new ImageIcon("assets\\save.JPG"));
		toolBar.add(b_save);
		
		JButton b_undo = new JButton("");
		b_undo.setIcon(new ImageIcon("assets\\undo.JPG"));
		toolBar.add(b_undo);
		
		tb_id = new JTextField();
		tb_id.setEditable(false);
		tb_id.setBounds(115, 72, 145, 19);
		contentPane.add(tb_id);
		tb_id.setColumns(10);
		
		tb_nume = new JTextField();
		tb_nume.setEditable(false);
		tb_nume.setColumns(10);
		tb_nume.setBounds(115, 101, 145, 19);
		contentPane.add(tb_nume);
		
		tb_varsta = new JTextField();
		tb_varsta.setEditable(false);
		tb_varsta.setColumns(10);
		tb_varsta.setBounds(115, 130, 145, 19);
		contentPane.add(tb_varsta);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(51, 75, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNume = new JLabel("Nume");
		lblNume.setBounds(51, 101, 45, 13);
		contentPane.add(lblNume);
		
		JLabel lblVarsta = new JLabel("Varsta");
		lblVarsta.setBounds(51, 133, 45, 13);
		contentPane.add(lblVarsta);
		
		tb_id.setText(firstPerson.getId());
		tb_nume.setText(firstPersonName);
		tb_varsta.setText(firstPerson.getVarsta());
		
		numberOfPersons=manageDB.getRowCount();
		pageCounter=Integer.parseInt(tb_id.getText());
		
		
		tb_page_no.setText(pageCounter+"/"+numberOfPersons);
		
	}
	public void dispatchEvent(WindowEvent windowEvent) {
		System.exit(0);
	}
}
