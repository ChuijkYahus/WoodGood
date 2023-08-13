package net.mehvahdjukaar.every_compat.modules.graveyard;

import com.finallion.graveyard.TheGraveyard;
import com.finallion.graveyard.blockentities.SarcophagusBlockEntity;
import com.finallion.graveyard.blockentities.render.SarcophagusBlockEntityRenderer;
import com.finallion.graveyard.blocks.SarcophagusBlock;
import com.finallion.graveyard.config.GraveyardConfig;
import com.finallion.graveyard.init.TGBlocks;
import com.finallion.graveyard.init.TGItems;
import com.starfish_studios.another_furniture.block.ShelfBlock;
import com.starfish_studios.another_furniture.block.entity.ShelfBlockEntity;
import com.starfish_studios.another_furniture.client.renderer.blockentity.PlanterBoxRenderer;
import com.starfish_studios.another_furniture.client.renderer.blockentity.ShelfRenderer;
import net.mehvahdjukaar.every_compat.WoodGood;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.every_compat.modules.another_furniture.AnotherFurnitureModule;
import net.mehvahdjukaar.selene.block_set.wood.WoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class GraveyardModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> COFFINS;

    public GraveyardModule(String modId) {
        super(modId, "gy");


        COFFINS = SimpleEntrySet.builder(WoodType.class, "coffin",
                        () -> getModBlock("oak_coffin"), () -> WoodType.OAK_WOOD_TYPE,
                        w -> new CompatCoffinfBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion().strength(1.0F),w))
                .addTag(modRes("coffins"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("coffins"), Registry.ITEM_REGISTRY)
                .defaultRecipe()
                .setTab(() -> TheGraveyard.GROUP)
                .addTile(CompatCoffinBlockTile::new)
                .addTextureM(modRes("block/oak_coffin"), WoodGood.res("model/oak_coffin_m"))

                .build();

        this.addEntry(COFFINS);

    }

    @Override
    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType<CompatCoffinBlockTile>) (COFFINS.getTileHolder().tile), CompatCoffinRenderer::new);
    }


    //idk why but object holder class loader thingie keeps trying to load this if its not inner private like this
    class CompatCoffinBlockTile extends SarcophagusBlockEntity {

        private final  WoodType woodType;

        public CompatCoffinBlockTile(BlockPos pos, BlockState state) {
            super(pos, state);
            this.woodType =  ((CompatCoffinfBlock)state.getBlock()).getWoodType();
        }

        @Override
        public BlockEntityType<?> getType() {
            return COFFINS.getTileHolder().tile;
        }

        public WoodType getWoodType() {
          return   this.woodType;
        }
    }

    private class CompatCoffinfBlock extends SarcophagusBlock {
        private final WoodType woodType;

        public CompatCoffinfBlock(Properties properties, WoodType woodType) {
            super(properties,true, "lid", "base");
            this.woodType = woodType;
        }

        public WoodType getWoodType() {
            return woodType;
        }

        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CompatCoffinBlockTile(pos, state);
        }
    }
}
