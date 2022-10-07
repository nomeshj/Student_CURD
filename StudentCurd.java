import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentCurd {
	
	public static void main(String[] args) {
		
		PreparedStatement st;
		ResultSet rs;
		Statement s;
		Connection con;
		int option,stud_id;
		String stud_name,stud_email,blood;
		Scanner sc= new Scanner(System.in);
		
		System.out.println("1. Insert Student data into Student table\n"
				+ "2. Update Student data into Student table\n"
				+ "3. Delete Student data from Student table\n"
				+ "4. Get a list of all Students\n");
		
		System.out.println("Enter your option: ");
		option=sc.nextInt();
		
		String jdbcurl="jdbc:oracle:thin:@localhost:1521:xe";
		String username="system";
		String password="system";
		
		
		try {
			con= DriverManager.getConnection(jdbcurl, username, password);
			if(con != null) {
				System.out.println("Connected");
				switch(option) {
				case 1:  
					System.out.println("enter Student ID");
					stud_id=sc.nextInt();
					System.out.println("enter Student Name");
					stud_name=sc.next();
					System.out.println("enter Student Email");
					stud_email=sc.next();
					System.out.println("enter Student Blood Type");
					blood=sc.next();
					
					
					String INSERT_STU_INFO= "INSERT INTO student VALUES(?,?,?,?)";
					st=con.prepareStatement(INSERT_STU_INFO);
					st.setInt(1,stud_id);
					st.setString(2,stud_name);
					st.setString(3,stud_email);
					st.setString(4,blood);
					
					
					try {
					int rows=st.executeUpdate();
					if(rows>0) {
						System.out.println("Inserted new row");		
					}
					}
					catch(java.sql.SQLIntegrityConstraintViolationException e)
					{
						System.out.println("Data not inserted.You need to enter unique student Id");
					}
				
				break;
				case 2: 
					System.out.println("enter Student_no for which you want to update Student information: ");
					stud_id=sc.nextInt();
					System.out.println("enter name to update:");
					stud_name=sc.next();
					System.out.println("enter email to update: ");
					stud_email=sc.next();					
					System.out.println("enter Blood Type to update: ");
					blood=sc.next();
					
					String UPDATE_STU_INFO = "update student SET name = ?,email=?,blood=? WHERE id = ?";
					
					st=con.prepareStatement(UPDATE_STU_INFO);
					st.setString(1, stud_name);
					st.setString(2,stud_email);
					st.setString(3, blood);
					st.setInt(4,stud_id);
					
					
					int row=st.executeUpdate();
					if(row>0) {
						System.out.println("An existing user was updated  successfully!");		
					}
					
				break;
				case 3: 
							System.out.println("Enter the Id of the student whose information has to be deleted");
							stud_id=sc.nextInt();
							
							String DELETE_STU_INFO = "delete from student where id = ?";
							st = con.prepareStatement(DELETE_STU_INFO);
							st.setInt(1, stud_id);
							int del_row=st.executeUpdate();
							
							if(del_row>0) {
								System.out.println("Deleted");
							}
							
				break;
				case 4: System.out.println("Getting the data all the students ");
				String SELECT_ALL_STU_INFO = "select * from student";
					s=con.createStatement();
					rs=s.executeQuery(SELECT_ALL_STU_INFO);
				
				while(rs.next()) {
					stud_id=rs.getInt(1);
					stud_name=rs.getString(2);
					stud_email=rs.getString(3);
					blood=rs.getString(4);
					
					
					System.out.println("Student_No: "+stud_id+" || Student_name: "+stud_name+" || Student_email: "+stud_email+" || Student Blood Type: "+blood+"\n");
				}
				break;
				
				default: System.out.println("Incorrect option ");
				
				}
				con.close();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

}