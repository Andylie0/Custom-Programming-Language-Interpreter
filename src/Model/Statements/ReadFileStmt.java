package Model.Statements;

import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.IADTDictionary;
import Model.Expressions.IExp;
import Model.Program_State.ProgramState;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private final IExp expression;
    private final String v;

    public ReadFileStmt(IExp exp, String v) {
        this.expression = exp;
        this.v = v;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException, ExpressException{
        var sym = state.getSymbolTable();
        var file = state.getFileTable();

        if(!sym.containsKey(v))
            throw new StatementException("READ FILE Error: Variable "+v+" was not declared before");

        IValue value = sym.get(v);
        if(!value.getType().equals(new IntType()))
            throw new StatementException("READ FILE Error: Variable "+value+" is not of type int");

        value = expression.eval(sym, state.getHeap());
        if(!value.getType().equals(new StringType()))
            throw new StatementException("READ FILE Error: Expression "+value+" is not of type string");

        StringValue c_value = (StringValue) value;
        if(!file.containsKey(c_value.getValue()))
            throw new StatementException("READ FILE Error: File "+c_value.getValue()+" was not found");

        BufferedReader buff = file.get(c_value.getValue());
        try{
            String line = buff.readLine();
            if(line == null)
                line = "0";

            sym.put(v, new IntValue(Integer.parseInt(line)));
        }
        catch(IOException e){
            throw new StatementException("READ FILE Error: File "+c_value.getValue()+" did not open");
        }

        return null;
    }

    @Override
    public String toString(){
        return String.format("ReadFile(%s, %s)", expression, v);
    }

    @Override
    public IADTDictionary<String, IType> typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException, StatementException{
        if(!expression.typeCheck(typeTable).equals(new StringType()))
            throw new StatementException("READ FILE Error: Expression is not of type string");

        if(!typeTable.get(v).equals(new IntType()))
            throw new StatementException("READ FILE Error: Variable "+v+" is not of type int");

        return typeTable;
    }

}
