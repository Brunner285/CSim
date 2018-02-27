/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class VarDecl extends Decl {
    
    public VarDecl(){
        super(3);
    }

    @Override
    public Expression getInit() {
        return (Expression) children.get(2);
    }

    @Override
    public void setInit(PTNode expr) {
        if (expr instanceof Expression) {
            children.set(2, expr);
        }
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append(getType().format(indent));
        sb.append(" ");
        sb.append(getName().format(indent));
        if (hasInit()) {
            sb.append(" = ");
            sb.append(getInit().format(indent));
        }
        sb.append(";\n");
        return sb.toString();
    }
    
    @Override
    public ast.VarDecl toAST(){
        ast.VarDecl varDecl = new ast.VarDecl();
        varDecl.setType(getType().toAST());
        varDecl.setVariable(getName().toAST());
        varDecl.setScope(getScope());
        if(hasInit()){
            varDecl.setInit(getInit().toAST());
        }
        return varDecl;
    }
}
