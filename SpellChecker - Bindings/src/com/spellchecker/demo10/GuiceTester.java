/*
 * As bindings are defined in Binding Module, Guice uses them whenever it needs to inject dependencies.
 * In case bindings are not present, it can attempt to create just-in-time bindings.
 * Bindings present in the binding module are called Explicit bindings and are of higher precedence whereas just-in-time bindings are called Implicit bindings.
 * Note that if both type of bindings are present, explicit bindings are considered for mapping.
 */

/*
 * @ProvidedBy annotation tells the guice about the provider of implementation class.
 * No binding is required in Binding Module in such a case.
 */

package com.spellchecker.demo10;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.ProvidedBy;
import com.google.inject.Provider;

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
		
	}
}

@ProvidedBy(SpellCheckerProvider.class)
interface SpellChecker {
	public void checkSpelling();
}

//spell checker implementation
class SpellCheckerImpl implements SpellChecker {
	private String dbUrl;
	private String user;
	private Integer timeout;
	
	@Inject
	public SpellCheckerImpl(String dbUrl,
			String user,
			Integer timeout) {
		this.dbUrl = dbUrl;
		this.user = user;
		this.timeout = timeout;
	}
	
	@Override
	public void checkSpelling() {
		System.out.println("Inside checkSpelling. ");
		System.out.println(dbUrl);
		System.out.println(user);
		System.out.println(timeout);
	}
}

class SpellCheckerProvider implements Provider<SpellChecker> {
	@Override
	public SpellChecker get() {
		String dbUrl = "jdbc:mysql://localhost:5326/emp";
		String user = "user";
		Integer timeout = 100;
		
		SpellChecker SpellChecker = new SpellCheckerImpl(dbUrl, user, timeout);
		return SpellChecker;
	}
}