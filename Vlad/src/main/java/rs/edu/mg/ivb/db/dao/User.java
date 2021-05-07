package rs.edu.mg.ivb.db.dao;


public class User {
    
    public Integer ID;
    
    public String Username;
    
    public String Password;
    
    public String FirstName;
    
    public String LastName;
    
    public String Email;
    
    public String Image;
    
    public void print(){
        System.out.printf("ID: %d\tUsername:%s\tPassword:%s\tFirstName:%s\tLastName:%s\tEmail:%s\n", ID, Username, Password, FirstName, LastName,Email );
    }
    
    
    public User(Integer ID,String Username,String Password,String FirstName,String LastName,String Email){
        this.ID=ID;
        this.Username=Username;
        this.Password=Password;
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
    }
    
}
