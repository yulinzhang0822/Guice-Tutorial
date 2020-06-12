/*
 * Guice provides inbuilt binding for java.util.logging.Logger class.
 * Logger's name is automatically set to the name of the class into which the Logger is injected.
 */

package com.spellchecker.demo7;

import java.util.logging.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceTester {

	public static void main(String[] args) {
		
		Injector injector = Guice.createInjector(new TextEditorModule());
		TextEditor editor = injector.getInstance(TextEditor.class);
		editor.makeSpellCheck();

	}

}

class TextEditor {
	private Logger logger;
	
	@Inject
	public TextEditor(Logger logger) {
		this.logger = logger;
	}
	public void makeSpellCheck() {
		logger.info("In TextEditor.makeSpellCheck() method");
	}
}

//Binding Module
class TextEditorModule extends AbstractModule {
	@Override
	protected void configure() {
		
	}
}