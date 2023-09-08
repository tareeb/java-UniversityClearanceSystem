
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseController {

    Connection con = null ;
	Statement  stm = null ;
	
	DatabaseController(String username , String password ){
		con = makeconnection(username , password)  ;
		stm = makestatment(con) ; 
	}

	private Connection makeconnection(String username , String password) {
    	
        String url = "jdbc:sqlserver://DESKTOP-75OFPST:1433;databaseName=MCS";  //Database URL
        
        Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);         // to make connection
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    	return con ;
    }

    private Statement makestatment(Connection con) {
    	
    	Statement stm = null ;
    	try {
			stm = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stm;
		
    }

    

    public boolean[] signin(boolean isStudent , String userid , String Password  ){

        boolean arr[]= {false , false} ;

        String Query2 = "" ; 
        String passwordColumn = "" ; 

        if(isStudent){
            Query2 = "Select * from student where StudentID = '"+ userid +"'" ; 
            passwordColumn = "Studentpassword" ;
        }else{
           Query2 =  "Select * from Employee where EID = '"+ userid +"'" ; 
           passwordColumn = "Employeepassword" ;
        }

        ResultSet result = null ; 
        try {
            result = stm.executeQuery(Query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String tempPassword = ""; 


        try {
            result.next();
            tempPassword = result.getString(passwordColumn);
            arr[0] = true ; 
        } catch (SQLException e) {
            arr[0] = false ;            // it shows if result set is empty // Userid Not found
            //e.printStackTrace();      
        }

        if(Password.equals( tempPassword )){
            arr[1] = true ; 
        }else{
            arr[1] = false ; 
        }

        return arr ; 
    }

    public ArrayList<employee> getemployeedata(String EID , String Firstname , String Lastname , String Position , String Dname){

        EID = EID + "%";
        Firstname = Firstname + "%";
        Lastname = Lastname + "%";
        Position = "%" + Position + "%";
        Dname = Dname + "%";

        ArrayList<employee> employeeData = new ArrayList<employee>();

        String Query3 = " select * from Employee " +                    /// used for search
        "inner join Department " +            
        "on Employee.DID = Department.DID " + 
        "where CONVERT(varchar(10), eid) LIKE '"+EID+"' and " +
        "FirstName like '"+Firstname+"' and LastName like '"+Lastname+"' and "+
        "Position like '"+Position+"' and Dname like '"+ Dname+"' " +
        "order by EID" ; 

        ResultSet result = null ; 
        try {
             result = stm.executeQuery(Query3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            
            while (result.next()) {

                employeeData.add(

                new employee(result.getInt("EID"), result.getString("FirstName"),
                result.getString("LastName"), result.getString("position"), 
                result.getInt("DID"),result.getString("dname"))
                
                );

            }


        } catch (SQLException e) {
           
            e.printStackTrace();
            
        }

        return employeeData ; 
    }

    public boolean[] Set_Clearifiers(int emp_id , int dept_id , int priority){
        boolean[] arr = {true , true , true} ;  
        // first for FK constraint EMP_ID  
        // Second for PK (EMP_ID,DEPT_ID)

        //insert into Clearifiers values ('23456',1,1)

        String Query4 = " insert into Clearifiers values("+emp_id+", "+dept_id+" , "+priority+" ) " ; 
        
        
        try {
            stm.executeUpdate(Query4);
        } 
        catch (SQLException e) {
            
            if(e.getErrorCode() == 547){            // Foriegn Key error Code
                arr[0] = false ; 
            }else if (e.getErrorCode() == 2627){    // Primary key Error code
                arr[1] = false ; 
            }else if (e.getErrorCode() == 3609  ){  // Trigger is called
                arr[2] = false ; 
                e.printStackTrace();
            }
        }

        return arr ; 
    }

    public student get_student_data(String studentId){

        String Query5 = "select * from Student " + 
        " inner join Course " + 
        " on Student.CID = Course.cid " +  
        " inner join Department " + 
        " on Course.DID = Department.DID " +
        " where StudentID = " + studentId  ;
        
        ResultSet result = null ;
		
    	try {	result = stm.executeQuery(Query5) ;
		} catch (SQLException e) {	
            e.printStackTrace();
		}

        student student = null ; 
        try {
            result.next() ; 

            student = new student(String.valueOf(result.getInt("studentId"))
            , result.getString("FirstName"), result.getString("LastName"),
             result.getString("StudentRank"), result.getString("CID"), 
             String.valueOf(result.getString("DID")), 
             result.getString("Dname")) ;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student ; 
    }

    
    public int apply_for_clearance(String studentId , String DID , int priority){

        // return 0 if no error 
        // returns 1 if unique key constraint
        // return 2 if clearance not allowed trigger is called

        // getting employees id present in clearifires table

        String Query5 = "select * from Clearifiers "+
                         "where ( DID = 0 or DID = "+ DID +" ) and Clearancepriority = " + priority ; 

        ResultSet result = null ;
        try {
            result = stm.executeQuery(Query5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String Query6 = " insert into clearance values " ; 

        try {
            while(result.next()){
                Query6 = Query6 + "( " + studentId + " , " +  result.getString("EID")  + "  , 0 , null ) ," ; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Query6 = Query6.substring(0, Query6.length()-1);

        try {
            stm.executeUpdate(Query6);
        } catch (SQLException e) {
            
            if(e.getErrorCode() == 2627){       // error code for unique constraint
                return 1 ; 
            }
            else if(e.getErrorCode() == 3609){       // roll back transction using trigger ocured
                return 2 ; 
            }else{
                e.printStackTrace();
            }
            
        }

        return 0 ; 

    }

    public ArrayList<clearance1> get_clearance_status_1(String StudentId){

        String Query7 = "select * from clearance " + 
        " inner join Employee " + 
        " on Employee.EID = clearance.EID " + 
        " where StudentID = " + StudentId ;

        ResultSet result = null ; 

        try {
            result = stm.executeQuery(Query7);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<clearance1> clearancedata = new ArrayList<clearance1>();

        try {
            while(result.next()){
                clearancedata.add(new clearance1(String.valueOf(result.getInt("EID")), 
                result.getString("FirstName"), result.getString("lastName"), 
                result.getString("Position"), result.getString("clearanceStatus"), 
                result.getString("comment")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clearancedata ; 
 
    }

    public ArrayList<clearance2> get_clearance_status_2(String EmployeeId){

        String Query7 = 
        " select * from clearance " + 
        " inner join Student " +  
        " on Student.StudentID = clearance.StudentID " +
        " where EID = "+ EmployeeId ; 

        ResultSet result = null ; 

        try {
            result = stm.executeQuery(Query7);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<clearance2> clearancedata = new ArrayList<clearance2>();

        try {
            while(result.next()){
                clearancedata.add(new clearance2(String.valueOf(result.getInt("StudentId")), 
                result.getString("FirstName"), result.getString("lastName"), 
                result.getString("StudentRank"), result.getString("CID"),
                result.getString("clearanceStatus"), result.getString("comment")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clearancedata ; 
    }



    public int update_clearance(String EmployeeId , String StudentId , int status , String comment){

        String Query8 ; 

        if(comment.equals("")){
            Query8 = "update clearance " + 
            " set ClearanceStatus = " + status + " , comment = null " + 
            " where studentid = " + StudentId + "  and EID = "+ EmployeeId +" "  ;
        }else{
            Query8 = "update clearance " + 
            " set ClearanceStatus = " + status + " , comment = '"+comment+"' " +
            " where studentid = " + StudentId + "  and EID = "+ EmployeeId +" "  ; 
        }
           
        
        int x ; 
        try {
             x = stm.executeUpdate(Query8); // x will return no of rows effected
        } catch (SQLException e) {
            e.printStackTrace();
            return 2 ; // if some error occur 
        }

        if(x==0){
            return 1 ;  // if no row get affected
        }
    
        return 0 ;  // 0 for successfull , 
    }

    public int restrict_clearance(){
        
        String Query9 ; 

        Query9 = " create trigger clearance_not_allowed " +
        " on clearance " +
        " instead of insert " + 
        " as " +
        " print'Currently Clearance is not Allowed' " + 
        " rollback transaction " + 
        " go " ; 

        try {
            stm.execute(Query9);
             
        } catch (SQLException e) {
            if(e.getErrorCode() == 2111){
                return 1 ; 
            }else{
                e.printStackTrace();
            } 
        }

        return 0 ;
    }

    public int allow_clearance(){
        
        String Query10 ; 

        Query10 = " drop trigger clearance_not_allowed "  ; 

        try {
            stm.execute(Query10);
             
        } catch (SQLException e) {
            if(e.getErrorCode() == 3701){
                return 1 ; 
            }else{
                e.printStackTrace();
            }
           
        }

        return 0 ;
    }

    public ResultSet get_department_vise_data(){
        String query11 = "select Department.dname, count(EID) as count  from Employee " +
        "inner join Department " +
        "on Employee.did = department.did " +
        "group by department.Dname " ;

        ResultSet result = null ; 
        try {
            result = stm.executeQuery(query11);
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return result ; 
        
    }


}