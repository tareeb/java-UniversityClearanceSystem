public class student {
    
    String StudentId ; 
    String FirstName; 
    String LastName ; 
    String StudentRank ; 
    String CID ; 
    String DID ; 
    String Dname ; 

    public student(String StudentId, String FirstName, String LastName, String StudentRank, String CID, String DID, String Dname) {
        this.StudentId = StudentId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.StudentRank = StudentRank;
        this.CID = CID;
        this.DID = DID;
        this.Dname = Dname;
    }

    public String getStudentId() {
        return this.StudentId;
    }

    public void setStudentId(String StudentId) {
        this.StudentId = StudentId;
    }

   
    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getStudentRank() {
        return this.StudentRank;
    }

    public void setStudentRank(String StudentRank) {
        this.StudentRank = StudentRank;
    }

    public String getCID() {
        return this.CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getDID() {
        return this.DID;
    }

    public void setDID(String DID) {
        this.DID = DID;
    }

    public String getDname() {
        return this.Dname;
    }

    public void setDname(String Dname) {
        this.Dname = Dname;
    }

}
