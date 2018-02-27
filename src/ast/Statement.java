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
public abstract class Statement extends ASTNode {

    public Statement() {
    }

    public Statement(Token symbol) {
        super(symbol);
    }
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        generate(code, inFunction, 0);
    }
    
    //generate array list 
    public abstract void generate(ArrayList<String> code, boolean inFunction, int offset);

}
