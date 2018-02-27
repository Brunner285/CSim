/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class RetState extends Statement {
    
    public RetState(){
        super(1);
    }
    
    public Expression getExpr(){
        return (Expression) children.get(0);
    }
    
    public void setExpr(Expression expr){
        children.set(0,expr);
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append("return");
        if(getExpr()!=null){
            sb.append(" ");
            sb.append(getExpr().format(indent));
        }
        sb.append(";\n");
        return sb.toString();
    }
    
    @Override
    public ast.Return toAST(){
        ast.Return ret = new ast.Return();
        if(getExpr()!=null){
            ret.setValue(getExpr().toAST());
        }
        return ret;
    }
    
}
