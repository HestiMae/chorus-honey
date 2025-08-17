package io.github.hestimae.chorushoney;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ChorusHoneyBottleItem extends Item {
	public ChorusHoneyBottleItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity player) {
			Vec3d pos = user.getPos();
			double oldX = pos.getX();
			double oldY = pos.getY();
			double oldZ = pos.getZ();

			if (user.hasVehicle()) {
				user.stopRiding();
			}
			Integer ceilingY = null;
			for (int i = 1; i < 8; i++) {
				if (!world.getBlockState(user.getBlockPos().up(i)).getCollisionShape(world, user.getBlockPos().up(i)).isEmpty()) {
					ceilingY = user.getBlockY() + i + 1;
					break;
				}
			}
			if (ceilingY != null) {
				int bottomY = world.getBottomY();
				int maxY = bottomY + world.getHeight();
				for (int newY = Math.max(ceilingY, bottomY); newY < maxY - 1; ++newY) {
					if (user.teleport(oldX, newY, oldZ, true)) {
						world.emitGameEvent(GameEvent.TELEPORT, pos, GameEvent.Emitter.of(user));
						world.playSound(player, oldX, oldY, oldZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 0.8F);
						player.getItemCooldownManager().set(stack, 60);
						return super.finishUsing(stack, world, user);
					}
				}
			} else {
				world.playSound(player, oldX, oldY, oldZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 0.8F);
				player.getItemCooldownManager().set(stack, 100);
			}
		}
		return stack;
	}
}
