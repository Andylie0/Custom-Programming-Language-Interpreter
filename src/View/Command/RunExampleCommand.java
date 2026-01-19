package View.Command;

import Controller.Controller;
import Exceptions.ADTException;
import Exceptions.ExpressException;
import Exceptions.StatementException;

import java.io.IOException;

public class RunExampleCommand extends Command{
    private final Controller ctrl;

    public RunExampleCommand(String key, String description, Controller ctrl) {
        super(key, description);
        this.ctrl = ctrl;
    }

    @Override
    public void execute(){
       try{
           ctrl.allStep();
       }
       catch(InterruptedException | ADTException | StatementException e){
           System.out.println(e.getMessage());
       }
    }
}
