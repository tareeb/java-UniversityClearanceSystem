public class clearance2 {
    
    String studentId;
    String student_FirstName;
    String student_lastName;
    String student_rank;
    String student_CID ; 
    String ClearanceStatus ; 
    String Comment ; 


    public clearance2(String studentId, String student_FirstName, String student_lastName, String student_rank, String student_CID, String ClearanceStatus, String Comment) {
        this.studentId = studentId;
        this.student_FirstName = student_FirstName;
        this.student_lastName = student_lastName;
        this.student_rank = student_rank;
        this.student_CID = student_CID;
        this.ClearanceStatus = ClearanceStatus;
        this.Comment = Comment;
    }

    
    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudent_FirstName() {
        return this.student_FirstName;
    }

    public void setStudent_FirstName(String student_FirstName) {
        this.student_FirstName = student_FirstName;
    }

    public String getStudent_lastName() {
        return this.student_lastName;
    }

    public void setStudent_lastName(String student_lastName) {
        this.student_lastName = student_lastName;
    }

    public String getStudent_rank() {
        return this.student_rank;
    }

    public void setStudent_rank(String student_rank) {
        this.student_rank = student_rank;
    }

    public String getStudent_CID() {
        return this.student_CID;
    }

    public void setStudent_CID(String student_CID) {
        this.student_CID = student_CID;
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
