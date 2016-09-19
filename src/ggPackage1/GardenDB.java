package ggPackage1;

import java.util.ArrayList;
import java.sql.*;
import java.lang.Object;

public class GardenDB {
	
	private String JDBC_DRIVER; 
	private String DB_URL; 
	private String USER;
	private String PASS;
	
	public GardenDB(){
		this.JDBC_DRIVER = "com.mysql.jdbc.Driver";
		this.DB_URL = "jdbc:mysql://localhost/gardenguru?useSSL=false";
		this.USER = "root";
		this.PASS = "dude";
	}
	
	public void addCommonPlant(CommonPlant plant){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "INSERT INTO common (name, vegetable, companions, anticompanion, daystomaturity, size) "+
						"VALUES ('"+plant.getName()+"', '"+plant.vegeOrHerb()+"', '"+
						plant.getCompanion()+"', '"+plant.getAnticompanion()+"', '"+plant.getDays()+"', '"+
						plant.getSize()+"')";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	public void updateCommonPlant(CommonPlant plant){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "Update common "+
						"SET companions='"+plant.getCompanion()+"', anticompanion='"+plant.getAnticompanion()+
						"', daystomaturity='"+plant.getDays()+"', size='"+plant.getSize()+"' "+
						"WHERE name='"+plant.getName()+"'";

		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	public ArrayList<CommonPlant> returnCommonPlants(){
		Connection conn = null;
		Statement stmt = null;
		ArrayList<CommonPlant> result = new ArrayList<CommonPlant>();
		try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "SELECT * FROM common ORDER BY name";
		ResultSet list = stmt.executeQuery(sql);
		
		while(list.next()){
			String name = list.getString(2);
			String veg = list.getString(3);
			String compan = list.getString(4);
			String anticomp = list.getString(5);
			int days = list.getInt(6);
			double size = list.getDouble(7);
			
			boolean vegge = toVegge(veg);
			CommonPlant temp = new CommonPlant(name, vegge, size, days);
			temp.addCompanion(compan);
			temp.addAnticompanion(anticomp);
			result.add(temp);
		}
		}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	return result;
	}
	public ArrayList<String> getUsers(){
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> result = new ArrayList<String>();
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "SELECT username FROM users";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			String temp = rs.getString("username");
			result.add(temp);
		}
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	return result;
	}
	
	public String checkPW(String user){
		Connection conn = null;
		Statement stmt = null;
		String result = "";
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "SELECT password FROM users WHERE username = '"+user+"'";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
		result = rs.getString("password");
		}
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	return result;
	}
	
	public void addPlant(Plant plant, String user, String bed){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "INSERT INTO plants (name, common, daystomature, dateplanted, space, numberplanted, user, bed, veggie) "+
						"VALUES ('"+plant.getName()+"', '"+plant.getFamily()+"', '"+
						plant.getDays()+"', '"+plant.getPlanted()+"', '"+plant.getSize()+"', '"+
						plant.getNumberPlanted()+"', '"+user+"', '"+bed+"', '"+plant.vegeOrHerb()+"')";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	public ArrayList<Plant> returnBedPlants(String username, String bed){
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Plant> result = new ArrayList<Plant>();
		try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "SELECT * FROM plants WHERE user = '"+username+"' AND bed='"+bed+"'";
		ResultSet list = stmt.executeQuery(sql);
		
		while(list.next()){
			String name = list.getString(2);
			String common = list.getString(3);
			int days = list.getInt(4);
			String planted = list.getString(5);
			double size = list.getDouble(6);
			int numplanted = list.getInt(7);
			String veg = list.getString(8);
			boolean vegge = toVegge(veg);
			Plant temp = new Plant(name, common, vegge, days, planted, size, numplanted);

			result.add(temp);
		}
		}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	return result;
	}
	public void addBed(String user, String bed, Double size){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "INSERT INTO beds (bedname, user, bedsize) "+
						"VALUES ('"+bed+"', '"+user+"', '"+size+"')";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	
	public void removeBed(String user, String bed){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "DELETE FROM beds WHERE bedname='"+bed+"'";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	
	public void removeCommon(String common){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "DELETE FROM common WHERE name='"+common+"'";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	
	public void removePlant(String user, String bed, String plant){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "DELETE FROM plants WHERE user='"+user+"' and name='"+plant+"'";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}
	
	public ArrayList<Bed> getBeds(String username){
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Bed> result = new ArrayList<Bed>();
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "SELECT * FROM beds WHERE user='"+username+"'";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			String name = rs.getString("bedname");
			Double size = rs.getDouble("bedsize");
			Bed temp = new Bed(name, size);
			result.add(temp);
		}
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }
	return result;
	}
	
	public boolean toVegge(String b){
		if(b.equalsIgnoreCase("Vegetable")){
			return true;
		}
		else {
			return false;
		}
	}
	public void addUser(String user, String password){
		Connection conn = null;
		Statement stmt = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		stmt = conn.createStatement();
		String sql = "INSERT INTO users (username, password) "+
						"VALUES ('"+user+"', '"+password+"')";
		stmt.executeUpdate(sql);
		
		
	}
	 catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
	 }
	 catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
	}finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            conn.close();
	      }catch(SQLException se){
	      }// do nothing
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }
	   }

	}

	public static void main(String args[]) throws SQLException{
	GardenDB news = new GardenDB();
	ArrayList<Bed> temp = news.getBeds("Mike");
	System.out.println(temp.get(0).getName());
	}
	
}	


