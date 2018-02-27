
package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alan
 */
public class ExprList extends PTNode {
    
    public ExprList(){
    }
    
    public void addExpr(Expression expr){
        children.add(expr);
    }
    
    public Expression getExpr(int index){
        return (Expression) children.get(index);
    }
    
    public void setExpr(int index, Expression expr){
        children.set(index, expr);
    }
    
     public String toPolish() {
        StringBuilder sb = new StringBuilder("$");
        Iterator<PTNode> expressions = children.iterator();
        while (expressions.hasNext()) {
            Expression arg = (Expression) expressions.next();
            sb.append(arg.toPolish());
        }
        return sb.toString();
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        Iterator<PTNode> expressions = children.iterator();
        while(expressions.hasNext()){
            sb.append(expressions.next().format(indent));
            if(expressions.hasNext()){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public ASTNode toAST() {
        ast.ExprList exprList = new ast.ExprList();
        Iterator<PTNode> expressions = children.iterator();
        while(expressions.hasNext()){
            exprList.add((ast.Expression)expressions.next().toAST());
        }
        return exprList;
    }

}
