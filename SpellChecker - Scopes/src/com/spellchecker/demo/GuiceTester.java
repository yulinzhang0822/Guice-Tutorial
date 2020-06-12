/*
 * Guice returns a new instance every time it supplies a value as its default behavior.
 * It is configurable via scopes.
 * @Singleton − Single instance for lifetime of the application. @Singleton object needs to be threadsafe.
 * @SessionScoped − Single instance for a particular session of the web application. @SessionScoped object needs to be threadsafe.
 * @RequestScoped − Single instance for a particular request of the web application. @RequestScoped object does not need to be threadsafe.
 * 
 * Application of Scopes
 * You can apply scopes in the following ways -
 * At Class level
 * At Configuration level
 * At Method level
 */

/*
 * Result With @Singleton Annotation
 */

package com.spellchecker.demo;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class GuiceTester {

	public static void main(String[] args) {
		
		Injector injector = Guice.createInjector(new TextEditorModule());
		SpellChecker spellChecker = new SpellCheckerImpl();
		injector.injectMembers(spellChecker);
		
		TextEditor editor = injector.getInstance(TextEditor.class);
		System.out.println(editor.getSpellCheckerId());
		
		TextEditor editor1 = injector.getInstance(TextEditor.class);
		System.out.println(editor1.getSpellCheckerId());

	}

}

class TextEditor {
	private SpellChecker spellChecker;
	
	@Inject
	public void setSpellChecker(SpellChecker spellChecker) {
		this.spellChecker = spellChecker;
	}
	public TextEditor() { }
	
	public void makeSpellCheck() {
		spellChecker.checkSpelling();
	}
	public double getSpellCheckerId() {
		return spellChecker.getId();
	}
}

//Binding Module
class TextEditorModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(SpellChecker.class).to(SpellCheckerImpl.class);
	}
}
interface SpellChecker {
	public double getId();
	public void checkSpelling();
}
@Singleton
class SpellCheckerImpl implements SpellChecker {
	double id;
	
	public SpellCheckerImpl() {
		id = Math.random();
	}
	@Override
	public void checkSpelling() {
		System.out.println("Inside checkSpelling.");
	}
	@Override
	public double getId() {
		return id;
	}
}