package io.github.hestimae.chorushoney;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.consume.RemoveEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.component.type.ConsumableComponents.drink;
import static net.minecraft.item.Items.GLASS_BOTTLE;

public class ChorusHoney implements ModInitializer {
	public static final String ID = "chorus_honey";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final FoodComponent CHORUS_HONEY_BOTTLE_FOOD = new FoodComponent.Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build();
	public static final ConsumableComponent CHORUS_HONEY_BOTTLE_CONSUMABLE = drink().consumeSeconds(2.0F).sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).consumeEffect(new RemoveEffectsConsumeEffect(StatusEffects.POISON)).build();
	public static final ChorusHoneyBottleItem CHORUS_HONEY_BOTTLE_ITEM = Registry.register(Registries.ITEM, Identifier.of(ID, "chorus_honey_bottle"),
		new ChorusHoneyBottleItem(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ID, "chorus_honey_bottle"))).recipeRemainder(GLASS_BOTTLE)
			.food(CHORUS_HONEY_BOTTLE_FOOD, CHORUS_HONEY_BOTTLE_CONSUMABLE)
			.useRemainder(GLASS_BOTTLE).maxCount(16)));

	@Override
	public void onInitialize() {
		LOGGER.info("[Chorus Honey] Bzzzzzz... Choruses ur honey");
	}
}
