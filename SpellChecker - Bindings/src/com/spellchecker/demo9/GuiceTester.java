/*
 * As bindings are defined in Binding Module, Guice uses them whenever it needs to inject dependencies.
 * In case bindings are not present, it can attempt to create just-in-time bindings.
 * Bindings present in the binding module are called Explicit bindings and are of higher precedence whereas just-in-time bindings are called Implicit bindings.
 * Note that if both type of bindings are present, explicit bindings are considered for mapping.
 */

/*
 * @ImplementatedBy annotation tells the guice about the implementation class.
 * No binding is required in Binding Module in such a case.
 */

package com.spellchecker.demo9;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

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
	public TextEditor( SpellChecker spellChecker) {
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
		bind(String.class)
		.annotatedWith(Names.named("JDBC"))
		.toInstance("jdbc:mysql://localhost:5326/emp");
	}
}

@ImplementedBy(SpellCheckerImpl.class)
interface SpellChecker {
	public void checkSpelling();
}

//spell checker implementation
class SpellCheckerImpl implements SpellChecker {
	@Inject @Named("JDBC")
	private String dbUrl;
	public SpellCheckerImpl() {}
	
	@Override
	public void checkSpelling() {
		System.out.println("Inside checkSpelling." );
		System.out.println(dbUrl);
	}
}