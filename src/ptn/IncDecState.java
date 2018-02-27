/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class IncDecState extends Statement {
    
    public IncDecState(){
        super(2);
    }
    
    public Operator getOp(){
        return (Operator) children.get(0);
    }

    public void setOp(Operator op) {
        children.set(0,op);
    }
    
    public VarRef getVarRef(){
        return (VarRef) children.get(1);
    }

    public void setVarRef(VarRef varRef) {
        children.set(1,varRef);
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append(getOp().format(indent));
        sb.append(getVarRef().format(indent));
        sb.append(";\n");
        return sb.toString();
    }
    
    @Override
    public ast.Statement toAST(){
        ast.Assignment ass = new ast.Assignment();
        ass.setVarRef(getVarRef().toAST());
        ast.Unary u = new ast.Unary();
        u.setOp(getOp().toAST());
        u.setTerm(getVarRef().toAST());
        ass.setExpr(u);
        return ass;
    }
    
}
