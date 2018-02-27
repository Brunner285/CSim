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
public class Proccall extends Statement {

    public Proccall(Token symbol) {
        super(symbol);
    }

    public Proccall() {
    }

    public Proccall(Token symbol, Name name, ExprList args) {
        super(symbol);
        this.name = name;
        this.args = args;
    }

    
    public ExprList getArgs() {
        return args;
    }

    public void setArgs(ExprList args) {
        this.args = args;
    }

    public void addArg(Expression arg) {
        args.add(arg);
    }

    //return name 
    public Name getName() {
        return name;
    }

    //set name value 
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (args != null) {
            args.typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction, int offset) {
        if(args!=null){
            args.generate(code, inFunction);
        }
        code.add("\tcall " + name.getName() + (args!=null?args.toPolish():"$"));
        if(args!=null){
            code.add("\tdloc " + args.size());
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Proccall: ");
        sb.append(this.getName().format(indent, true));
        sb.append(" Args: ");
        if (args != null) {
            sb.append(args.format(indent, true));
        } else {
            sb.append("()");
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Name name;
    private ExprList args;

}
