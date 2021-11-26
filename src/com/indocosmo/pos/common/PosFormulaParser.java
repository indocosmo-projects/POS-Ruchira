package com.indocosmo.pos.common;

import java.util.Map;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;

public final class PosFormulaParser {
	
	
	/**
	 * Evaluate the expression
	 * @param expression
	 * @param variables
	 * @return
	 */
	public static double eval(String expression, Map<String, Double> variables){
		double result=0;
		if(validateExpression(expression)){
			String newExpression=expand(expression);
			ExpressionBuilder expBuilder=new ExpressionBuilder(newExpression);
			if(variables!=null)
				expBuilder.withVariables(variables);
			try {
				Calculable calc=expBuilder.build();
				
				result=calc.calculate();
			} catch (UnknownFunctionException e) {
				PosLog.write("PosFormulaParser","eval: ",e);
			} catch (Exception e) {
				PosLog.write("PosFormulaParser","eval: ",e);
			}
		}
		return result;
	}
	
	/**
	 * Evaluate the expression
	 * @param expression
	 * @return
	 */
	public static double eval(String expression){
		return eval(expression,null);
	}
	
	public static boolean validateExpression(String expression){
		return (!expression.trim().equals(""));
	}
	
	private static String expand(String expression){
//		expression.replaceAll("%", "/100");
		return expression.replaceAll("%", "/100");
	}

}
