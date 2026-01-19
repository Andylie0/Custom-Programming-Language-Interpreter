package Model.Expressions;

import Exceptions.ExpressException;
import Model.ADT.IADTDictionary;
import Model.ADT.IADTHeap;
import Model.Types.IType;
import Model.Values.IValue;

public interface IExp {
    IValue eval(IADTDictionary<String, IValue> symTable, IADTHeap heap) throws ExpressException;

    IType typeCheck(IADTDictionary<String, IType> typeTable) throws ExpressException;
}
