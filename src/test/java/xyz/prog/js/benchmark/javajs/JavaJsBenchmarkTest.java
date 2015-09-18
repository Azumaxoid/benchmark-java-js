package xyz.prog.js.benchmark.javajs;

import java.util.stream.IntStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class JavaJsBenchmarkTest {

	@Test
	public void define1000Function() throws ScriptException {
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, 1000).forEach(i -> {
			sb.append("var func").append(i).append(" = function(){};");
		});
		sb.append("'end';");
		testSpeed("define 1000 function", sb.toString());
	}

	@Test
	public void callFunction1000Times() throws ScriptException {
		StringBuilder sb = new StringBuilder();
		sb.append("var test = function(){};");
		IntStream.range(0, 1000).forEach(i -> {
			sb.append("test();");
		});
		sb.append("'end';");
		testSpeed("call function 1000 times", sb.toString());
	}
	
	@Test
	public void calc1000Times() throws ScriptException {
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, 1000).forEach(i -> {
			sb.append(i + " + " + (i + 1) + ";");
		});
		sb.append("'end';");
		testSpeed("calc 1000 times", sb.toString());
	}

	private void testSpeed(String title, String script) throws ScriptException {
		StringBuilder scriptSb = new StringBuilder();
		Context cx = Context.enter();
		try {
			Scriptable scope = cx.initStandardObjects();
			long rhinoS = System.currentTimeMillis();
			Object rhinoRes = cx.evaluateString(scope, scriptSb.toString(), "",
					1, null);
			// Convert the result to a string and print it.
			long rhinoE = System.currentTimeMillis();
			long nashornS = System.currentTimeMillis();
			ScriptEngineManager engineManager = new ScriptEngineManager();
			ScriptEngine engine = engineManager.getEngineByName("nashorn");
			Object nashornRes = engine.eval(script);
			long nashornE = System.currentTimeMillis();

			System.err.println("SpeedTest rhino:nashorn = " + (rhinoE - rhinoS)
					+ " : " + (nashornE - nashornS) + " (ms). Result (" + title
					+ ") rhino:nashorn = " + rhinoRes + " : " + nashornRes
					+ ".");
		} finally {
			Context.exit();
		}
	}

}
