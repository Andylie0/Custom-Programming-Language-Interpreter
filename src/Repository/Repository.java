package Repository;

import Model.Program_State.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository{
    private final List<ProgramState> states;

    private final String logFilePath;

    public Repository(String logFilePath) {
        this.states = new LinkedList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public List <ProgramState> getPrgList() { return states; }

    @Override
    public void setPrgList(List<ProgramState> prgList) {
        this.states.clear();
        this.states.addAll(prgList);
    }

    @Override
    public void addProgramState(ProgramState state) {
        this.states.add(state);
    }

    @Override
    public void logPrgStateExec(ProgramState state) throws IOException{
        PrintWriter logfile;
        logfile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logfile.println(state.toString());
        logfile.close();
    }
}
