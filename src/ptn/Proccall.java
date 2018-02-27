/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class Proccall extends Statement {

    public Proccall() {
        super(2);
    }

    public Name getName() {
        return (Name) children.get(0);
    }

    public void setIdent(Name name) {
        children.set(0, name);
    }

    public ExprList getArgs() {
        return (ExprList) children.get(1);
    }

    public void setArgs(ExprList args) {
        children.set(1, args);
    }

    public boolean hasArgs() {
        return children.get(1) != null;
    }

    public String getNameMangle() {
        if (getArgs() == null) {
            return getName().toString() + "$";
        } else {
            return getName().toString() + getArgs().toPolish();
        }
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append(getName().format(indent));
        sb.append("(");
        if(getArgs()!=null){
            sb.append(getArgs().format(indent));
        }
        sb.append(")");
        sb.append(";\n");
        return sb.toString();
    }

    @Override
    public ast.Proccall toAST() {
        ast.Proccall funcall = new ast.Proccall();
        funcall.setName(getName().toAST());
        if (hasArgs()) {
            funcall.setArgs((ast.ExprList) getArgs().toAST());
        }
        return funcall;
    }

}
