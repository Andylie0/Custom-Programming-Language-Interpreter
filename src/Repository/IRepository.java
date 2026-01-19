package Repository;

import Model.Program_State.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    List<ProgramState> getPrgList();

    void setPrgList(List<ProgramState> prgList);

    void addProgramState(ProgramState newState);

    void logPrgStateExec(ProgramState state) throws IOException;
}
