/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class Expression extends PTNode {

    public Expression() {
        super(3);
    }

    public Expression getLhs() {
        return (Expression) children.get(0);
    }

    public void setLhs(Expression e) {
        children.set(0, e);
    }

    public Operator getOp() {
        return (Operator) children.get(1);
    }

    public void setOp(Operator op) {
        children.set(1, op);
    }

    public Expression getRhs() {
        return (Expression) children.get(2);
    }

    public void setRhs(Expression e) {
        children.set(2, e);
    }

    public boolean hasOp() {
        return children.get(1) != null;
    }

    public boolean hasRhs() {
        return children.get(2) != null;
    }
    
    private boolean isTerm(){
        return !hasOp() && !hasRhs();
    }
    
    private boolean isUnary(){
        return hasOp() && !hasRhs();
    }
    
    public String toPolish(){
        return "_e";
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        if (isTerm()) {
            sb.append(getLhs().format(indent));
            return sb.toString();
        } else if (isUnary()) {
            sb.append(getOp().format(indent));
            sb.append(getLhs().format(indent));
            return sb.toString();
        } else {
            sb.append("(");
            sb.append(getLhs().format(indent));
            sb.append(" ");
            sb.append(getOp().format(indent));
            sb.append(" ");
            sb.append(getRhs().format(indent));
            sb.append(")");
            return sb.toString();
        }
    }
    
    @Override
    public ast.Expression toAST(){
        if(isTerm()){
            if(getLhs()==null){
                throw new RuntimeException("error converting expression");
            }
            return getLhs().toAST();
        } else if(isUnary()){
            ast.Unary unary = new ast.Unary();
            unary.setTerm(getLhs().toAST());
            unary.setOp(getOp().toAST());
            return unary;
        } else {
            ast.Binary binary = ast.binary.Factory.getExpression(getOp().getSymbol().getSymbol());
            binary.setLhs(getLhs().toAST());
            binary.setOp(getOp().toAST());
            binary.setRhs(getRhs().toAST());
            return binary;
        }
    }
    
}
