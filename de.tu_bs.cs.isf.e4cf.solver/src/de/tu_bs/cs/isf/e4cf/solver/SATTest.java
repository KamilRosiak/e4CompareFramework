package de.tu_bs.cs.isf.e4cf.solver;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

public class SATTest {
	static String toSolve = 
    "c  simple_v3_c2.cnf \n" +
    "c \n"+
    "p cnf 3 2 \n"+
    "1 -3 0\n"+
    "2 3 -1 0";
	
	static String notSatisfiable = 
			"c FILE: dubois20.cnf\r\n" + 
			"c\r\n" + 
			"c SOURCE: Olivier Dubois (dubois@laforia.ibp.fr)\r\n" + 
			"c\r\n" + 
			"c DESCRIPTION: Instance from generator gensathard.c\r\n" + 
			"c\r\n" + 
			"c NOTE: Not satisfiable\r\n" + 
			"c\r\n" + 
			"c d = 20\r\n" + 
			"c n = 60\r\n" + 
			"c p = 160\r\n" + 
			"c r = 3\r\n" + 
			"p cnf 60 160\r\n" + 
			" 39  40   1  0\r\n" + 
			"-39 -40   1  0\r\n" + 
			" 39 -40  -1  0\r\n" + 
			"-39  40  -1  0\r\n" + 
			"  1  41   2  0\r\n" + 
			" -1 -41   2  0\r\n" + 
			"  1 -41  -2  0\r\n" + 
			" -1  41  -2  0\r\n" + 
			"  2  42   3  0\r\n" + 
			" -2 -42   3  0\r\n" + 
			"  2 -42  -3  0\r\n" + 
			" -2  42  -3  0\r\n" + 
			"  3  43   4  0\r\n" + 
			" -3 -43   4  0\r\n" + 
			"  3 -43  -4  0\r\n" + 
			" -3  43  -4  0\r\n" + 
			"  4  44   5  0\r\n" + 
			" -4 -44   5  0\r\n" + 
			"  4 -44  -5  0\r\n" + 
			" -4  44  -5  0\r\n" + 
			"  5  45   6  0\r\n" + 
			" -5 -45   6  0\r\n" + 
			"  5 -45  -6  0\r\n" + 
			" -5  45  -6  0\r\n" + 
			"  6  46   7  0\r\n" + 
			" -6 -46   7  0\r\n" + 
			"  6 -46  -7  0\r\n" + 
			" -6  46  -7  0\r\n" + 
			"  7  47   8  0\r\n" + 
			" -7 -47   8  0\r\n" + 
			"  7 -47  -8  0\r\n" + 
			" -7  47  -8  0\r\n" + 
			"  8  48   9  0\r\n" + 
			" -8 -48   9  0\r\n" + 
			"  8 -48  -9  0\r\n" + 
			" -8  48  -9  0\r\n" + 
			"  9  49  10  0\r\n" + 
			" -9 -49  10  0\r\n" + 
			"  9 -49 -10  0\r\n" + 
			" -9  49 -10  0\r\n" + 
			" 10  50  11  0\r\n" + 
			"-10 -50  11  0\r\n" + 
			" 10 -50 -11  0\r\n" + 
			"-10  50 -11  0\r\n" + 
			" 11  51  12  0\r\n" + 
			"-11 -51  12  0\r\n" + 
			" 11 -51 -12  0\r\n" + 
			"-11  51 -12  0\r\n" + 
			" 12  52  13  0\r\n" + 
			"-12 -52  13  0\r\n" + 
			" 12 -52 -13  0\r\n" + 
			"-12  52 -13  0\r\n" + 
			" 13  53  14  0\r\n" + 
			"-13 -53  14  0\r\n" + 
			" 13 -53 -14  0\r\n" + 
			"-13  53 -14  0\r\n" + 
			" 14  54  15  0\r\n" + 
			"-14 -54  15  0\r\n" + 
			" 14 -54 -15  0\r\n" + 
			"-14  54 -15  0\r\n" + 
			" 15  55  16  0\r\n" + 
			"-15 -55  16  0\r\n" + 
			" 15 -55 -16  0\r\n" + 
			"-15  55 -16  0\r\n" + 
			" 16  56  17  0\r\n" + 
			"-16 -56  17  0\r\n" + 
			" 16 -56 -17  0\r\n" + 
			"-16  56 -17  0\r\n" + 
			" 17  57  18  0\r\n" + 
			"-17 -57  18  0\r\n" + 
			" 17 -57 -18  0\r\n" + 
			"-17  57 -18  0\r\n" + 
			" 18  58  19  0\r\n" + 
			"-18 -58  19  0\r\n" + 
			" 18 -58 -19  0\r\n" + 
			"-18  58 -19  0\r\n" + 
			" 19  59  60  0\r\n" + 
			"-19 -59  60  0\r\n" + 
			" 19 -59 -60  0\r\n" + 
			"-19  59 -60  0\r\n" + 
			" 20  59  60  0\r\n" + 
			"-20 -59  60  0\r\n" + 
			" 20 -59 -60  0\r\n" + 
			"-20  59 -60  0\r\n" + 
			" 21  58  20  0\r\n" + 
			"-21 -58  20  0\r\n" + 
			" 21 -58 -20  0\r\n" + 
			"-21  58 -20  0\r\n" + 
			" 22  57  21  0\r\n" + 
			"-22 -57  21  0\r\n" + 
			" 22 -57 -21  0\r\n" + 
			"-22  57 -21  0\r\n" + 
			" 23  56  22  0\r\n" + 
			"-23 -56  22  0\r\n" + 
			" 23 -56 -22  0\r\n" + 
			"-23  56 -22  0\r\n" + 
			" 24  55  23  0\r\n" + 
			"-24 -55  23  0\r\n" + 
			" 24 -55 -23  0\r\n" + 
			"-24  55 -23  0\r\n" + 
			" 25  54  24  0\r\n" + 
			"-25 -54  24  0\r\n" + 
			" 25 -54 -24  0\r\n" + 
			"-25  54 -24  0\r\n" + 
			" 26  53  25  0\r\n" + 
			"-26 -53  25  0\r\n" + 
			" 26 -53 -25  0\r\n" + 
			"-26  53 -25  0\r\n" + 
			" 27  52  26  0\r\n" + 
			"-27 -52  26  0\r\n" + 
			" 27 -52 -26  0\r\n" + 
			"-27  52 -26  0\r\n" + 
			" 28  51  27  0\r\n" + 
			"-28 -51  27  0\r\n" + 
			" 28 -51 -27  0\r\n" + 
			"-28  51 -27  0\r\n" + 
			" 29  50  28  0\r\n" + 
			"-29 -50  28  0\r\n" + 
			" 29 -50 -28  0\r\n" + 
			"-29  50 -28  0\r\n" + 
			" 30  49  29  0\r\n" + 
			"-30 -49  29  0\r\n" + 
			" 30 -49 -29  0\r\n" + 
			"-30  49 -29  0\r\n" + 
			" 31  48  30  0\r\n" + 
			"-31 -48  30  0\r\n" + 
			" 31 -48 -30  0\r\n" + 
			"-31  48 -30  0\r\n" + 
			" 32  47  31  0\r\n" + 
			"-32 -47  31  0\r\n" + 
			" 32 -47 -31  0\r\n" + 
			"-32  47 -31  0\r\n" + 
			" 33  46  32  0\r\n" + 
			"-33 -46  32  0\r\n" + 
			" 33 -46 -32  0\r\n" + 
			"-33  46 -32  0\r\n" + 
			" 34  45  33  0\r\n" + 
			"-34 -45  33  0\r\n" + 
			" 34 -45 -33  0\r\n" + 
			"-34  45 -33  0\r\n" + 
			" 35  44  34  0\r\n" + 
			"-35 -44  34  0\r\n" + 
			" 35 -44 -34  0\r\n" + 
			"-35  44 -34  0\r\n" + 
			" 36  43  35  0\r\n" + 
			"-36 -43  35  0\r\n" + 
			" 36 -43 -35  0\r\n" + 
			"-36  43 -35  0\r\n" + 
			" 37  42  36  0\r\n" + 
			"-37 -42  36  0\r\n" + 
			" 37 -42 -36  0\r\n" + 
			"-37  42 -36  0\r\n" + 
			" 38  41  37  0\r\n" + 
			"-38 -41  37  0\r\n" + 
			" 38 -41 -37  0\r\n" + 
			"-38  41 -37  0\r\n" + 
			" 39  40 -38  0\r\n" + 
			"-39 -40 -38  0\r\n" + 
			" 39 -40  38  0\r\n" + 
			"-39  40  38  0\r\n";
	
    public static void main(String[] args) {
    	ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        PrintWriter out = new PrintWriter(System.out,true);
        // CNF filename is given on the command line 
        try {
        	InputStream is = new ByteArrayInputStream(notSatisfiable.getBytes());
            IProblem problem = reader.parseInstance(is);
            
            try {
				if (problem.isSatisfiable()) {
				    System.out.println("Satisfiable !");
				    reader.decode(problem.model(),out);
				} else {
				    System.out.println("Unsatisfiable !");
				}
			} catch (org.sat4j.specs.TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        } catch (ParseFormatException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        }
    }
    
}
