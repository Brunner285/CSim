/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import lexer.Token;

/**
 *
 * @author Alan
 */
public class Operator extends PTNode {

    public Operator(int numChildren) {
        super(numChildren);
    }

    public Operator(Token symbol) {
        super(symbol);
    }
    
    @Override
    public String format(int indent){
        return getSymbol().getSymbol();
    }
    
    @Override
    public ast.Operator toAST(){
        ast.Operator operator = new ast.Operator(getSymbol());
        return operator;
    }
    
}
