package SQL_Compiler;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.*;

import JDBC_Demo.JDBC_Connection;

public class SqlInterface extends JFrame implements ActionListener {

	DefaultTableModel DTM;
	String arr[]={};
	String data[][]={{},{}};

	JTextArea txtmain;
	JLabel lbstu;
	JTable jt;
	JScrollPane sp;
	JButton brun, bsave, bexit;
	JPanel p1, p2, p3, mp1, mp2, mp;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ResultSetMetaData rsmd;

	SqlInterface() {
		DTM = new DefaultTableModel(data, arr);
		brun = new JButton("Run");
		bsave = new JButton("Save");
		bexit = new JButton("Exit");

		jt = new JTable(DTM);

		setLayout(new FlowLayout());
		p1 = new JPanel(new GridLayout(1, 1, 10, 10));
		p2 = new JPanel();
		p3 = new JPanel(new GridLayout(1, 1));
		mp1 = new JPanel(new GridLayout(3, 1, 10, 10));
		mp2 = new JPanel(new BorderLayout());
		mp = new JPanel(new BorderLayout());

		txtmain = new JTextArea(5, 8);
		lbstu = new JLabel("Students details are");
		p1.add(txtmain);
		p2.add(brun);
		p2.add(bsave);
		p2.add(bexit);
		p3.add(lbstu);

		mp1.add(p2);
		mp1.add(p1);
		mp2.add(p3);

		// mp1.add(mp);
		// mp2.add(mp);
		mp.add(mp1, BorderLayout.NORTH);
		mp.add(mp2, BorderLayout.CENTER);
		mp2.add(p3, BorderLayout.NORTH);
		mp2.add(jt, BorderLayout.CENTER);
		add(mp);

		add(jt);
		int hsb = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
		int vsb = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		sp = new JScrollPane(jt, vsb, hsb);
		add(sp);
		mp2.add(sp);

		setTitle("Sql Interface");
		setVisible(true);
		setBounds(200, 200, 500, 500);

		brun.addActionListener(this);
		bsave.addActionListener(this);
		bexit.addActionListener(this);
		
		con = JDBC_Connection.make_con();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == brun) {
			try {
				String sqlcmd = txtmain.getText().trim();
				pst = con.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = pst.executeQuery();
				rsmd = rs.getMetaData();
				int cols = rsmd.getColumnCount();
				arr = new String[cols];
				for(int i=1; i<=cols; i++) {     //i=1 bcoz columnName start from 1
					arr[i-1] = rsmd.getColumnName(i);
				}
				int rows=0;
				
				while(rs.next()) rows++;
				
				data = new String[rows][cols];
				rs.beforeFirst();
				
				rows=0;
				while(rs.next()) {
					for(int i=1; i<=cols; i++) {
						data[rows][i-1] = rs.getString(i);
					}
					rows++;
				}
				DTM = new DefaultTableModel(data, arr);
				jt.setModel(DTM);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(mp, "Error in Querry ","SyntaxError",JOptionPane.ERROR_MESSAGE);
			}
		}

		if (ae.getSource() == bsave) {

		}

		if (ae.getSource() == bexit) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new SqlInterface();
	}
}
