package net.mehvahdjukaar.every_compat.modules.neoforge.productive_bees;

/*
public class ProductiveBeesModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> advancedBeehives;
    public final SimpleEntrySet<WoodType, Block> expansionBoxes;

    public ProductiveBeesModule(String modId) {
        super(modId, "pb");
        CreativeModeTab tab = ModItemGroups.PRODUCTIVE_BEES;


        //TODO: all these are invalid now
        advancedBeehives = SimpleEntrySet.builder(WoodType.class, "beehive", "advanced",
                        ModBlocks.ADVANCED_SPRUCE_BEEHIVE, () -> WoodTypeRegistry.getValue(new ResourceLocation("spruce")),
                        w -> new AdvancedBeehive(Utils.copyPropertySafe(w.planks)))
                // Textures are not generated for this, temporary until model overrides work
                .addModelTransform(m -> m.replaceString("buzzier_bees:block/spruce", "productivebees:block/spruce"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_right_honey"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_right_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_left_honey"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_left_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_right"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_right_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_left"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_left_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_side_right"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_side_right_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_side_left"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_side_left_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_front_top_honey"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_front_top_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_side_top_honey"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_side_top_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_front_honey"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_front_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_side_top"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_side_top_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_front"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_front_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_side"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_side_m"))
                .addTextureM(EveryCompat.res("block/spruce_beehive_front_honey"), EveryCompat.res("block/spruce_beehive_front_honey_m"))
                .addTextureM(EveryCompat.res("block/spruce_beehive_front"), EveryCompat.res("block/spruce_beehive_front_m"))
                .addTextureM(EveryCompat.res("block/spruce_beehive_side"), EveryCompat.res("block/spruce_beehive_side_m"))
                .addTexture(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_end_right"))
                .addTexture(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_end_left"))
                .addTexture(EveryCompat.res("block/spruce_beehive_end"))
                .addTag(modRes("advanced_beehives"), Registries.BLOCK)
                .addTag(modRes("advanced_beehives"), Registries.ITEM)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(BlockTags.BEEHIVES, Registries.BLOCK)
                .addTile(ModBlockEntityTypes.ADVANCED_BEEHIVE)
                // Having two recipes overwrites each other TODO: no actually it souldnt
                .addRecipe(modRes("hives/advanced_spruce_beehive_clear"))
                .addRecipe(modRes("hives/advanced_spruce_beehive"))
                .setTab(() -> tab)
                .build();

        this.addEntry(advancedBeehives);

        expansionBoxes = SimpleEntrySet.builder(WoodType.class, "", "expansion_box",
                        ModBlocks.EXPANSION_BOX_SPRUCE, () -> WoodTypeRegistry.getValue(new ResourceLocation("spruce")),
                        w -> new ExpansionBox(Utils.copyPropertySafe(w.planks)))
                // Textures are not generated for this, temporary until model overrides work
                .addModelTransform(m -> m.replaceString("buzzier_bees:block/spruce", "productivebees:block/spruce"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_right_honey"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_right_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_left_honey"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_left_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_right"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_right_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_front_left"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_front_left_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_side_right"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_side_right_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_side_left"), EveryCompat.res("block/pb/advanced_beehive/horizontal/oak_beehive_side_left_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_front_top_honey"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_front_top_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_side_top_honey"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_side_top_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_front_honey"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_front_honey_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_side_top"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_side_top_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_front"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_front_m"))
                .addTextureM(EveryCompat.res("block/advanced_beehive/spruce_beehive_side"), EveryCompat.res("block/pb/advanced_beehive/oak_beehive_side_m"))
                .addTextureM(EveryCompat.res("block/spruce_beehive_front_honey"), EveryCompat.res("block/spruce_beehive_front_honey_m"))
                .addTexture(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_end_right"))
                .addTexture(EveryCompat.res("block/advanced_beehive/horizontal/spruce_beehive_end_left"))
                .addTexture(EveryCompat.res("block/spruce_beehive_front"))
                .addTexture(EveryCompat.res("block/spruce_beehive_side"))
                .addTexture(EveryCompat.res("block/spruce_beehive_end"))
                .addTag(modRes("expansion_boxes"), Registries.BLOCK)
                .addTag(modRes("expansion_boxes"), Registries.ITEM)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(BlockTags.BEEHIVES, Registries.BLOCK)
                .addTile(ModBlockEntityTypes.EXPANSION_BOX)
                .addRecipe(modRes("boxes/expansion_box_spruce"))
                .setTab(() -> tab)
                .build();

        this.addEntry(expansionBoxes);

    }
}
*/