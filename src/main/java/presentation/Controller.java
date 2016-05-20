package presentation;

import domain.Ingredient;
import domain.Supplier;
import javax.swing.JFrame;

// De Controller bepaalt welk frame er zichtbaar is
public class Controller
{
   SupplierFrame frame2;
   SupplierOverviewPanel panel2_0;
   SupplierAddPanel panel2_1;
   SupplierUpdatePanel panel2_2;
   SupplierDeletePanel panel2_3;

   Mainmenu frame1;

   IngredientFrame frame3;
   IngredientOverviewPanel panel3_0;
   IngredientAddPanel panel3_1;
   IngredientUpdatePanel panel3_2;
   IngredientDeletePanel panel3_3;
   
   
// Maakt de controller aan
   public Controller()
   {
      frame1 = new Mainmenu(this);
      
      frame2 = new SupplierFrame(this);
      panel2_0 = new SupplierOverviewPanel(this);
      panel2_1 = new SupplierAddPanel(this);
      panel2_2 = new SupplierUpdatePanel(this);
      panel2_3 = new SupplierDeletePanel(this);
 
      frame3 = new IngredientFrame(this);
      panel3_0 = new IngredientOverviewPanel(this);
      panel3_1 = new IngredientAddPanel(this);
      panel3_2 = new IngredientUpdatePanel(this);
      panel3_3 = new IngredientDeletePanel(this);
   }
   
// Maakt een bepaald frame zichtbaar
   public void makeVisible( String framenaam){
       
        frame1.setVisible(false);
        frame2.setVisible(false);
        frame3.setVisible(false);

        switch(framenaam){
            case "Mainmenu":
                frame2.setVisible(false);
                frame3.setVisible(false);
                frame1.setVisible(true);
                break;
            
            case "Supplier_overview":
                frame1.setVisible(false);
                frame3.setVisible(false);
                
                frame2.setContentPane(panel2_0);
                panel2_0.refreshTable();
                panel2_0.setVisible(true);
                frame2.setVisible(true);
                break;
                
            case "Supplier_add":
                frame2.setContentPane(panel2_1);
                panel2_1.setVisible(true);
                frame2.setVisible(true);
                break;
            
            case "Supplier_update":
                frame2.setContentPane(panel2_2);
                panel2_2.setVisible(true);
                frame2.setVisible(true); 
                break;
            
            case "Supplier_delete":
                frame2.setContentPane(panel2_3);
                panel2_3.setVisible(true);
                frame2.setVisible(true);
                break;
                
            case "Ingredient_overview":
                frame1.setVisible(false);
                frame2.setVisible(false);
                
                frame3.setContentPane(panel3_0);
                panel3_0.refreshTable();
                panel3_0.setVisible(true);
                frame3.setVisible(true);
                break;
                
            case "Ingredient_add":
                frame3.setContentPane(panel3_1);
                panel3_1.setVisible(true);
                frame3.setVisible(true);
                break;
            
            case "Ingredient_update":
                frame3.setContentPane(panel3_2);
                panel3_2.setVisible(true);
                frame3.setVisible(true);
                break;
            
            case "Ingredient_delete":
                frame3.setContentPane(panel3_3);
                panel3_3.setVisible(true);
                frame3.setVisible(true);
                break;
            
            default:
                frame1.setVisible(true);
                frame2.setVisible(false);
                frame3.setVisible(false);                
                break;
        }
    }
   
   public void makeVisible( String framenaam, Ingredient ing){
        switch(framenaam){
            default:
                frame3.setContentPane(panel3_0);
                panel3_0.refreshTable();
                panel3_0.setVisible(true);
                break;
            
            case "Ingredient_update":
                frame3.setContentPane(panel3_2);
                panel3_2.setVisible(true);
                frame3.setVisible(true);
                panel3_2.setIngredient(ing);
                break;
            
            case "Ingredient_delete":
                frame3.setContentPane(panel3_3);
                panel3_3.setVisible(true);
                frame3.setVisible(true);
                panel3_3.setIngredient(ing);
                break;
        }
    }
   
    public void makeVisible( String framenaam, Supplier sup){
        switch(framenaam){
            default:
                frame2.setContentPane(panel2_0);
                panel2_0.refreshTable();
                panel2_0.setVisible(true);
                break;
            
            case "Supplier_update":
                frame2.setContentPane(panel2_2);
                panel2_2.setVisible(true);
                frame2.setVisible(true);
                panel2_2.setSupplier(sup);
                break;
            
            case "Supplier_delete":
                frame2.setContentPane(panel2_3);
                panel2_3.setVisible(true);
                frame2.setVisible(true);
                panel2_3.setSupplier(sup);
                break;
        }
    }
}