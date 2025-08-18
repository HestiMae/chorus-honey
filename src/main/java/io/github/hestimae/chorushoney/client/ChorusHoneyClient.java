package io.github.hestimae.chorushoney.client;

import io.github.hestimae.chorushoney.ChorusHoney;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class ChorusHoneyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.putBlock(ChorusHoney.CHORUS_HONEY_BLOCK, BlockRenderLayer.TRANSLUCENT);
	}
}
