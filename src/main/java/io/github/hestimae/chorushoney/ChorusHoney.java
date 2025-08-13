package io.github.hestimae.chorushoney;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChorusHoney implements ModInitializer {
	public static final String ID = "chorus_honey";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("[Chorus Honey] Bzzzzzz... Choruses ur honey");
	}
}
