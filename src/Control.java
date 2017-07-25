import java.util.*;
import java.io.*;

public class Control{
  
  Console console;
  String order;
  boolean open;
  
  
  /**
   * Method to take the user input for creating a recipe 
   * @param cnsl the console being used
   * @param com the Commands class 
   */
  public void add(Console cnsl,Commands com){
    String name, type, cuisine,main,addOns,sides,prep,cook;
    
    name = cnsl.readLine("Name:");
    type = cnsl.readLine("Type:");
    cuisine = cnsl.readLine("Cuisine:");
    main = cnsl.readLine("Main Ingredients:");
    addOns = cnsl.readLine("Add-ons:");
    sides = cnsl.readLine("Sides:");
    prep  = cnsl.readLine("Prep:");
    cook = cnsl.readLine("Cook:");
    
    com.add(name,type,cuisine,main,addOns,sides,prep,cook);
    
    System.out.println("The recipe has been added. What would master command next?");
    
  }
  
  /**
   * Method to delete a recipe which displays the recipe the user wants to delete and then asks for confirmation of deletion
   * @param cnsl the system console
   * @param com the commands class instant
   */
  public void delete(Console cnsl,Commands com){
    
    String name=cnsl.readLine("Name:");
    Recipe rec = com.display(name);
    if(rec != null){
      String line=cnsl.readLine("Do you wish to delete this recipe?");
      com.delete(rec,line);
    }else{
      System.out.println("What would master command next?"); 
    }
    
  }
  
  /**
   * Method which is used to find a recipe pertaining to certain parameters specified by the user
   * if recipe can be found, the recipe which comes closest and less to the time constraint is displayed otherwise an error message is given
   * @param cnsl the system console
   * @param com the commands class being used
   */
  public void find(Console cnsl,Commands com){
    String time=cnsl.readLine("How long(max) should the food take in total to prepare and cook(minutes)?:");
    String requiredItems=cnsl.readLine("What are the requirements?:");
    String excludedItems=cnsl.readLine("What are the exclusions?:");
    String cuisine =cnsl.readLine("What type of cuisine(s) can the recipe be?:");
    
    if( com.find(time,requiredItems,excludedItems,cuisine)){
      System.out.println("The recipes have been found. What would master command next?");
    }else{
      System.out.println("The recipe has not been found. What would master command next?");
    }
  }
  
  /**
   * Method which is used to plan a meal pertaining to certain parameters specified by the user
   * if plan can be generated, the plan which comes closest and less to the time constraint is displayed otherwise an error message is given
   * @param cnsl the system console
   * @param com the commands class being used
   */
  public void plan(Console cnsl,Commands com){
    String time=cnsl.readLine("How long(max) should the meal take in total to prepare and cook(minutes)?:");
    String requiredItems=cnsl.readLine("What are the requirements?:");
    String excludedItems=cnsl.readLine("What are the exclusions?:");
    String cuisine =cnsl.readLine("What type of cuisine(s) can the meal be?:");
    
    if( com.planMeal(time,requiredItems,excludedItems,cuisine)){
      System.out.println("The meal plan has been generated. What would master command next?");
    }else{
      System.out.println("A meal plan could not be generated. What would master command next?");
    }
  }
  
  /**
   * Method to write all the recipe to a new file
   * @param com the instance of the commands class being used
   */
  public void write( Commands com){
    try{ 
      BufferedWriter writer = new BufferedWriter(new FileWriter("newRecipes.txt"));;
      
      com.write(writer);
      
      writer.close();
    }catch(Exception e){
      e.printStackTrace(); 
    }
    
  }
  
  /**
   * The run method 
   * @param file the name of the file being used
   */
  public void run(String file){
    Commands commands = new Commands();
    commands.createDB(file);
    try{
      console = System.console();
      
      open = true;
      System.out.println("Welcome to the Electronic CookBook. What would you like to do with the recipes?");
      order = console.readLine();
      
    }catch(Exception e){
      e.printStackTrace();
    }
    
    while(open){
      
      //---------------------------------------------------------------------
      // if command is add
      if(order.compareTo("add")==0){
        add(console,commands);
        order = console.readLine();
      }
      //------------------------------------------------------------------------
      //if command is delete
      else if (order.compareTo("delete")==0){
        
        delete(console,commands);
        order = console.readLine();
      }
      //----------------------------------------------------------------------------
      //if command is find
      else if(order.compareTo("find")==0){
        find(console,commands);
        order = console.readLine();
      }
      
      //----------------------------------------------------------------------------
      //if command is plan
      else if(order.compareTo("plan")==0){
        
        plan(console,commands);
        
        order = console.readLine();
      }
      
      //----------------------------------------------------------------------------
      //if command is exit
      else if(order.compareTo("exit")==0){
        write(commands);
        open = false;
        
        
      }
      
    }
    
    
    
    
  }
  
  
  
  public static void main(String [] args){
    
    Control control = new Control();
    
 //  control.experiment();
      control.run("recipe_1000.txt");
    
  }
  
  public void experiment(){
    Commands command = new Commands(); 
    
   
    command.createDB("recipe_10000a.txt");
    
    long start,stop; 
    
    //experiment addition
    start = System.nanoTime();
    command.add("name","type","cuisine","main","sides","addOns","12","12");
    stop = System.nanoTime();
    
    long addTime = stop-start;
    
    //experiment deletion
    start = System.nanoTime();
    command.delete(command.display("name"),"yes");
    stop = System.nanoTime();
    
    long deleteTime=stop-start;
    
    //experiment find empty
    start = System.nanoTime();
    command.find("","","","");
    stop = System.nanoTime();
    long findTimeEmpty=stop-start;;
    
    //experiment find through all
    start = System.nanoTime();
    command.find("10000","","","");
    stop = System.nanoTime();
    long findTimeFull=stop-start;
    
    //experiment find through all
    start = System.nanoTime();
    command.planMeal("10000","","","");
    stop = System.nanoTime();
    long planTimeFull=stop-start;
    
    System.out.println(addTime+","+deleteTime+","+findTimeEmpty+","+findTimeFull+","+planTimeFull);
    
     start = System.nanoTime();
    write(command);
    stop = System.nanoTime();
    long writeTime=stop-start;
    
    System.out.println(writeTime);
  }
    
  
  
  
  
}