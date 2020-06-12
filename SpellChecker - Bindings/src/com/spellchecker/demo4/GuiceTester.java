/*
 * Guice provides a way to create bindings with complex objects using @provides method.
 */

package com.spellchecker.demo4;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;

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
	protected void configure() {}
	
	@Provides
	public SpellChecker provideSpellChecker() {
		String dbUrl = "jdbc:mysql://localhost:5326/emp";
		String user = "user";
		int timeout = 100;
		
		SpellChecker SpellChecker = new SpellCheckerImpl(dbUrl, user, timeout);
		return SpellChecker;
	}
}

//spell checker interface
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
		System.out.println("Inside checkSpelling");
		System.out.println(dbUrl);
		System.out.println(user);
		System.out.println(timeout);
	}
}