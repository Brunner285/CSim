/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;

/**
 *
 * @author Alan
 */
public class Funcall extends Expression {

    public Funcall(Token symbol) {
        super(symbol);
    }

    public Funcall() {
    }

    //create token args and string for name 
    public Funcall(Token symbol, Name name, ExprList args) {
        super(symbol);
        this.name = name;
        this.args = args;
    }

    //return value of args 
    public ExprList getArgs() {
        return args;
    }

    //set value of args 
    public void setArgs(ExprList args) {
        this.args = args;
    }

    //add args to token 
    public void addArg(Expression arg) {
        args.add(arg);
    }

    //return name
    public Name getName() {
        return name;
    }

    //set value for name 
    public void setName(Name name) {
        this.name = name;
    }

    //return value of scope 
    public Scope getScope() {
        return scope;
    }

    //set the value of scope 
    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    private String getMangledName(){
        return name.getName() + (args!=null?getArgs().toPolish():"$");
    }
    

    @Override
    public Type getType() {
        System.out.println("Getting type of " + getMangledName() + " as " + scope.getType(getMangledName()).toAST());
        return (scope.getType(getMangledName())).toAST();
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if(args!=null){
            args.typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (args != null) {
            args.generate(code, inFunction);
            code.add("\tcall " + name.getName() + args.toPolish());
            code.add("\tdloc " + args.size());
        } else {
            code.add("\tcall " + name.getName() + "$");
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Funcall: ");
        sb.append(this.getName().format(indent, true));
        sb.append(" Args: ");
        if (getArgs() != null) {
            sb.append(this.getArgs().format(indent, true));
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
    private Scope scope;

}
