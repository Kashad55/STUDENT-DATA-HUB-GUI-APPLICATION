package JDBC_Demo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel luid, lpass;
	JTextField tuid;
	JPasswordField tpass;
	JButton login, exit;
	JPanel p1, p2, p3, mp;
	JOptionPane JOP;
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	LoginPage(){
		luid = new JLabel("User ID");
		lpass = new JLabel("Password");
		tuid = new JTextField(10);
		tpass = new JPasswordField(10);
		login = new JButton("login");
		exit = new JButton("exit");
		p1 = new JPanel(new GridLayout(1,2,10,10));
		p2 = new JPanel(new GridLayout(1,2,10,10));
		p3 = new JPanel(new GridLayout(1,2,10,10));
		mp = new JPanel(new GridLayout(3,2,10,10));
		p1.add(luid);
		p1.add(tuid);
		p2.add(lpass);
		p2.add(tpass);
		p3.add(login);
		p3.add(exit);
		mp.add(p1);
		mp.add(p2);
		mp.add(p3);
		add(mp);

		setLayout(new FlowLayout());

		setTitle("Login Page");
		setBounds(200, 200, 500, 500);
		setVisible(true);
		login.addActionListener(this);
		JOP = new JOptionPane();
		con=JDBC_Connection.make_con();      // Establish database connection
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == login){
			String username = tuid.getText();
			String password = new String(tpass.getPassword());
		
		
			try {
				// Prepare SQL statement
				pst=con.prepareStatement("Select * from userdetails where username=? and password=?");
				pst.setString(1, username);
				pst.setString(2, password);
				rs=pst.executeQuery();
				if(rs.next())
				{
					JOP.showMessageDialog(this,"Login Success !","success",JOP.INFORMATION_MESSAGE);
					this.setVisible(false);
					new HomePage();     // Open new window on successful login
				}
				else
				{
					JOP.showMessageDialog(this,"User ID or Password is incorrect !","ERROR",JOP.ERROR_MESSAGE);
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource() == exit){
			System.exit(0);
		}
	}
	public static void main(String [] args){
		new LoginPage();
	}
}
class HomePage extends JFrame implements ActionListener{
	JButton insert, delete, search, display;
	JPanel mp;
	HomePage(){
		insert = new JButton("Insert");
		delete = new JButton("Delete");
		search = new JButton("Search");
		display = new JButton("Display");
		mp = new JPanel(new GridLayout(4,1,10,10));

		mp.add(insert);
		mp.add(delete);
		mp.add(search);
		mp.add(display);
		add(mp);

		setLayout(new FlowLayout());

		setTitle("Home Page");
		setBounds(200, 200, 500, 500);
		setVisible(true);
		insert.addActionListener(this);
		delete.addActionListener(this);
		search.addActionListener(this);
		display.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == insert){
			this.setVisible(false);
			new InsertPage();
		}
		if(e.getSource() == delete){
			this.setVisible(false);
			new DeletePage();
		}
		if(e.getSource() == search){
			this.setVisible(false);
			new SearchPage();
		}
		if(e.getSource() == display){
			this.setVisible(false);
			new DisplayPage();
		}
	}
}
class InsertPage extends JFrame implements ActionListener{
	Connection con;
	PreparedStatement pst;
	JLabel name, roll, address, contact;
	JTextField tname, troll, taddress, tcontact;
	JButton insert, clear, home;
	JPanel p1, p2, p3, p4,p5,mp;
	InsertPage(){
		name = new JLabel("Name");
		roll = new JLabel("Roll Number");
		address = new JLabel("Address");
		contact = new JLabel("Contact Number");
		tname = new JTextField(10);
		troll = new JTextField(10);
		taddress = new JTextField(10);
		tcontact = new JTextField(10);
		insert = new JButton("Insert");
		clear = new JButton("clear");
		home = new JButton("Home");
		p1 = new JPanel(new GridLayout(1,2,10,10));
		p2 = new JPanel(new GridLayout(1,2,10,10));
		p3 = new JPanel(new GridLayout(1,2,10,10));
		p4 = new JPanel(new GridLayout(1,3,10,10));
		p5 = new JPanel(new GridLayout(1,2,10,10));
		mp = new JPanel(new GridLayout(5,1,10,10));

		p1.add(name);
		p1.add(tname);
		p2.add(roll);
		p2.add(troll);
		p3.add(address);
		p3.add(taddress);
		p5.add(contact);
		p5.add(tcontact);
		p4.add(insert);
		p4.add(clear);
		p4.add(home);
		mp.add(p1);
		mp.add(p2);
		mp.add(p3);
		mp.add(p5);      //bcoz this panel added later
		mp.add(p4);
		add(mp);

		setLayout(new FlowLayout());

		setTitle("Insert Page");
		setBounds(200, 200, 500, 500);
		setVisible(true);
		con=JDBC_Connection.make_con();
		insert.addActionListener(this);
		clear.addActionListener(this);
		home.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == insert){
			String name=tname.getText();
			String address=taddress.getText();
			int roll=Integer.parseInt(troll.getText());
			long contact=Long.parseLong(tcontact.getText());
			try {
				//pst=con.prepareStatement("Insert into student(name, roll, address) values(?,?,?)");
				pst=con.prepareStatement("Insert into student values(?,?,?,?)");
				pst.setInt(1, roll);
				pst.setString(2, name);
				pst.setString(3, address);
				pst.setLong(4, contact);
				int res=pst.executeUpdate();
				if(res>=1)
				{
					JOptionPane.showMessageDialog(mp, res+" record is inserted successfully");
				}
					
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(mp, "Error in record  insertion ","InsertError",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}

		}
		if(e.getSource() == clear){
			
			troll.setText("");
			tname.setText("");
			taddress.setText("");
			tcontact.setText("");
			tname.requestFocus();    //It'll moves cursor to TextField-Name

		}
		if(e.getSource() == home){
			this.setVisible(false);
			new HomePage();
			
		}
	}
}
class DeletePage extends JFrame implements ActionListener{
		JLabel roll;
		JTextField troll;
		JButton delete, home;
		JPanel p1, p2, mp;
		Connection con;
		PreparedStatement pst;
		DeletePage(){
			roll = new JLabel("Roll Number");
			troll = new JTextField(10);
			delete = new JButton("Delete");
			home = new JButton("Home");
			p1 = new JPanel(new GridLayout(1,2,10,10));
			p2 = new JPanel(new GridLayout(1,2,10,10));
			mp = new JPanel(new GridLayout(2,1,10,10));

			p1.add(roll);
			p1.add(troll);
			p2.add(delete);
			p2.add(home);
			mp.add(p1);
			mp.add(p2);
			add(mp);

			setLayout(new FlowLayout());

			setTitle("Delete Page");
			setBounds(200, 200, 500, 500);
			setVisible(true);
			con=JDBC_Connection.make_con();
			delete.addActionListener(this);
			home.addActionListener(this);
			
		}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == delete){
			int roll=Integer.parseInt(troll.getText());
			try {
				
				pst=con.prepareStatement("Delete from student where roll=?");
				pst.setInt(1, roll);
				int res=pst.executeUpdate();
				if(res>=1)
				{
					JOptionPane.showMessageDialog(mp, "Roll no."+roll+" is deleted Successfully");
					troll.setText("");
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(mp, "Error in record  deletion ","DeleteError",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				
			}
			
		}
		if(e.getSource() == home){
			this.setVisible(false);
			new HomePage();
		}
	}
}
class SearchPage extends JFrame implements ActionListener{
	JLabel name, roll, address, contact;
	JTextField tname, troll, taddress, tcontact;
	JButton search, update, home;
	JPanel p1, p2, p3, p4, p5, mp;
	PreparedStatement pst, pst1;    //pst1 is used for Update-button
	Connection con;
	ResultSet rs;
	SearchPage(){
		name = new JLabel("Name");
		roll = new JLabel("Enter Roll Number :");
		address = new JLabel("Address");
		contact = new JLabel("Contact");
		tname = new JTextField(10);
		troll = new JTextField(10);
		taddress = new JTextField(10);
		tcontact = new JTextField(10);
		search = new JButton("Search");
		update = new JButton("Update");
		home = new JButton("Home");
		p1 = new JPanel(new GridLayout(1,2,10,10));
		p2 = new JPanel(new GridLayout(1,2,10,10));
		p3 = new JPanel(new GridLayout(1,2,10,10));
		p4 = new JPanel(new GridLayout(1,3,10,10));
		p5 = new JPanel(new GridLayout(1,2,10,10));
		mp = new JPanel(new GridLayout(5,1,10,10));

		p1.add(name);
		p1.add(tname);
		p2.add(roll);
		p2.add(troll);
		p3.add(address);
		p3.add(taddress);
		p4.add(search);
		p4.add(update);
		p4.add(home);
		p5.add(contact);
		p5.add(tcontact);
		mp.add(p2);
		mp.add(p1);
		mp.add(p3);
		mp.add(p5);
		mp.add(p4);
		add(mp);

		setLayout(new FlowLayout());

		setTitle("Search Page");
		setBounds(200, 200, 500, 500);
		setVisible(true);
		search.addActionListener(this);
		update.addActionListener(this);
		home.addActionListener(this);
		con = JDBC_Connection.make_con();
		try {
			pst = con.prepareStatement("select * from student where roll = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == search){
			int roll = Integer.parseInt(troll.getText());
			try {
				pst.setInt(1, roll);
				rs=pst.executeQuery();
				if(rs.next())
				{
					JOptionPane.showMessageDialog(mp, " record is found !");
					tname.setText(rs.getString(2));
					taddress.setText(rs.getString(3));
					tcontact.setText(rs.getString(4));
					troll.setEditable(false);				}	
				else {
					JOptionPane.showMessageDialog(mp, "Record Not Found ! ","SearchError",JOptionPane.ERROR_MESSAGE);				
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(mp, "Error in record search","SearchError",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				
			}
		}
		if(e.getSource() == update){
			int newroll = Integer.parseInt(troll.getText());
			String newname = tname.getText();
			String newaddress = taddress.getText();
			Long newcontact = Long.parseLong(tcontact.getText());
			try {
				pst1 = con.prepareStatement("update student set name = ? , address=?, contact = ? where roll = ?");
				pst1.setString(1, newname);
				pst1.setString(2, newaddress);
				pst1.setLong(3, newcontact);
				pst1.setInt(4, newroll);
				int res = pst1.executeUpdate();
				if(res >= 1)
				{
					JOptionPane.showMessageDialog(mp, res+" record is updated successfully");
				}
					
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(mp, "Error in record  updation ","updateError",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		if(e.getSource() == home){
			this.setVisible(false);
			new HomePage();
		}
	}
}
class DisplayPage extends JFrame implements ActionListener{
	JLabel name, roll, address, contact;
	JTextField tname, troll, taddress, tcontact;
	JButton prev, next, first, last, home;
	JPanel p1, p2, p3, p4, p5, mp;
	PreparedStatement pst;
	Connection con;
	ResultSet rs;
	DisplayPage(){
		name = new JLabel("Name");
		roll = new JLabel("Roll Number");
		address = new JLabel("Address");
		contact = new JLabel("Contact");
		tname = new JTextField(10);
		troll = new JTextField(10);
		taddress = new JTextField(10);
		tcontact = new JTextField(10);
		prev = new JButton("<");
		next = new JButton(">");
		first = new JButton("<<");
		last = new JButton(">>");
		home = new JButton("Home");
		p1 = new JPanel(new GridLayout(1,2,10,10));
		p2 = new JPanel(new GridLayout(1,2,10,10));
		p3 = new JPanel(new GridLayout(1,2,10,10));
		p4 = new JPanel(new GridLayout(1,5,10,10));
		p5 = new JPanel(new GridLayout(1,2,10,10));
		mp = new JPanel(new GridLayout(5,1,10,10));

		p1.add(name);
		p1.add(tname);
		p2.add(roll);
		p2.add(troll);
		p3.add(address);
		p3.add(taddress);
		p5.add(contact);
		p5.add(tcontact);
		p4.add(first);
		p4.add(prev);
		p4.add(next);
		p4.add(last);
		p4.add(home);
		mp.add(p1);
		mp.add(p2);
		mp.add(p3);
		mp.add(p5);
		mp.add(p4);
		add(mp);

		setLayout(new FlowLayout());

		setTitle("Display Page");
		setBounds(200, 200, 500, 500);
		setVisible(true);
		first.addActionListener(this);
		last.addActionListener(this);
		prev.addActionListener(this);
		next.addActionListener(this);
		home.addActionListener(this);
		con = JDBC_Connection.make_con();
		
		try {
			pst = con.prepareStatement("select * from student",ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void displayData() {
		try {
			tname.setText(rs.getString(1));
			troll.setText(rs.getString(2));
			taddress.setText(rs.getString(3));
			tcontact.setText(rs.getString(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == first){
			try {
				if(rs.first()) {
					displayData();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource() == prev){
			try {
				if(rs.previous()) {
					displayData();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource() == next){
			try {
				if(rs.next()) {
					displayData();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource() == last){
			try {
				if(rs.last()) {
					displayData();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource() == home){
			this.setVisible(false);
			new HomePage();
		}
	}
}