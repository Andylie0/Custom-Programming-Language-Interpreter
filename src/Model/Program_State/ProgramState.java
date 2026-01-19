package Model.Program_State;

import Exceptions.ADTException;
import Exceptions.ExpressException;
import Exceptions.StatementException;
import Model.ADT.*;
import Model.Statements.IStmt;
import Model.Values.IValue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class ProgramState {
    private final IADTStack<IStmt> execStack;
    private final IADTDictionary<String, IValue> symbolTable;
    private final IADTDictionary<String, BufferedReader> fileTable;
    private final IADTHeap heap;
    private final IADTList<String> out;
    public final Integer id;
    private static int lastId = 0;

    public ProgramState(IStmt initStmt) {
        execStack = new ADTStack<>();
        symbolTable = new ADTDictionary<>();
        fileTable = new ADTDictionary<>();
        heap = new ADTHeap();
        out = new ADTLists<>();
        id = setId();

        execStack.push(initStmt);
    }

    public ProgramState(IADTStack<IStmt> execStack, IADTDictionary<String, IValue> symbolTable, IADTDictionary<String, BufferedReader> fileTable,
                        IADTHeap heap, IADTList<String> out) {
        this.execStack = execStack;
        this.symbolTable = symbolTable;
        this.fileTable = fileTable;
        this.heap = heap;
        this.out = out;
        id = setId();
    }

    public IADTStack<IStmt> getExecStack() {
        return execStack;
    }

    public IADTDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public IADTDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IADTHeap getHeap() { return  heap; }

    public IADTList<String> getOut() {
        return out;
    }

    public Integer getId() { return id; }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public boolean isNotCompleted(){
        return !execStack.isEmpty();
    }

    public ProgramState oneStep() throws ADTException, StatementException, ExpressException {
        if (execStack.isEmpty()){
            throw new ADTException("EXECUTION STACK ERROR : Execution stack is empty.");
        }

        var crtStmt = execStack.pop();
        return crtStmt.execute(this);

    }

    public String executionStackToString(){
        StringBuilder sb = new StringBuilder();

        for(var stmt : execStack){
            sb.append(stmt.toString()).append("|");
        }

        return sb.toString();
    }

    public String symbolTableToString(){
        StringBuilder sb = new StringBuilder();

        for(var k : symbolTable.keySet()){
            sb.append(String.format("%s -> %s\n", k, symbolTable.get(k).toString()));
        }

        return sb.toString();
    }

    public String outToString(){
        StringBuilder sb = new StringBuilder();

        for(var s : out){
            sb.append(s).append("\n");
        }

        return sb.toString();
    }

    public String fileTableToString(){
        StringBuilder sb = new StringBuilder();

        for(var s : fileTable.keySet()){
            sb.append(s).append("\n");
        }

        return sb.toString();
    }

    public String heapToString(){
        StringBuilder sb = new StringBuilder();
        Map<Integer,IValue> map = heap.getContent();

        for(var s : map.keySet()){
            sb.append(String.format("%s -> %s\n", s, map.get(s).toString()));
        }

        return sb.toString();
    }

    @Override
    public String toString(){
        return String.format("------ID------\n%d\n------EXE_STACK------\n%s\n------SYM_TABLE------\n%s\n------OUT------\n%s\n------HEAP_TABLE------\n%s\n------FILE_TABLE------\n%s\n",
                id,executionStackToString(), symbolTableToString(), outToString(), heapToString(), fileTableToString());
    }






}
