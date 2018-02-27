/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alan
 */
public class DeclStats extends PTNode {
    
    public DeclStats(){
        decls = new ArrayList<Decl>();
        stats = new ArrayList<Statement>();
    }

    public ArrayList<Decl> getDecls() {
        return decls;
    }

    public void setDecls(ArrayList<Decl> decls) {
        this.decls = decls;
    }

    public ArrayList<Statement> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Statement> stats) {
        this.stats = stats;
    }
    
    public void addDecl(Decl decl){
        decls.add(decl);
    }
    
    public void addStat(Statement stat){
        stats.add(stat);
    }
    
    public boolean isEmpty(){
        return decls.isEmpty() && stats.isEmpty();
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        Iterator<Decl> myDecls = decls.iterator();
        while(myDecls.hasNext()){
            sb.append(myDecls.next().format(indent));
        }
        Iterator<Statement> myStats = stats.iterator();
        while(myStats.hasNext()){
            sb.append(myStats.next().format(indent));
        }
        return sb.toString();
    }
    
    @Override
    public ast.ASTNode toAST(){
        throw new UnsupportedOperationException("toAST not supported by DeclStats");
    }
    
    public ArrayList<ast.Declaration> toDeclarations(){
        ArrayList<ast.Declaration> declarations = new ArrayList<ast.Declaration>();
        Iterator<Decl> it = decls.iterator();
        while(it.hasNext()){
            Decl decl = it.next();
            declarations.add((ast.Declaration)decl.toAST());
        }
        return declarations;
    }
    
    public ArrayList<ast.Statement> toStatements(){
        ArrayList<ast.Statement> statements = new ArrayList<ast.Statement>();
        Iterator<Statement> it = stats.iterator();
        while(it.hasNext()){
            Statement stat = it.next();
            statements.add((ast.Statement)stat.toAST());
        }
        return statements;
    }
            
    private ArrayList<Decl> decls;
    private ArrayList<Statement> stats;
    
}
