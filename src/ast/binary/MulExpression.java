package ast.binary;

import ast.Binary;
import ast.Type;
import java.util.ArrayList;

/**
 *
 * @author alan.whitehurst
 */
public class MulExpression extends Binary {
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        getLhs().generate(code, inFunction);
        code.add("\tpush");
        getRhs().generate(code, inFunction);
        code.add("\tmult");
    }
    
    @Override
    public Type getType() {
        if(getLhs().getType().compareTo(getRhs().getType())>=0){
            return getLhs().getType();
        } else {
            return getRhs().getType();
        }
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        getLhs().typeCheck(msgs);
        getRhs().typeCheck(msgs);
        if(!getType().isTypeCompatible(Type.INT_TYPE)){
            msgs.add(
                    String.format("Multiply operation (%s) is not on int at line %d, column %d.",
                            getType(),
                            getType().getSymbol().getLine(),
                            getType().getSymbol().getCol()));
        }
    }
    
}
