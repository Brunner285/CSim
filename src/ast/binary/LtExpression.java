/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.binary;

import ast.Binary;
import ast.Type;
import csim.Generator;
import java.util.ArrayList;

/**
 *
 * @author alan.whitehurst
 */
public class LtExpression extends Binary {
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        getLhs().generate(code, inFunction);
        code.add("\tpush");
        getRhs().generate(code, inFunction);
        code.add("\tscmp");
        code.add("\taddc 1");
        String ltLabel = Generator.getLabel();
        code.add("\tjz " + ltLabel);
        code.add("\tldc 0");
        code.add("\tja *+2");
        code.add(ltLabel + ":");
        code.add("\tldc 1");
    }
    
    @Override
    public Type getType() {
        return Type.BOOL_TYPE;
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        getLhs().typeCheck(msgs);
        getRhs().typeCheck(msgs);
        if(!getLhs().getType().isTypeCompatible(getRhs().getType())){
            msgs.add(
                    String.format("Relational operation on non-compatible types at line %d, column %d.",
                            getType().getSymbol().getLine(),
                            getType().getSymbol().getCol()));
        }
    }
    
}
