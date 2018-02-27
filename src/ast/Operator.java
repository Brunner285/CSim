
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Operator extends ASTNode {

    //create operator string 
    public Operator(Token symbol) {
        super(symbol);
        opString = symbol.getSymbol();
    }
    
    public Operator(String str){
        opString = str;
    }

    public Operator() {
        opString = null;
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Op: ");
        sb.append(getSymbol().getSymbol());
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString(){
        if(opString==null){
         return getSymbol().getSymbol();
        } else {
            return opString;
        }
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        throw new UnsupportedOperationException("Generate called on Operator"); //To change body of generated methods, choose Tools | Templates.
    }
    
    String opString;
    
}
