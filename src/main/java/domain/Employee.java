package domain;

public class Employee {
    int id;
    String userName,name;
            
    public Employee(int id, String userName, String name){
        this.id = id;
        this.userName = userName;
        this.name = name;
    }
    
    public int getId(){
        return id;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public String getName(){
        return name;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
