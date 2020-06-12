/*
 * Injection is a process of injecting dependency into an object.
 * Method and field injections can be used to initialize using exiting object using injector.injectMembers() method.
 */

package com.spellchecker.demo5;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceTester {

	public static void main(String[] args) {
		
		Injector injector = Guice.createInjector(new TextEditorModule());
		SpellChecker spellChecker = new SpellCheckerImpl();
		injector.injectMembers(spellChecker);
		
		TextEditor editor = injector.getInstance(TextEditor.class);
		editor.makeSpellCheck();

	}

}

class TextEditor {
	private SpellChecker spellChecker;
	
	@Inject
	public void setSpellChecker(SpellChecker spellChecker) {
		this.spellChecker = spellChecker;
	}
	
	public TextEditor() {}
	
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
@ImplementedBy(SpellCheckerImpl.class)
interface SpellChecker {
	public void checkSpelling();
}

//spell checker implementation
class SpellCheckerImpl implements SpellChecker {
	public SpellCheckerImpl() {}
	
	@Override
	public void checkSpelling() {
		System.out.println("Inside checkSpelling.");
	}
}