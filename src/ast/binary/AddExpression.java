package ast.binary;

import ast.Binary;
import ast.Literal;
import ast.Type;
import java.util.ArrayList;

/**
 *
 * @author alan.whitehurst
 */
public class AddExpression extends Binary {

    @Override
    public void generate(   ArrayList<String> code, boolean inFunction) {
        if ((getLhs() instanceof Literal) && (getRhs() instanceof Literal)) {
            getLhs().generate(code, inFunction);
            code.add("\taddc " + ((Literal)getRhs()).toObject().toString());
        } else if (getLhs() instanceof Literal) {
            getRhs().generate(code, inFunction);
            code.add("\taddc " + ((Literal)getLhs()).toObject().toString());
        } else if (getRhs() instanceof Literal) {
            getLhs().generate(code, inFunction);
            code.add("\taddc " + ((Literal)getRhs()).toObject().toString());
        } else {
            getRhs().generate(code, inFunction);
            code.add("\tst @temp");
            getLhs().generate(code, inFunction);
            code.add("\tadd @temp");
        }
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
                    String.format("Add operation (%s) is not on int at line %d, column %d.",
                            getType(),
                            getType().getSymbol().getLine(),
                            getType().getSymbol().getCol()));
        }
    }


}
