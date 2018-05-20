import java.io.File;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;



/**
 * Executes a Java source file giving it an input file and compares the programs
 * output to the output expected given by another file.
 * 
 * java JudgeJuryAndExecutor ProblemName
 * 	Source = ProblemName.java
 * 	Input = Problem.in
 * 	Output = Problem.out
 * 
 * @author Philip Diffenderfer
 *
 */
public class JudgeJuryAndExecutor
{

	public static void main(String[] args) throws Exception {
		
		String problemName = args[1];
		URL javaSource = new URL(problemName + ".java");
		URL inputFile = new URL(problemName + ".in");
		URL outputFile = new URL(problemName + ".out");
			
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int success = compiler.run(inputFile.openStream(), System.out, System.err, javaSource.getFile());
		
		if (success == 0) {
			System.out.println("Executed");
		}
	}
	
}
