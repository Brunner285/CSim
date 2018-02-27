package ast.binary;

import ast.Binary;
import ast.Type;
import csim.Generator;
import java.util.ArrayList;

/**
 *
 * @author alan.whitehurst
 */
public class LorExpression extends Binary {

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        getLhs().generate(code, inFunction);
        String skipLabel = Generator.getLabel();
        code.add("\tjnz " + skipLabel);
        getRhs().generate(code, inFunction);
        code.add(skipLabel + ":");
    }
    
    @Override
    public Type getType() {
        return Type.BOOL_TYPE;
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        getLhs().typeCheck(msgs);
        getRhs().typeCheck(msgs);
        if(getLhs().getType()!=Type.BOOL_TYPE || getRhs().getType()!=Type.BOOL_TYPE){
            msgs.add(
                    String.format("Logical operation on non-boolean types at line %d, column %d.",
                            getLhs().getSymbol().getLine(),
                            getLhs().getSymbol().getCol()));
        }
    }

}
