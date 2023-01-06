package de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader;

import de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader.jep.Interpreter;
import de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader.jep.SharedInterpreter;

public class Main {

	public static void main(String[] args) {
		try (Interpreter interp = new SharedInterpreter()) {
			 interp.exec("import ast");
			 interp.exec("file = open(\"C:/Users/david/Documents/Informatik/Hiwi/commands.py\", \"r\")");
			 interp.exec("data = file.read()");
			 interp.exec("file.close()");
			 interp.exec("tree = ast.parse(data)");
			 interp.exec("printTree = ast.dump(tree)");
			 Object object =  interp.getValue("printTree");
			 
			 System.out.println(object);
	
		}


	}

}
