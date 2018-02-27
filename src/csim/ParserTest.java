
package csim;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;
import lexer.Lexer;
import ptn.PTNode;
import parser.Parser;

/**
 *
 * @author alan.whitehurst
 */
public class ParserTest {

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
        }
    }
}
