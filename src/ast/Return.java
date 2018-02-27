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
public class Return extends Statement {

    public Return() {
    }

    public Return(Token symbol) {
        super(symbol);
    }

  
    public Return(Token symbol, Expression value) {
        super(symbol);
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
    
    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if(getValue()!=null){
            getValue().typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int offset) {
        if(getValue()!=null){
            getValue().generate(code, inFunction);
        }
        if(inFunction){
            code.add("\tret");
        }
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Return: ");
        if(getValue()!=null){
            sb.append(this.getValue().format(indent,true));
        }
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    private Expression value;

    
}
