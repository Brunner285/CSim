/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csim;

import ast.ASTNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexer.Lexer;
import ptn.PTNode;
import parser.Parser;

/**
 *
 * @author alan.whitehurst
 */
public class CSim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: csim <filename>");
            System.exit(1);
        }
        Lexer lexer = new Lexer(args[0]);
        Parser parser = new Parser(lexer);
        PTNode ptnRoot = parser.parse();
        if (parser.hasErrors()) {
            Iterator<String> msgs = parser.getErrorMessages().iterator();
            while (msgs.hasNext()) {
                System.out.println(msgs.next());
            }
        } else {
            System.out.println("\n\nFormatted Parse Tree: \n");
            System.out.println(ptnRoot.format(0));
            System.out.println("\n\nSymbol Table: \n");
            System.out.println(parser.getSymbolTable());
            System.out.println("\n\nAbstract Syntax Tree: ");
            ASTNode astRoot = ptnRoot.toAST();
            System.out.println(astRoot.format(0));
            Checker checker = new Checker(parser.getSymbolTable(), astRoot);
            checker.check();
            if (checker.hasError()) {
                Iterator<String> msgs = checker.getErrorMessages().iterator();
                while (msgs.hasNext()) {
                    System.out.println(msgs.next());
                }
            } else {
                BufferedWriter writer = null;
                try {
                    Generator cg = new Generator("test", parser.getSymbolTable(), astRoot);
                    ArrayList<String> output = cg.generate();
                    System.out.println("Output:\n\n");
                    for (String line : output) {
                        System.out.println(line);
                    }
                    String ofilename = args[0];
                    if (ofilename.endsWith(".csm")) {
                        ofilename = ofilename.substring(0, ofilename.length() - 4) + ".as";
                    } else {
                        ofilename += ".as";
                    }
                    System.out.println("Writing '" + ofilename + "'");
                    writer = new BufferedWriter(new FileWriter(ofilename));
                    for (String line : output) {
                        writer.append(line);
                        writer.append("\n");
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(CSim.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(CSim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
