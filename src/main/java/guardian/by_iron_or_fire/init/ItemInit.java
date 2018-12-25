package guardian.by_iron_or_fire.init;

import guardian.by_iron_or_fire.item.ArmorBase;
import guardian.by_iron_or_fire.item.DyeableArmor;
import guardian.by_iron_or_fire.item.ItemMortarCore;
import guardian.by_iron_or_fire.item.MortarBase;
import guardian.by_iron_or_fire.util.References;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<Item>();


    /**
     *Materials
     */
    public static final ItemArmor.ArmorMaterial CHAINED_EMERALD = EnumHelper.addArmorMaterial("chained_emerald", References.MODID + ":chained_emerald", 28, new int[]{5, 8, 10, 5}, 9, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 3.0F);
    public static final ItemArmor.ArmorMaterial EMERALD = EnumHelper.addArmorMaterial("emerald", References.MODID + ":emerald", 26, new int[]{5, 8, 10, 5}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F);
    public static final ItemArmor.ArmorMaterial CHAINED_DIAMOND = EnumHelper.addArmorMaterial("chained_diamond", References.MODID + ":chained_diamond", 36, new int[]{4, 7, 9, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 2.0F);
    public static final ItemArmor.ArmorMaterial CHAINED_IRON = EnumHelper.addArmorMaterial("chained_iron", References.MODID + ":chained_iron", 18, new int[]{3, 6, 7, 4}, 9, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F);
    public static final ItemArmor.ArmorMaterial CHAINED_GOLD = EnumHelper.addArmorMaterial("chained_gold", References.MODID + ":chained_gold", 10, new int[]{2, 4, 6, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F);
    public static final ItemArmor.ArmorMaterial CHAINED_LEATHER = EnumHelper.addArmorMaterial("chained_leather", References.MODID + ":chained_leather", 8, new int[]{2, 3, 4, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F);
    /**
     * Weapon Items
     */
    public static final Item HAND_MORTAR = new MortarBase("Hand_Mortar");
    public static final Item MORTAR_CORE = new ItemMortarCore("Mortar_Core");



    /**
     *Armor Items
     */

    /**
     Emerald Variant
     */
    public static final Item CHAINED_EMERALD_HELMET = new ArmorBase("chained_emerald_helmet", CHAINED_EMERALD,3, EntityEquipmentSlot.HEAD);
    public static final Item CHAINED_EMERALD_CHESTPLATE = new ArmorBase("chained_emerald_chestplate", CHAINED_EMERALD,3,EntityEquipmentSlot.CHEST);
    public static final Item CHAINED_EMERALD_LEGGINGS = new ArmorBase("chained_emerald_leggings", CHAINED_EMERALD,3,EntityEquipmentSlot.LEGS);
    public static final Item CHAINED_EMERALD_BOOTS = new ArmorBase("chained_emerald_boots", CHAINED_EMERALD,3,EntityEquipmentSlot.FEET);

    /**
     Emerald Armor
     */
    public static final Item EMERALD_HELMET = new ArmorBase("emerald_helmet", EMERALD,3, EntityEquipmentSlot.HEAD);
    public static final Item EMERALD_CHESTPLATE = new ArmorBase("emerald_chestplate", EMERALD,3,EntityEquipmentSlot.CHEST);
    public static final Item EMERALD_LEGGINGS = new ArmorBase("emerald_leggings", EMERALD,3,EntityEquipmentSlot.LEGS);
    public static final Item EMERALD_BOOTS = new ArmorBase("emerald_boots", EMERALD,3,EntityEquipmentSlot.FEET);

    /**
    Diamond Variant
     */
    public static final Item CHAINED_DIAMOND_HELMET = new ArmorBase("chained_diamond_helmet", CHAINED_DIAMOND,3, EntityEquipmentSlot.HEAD);
    public static final Item CHAINED_DIAMOND_CHESTPLATE = new ArmorBase("chained_diamond_chestplate", CHAINED_DIAMOND,3,EntityEquipmentSlot.CHEST);
    public static final Item CHAINED_DIAMOND_LEGGINGS = new ArmorBase("chained_diamond_leggings", CHAINED_DIAMOND,3,EntityEquipmentSlot.LEGS);
    public static final Item CHAINED_DIAMOND_BOOTS = new ArmorBase("chained_diamond_boots", CHAINED_DIAMOND,3,EntityEquipmentSlot.FEET);

    /**
     *Iron Variant
     */
    public static final Item CHAINED_IRON_HELMET = new ArmorBase("chained_iron_helmet", CHAINED_IRON,2,EntityEquipmentSlot.HEAD);
    public static final Item CHAINED_IRON_CHESTPLATE = new ArmorBase("chained_iron_chestplate", CHAINED_IRON,2,EntityEquipmentSlot.CHEST);
    public static final Item CHAINED_IRON_LEGGINGS = new ArmorBase("chained_iron_leggings", CHAINED_IRON,2,EntityEquipmentSlot.LEGS);
    public static final Item CHAINED_IRON_BOOTS = new ArmorBase("chained_iron_boots", CHAINED_IRON,2,EntityEquipmentSlot.FEET);

    /**
     *Gold Variant
     */
    public static final Item CHAINED_GOLD_HELMET = new ArmorBase("chained_gold_helmet", CHAINED_GOLD,4,EntityEquipmentSlot.HEAD);
    public static final Item CHAINED_GOLD_CHESTPLATE = new ArmorBase("chained_gold_chestplate", CHAINED_GOLD,4,EntityEquipmentSlot.CHEST);
    public static final Item CHAINED_GOLD_LEGGINGS = new ArmorBase("chained_gold_leggings", CHAINED_GOLD,4,EntityEquipmentSlot.LEGS);
    public static final Item CHAINED_GOLD_BOOTS = new ArmorBase("chained_gold_boots", CHAINED_GOLD,4,EntityEquipmentSlot.FEET);

    /**
     *Leather Variant
     */
    public static final Item CHAINED_LEATHER_HELMET = new DyeableArmor("chained_leather_helmet", CHAINED_LEATHER,0,EntityEquipmentSlot.HEAD);
    public static final Item CHAINED_LEATHER_CHESTPLATE = new DyeableArmor("chained_leather_chestplate", CHAINED_LEATHER,0,EntityEquipmentSlot.CHEST);
    public static final Item CHAINED_LEATHER_LEGGINGS = new DyeableArmor("chained_leather_leggings", CHAINED_LEATHER,0,EntityEquipmentSlot.LEGS);
    public static final Item CHAINED_LEATHER_BOOTS = new DyeableArmor("chained_leather_boots", CHAINED_LEATHER,0,EntityEquipmentSlot.FEET);

}
