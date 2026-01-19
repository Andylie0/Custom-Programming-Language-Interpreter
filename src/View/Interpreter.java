package View;

import Controller.Controller;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.Command.ExitCommand;
import View.Command.RunExampleCommand;
import com.sun.jdi.Value;


public class Interpreter {
    static void main() {
        //int v; v = 2; Print (v); -> 2
        IStmt statement = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        IRepository repo1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repo1,true);
        controller1.addProgram(statement);



        //int a; int b; a=2+3*5; b=a+1; Print(b); -> 18
        IStmt statement2 = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),
                                new ArithExp(new ValueExp(new IntValue(3)),new ValueExp(new IntValue(5)),'*'),'+')),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)),'+')),
                                        new PrintStmt(new VarExp("b"))))));
        IRepository repo2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repo2,true);
        controller2.addProgram(statement2);


        //bool a; int v; a=true;(If a Then v=2 Else v=3); Print(v) -> 2
        IStmt statement3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a",new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v",new ValueExp(new IntValue(3)))),new PrintStmt(new VarExp("v"))))));
        IRepository repo3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repo3,true);
        controller3.addProgram(statement3);

         /* string varf; varf = "test.in"; OpenReadFile("varf"); int varc; ReadFile("varf", "varc"); Print(varc);
           ReadFile("varf", "varc"); Print(varc); CloseReadFile("varf")  */
        IStmt statement4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf", new ValueExp(
                new StringValue("test.in"))),new CompStmt(new OpenRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new
                IntType()),new CompStmt(new ReadFileStmt(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                new CompStmt(new ReadFileStmt(new VarExp("varf"),"varc"),new CompStmt(new PrintStmt(new VarExp("varc")),
                        new CloseRFileStmt(new VarExp("varf"))))))))));

        IRepository repo4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repo4,true);
        controller4.addProgram(statement4);

        //int a; int b; a=5; b=7; (If a>b Then Print(a) Else Print(b))
        IStmt statement5 = new CompStmt(new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                        new IfStmt(new RelExp(new VarExp("a"),new VarExp("b"),">"),new PrintStmt(new VarExp("a")),
                                new PrintStmt(new VarExp("b")))))));
        IRepository repo5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repo5,true);
        controller5.addProgram(statement5);

        // int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        IStmt statement6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)),">"),new CompStmt(
                        new PrintStmt(new VarExp("v")),(new AssignStmt("v", new ArithExp(new VarExp("v"),
                        new ValueExp(new IntValue(1)), '-'))))), new PrintStmt(new VarExp("v")))));

        IRepository repo6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repo6,true);
        controller6.addProgram(statement6);

        //Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
        IStmt statement7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),new CompStmt(new HeapAllocStmt("v",
                new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),new CompStmt(
                new HeapAllocStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new VarExp("v")),
                new PrintStmt(new VarExp("a")))))));

        IRepository repo7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repo7,true);
        controller7.addProgram(statement7);

        //Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
        IStmt statement8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),new CompStmt(new HeapAllocStmt("v",
                new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),new CompStmt(
                new HeapAllocStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new HeapReadExpr(new VarExp("v"))),
                new PrintStmt(new ArithExp(new HeapReadExpr(new HeapReadExpr(new VarExp("a"))), new ValueExp(new IntValue(5)),'+')))))));

        IRepository repo8 = new Repository("log8.txt");
        Controller controller8 = new Controller(repo8,true);
        controller8.addProgram(statement8);

        //Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);

        IStmt statement9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v",
                new ValueExp(new IntValue(20))), new CompStmt(new PrintStmt(new HeapReadExpr(new VarExp("v"))), new CompStmt(
                new HeapWriteStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp(new HeapReadExpr(new VarExp("v")),
                new ValueExp(new IntValue(5)),'+'))))));

        IRepository repo9 = new Repository("log9.txt");
        Controller controller9 = new Controller(repo9,true);
        controller9.addProgram(statement9);

        //Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        IStmt statement10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v",
                new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                new CompStmt(new HeapAllocStmt("a", new VarExp("v")), new CompStmt(new HeapAllocStmt("v",
                        new ValueExp(new IntValue(30))), new PrintStmt(new HeapReadExpr(new HeapReadExpr(new VarExp("a")))))))));

        IRepository repo10 = new Repository("log10.txt");
        Controller controller10 = new Controller(repo10,true);
        controller10.addProgram(statement10);

        /* int v; Ref int a; v=10; new(a,22); fork(wH(a,30); v=32; print(v); print(rH(a))); print(v); print(rH(a)) */
        IStmt statement11 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a"
        ,new RefType(new IntType())),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(10))),new CompStmt(
                new HeapAllocStmt("a",new ValueExp(new IntValue(20))),new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a"
                ,new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(32))), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExpr(new VarExp("a"))))))),new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExpr(new VarExp("a")))))))));
        IRepository repo11 = new Repository("log11.txt");
        Controller controller11 = new Controller(repo11,true);
        controller11.addProgram(statement11);

        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExampleCommand("1", statement.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2",statement2.toString(),controller2));
        menu.addCommand(new RunExampleCommand("3",statement3.toString(),controller3));
        menu.addCommand(new RunExampleCommand("4",statement4.toString(),controller4));
        menu.addCommand(new RunExampleCommand("5",statement5.toString(),controller5));
        menu.addCommand(new RunExampleCommand("6",statement6.toString(),controller6));
        menu.addCommand(new RunExampleCommand("7",statement7.toString(),controller7));
        menu.addCommand(new RunExampleCommand("8",statement8.toString(),controller8));
        menu.addCommand(new RunExampleCommand("9",statement9.toString(),controller9));
        menu.addCommand(new RunExampleCommand("10",statement10.toString(),controller10));
        menu.addCommand(new RunExampleCommand("11",statement11.toString(),controller11));
        menu.addCommand(new ExitCommand("0","Exit."));
        menu.show();
    }
}
