public class employee {
    
    int EID          ; 
    String FirstName ;
    String LastName  ;
    String Position  ;
    int DID          ;  
    String Dname     ; 

   
    public employee(int EID, String FirstName, String LastName, String Position, int DID , String Dname) {
        this.EID = EID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Position = Position;
        this.DID = DID;
        this.Dname = Dname ; 
    }

    public int getEID() {
        return this.EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
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

    public String getPosition() {
        return this.Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public int getDID() {
        return this.DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public String getDname() {
        return this.Dname;
    }

    public void setDname(String Dname) {
        this.Dname = Dname;
    }


}
