package Controller;

import Exceptions.ExpressException;
import Exceptions.ADTException;
import Exceptions.StatementException;
import Model.ADT.ADTDictionary;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.ADT.IADTStack;
import Model.Statements.IStmt;
import Model.Program_State.*;
import Model.Types.RefType;
import Model.Types.IType;
import Model.Values.IValue;
import Model.Values.RefValue;
import Repository.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final IRepository repo;
    private boolean displayFlag;
    private ExecutorService executor;


    public Controller(IRepository repo, boolean displayFlag){
        this.repo = repo;
        this.displayFlag = displayFlag;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public IRepository getRepository(){
        return repo;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void setProgramStates(List <ProgramState> programStates){
        repo.setPrgList(programStates);
    }

    /*
    HashMap<Integer, IValue> safeGarbageCollector(IADTDictionary<String, IValue> symbolTable, Map<Integer, IValue> heap){
        HashMap<Integer, IValue> garbage = new HashMap<>();

        for(var val : symbolTable.getDictionary().values()){
            if(val instanceof RefValue){
                int address = ((RefValue)val).getAddress();
                if(heap.containsKey(address)){
                    garbage.put(address, heap.get(address));
                }
                if(heap.containsKey(address)){
                    var value = heap.get(address);
                    while(value.getType() instanceof RefType){
                        int address2 = ((RefValue)value).getAddress();
                        if(heap.containsKey(address2)){
                            garbage.put(address2, heap.get(address2));
                        }
                        value = heap.get(address2);
                    }
                }
            }
        }
        return garbage;
    }



    public void allStep() throws ADTException, StatementException, ExpressException, IOException {
        var prg = repo.getCurrentState();
        repo.logPrgStateExec(prg);

        if(displayFlag)
            displayState(prg);

        while(!prg.getExecStack().isEmpty()){
            oneStep(prg);
            prg.getHeap().setContent(safeGarbageCollector(prg.getSymbolTable(), prg.getHeap().getContent()));
            repo.logPrgStateExec(prg);
        }
    }
    */

    public void addProgram(IStmt program){
        repo.addProgramState(new ProgramState(program));
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> programStates) {
        return programStates.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symbolTable){
        return symbolTable.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue ref = (RefValue) v; return ref.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<IValue> heapValues){
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue ref = (RefValue) v; return ref.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, IValue> heap){
        return heap.entrySet().stream()
                .filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<ProgramState> prgList){
        List<Integer> symTableAddresses = Objects.requireNonNull(prgList.stream()
                .map(p -> getAddrFromSymTable(p.getSymbolTable().getDictionary().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(Stream.empty()))
        .collect(Collectors.toList());

        prgList.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, IValue>) safeGarbageCollector(symTableAddresses,getAddrFromHeap(
                    p.getHeap().getContent().values()),p.getHeap().getContent()));
        });
    }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {

        prgList.forEach(prg->{
            try{
                repo.logPrgStateExec(prg);
                displayState(prg);
                repo.setPrgList(prgList);
            }
            catch (IOException e){
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream().filter(ProgramState::isNotCompleted).
                map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep)).collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future ->{
                    try{
                        return future.get();
                    }catch(InterruptedException | ExecutionException e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg->{
            try{
                repo.logPrgStateExec(prg);
            }
            catch (IOException e){
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        repo.setPrgList(prgList);
    }

    public void displayState(ProgramState state){
        System.out.println(state.toString() + "\n");
    }

    public void runTypeChecker() throws ADTException, StatementException, ExpressException {
        for (ProgramState state: repo.getPrgList()) {
            IADTDictionary<String, IType> typeTable = new ADTDictionary<>();

            if(!state.getExecStack().isEmpty()) {
                state.getExecStack().peek().typeCheck(typeTable);
            }
        }
    }

    public void allStep() throws InterruptedException, ADTException, StatementException, ExpressException {
        executor = Executors.newFixedThreadPool(2);
        runTypeChecker();

        List<ProgramState> prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdown();
        repo.setPrgList(prgList);

    }
}
