/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csim;

import ast.ASTNode;
import java.util.ArrayList;
import parser.SymbolTable;

/**
 *
 * @author alan.whitehurst
 */
public class Generator {
    
    public Generator(String filename, SymbolTable stab, ASTNode astRoot){
        this.filename = filename;
        this.stab = stab;
        this.astRoot = astRoot;
    }
    
    public ArrayList<String> generate(){
        output = new ArrayList<>();
        astRoot.generate(output, false);
        return output;
    }
    
    public static String getLabel(){
        return String.format("@L%03d", labelCount++);
    }
    
    private final String filename;
    private final SymbolTable stab;
    private final ASTNode astRoot;
    private ArrayList<String> output;
    private static int labelCount = 0;
}
