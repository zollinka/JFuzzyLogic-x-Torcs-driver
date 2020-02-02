package EP;
import net.sourceforge.jFuzzyLogic.optimization.ErrorFunction;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.FIS;
import java.io.*;
import java.util.*;
import java.lang.*;
public class ErrorFunctionQualify extends ErrorFunction {
    public double evaluate(RuleBlock ruleBlock) {
        double error = 2;
		FIS fis = new FIS();
		FunctionBlock fBlock = ruleBlock.getFunctionBlock();
		fis.addFunctionBlock("driver",fBlock);
		try {
			PrintStream out = new PrintStream(new FileOutputStream("driver.fcl"));
			out.print(fis.toStringFcl());}
		catch (IOException e)
		{
			
		}
		try{
		ProcessBuilder pB_driver = new ProcessBuilder();
		ProcessBuilder pB_race = new ProcessBuilder();
		
		pB_race.command("run-race.bat","quickrace.xml","driver.fcl");
		final Process p_race = pB_race.start();
		BufferedReader br=new BufferedReader(new InputStreamReader(p_race.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while((line=br.readLine())!=null) {sb.append(line);}
		int s_czas = sb.lastIndexOf("[s]");
		if (s_czas < 7 || s_czas == sb.indexOf("[s]")) 
		{
			error = 1000;
		}
		else {
			error = Double.parseDouble((sb.substring(s_czas-7,s_czas-1).trim()));
		}
		}
		catch (IOException e) {
			System.out.println(e);
			error = 1000;
		}
		System.out.println(error);
        return error;
    }
}  
