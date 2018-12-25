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


    /**
     * On a players death, any armor pieces within said player equipment inventory that have the <code>NBTTagCompound</code>
     * key <code>totemed</code> will be given a subsequent <code>NBTTagCompound</code> key <code>worn</code>.
     * This Tag is used later during a players respawn to return the item to its original inventory slot.
     * To see this tags use see the <code>onPlayerRespawn</code>.
     * @see guardian.by_iron_or_fire.inventory.ContainerArmorer#nbtIntGen(ItemStack, String, int)
     * @see #onPlayerRespawn(PlayerEvent.Clone)
     */
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event){
        if(event.getEntity() instanceof EntityPlayer){
            /*
             * this loops through the players inventory and checks if they were wearing a totemed item
             * if true then the NBT 'worn' will be given to the item; to be used for later
             */
            for(int i = 0; i < 4; i++) {

                wornItem = ((EntityPlayer) event.getEntity()).inventory.armorInventory.get(i);

                //double check that there is an actual item in the slot
                if(((EntityPlayer) event.getEntity()).inventory.armorInventory.get(i) != ItemStack.EMPTY){
                    //checks to see if the Item has the necessary key
                    if(wornItem.getTagCompound().hasKey("totemed")){
                        //generation of the actual NBT on the item
                        NBTTagCompound wornItemTagCompound = wornItem.getTagCompound();
                        if (wornItemTagCompound == null)
                            wornItemTagCompound = new NBTTagCompound();
                        wornItemTagCompound.setBoolean("worn", true);
                        wornItem.setTagCompound(wornItemTagCompound);
                    }
                }
            }
        }
    }

    /**
     * On a players item drop event, said players equipment inventory is checked for the <code>NBTTagCompound</code> key <code>totemed</code>.
     * If the tag key is present on an item said item is removed from the players drops and returned to the the players
     * inventory upon respawn. All tag integer values for each instance are incremented by one, and if the int's value
     * is greater than one there is a chance to remove the tag, replacing it with a curse of vanishing.
     * This is preformed for all ItemStacks within said players equipment inventory.
     * Armor curse chance is settable within <code>References</code>.
     * This <code>NBTTagCompound</code> in the <code>ArmorerContainer</code> though this may change.
     * @see guardian.by_iron_or_fire.inventory.ContainerArmorer#nbtIntGen(ItemStack, String, int)
     * @see guardian.by_iron_or_fire.util.References#armorCurseChance
     */
    @SubscribeEvent
    public static void onPlayerDrops(PlayerDropsEvent event){

            EntityPlayer player = event.getEntityPlayer();
            int size = (event.getDrops().size());

            if(!player.world.isRemote) {

                for (int i = 0; i < size; i++) {
                    boundItem = event.getDrops().get(i).getItem();


                    //check that the item has NBT
                    if (boundItem.hasTagCompound()) {
                        if (boundItem.getTagCompound().hasKey("totemed")) {

                            int count = boundItem.getTagCompound().getInteger("totemed");

                            //checks current # of uses if first use there is no chance of a curse
                            if (count < 1) {
                                boundItem.getTagCompound().setInteger("totemed", count + 1);
                                player.inventory.add(i, event.getDrops().get(i).getItem());
                                event.getDrops().get(i).setItem(ItemStack.EMPTY);
                                //if number of uses is greater than one then it is run through a percentage chance
                            }else{

                                int randi = ((int) Math.round(Math.random() * 100));

                                //if chance was a failure; results in a curse
                                if (randi >= 0 && randi <= armorCurseChance) {
                                    boundItem.getTagCompound().removeTag("totemed");
                                    boundItem.addEnchantment(Enchantment.getEnchantmentByID(71), 1);
                                    player.inventory.add(i, event.getDrops().get(i).getItem());
                                    event.getDrops().get(i).setItem(ItemStack.EMPTY);

                                }
                                //if chance was a success
                                else {
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

    /**
     * On a players respawn, any items which would have been maintained postmortem, containing the <code>NBTTagCompound</code>
     * key <code>worn</code> are returned to there valid equipment slot. As the <code>worn</code> key is only set on death,
     * and only for equipped items, there can never be duplicate items destined for the same slot. Once this act is done the <code>worn</code>
     * <code>NBTTagCompound</code> is removed so as to not double up this tag upon the next death.
     * The <code>worn</code> key is set by <code>onPlayerDeath</code>
     * @see #onPlayerDeath(LivingDeathEvent)
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {

        //double check that the player is dead
        if (event.isWasDeath()) {

            InventoryPlayer playerOldInv = event.getOriginal().inventory;
            InventoryPlayer playerThisInv = ((EntityPlayer) event.getEntity()).inventory;

            //loop to transfer the items back into the new entity
            for(int j = 0; j< playerOldInv.getSizeInventory(); j++)


                if(playerOldInv.getStackInSlot(j).hasTagCompound()) {

                    //gets the current item being checked
                    ItemStack armorStack = playerOldInv.getStackInSlot(j);

                    //double checks if the item is indeed a real item
                    if(armorStack != ItemStack.EMPTY) {
                        //checks if the item was being worn, if true it is returned to the equipment slot
                        if (armorStack.getTagCompound().hasKey("worn")) {
                            int slotIndex = ((ItemArmor) armorStack.getItem()).armorType.getIndex();
                            armorStack.getTagCompound().removeTag("worn");
                            playerThisInv.armorInventory.set(slotIndex, armorStack);
                            //if the item was not worn then it will simply be returned to any open slot
                        } else {
                            playerThisInv.add(j, armorStack);
                        }
                    }
                    //if the item was not worn then it will simply be returned to any open slot
                } else {
                    playerThisInv.add(j,playerOldInv.getStackInSlot(j));
            }
        }
    }
}
