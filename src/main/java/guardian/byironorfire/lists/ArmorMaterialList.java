package guardian.byironorfire.lists;

import guardian.byironorfire.ByIronOrFire;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public enum ArmorMaterialList implements IArmorMaterial {

    //Armor Materials
    CHAINED_EMERALD("chained_emerald", 28, new int[]{5, 8, 10, 5}, 28, Items.EMERALD,"items.armor.equip_chain", 3.0F),
    EMERALD("emerald", 26, new int[]{5, 8, 10, 5}, 9, Items.EMERALD,"items.armor.equip_chain", 3.0F),
    /**TODO: Determine Render Dist values. Determine Defence Values. Determine Toughness*/
    CHAINED_NETHERITE("chained_netherite", 36, new int[]{4, 7, 9, 4}, 10, Items.DIAMOND, "items.armor.equip_chain",  3.0F),
    CHAINED_DIAMOND("chained_diamond", 36, new int[]{4, 7, 9, 4}, 10, Items.DIAMOND, "items.armor.equip_chain",  2.0F),
    CHAINED_IRON("chained_iron", 18, new int[]{3, 6, 7, 4}, 9, Items.IRON_INGOT, "items.armor.equip_chain", 0.0F),
    CHAINED_GOLD("chained_gold", 10, new int[]{2, 4, 6, 3}, 25, Items.GOLD_INGOT, "items.armor.equip_chain", 0.0F),
    CHAINED_LEATHER("chained_leather", 8, new int[]{2, 3, 4, 2}, 15, Items.LEATHER, "items.armor.equip_chain", 0.0F);


    private static final int[] max_damage_array = new int[]{13,15,16,11};
    private String name, equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmounts;
    private float toughness;

    ArmorMaterialList(String name, int durability, int[] damageReductionAmounts, int enchantability, Item repairItem, String equipeSound, float toughness){

        this.name = name;
        this.equipSound = equipeSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmounts = damageReductionAmounts;
        this.toughness = toughness;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return max_damage_array[slotIn.getIndex()] * this.durability;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmounts[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return new SoundEvent(new ResourceLocation(equipSound));
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairItem);
    }

    @Override
    public String getName() {
        return ByIronOrFire.modId + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }
}
