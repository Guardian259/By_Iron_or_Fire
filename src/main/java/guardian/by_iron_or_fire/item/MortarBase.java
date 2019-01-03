package guardian.by_iron_or_fire.item;

import guardian.by_iron_or_fire.Main;
import guardian.by_iron_or_fire.entity.projectile.EntityMortar;
import guardian.by_iron_or_fire.init.ItemInit;
import guardian.by_iron_or_fire.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class MortarBase extends Item implements IHasModel {


    public MortarBase(String name) {

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);

        ItemInit.ITEMS.add(this);

    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }


    /**
     * Called when the equipped item is right clicked.
     *
     @Override
     public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) { return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
     }
     */

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

         if(entityLiving instanceof EntityPlayer) {
             EntityPlayer playerIn = ((EntityPlayer) entityLiving);
             World worldIn = ((EntityPlayer) entityLiving).world;

             boolean flag = playerIn.capabilities.isCreativeMode;
             ItemStack itemStack = this.findAmmo(playerIn);

             if (!itemStack.isEmpty() || flag) {
                 if (!worldIn.isRemote) {
                     itemStack = new ItemStack(ItemInit.MORTAR_CORE);
                     ItemMortarCore mortarCore = (ItemMortarCore) (itemStack.getItem() instanceof ItemMortarCore ? itemStack.getItem() : ItemInit.MORTAR_CORE);
                     EntityMortar entityMortar = mortarCore.createBomb(worldIn);
                     entityMortar.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 4.0F, 1.0F);
                 }
             }
         }
        return super.onEntitySwing(entityLiving, stack);
    }

    public ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isBomb(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isBomb(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isBomb(itemstack))
                {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    public boolean isBomb(ItemStack stack){
         return stack.getItem() instanceof ItemMortarCore;
    }


    /**
    * Called when the equipped item is right clicked.
    *
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            //itemstack.shrink(1);
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, -1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
            /**fires a new entity from the item*
            //EntityMortar entitymortar = new EntityMortar(worldIn, playerIn);
            EntitySnowball entitysnowball = new EntitySnowball(worldIn, playerIn);
            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            /**back up of the above line; just in case*
            entitysnowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            entitysnowball.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, entitysnowball.posX, entitysnowball.posY - 0.3D, entitysnowball.posZ, 0.05D,  -0.5D, 0.05D);
            worldIn.spawnEntity(entitysnowball);
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
     */


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}

