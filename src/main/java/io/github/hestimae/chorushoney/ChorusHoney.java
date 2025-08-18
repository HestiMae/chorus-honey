package io.github.hestimae.chorushoney;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.consume.RemoveEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
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
	public static final ChorusHoneyBlock CHORUS_HONEY_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(ID, "chorus_honey_block"), new ChorusHoneyBlock(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ID, "chorus_honey_block"))).mapColor(MapColor.PURPLE).velocityMultiplier(0.4F).jumpVelocityMultiplier(0.5F).nonOpaque().sounds(BlockSoundGroup.HONEY)));
	public static final BlockItem CHORUS_HONEY_BLOCK_ITEM = Registry.register(Registries.ITEM, Identifier.of(ID, "chorus_honey_block"), new BlockItem(CHORUS_HONEY_BLOCK, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ID, "chorus_honey_block")))));

	public static final ItemGroup CHORUS_HONEY_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(ID, "items"), FabricItemGroup.builder()
		.displayName(Text.translatable("itemGroup.%s.%s".formatted(ID, "items")))
		.icon(() -> new ItemStack(CHORUS_HONEY_BLOCK_ITEM))
		.entries((c, e) -> {
			e.add(CHORUS_HONEY_BLOCK_ITEM);
			e.add(CHORUS_HONEY_BOTTLE_ITEM);
		})
		.build()
	);

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
			entries.addAfter(Items.HONEY_BLOCK, CHORUS_HONEY_BLOCK_ITEM);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
			entries.addAfter(Items.HONEY_BOTTLE, CHORUS_HONEY_BOTTLE_ITEM);
		});
		LOGGER.info("[Chorus Honey] Bzzzzzz... Choruses ur honey");
	}
}
