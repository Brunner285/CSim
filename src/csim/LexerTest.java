package csim;

import lexer.Lexer;
import lexer.Token;

/**
 *
 * @author alan.whitehurst
 */
public class LexerTest {
    
    public static void main(String [] args){
                if (args.length != 1) {
            System.err.println("usage: LexerTest <filename>");
            System.exit(1);
        }
        Lexer lexer = new Lexer(args[0]);
        while(lexer.hasNext()){
            Token token = lexer.next();
            System.out.println(token);
        }
    }
    
}
