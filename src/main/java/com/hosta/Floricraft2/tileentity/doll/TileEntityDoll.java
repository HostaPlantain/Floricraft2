package com.hosta.Floricraft2.tileentity.doll;

import java.util.List;

import javax.annotation.Nonnull;

import com.hosta.Floricraft2.item.fragrance.ItemSachet;
import com.hosta.Floricraft2.tileentity.TileEntityBasicRendering;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class TileEntityDoll extends TileEntityBasicRendering implements ITickable {

	private ItemStack itemDisplay = ItemStack.EMPTY;

	public void setDisplayedItem(ItemStack stack)
	{
		this.itemDisplay = stack;
	}

	public ItemStack getDisplayedItem()
	{
		return this.itemDisplay;
	}

	public void onRightClick(EntityPlayer player, @Nonnull ItemStack stackIn, EnumHand hand)
	{
		if (this.getDisplayedItem().isEmpty() && !stackIn.isEmpty())
		{
			if (!this.world.isRemote)
			{
				player.setHeldItem(hand, ItemStack.EMPTY);
			}
			this.setDisplayedItem(stackIn);
			this.markDirty();
		}
		else if (!this.getDisplayedItem().isEmpty() && stackIn.isEmpty())
		{
			if (!this.world.isRemote)
			{
				ItemHandlerHelper.giveItemToPlayer(player, this.getDisplayedItem());
			}
			this.setDisplayedItem(stackIn);
			this.markDirty();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		if (this.getDisplayedItem() != null)
		{
			NBTTagCompound nbtItem = new NBTTagCompound();
			nbtItem = this.getDisplayedItem().writeToNBT(nbtItem);
			nbt.setTag("Item", nbtItem);
		}

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		if (nbt.hasKey("Item"))
		{
			NBTTagCompound nbtItem = nbt.getCompoundTag("Item");
			this.setDisplayedItem(new ItemStack(nbtItem));
		}
	}

	@Override
	public void update()
	{
		ItemStack stack = this.getDisplayedItem();
		if (!this.world.isRemote && !stack.isEmpty())
		{
			Item item = stack.getItem();
			if (item instanceof ItemSachet)
			{
				ItemSachet sachet = ((ItemSachet)item);
				int range = 5;
				AxisAlignedBB aabb = this.getAxisAlignedBB().expand(range, range, range).expand(-range, -range, -range);
				List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);
				for (EntityPlayer player : list)
				{
					int addedEffects = sachet.appendEffects(stack, player);
					if (addedEffects > 0)
					{
						addedEffects = 1 << (addedEffects - 1);
						this.setDisplayedItem(sachet.damageItem(stack, addedEffects));
						this.markDirty();
					}
				}
			}
		}
	}
}
