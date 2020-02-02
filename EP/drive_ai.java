package EP;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.optimization.OptimizationDeltaJump;
import net.sourceforge.jFuzzyLogic.optimization.OptimizationPartialDerivate;
import net.sourceforge.jFuzzyLogic.optimization.Parameter;
import java.util.ArrayList;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import java.io.*;

public class drive_ai {

  public static void main(String[] args) throws Exception {

//---
// Load FIS (Fuzzy Inference System)
//---
FIS fis = FIS.load("forza3.fcl");
RuleBlock ruleBlock = fis.getFunctionBlock("driver").getFuzzyRuleBlock("No1");

//---
// Create a list of parameter to optimize
//---
ArrayList<Parameter> parameterList = new ArrayList<Parameter>();

// Add variables. 
// Note: Fuzzy sets' parameters for these 
// variables will be optimized
parameterList = Parameter.parameters(ruleBlock);

parameterList.addAll(Parameter.parametersMembershipFunction(fis.getVariable("track9")));
parameterList.addAll(Parameter.parametersMembershipFunction(fis.getVariable("track8")));
parameterList.addAll(Parameter.parametersMembershipFunction(fis.getVariable("track10")));
parameterList.addAll(Parameter.parametersMembershipFunction(fis.getVariable("trackPos")));
parameterList.addAll(Parameter.parametersMembershipFunction(fis.getVariable("speed")));

// Create an error function to be 
// optimzed (i.e. minimized)
//---
ErrorFunctionQualify errorFunction = new ErrorFunctionQualify();

//---
// Optimize (using 'Delta jump optimization')
//---
OptimizationDeltaJump optimization = 
        new OptimizationDeltaJump(ruleBlock
                , errorFunction, parameterList);

// Number optimization of iterations
optimization.setMaxIterations(2);

optimization.optimize();
}}


//COMPILING
//javac -cp jFuzzyLogic.jar drive_ai.java ErrorFunctionQualify.java
//RUNNING
//java -cp jFuzzyLogic.jar;. EP.drive_ai