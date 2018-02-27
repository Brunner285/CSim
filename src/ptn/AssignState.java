/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class AssignState extends Statement {
    
    public AssignState(){
        super(2);
    }
    
    public VarRef getIdent(){
        return (VarRef) children.get(0);
    }

    public void setIdent(VarRef ident) {
        children.set(0,ident);
    }
    
    public Expression getExpression(){
        return (Expression) children.get(1);
    }

    public void setExpression(Expression expr) {
        children.set(1, expr);
    }
    
    @Override
    public String format(int indent, boolean suppressNewline){
        StringBuilder sb = new StringBuilder();
        if(!suppressNewline){
            sb.append(indent(indent));
        }
        sb.append(getIdent().format(indent));
        sb.append(" = ");
        sb.append(getExpression().format(indent));
        if(!suppressNewline){
            sb.append(";\n");
        }
        return sb.toString();
    }
    
    @Override
    public ast.Assignment toAST(){
        ast.Assignment assignment = new ast.Assignment();
        assignment.setVarRef(getIdent().toAST());
        assignment.setExpr(getExpression().toAST());
        return assignment;
    }
    
}
