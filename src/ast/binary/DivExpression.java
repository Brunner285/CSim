/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.binary;

import ast.Binary;
import ast.Type;
import java.util.ArrayList;

/**
 *
 * @author alan.whitehurst
 */
public class DivExpression extends Binary {
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        getLhs().generate(code, inFunction);
        code.add("\tpush");
        getRhs().generate(code, inFunction);
        code.add("\tdiv");
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
                    String.format("Divide operation (%s) is not on int at line %d, column %d.",
                            getType(),
                            getType().getSymbol().getLine(),
                            getType().getSymbol().getCol()));
        }
    }
    
}
