/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Program extends ASTNode {

    public Program(Token symbol) {
        super(symbol);
    }

    public Program() {
    }

    public Program(Token symbol, ArrayList<Function> functs, Block body) {
        super(symbol);
        this.functs = functs;
        this.body = body;
    }

    //add to array list 
    public void addFunct(Function f) {
        functs.add(f);
    }

    //set values in array list 
    public void setFuncts(ArrayList<Function> functs) {
        this.functs = functs;
    }

    //add declaration token 
    public void addDecl(Declaration d) {
        body.addDecl(d);
    }

    
    public void addStat(Statement s) {
        body.addStat(s);
    }

    //
    public void setBody(Block body) {
        this.body = body;
    }

    //return body values 
    public Block getBody() {
        return body;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (functs != null) {
            Iterator<Function> functions = functs.iterator();
            while (functions.hasNext()) {
                functions.next().typeCheck(msgs);
            }
        }
        body.typeCheck(msgs);
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        code.add("!o");
        if (functs!=null && !functs.isEmpty()) {
            code.add("; functions");
            for (Function f : functs) {
                f.generate(code, true);
            }
        }
        code.add("@temp:\tdw 0");
        code.add("main$:");
        body.generate(code, true);
        code.add("\thalt");
        code.add("\tend main$");
    }

    @Override
    public String format(int indent, boolean suppressNewline) {
        StringBuilder sb = new StringBuilder();
        if (functs != null) {
            Iterator<Function> theFuncts = functs.iterator();
            while (theFuncts.hasNext()) {
                sb.append(theFuncts.next().format(indent, false));
                sb.append("\n");
            }
        }
        sb.append(indent(indent));
        sb.append("[Program:\n");
        sb.append(getBody().format(indent + 3, false));
        sb.append("\n");
        sb.append(indent(indent));
        sb.append("]\n");
        return sb.toString();
    }
    //
    private ArrayList<Function> functs;
    private Block body;

}
