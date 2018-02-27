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
public class LandExpression extends Binary {

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        getLhs().generate(code, inFunction);
        String skipLabel = Generator.getLabel();
        code.add("\tjz " + skipLabel);
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
