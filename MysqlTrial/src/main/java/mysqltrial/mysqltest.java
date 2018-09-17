package mysqltrial;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Servclass
 */
@WebServlet("/Servclass")
public class mysqltest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mysqltest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init(){
    	
    }
    
    public static final String getStatement = "select * from Random";
    public static final String updateStatement = "update Random set VALUE=?? where VALUE in ([])";
    public static final String insertStatment = "insert into Random (VALUE) values (?)";
	public static final String deleteStatement = "delete from Random where MINUTE(DATE)%2=0";
	public static final String selDeleteStatement = "select * from Random where MINUTE(DATE)%2=0";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ipAddress = request.getRemoteAddr();
		Date date = new Date();
		String time = date.toString();
		
		PrintWriter out = response.getWriter();
		out.println(" Initial Get : ");
		out.println(getData());
		
		int randomValue = 10;
		out.println(" Insert Random Value: " + randomValue);
		String insertStatus = insertData(randomValue);
		if (insertStatus!=null){out.println(insertStatus);}

        int updateRandomValue = randomValue + 5; 		
		out.println(" Update Random Value: " + updateRandomValue);
		String updateStatus = updateData(updateRandomValue, randomValue);
		if (updateStatus!=null){out.println(updateStatus);}
		
		out.println(" Final Get : ");
		out.println(getData());
		
		out.println(" Delete Will Delete if minute is div by 2 : ");
		out.println(deleteData());
		
		out.println("IPAddress: " +ipAddress+ " \n Time: " + time);
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
	}
	
	public void destroy(){
	
	}
	
	public String insertData(int randomValue){
	 try{
	 Class.forName("com.mysql.jdbc.Driver").newInstance();;  
    Connection con=DriverManager.getConnection("jdbc:mysql://db:3306/petclinic","petclinic","password");  

    Statement stmt=con.createStatement();  
    String insert = insertStatment.replace("?", randomValue+"");
    stmt.executeUpdate(insert);
     con.close();  
    }catch(Exception e){
        return e.toString();
    }
    return null;
}

	
	public String updateData(int randomValue, int oldRandomValue){
	     String update ="";
	 try{
	 Class.forName("com.mysql.jdbc.Driver").newInstance();;  
    Connection con=DriverManager.getConnection("jdbc:mysql://db:3306/petclinic","petclinic","password");  

    Statement stmt=con.createStatement();
        update = updateStatement.replace("??", randomValue+"");
        update = update.replace("[]", oldRandomValue+"");
    stmt.executeUpdate(update);
    con.close();  
    }catch(Exception e){return update + " " + e.toString();}
        return null;
	}
	
	public String getData(){
	
	 String result = "";
	 try{
	 Class.forName("com.mysql.jdbc.Driver").newInstance();;  
    Connection con=DriverManager.getConnection("jdbc:mysql://db:3306/petclinic","petclinic","password");  

    Statement stmt=con.createStatement();  
    ResultSet rs=stmt.executeQuery(getStatement);
   while(rs.next()) {
      result =  result + "\n" + rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3) + " \n";  
      }
    con.close();  
    }catch(Exception e){
        result = "Error" + e.getMessage() + e.toString();
    }
	    return result;
	}
	public String deleteData(){
	  String result = "";
	 try{
	 Class.forName("com.mysql.jdbc.Driver").newInstance();;  
    Connection con=DriverManager.getConnection("jdbc:mysql://db:3306/petclinic","petclinic","password");  

    Statement stmt=con.createStatement();  
    ResultSet rs=stmt.executeQuery(selDeleteStatement);
   while(rs.next()) {
      result =  result + "\n" + rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3) + " \n";  
      }
      
      int status = stmt.executeUpdate(deleteStatement);
    con.close();  
    }catch(Exception e){
        result = "Error" + e.getMessage() + e.toString();
    }
	    return result;
	}

}

