/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Assignment extends Statement {

    public Assignment() {
    }

    //create assigment 
    public Assignment(Token symbol) {
        super(symbol);
    }

    //set assignment boundries 
    public Assignment(VarRef v, Expression source) {
        super();
        this.varRef = v;
        this.expr = source;
    }

    //return expression 
    public Expression getExpr() {
        return expr;
    }

    //set expression
    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    //return variable referenace 
    public VarRef getVarRef() {
        return varRef;
    }

    public void setVarRef(VarRef v) {
        this.varRef = v;
    }
    

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        getExpr().typeCheck(msgs);
        if(!varRef.getType(true).isTypeCompatible(getExpr().getType())){
            msgs.add(
                    String.format("Type of expression (%s) does not match type of variable (%s) at line %d, column %d.",
                            getExpr().getType(),
                            varRef.getType(),
                            varRef.getVariable().getSymbol().getLine(),
                            varRef.getVariable().getSymbol().getCol()));
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int offset) {
        getExpr().generate(code, inFunction);
        if(inFunction){
            if(getVarRef().isArrayRef()){
                code.add("\tpush");
                getVarRef().getIndex().generate(code, inFunction);
                code.add("\taddc " + getVarRef().getScope().getRelOffset(getVarRef().getVariable().getName()));
                code.add("\tcora");
                code.add("\tsti");
            } else {
                if(getVarRef().isIndirect()){
                    code.add("\tpush");
                    code.add("\tldr " + getVarRef().getScope().getRelOffset(getVarRef().getVariable().getName()));
                    code.add("\tsti");
                } else {
                    code.add("\tstr " + getVarRef().getScope().getRelOffset(getVarRef().getVariable().getName()));
                }
            }
        } else {
            if(getVarRef().isArrayRef()){
                code.add("\tpush");
                getVarRef().getIndex().generate( code, inFunction);
                code.add("\taddc " + getVarRef().getQualifiedVariableName());
                code.add("\tsti");
            } else {
                if(getVarRef().isIndirect()){
                    code.add("\tpush");
                    code.add("\tld " + getVarRef().getQualifiedVariableName());
                    code.add("\tsti");
                } else {
                    code.add("\tst " + getVarRef().getQualifiedVariableName());
                }
            }
        }
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Assignment: ");
        sb.append(this.getVarRef().format(indent,true));
        sb.append(" ");
        if(Assignment.this.getExpr()!=null){
            sb.append(this.getExpr().format(indent,true));
        }
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private VarRef varRef;
    private Expression expr;
    
}
