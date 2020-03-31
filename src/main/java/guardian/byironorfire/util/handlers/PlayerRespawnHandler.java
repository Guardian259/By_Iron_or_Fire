package guardian.byironorfire.util.handlers;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class PlayerRespawnHandler{

    public static ItemStack wornItem;
    public static int armorCurseChance = 15;


    /**
     * On a players death, any armor pieces within said player's equipment inventory that have the <code>NBTTag</code>
     * key <code>totemed</code> will be given a subsequent <code>NBTTag</code> key <code>worn</code>.
     * This Tag is used later during a players respawn to return the items to its original inventory slot.
     * To see this tags use see the <code>onPlayerRespawn</code>.
     * @see #onPlayerRespawn(PlayerEvent.Clone)
     */
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            /*
             * this loops through the players inventory and checks if they were wearing a totemed items
             * if true then the NBT 'worn' will be given to the items; to be used for later
             */
            for(int i = 0; i < 4; i++) {

                wornItem = ((PlayerEntity) event.getEntity()).inventory.armorInventory.get(i);

                //double check that there is an actual items in the slot
                if(((PlayerEntity) event.getEntity()).inventory.armorInventory.get(i) != ItemStack.EMPTY){
                    //checks to see if the Item has the necessary key
                    if(wornItem.getTag().hasUniqueId("totemed")){
                        //generation of the actual NBT on the items
                        CompoundNBT wornItemTagCompound = wornItem.getTag();
                        if (wornItemTagCompound == null)
                            wornItemTagCompound = new CompoundNBT();
                        wornItemTagCompound.putBoolean("worn", true);
                        wornItem.setTag(wornItemTagCompound);
                    }
                }
            }
        }
    }

    /**
     * On a players items drop event, said players equipment inventory is checked for the <code>NBTTag</code> key <code>totemed</code>.
     * If the tag key is present on an items said items is removed from the players drops and returned to the the players
     * inventory upon respawn. All tag integer values for each instance are incremented by one, and if the int's value
     * is greater than one there is a chance to remove the tag, replacing it with a curse of vanishing.
     * This is preformed for all ItemStacks within said players equipment inventory.
     * Armor curse chance is settable within @Depreciated <code>References</code>.
     * This <code>NBTTag</code> in the <code>ArmorerContainer</code> though this may change; this is temporary
     * This Method makes use of  <code>deathDropCheck</code>
     * @see #deathDropCheck
     */
    @SubscribeEvent
    public static void onPlayerDrops(LivingDropsEvent event){

        //safety check that the entity is indeed a player
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) event.getEntity());

            if (!player.world.isRemote) {

                for (ItemEntity item : event.getDrops()
                ) {
                    //check that the items has NBT
                    if (item.getItem().hasTag()) {
                        if (item.getItem().getTag().hasUniqueId("totemed")) {

                            //De-buff check
                            deathDropCheck(item.getItem(), player);

                            //Removing the items from the drop pool
                            event.getDrops().iterator().remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks the current # of times a given items, with the <code>totemed</code> tag, has survived respawn.
     * If the value is less than one then said items simply respawn's. Should the # of times be greater than one
     * than each consecutive respawn runs a chance of removing the tag, ending its survivability, and adding the
     * curse of vanishing.
     * This method is used in <code>onPlayerDrops</code>
     * @see #onPlayerDrops(LivingDropsEvent)
     * @param item the items in question, is assumed to already have the needed <code>NBTTag</code>. This assumption is gained from <code>onPlayerDrops</code>.
     * @param player the player associated with the items above.
     */
    private static void deathDropCheck(ItemStack item, PlayerEntity player){

                //current # of times the items has survived death
                int count = item.getTag().getInt("totemed");

                //checks current # of uses if first use there is no chance of a curse
                if (count < 1) {
                    item.getTag().putInt("totemed", count + 1);
                    player.inventory.addItemStackToInventory(item);

                //if number of uses is greater than one then it is run through a percentage chance
                }else{

                    int randi = ((int) Math.round(Math.random() * 100));

                    //if chance was a failure; results in a curse
                    if (randi >= 0 && randi <= armorCurseChance) {
                        item.getTag().remove("totemed");
                        item.addEnchantment(Enchantment.getEnchantmentByID(71), 1);
                        player.inventory.addItemStackToInventory(item);

                    }
                    //if chance was a success
                    else {
                        item.getTag().putInt("totemed", count + 1);
                        player.inventory.addItemStackToInventory(item);
                    }
                }
    }

    /**
     * On a players respawn, any items which would have been maintained postmortem, containing the <code>NBTTag</code>
     * key <code>worn</code> are returned to there valid equipment slot. As the <code>worn</code> key is only set on death,
     * and only for equipped items, there can never be duplicate items destined for the same slot. Once this act is done the <code>worn</code>
     * <code>NBTTag</code> is removed so as to not double up this tag upon the next death.
     * The <code>worn</code> key is set by <code>onPlayerDeath</code>
     * @see #onPlayerDeath(LivingDeathEvent)
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {

        //double check that the player is dead
        if (event.isWasDeath()) {

            PlayerInventory playerOldInv = event.getOriginal().inventory;
            PlayerInventory playerThisInv = ((PlayerEntity) event.getEntity()).inventory;

            //loop to transfer the items back into the new entity
            for(int j = 0; j< playerOldInv.getSizeInventory(); j++)


                if(playerOldInv.getStackInSlot(j).hasTag()) {

                    //gets the current items being checked
                    ItemStack armorStack = playerOldInv.getStackInSlot(j);

                    //double checks if the items is indeed a real items
                    if(armorStack != ItemStack.EMPTY) {
                        //checks if the items was being worn, if true it is returned to the equipment slot
                        if (armorStack.getTag().hasUniqueId("worn")) {
                            int slotIndex = ((ArmorItem) armorStack.getItem()).getEquipmentSlot().getSlotIndex();
                            armorStack.getTag().remove("worn");
                            playerThisInv.armorInventory.set(slotIndex, armorStack);
                            //if the items was not worn then it will simply be returned to any open slot
                        } else {
                            playerThisInv.add(j, armorStack);
                        }
                    }
                    //if the items was not worn then it will simply be returned to any open slot
                } else {
                    playerThisInv.add(j,playerOldInv.getStackInSlot(j));
            }
        }
    }
}
