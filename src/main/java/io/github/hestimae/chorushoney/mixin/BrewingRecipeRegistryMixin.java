package io.github.hestimae.chorushoney.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.hestimae.chorushoney.ChorusHoney;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {

	@Inject(method = "registerDefaults", at = @At("TAIL"))
	private static void registerDefaults(BrewingRecipeRegistry.Builder builder, CallbackInfo ci)
	{
		((BrewingRecipeRegistryBuilderAccessor) builder).getPotionTypes().add(Ingredient.ofItem(Items.HONEY_BOTTLE));
		((BrewingRecipeRegistryBuilderAccessor) builder).getItemRecipes().add(new BrewingRecipeRegistry.Recipe<>(Items.HONEY_BOTTLE.getRegistryEntry(), Ingredient.ofItem(Items.CHORUS_FRUIT), ChorusHoney.CHORUS_HONEY_BOTTLE_ITEM.getRegistryEntry()));
	}

	@ModifyExpressionValue(method = "craft", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isEmpty()Z"))
	boolean allowNonPotions(boolean original)
	{
		return false;
	}

	@WrapOperation(method = "craft", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/PotionContentsComponent;createStack(Lnet/minecraft/item/Item;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/item/ItemStack;"))
	private ItemStack createNonPotionStack(Item item, RegistryEntry<Potion> potion, Operation<ItemStack> original) {
		if (potion == null) return new ItemStack(item);
		return original.call(item, potion);
	}

	@WrapOperation(method = "craft", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/entry/RegistryEntry;matches(Lnet/minecraft/registry/entry/RegistryEntry;)Z"))
	private boolean rejectNullPotions(RegistryEntry<Potion> instance, RegistryEntry<Potion> potion, Operation<Boolean> original) {
		if (potion == null) return false;
		return original.call(instance, potion);
	}

	@WrapOperation(method = "craft", at = @At(value = "INVOKE", target = "Ljava/util/Optional;get()Ljava/lang/Object;"))
	private Object allowEmpty(Optional<Object> instance, Operation<RegistryEntry<Potion>> original) {
		return instance.orElse(null);
	}

}
