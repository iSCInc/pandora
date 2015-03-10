package com.wikia.mobileconfig.utils;

import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranslatorTest {
	private static final Translator translator = Translator.getInstance();

	@Test
	public void translateSuccess() {
		assertEquals("G'day!", translator.translate("en-au", "_hello_"));
    assertEquals("Seeya", translator.translate("en-au", "_goodbye_"));
    assertEquals("Hello.", translator.translate("en-gb", "_hello_"));
    assertEquals("Farewell", translator.translate("en-gb", "_goodbye_"));
	}

  @Test
  public void translateMissingKey() {
    assertEquals("_rnd_", translator.translate("en-au", "_rnd_"));
  }

  @Test 
  public void translateMissingLanguage() {
    assertEquals("_hello_", translator.translate("en-gg", "_hello_"));
  }

  // These tests document how the system behaves, not necessarily how
  // it was designed to behave.
  
  @Test
  public void undefinedBehaviour_translateNullKey() {
    assertEquals(null, translator.translate("en-au", null));
  }

  @Test (expected = NullPointerException.class)
  public void undefinedBehaviour_translateNullLanguage() {
    translator.translate(null, "_hello_");
  }
}