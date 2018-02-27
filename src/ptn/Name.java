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
public class Name extends PTNode {

    public Name(Token s) {
       super(s);
    }
    
    @Override
    public String format(int indent){
        return toString();
    }
    
    @Override
    public ast.Name toAST(){
        return new ast.Name(symbol);
    }
    
    @Override
    public String toString(){
        return getSymbol().getSymbol();
    }
    
    
}
