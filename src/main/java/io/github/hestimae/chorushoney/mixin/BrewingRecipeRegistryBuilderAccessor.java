package io.github.hestimae.chorushoney.mixin;

import net.minecraft.item.Item;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BrewingRecipeRegistry.Builder.class)
public interface BrewingRecipeRegistryBuilderAccessor {
	@Accessor
	List<Ingredient> getPotionTypes();

	@Accessor
	List<BrewingRecipeRegistry.Recipe<Item>> getItemRecipes();
}
