package Model.Statements;

import Exceptions.ADTException;
import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Program_State.ProgramState;
import Model.Types.IType;

public interface IStmt {
    ProgramState execute(ProgramState state) throws StatementException, ExpressException, ADTException;

    IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException;
}
