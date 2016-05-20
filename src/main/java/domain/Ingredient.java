
package domain;

public class Ingredient {
    private int id,inStock,minStock,maxStock;
    private String name;
    
    public Ingredient(int id, String name, int inStock, int minStock, int maxStock){
        this.id = id;
        this.name = name;
        this.inStock = inStock;
        this.minStock = minStock;
        this.maxStock = maxStock;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public int getInStock(){
        return inStock;
    }
    
    public int getMinStock(){
        return minStock;
    }

    
    public int getMaxStock(){
        return maxStock;
    }

    public void setName(String newname){
        name = newname;
    }
    
    public void setAttInt(String what, int value){
        switch(what){
            default:
                break;
                
            case "id":
                id = value;
                break;
            
            case "inStock":
                inStock = value;
                break;
                
            case "minStock":
                minStock = value;
                break;    
            
            case "maxStock":
                maxStock = value;
                break;    
        }
    }
}
