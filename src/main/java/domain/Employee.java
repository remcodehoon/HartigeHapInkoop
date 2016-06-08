package domain;

public class Employee {
    int id;
    String userName;
            
    public Employee(int id, String userName){
        this.id = id;
        this.userName = userName;
    }
    
    public int getId(){
        return id;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
}
