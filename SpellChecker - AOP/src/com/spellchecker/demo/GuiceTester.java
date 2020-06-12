/*
 * AOP, Aspect oriented programming entails breaking down program logic into distinct parts called so-called concerns. 
 * The functions that span multiple points of an application are called cross-cutting concerns and these cross-cutting 
 * concerns are conceptually separate from the application's business logic. 
 * There are various common good examples of aspects like logging, auditing, declarative transactions, security, caching, etc.
 */

/*
 * The key unit of modularity in OOP is the class, whereas in AOP the unit of modularity is the aspect.
 * Dependency Injection helps you decouple your application objects from each other and AOP helps you decouple cross-cutting concerns from the objects that they affect.
 * AOP is like triggers in programming languages such as Perl, .NET, Java, and others. Guice provides interceptors to intercept an application.
 * For example, when a method is executed, you can add extra functionality before or after the method execution.
 */

/*
 * Matcher − Matcher is an interface to either accept or reject a value.
 * In Guice AOP, we need two matchers: one to define which classes participate, and another for the methods of those classes.
 */

/*
 * MethodInterceptor − MethodInterceptors are executed when a matching method is called.
 * They can inspect the call: the method, its arguments, and the receiving instance.
 * We can perform cross-cutting logic and then delegate to the underlying method.
 * Finally, we may inspect the return value or exception and return.
 */

package com.spellchecker.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;

public class GuiceTester {

	public static void main(String[] args) {
		
		Injector injector = Guice.createInjector(new TextEditorModule());
		TextEditor editor = injector.getInstance(TextEditor.class);
		editor.makeSpellCheck(); 

	}

}

class TextEditor {
	private SpellChecker spellChecker;
	
	@Inject
	public TextEditor(SpellChecker spellChecker) {
		this.spellChecker = spellChecker;
	}
	public void makeSpellCheck() {
		spellChecker.checkSpelling();
	}
}

//Binding Module
class TextEditorModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(SpellChecker.class).to(SpellCheckerImpl.class);
		bindInterceptor(Matchers.any(),
				Matchers.annotatedWith(CallTracker.class),
				new CallTrackerService());
	}
}

//spell checker interface
interface SpellChecker {
	public void checkSpelling();
}

//spell checker implementation
class SpellCheckerImpl implements SpellChecker {
	@Override @CallTracker
	
	public void checkSpelling() {
		System.out.println("Inside checkSpelling.");
	}
}
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
@interface CallTracker {}

class CallTrackerService implements MethodInterceptor {
	@Override
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Before " + invocation.getMethod().getName());
		Object result = invocation.proceed();
		System.out.println("After " + invocation.getMethod().getName());
		return result;
	}
}