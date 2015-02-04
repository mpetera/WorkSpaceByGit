package com.tradeshift.java;

/**
 * The java code that call the GroovyModel
 */

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;

import java.io.File;

public class GroovyModelTest {
	public static void main(String[] args) throws Exception {
		/*
		* GroovyClassLoader实现Java调用Groovy脚本进行计算
		* 适用于：脚本是一个完整的文件，特别是有API类型的时候
		* 比如类似于JAVA的接口，面向对象设计时，通常使用该方法
		* */
		ClassLoader parent = ClassLoader.getSystemClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class gclass = loader.parseClass(new File("./src/com/tradeshift/groovy/model.groovy"));
		GroovyObject groovyObject = (GroovyObject)gclass.newInstance();
		Object obj = groovyObject.invokeMethod("add", new Object[]{
				new Double(2.08), new Integer(1) });
		System.out.println(obj);
		
		/*
		* 使用Binding对象将变量传入表达式，并通过GroovyShell返回表达式的计算结果
		* 适用于：通常用回来运行“script片段”或者一些零散的表达式
		* */
		Binding binding = new Binding();
		binding.setVariable("x", 10);
		binding.setVariable("language", "Groovy");

		GroovyShell shell = new GroovyShell(binding);
		Object value = shell.evaluate("println \"Welcome to $language\"; y = x * 2; z = x * 3; return x ");
		
		System.err.println(value + "," + value.equals(10));
		System.err.println(binding.getVariable("y") + "," + binding.getVariable("y").equals(20));
		System.err.println(binding.getVariable("z") + "," + binding.getVariable("z").equals(30));
		
		/*使用GroovyScriptEngine脚本引擎加载Groovy脚本
		* JSR-223推荐的一种使用策略，规范化，而且简便
		* */
		try {
			String[] roots = new String[]{"./src/com/tradeshift/groovy/model.groovy"};
			GroovyScriptEngine engine = new GroovyScriptEngine(roots);
			Binding binding1 = new Binding();
			binding1.setVariable("x", 10);
			binding1.setVariable("y", 20);
//			binding1.invokeMethod("add", new Object[]{binding1.getVariable("x"), binding1.getVariable("y")});
			engine.run("add", binding1);
			System.out.println(engine.run("add", binding1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
