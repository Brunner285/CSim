
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public abstract class Binary extends Expression {

    public Binary() {
        
    }

    public Binary(Token symbol) {
        super(symbol);
    }

    public Binary(Token symbol, Operator op, Expression lhs, Expression rhs) {
        super(symbol);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    //return lhs
    public Expression getLhs() {
        return lhs;
    }

    //set lhs
    public void setLhs(Expression lhs) {
        this.lhs = lhs;
    }

    //return operator
    public Operator getOp() {
        return op;
    }

    //set operator
    public void setOp(Operator op) {
        this.op = op;
    }

    //return rhs
    public Expression getRhs() {
        return rhs;
    }

    //set rhs
    public void setRhs(Expression rhs) {
        this.rhs = rhs;
    }
    
    @Override
    public String format(int indent, boolean suppressNL){
        StringBuilder sb = new StringBuilder();
        if(!suppressNL){
            sb.append(indent(indent));
        }
        sb.append("[Binary ");
        sb.append(this.getLhs().format(indent,true));
        sb.append(" ");
        sb.append(this.getOp().format(indent,true));
        sb.append(" ");
        sb.append(this.getRhs().format(indent,true));
        sb.append(" ]");
        if(!suppressNL){
            sb.append("\n");
        }
        return sb.toString();
    }
    //    
    private Operator op;
    private Expression lhs;
    private Expression rhs;
    
}
