
// this clearance provide inforamtion for student 
// so it is employee focused and contains most data of an employee

public class clearance1 {
    
    String EID ; 
    String employee_FirstName  ;
    String employee_SecondName ; 
    String employee_Position ; 
    String ClearanceStatus ; 
    String Comment ; 

    public clearance1(String EID, String employee_FirstName, String employee_SecondName, String employee_Position, String ClearanceStatus, String Comment) {
        this.EID = EID;
        this.employee_FirstName = employee_FirstName;
        this.employee_SecondName = employee_SecondName;
        this.employee_Position = employee_Position;
        this.ClearanceStatus = ClearanceStatus;
        this.Comment = Comment;
    }


    public String getEID() {
        return this.EID;
    }

    public void setEID(String EID) {
        this.EID = EID;
    }

    public String getEmployee_FirstName() {
        return this.employee_FirstName;
    }

    public void setEmployee_FirstName(String employee_FirstName) {
        this.employee_FirstName = employee_FirstName;
    }

    public String getEmployee_SecondName() {
        return this.employee_SecondName;
    }

    public void setEmployee_SecondName(String employee_SecondName) {
        this.employee_SecondName = employee_SecondName;
    }

    public String getEmployee_Position() {
        return this.employee_Position;
    }

    public void setEmployee_Position(String employee_Position) {
        this.employee_Position = employee_Position;
    }

    public String getClearanceStatus() {
        return this.ClearanceStatus;
    }

    public void setClearanceStatus(String ClearanceStatus) {
        this.ClearanceStatus = ClearanceStatus;
    }

    public String getComment() {
        return this.Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

  
   

}
