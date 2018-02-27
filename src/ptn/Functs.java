/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Lab Admin
 */
public class Functs extends PTNode {

    public ArrayList<ast.Function> toFunctions(){
        ArrayList<ast.Function> functions = new ArrayList<ast.Function>();
        Iterator<PTNode> it = getChildren().iterator();
        while(it.hasNext()){
            Funct f = (Funct)it.next();
            ast.Function fast = (ast.Function)f.toAST();
            if(fast!=null){
                functions.add((ast.Function)f.toAST());
            }
        }
        return functions;
    }

    @Override
    public ASTNode toAST() {
        throw new UnsupportedOperationException("Not supported.");
    }

}
