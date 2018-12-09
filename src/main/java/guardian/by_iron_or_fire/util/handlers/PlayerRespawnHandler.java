package guardian.by_iron_or_fire.util.handlers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static guardian.by_iron_or_fire.util.References.armorCurseChance;


@Mod.EventBusSubscriber
public class PlayerRespawnHandler {

    public static ItemStack boundItem;
    public static ItemStack wornItem;


    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event){
        if(event.getEntity() instanceof EntityPlayer){
            /**
             * this loops through the players inventory and checks if they were wearing a totemed item
             * if true then the NBT 'worn' will be given to the item; to be used for later
             */
            for(int i = 0; i < 4; i++) {
                /**double check that there is an actual item in the slot*/
                if(((EntityPlayer) event.getEntity()).inventory.armorInventory.get(i) != ItemStack.EMPTY){
                    /**generation of the actual NBT on the item*/
                    wornItem = ((EntityPlayer) event.getEntity()).inventory.armorInventory.get(i);
                    NBTTagCompound wornItemTagCompound = wornItem.getTagCompound();
                    if(wornItemTagCompound == null)
                        wornItemTagCompound = new NBTTagCompound();
                    wornItemTagCompound.setBoolean("worn",true);
                    wornItem.setTagCompound(wornItemTagCompound);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDrops(PlayerDropsEvent event){

            EntityPlayer player = event.getEntityPlayer();
            int size = (event.getDrops().size());

            if(!player.world.isRemote) {

                for (int i = 0; i < size; i++) {
                    boundItem = event.getDrops().get(i).getItem();


                    /**check that the item has NBT*/
                    if (boundItem.hasTagCompound()) {
                        if (boundItem.getTagCompound().hasKey("totemed")) {

                            int count = boundItem.getTagCompound().getInteger("totemed");

                            /**checks current # of uses if first use there is no chance of a curse*/
                            if (count < 1) {
                                boundItem.getTagCompound().setInteger("totemed", count + 1);
                                player.inventory.add(i, event.getDrops().get(i).getItem());
                                event.getDrops().get(i).setItem(ItemStack.EMPTY);
                            /**if number of uses is greater than one then it is run through a percentage chance*/
                            }else{

                                int randi = ((int) Math.round(Math.random() * 100));

                                /**if chance was a failure; results in a curse*/
                                if (randi >= 0 && randi <= armorCurseChance) {
                                    boundItem.getTagCompound().removeTag("totemed");
                                    boundItem.addEnchantment(Enchantment.getEnchantmentByID(71), 1);
                                    player.inventory.add(i, event.getDrops().get(i).getItem());
                                    event.getDrops().get(i).setItem(ItemStack.EMPTY);
                                /**if chance was a success*/
                                } else {
                                    boundItem.getTagCompound().setInteger("totemed", count + 1);
                                    player.inventory.add(i, event.getDrops().get(i).getItem());
                                    event.getDrops().get(i).setItem(ItemStack.EMPTY);
                                }
                            }
                        }
                    }


                }
            }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {

        /**double check that the player is dead*/
        if (event.isWasDeath()) {

            InventoryPlayer playerOldInv = event.getOriginal().inventory;
            InventoryPlayer playerThisInv = ((EntityPlayer) event.getEntity()).inventory;

            /**loop to transfer the items back into the new entity*/
            for(int j = 0; j< playerOldInv.getSizeInventory(); j++)


                if(playerOldInv.getStackInSlot(j).hasTagCompound()) {

                    /**gets the current item being checked*/
                    ItemStack armorStack = playerOldInv.getStackInSlot(j);

                    /**double checks if the item is indeed a real item*/
                    if(armorStack != ItemStack.EMPTY) {
                        /**checks if the item was being worn, if true it is returned to the equipment slot*/
                        if (armorStack.getTagCompound().hasKey("worn")) {
                            int slotIndex = ((ItemArmor) armorStack.getItem()).armorType.getIndex();
                            armorStack.getTagCompound().removeTag("worn");
                            playerThisInv.armorInventory.set(slotIndex, armorStack);
                        /**if the item was not worn then it will simply be returned to any open slot*/
                        } else {
                            playerThisInv.add(j, armorStack);
                        }
                    }
                    /**if the item was not worn then it will simply be returned to any open slot*/
                } else {
                    playerThisInv.add(j,playerOldInv.getStackInSlot(j));
            }
        }
    }
}
