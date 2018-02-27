/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import csim.Generator;
import java.util.ArrayList;

/**
 *
 * @author Alan
 */
public class Conditional extends Statement {

    //get esential block
    public Block getElseBlock() {
        return elseBlock;
    }

    //set essential block
    public void setElseBlock(Block elseBlock) {
        this.elseBlock = elseBlock;
    }

    //get IfBlock value
    public Block getIfBlock() {
        return ifBlock;
    }

    //set IfBlock value
    public void setIfBlock(Block ifBlock) {
        this.ifBlock = ifBlock;
    }

    public Expression getTest() {
        return test;
    }

    public void setTest(Expression test) {
        this.test = test;
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (!getTest().getType().isTypeCompatible(Type.BOOL_TYPE)) {
            msgs.add(
                    String.format("Conditional test (%s) is not bool at line %d, column %d.",
                            getTest().getType(),
                            getTest().getSymbol().getLine(),
                            getTest().getSymbol().getCol()));
        }
        if(ifBlock!=null){
            ifBlock.typeCheck(msgs);
        }
        if(elseBlock!=null){
            elseBlock.typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int baseOffset) {
        getTest().generate(code, inFunction);
        String falseLabel = Generator.getLabel();
        code.add("\tjz " + falseLabel);
        if(getIfBlock()!=null){
            getIfBlock().generate(code, inFunction, baseOffset);
        }
        String exitLabel = Generator.getLabel();
        code.add("\tja " + exitLabel);
        code.add(falseLabel + ":");
        if(getElseBlock()!=null){
            getElseBlock().generate(code, inFunction, baseOffset);
        }
        code.add(exitLabel + ":");
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Conditional: ");
        sb.append(this.getTest().format(indent,true));
        sb.append("\n");
        sb.append(this.getIfBlock().format(indent+3,false));
        if(getElseBlock()!=null){
            sb.append(this.getElseBlock().format(indent+3,false));
        }
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Expression test = null;
    private Block ifBlock = null;
    private Block elseBlock = null;

    
    
}
