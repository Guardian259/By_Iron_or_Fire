package guardian.byironorfire.Init;

import guardian.byironorfire.ByIronOrFire;
import guardian.byironorfire.inventory.container.SmithingTableContainer;
import guardian.byironorfire.item.DyeableArmorBase;
import guardian.byironorfire.lists.ArmorMaterialList;
import guardian.byironorfire.lists.InitLists;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GenInit {


    private static ResourceLocation location(String name){
        return new ResourceLocation(ByIronOrFire.modId, name);
    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event){

        ByIronOrFire.logger.info("Initializing TileEntities Registration");

        event.getRegistry().registerAll(

        );

        ByIronOrFire.logger.info("TileEntities Registration Complete");

    }

    @SubscribeEvent
    public static void registerContianers(final RegistryEvent.Register<ContainerType<?>> event) {

        ByIronOrFire.logger.info("Initializing Container Registration");

        event.getRegistry().registerAll(

                InitLists.SMITHING_TABLE = new ContainerType<>(SmithingTableContainer::new).setRegistryName("smithing_table")

        );

        ByIronOrFire.logger.info("Container Registration Complete");
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){

        ByIronOrFire.logger.info("Initializing Block Registration");

        event.getRegistry().registerAll(



        );

        ByIronOrFire.logger.info("Block Registration Complete");

    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){

        ByIronOrFire.logger.info("Initializing Item Registration");

        event.getRegistry().registerAll(
                /*
                 *Armor Items
                 */

                /*
                 *Emerald Variant
                 * Might end up removing this. Models are still classic not current style.
                 */
                InitLists.chained_emerald_helmet = new ArmorItem(ArmorMaterialList.CHAINED_EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_emerald_helmet")),
                InitLists.chained_emerald_chestplate = new ArmorItem(ArmorMaterialList.CHAINED_EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_emerald_chestplate")),
                InitLists.chained_emerald_leggings = new ArmorItem(ArmorMaterialList.CHAINED_EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_emerald_leggings")),
                InitLists.chained_emerald_boots = new ArmorItem(ArmorMaterialList.CHAINED_EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_emerald_boots")),

                /*
                 *Emerald Armor
                 * Might end up removing this. Models are still classic not current style.
                 */
                InitLists.emerald_helmet = new ArmorItem(ArmorMaterialList.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("emerald_helmet")),
                InitLists.emerald_chestplate = new ArmorItem(ArmorMaterialList.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("emerald_chestplate")),
                InitLists.emerald_leggings = new ArmorItem(ArmorMaterialList.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("emerald_leggings")),
                InitLists.emerald_boots = new ArmorItem(ArmorMaterialList.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("emerald_boots")),

                /*
                 *Netherite Variant
                 */
                InitLists.chained_netherite_helmet = new ArmorItem(ArmorMaterialList.CHAINED_NETHERITE, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)).setRegistryName(location("chained_netherite_helmet")),
                InitLists.chained_netherite_chestplate = new ArmorItem(ArmorMaterialList.CHAINED_NETHERITE, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)).setRegistryName(location("chained_netherite_chestplate")),
                InitLists.chained_netherite_leggings = new ArmorItem(ArmorMaterialList.CHAINED_NETHERITE, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)).setRegistryName(location("chained_netherite_leggings")),
                InitLists.chained_netherite_boots = new ArmorItem(ArmorMaterialList.CHAINED_NETHERITE, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.EPIC)).setRegistryName(location("chained_netherite_boots")),

                /*
                 *Diamond Variant
                 */
                InitLists.chained_diamond_helmet = new ArmorItem(ArmorMaterialList.CHAINED_DIAMOND, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_diamond_helmet")),
                InitLists.chained_diamond_chestplate = new ArmorItem(ArmorMaterialList.CHAINED_DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_diamond_chestplate")),
                InitLists.chained_diamond_leggings = new ArmorItem(ArmorMaterialList.CHAINED_DIAMOND, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_diamond_leggings")),
                InitLists.chained_diamond_boots = new ArmorItem(ArmorMaterialList.CHAINED_DIAMOND, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_diamond_boots")),

                /*
                 *Iron Variant
                 */
                InitLists.chained_iron_helmet = new ArmorItem(ArmorMaterialList.CHAINED_IRON, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_iron_helmet")),
                InitLists.chained_iron_chestplate = new ArmorItem(ArmorMaterialList.CHAINED_IRON, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_iron_chestplate")),
                InitLists.chained_iron_leggings = new ArmorItem(ArmorMaterialList.CHAINED_IRON, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_iron_leggings")),
                InitLists.chained_iron_boots = new ArmorItem(ArmorMaterialList.CHAINED_IRON, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_iron_boots")),

                /*
                 *Gold Variant
                 */
                InitLists.chained_gold_helmet = new ArmorItem(ArmorMaterialList.CHAINED_GOLD, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_gold_helmet")),
                InitLists.chained_gold_chestplate = new ArmorItem(ArmorMaterialList.CHAINED_GOLD, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_gold_chestplate")),
                InitLists.chained_gold_leggings = new ArmorItem(ArmorMaterialList.CHAINED_GOLD, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_gold_leggings")),
                InitLists.chained_gold_boots = new ArmorItem(ArmorMaterialList.CHAINED_GOLD, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.RARE)).setRegistryName(location("chained_gold_boots")),

                /*
                 *Leather Variant
                 */
                InitLists.chained_leather_helmet = new DyeableArmorBase(ArmorMaterialList.CHAINED_LEATHER, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON)).setRegistryName(location("chained_leather_helmet")),
                InitLists.chained_leather_chestplate = new DyeableArmorBase(ArmorMaterialList.CHAINED_LEATHER, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON)).setRegistryName(location("chained_leather_chestplate")),
                InitLists.chained_leather_leggings = new DyeableArmorBase(ArmorMaterialList.CHAINED_LEATHER, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON)).setRegistryName(location("chained_leather_leggings")),
                InitLists.chained_leather_boots = new DyeableArmorBase(ArmorMaterialList.CHAINED_LEATHER, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON)).setRegistryName(location("chained_leather_boots"))

        );

        ByIronOrFire.logger.info("Item Registration Complete");
    }
}
