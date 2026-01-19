package View;

import Model.ADT.ADTDictionary;
import View.Command.Command;

import java.util.HashMap;
import java.util.Scanner;

public class TextMenu {
    private ADTDictionary<String, Command> commands;
    public TextMenu() {
        this.commands = new ADTDictionary<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(), c);
    }

    private void printMenu(){
        for(var com : commands.getDictionary().values()){
            String line = String.format("%s. %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner sc = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            Command com = commands.get(choice);
            if(com == null) {
                System.out.println("Invalid choice");
                continue;
            }
            com.execute();
        }
    }
}
