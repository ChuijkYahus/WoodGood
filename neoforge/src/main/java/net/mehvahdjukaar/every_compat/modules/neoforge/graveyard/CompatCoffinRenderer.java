package net.mehvahdjukaar.every_compat.modules.neoforge.graveyard;

import com.finallion.graveyard.blockentities.SarcophagusBlockEntity;
import com.finallion.graveyard.blockentities.enums.SarcophagusPart;
import com.finallion.graveyard.blockentities.render.SarcophagusBlockEntityRenderer;
import com.finallion.graveyard.blocks.SarcophagusBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;

@net.neoforged.api.distmarker.OnlyIn(Dist.CLIENT)
public class CompatCoffinRenderer extends SarcophagusBlockEntityRenderer<GraveyardModule.CompatCoffinBlockTile> {
    public CompatCoffinRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    public void render(SarcophagusBlockEntity entity, float tickDelta, PoseStack matrixStack, MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getBlockState();
        DoubleBlockCombiner.NeighborCombineResult<? extends SarcophagusBlockEntity> propertySource = DoubleBlockCombiner.combineWithNeigbour(
                (BlockEntityType) GraveyardModule.COFFIN_TILE,
                SarcophagusBlock::getBlockType,
                SarcophagusBlock::getConnectedDirection,
                ChestBlock.FACING,
                blockState,
                entity.getLevel(),
                entity.getBlockPos(),
                (worldx, pos) -> false
        );
        float g = propertySource.apply(SarcophagusBlock.opennessCombiner(entity)).get(tickDelta);
        g = 1.0F - g;
        g = 1.0F - g * g * g;
        String base = ((SarcophagusBlock) blockState.getBlock()).getBase();
        String lid = ((SarcophagusBlock) blockState.getBlock()).getLid();
        BakedModel baseModel = getCustomModel(base);
        BakedModel lidModel = getCustomModel(lid);
        if (entity.getLevel() != null && entity.getBlockState().getValue(SarcophagusBlock.PART) == SarcophagusPart.HEAD) {
            this.render(entity, matrixStack, vertexConsumers, light, overlay, g, lidModel, true);
            this.render(entity, matrixStack, vertexConsumers, light, overlay, g, baseModel, false);
        }

    }

    //TODO: this uses custom item models. adding is not trivial.
    private BakedModel getCustomModel(String base) {
        return null;
    }


    private void render(
            SarcophagusBlockEntity entity,
            PoseStack matrixStack,
            MultiBufferSource vertexConsumer,
            int light,
            int overlay,
            float g,
            BakedModel model,
            boolean isLid
    ) {
        matrixStack.pushPose();
        Direction direction = (entity.getBlockState().getValue(SarcophagusBlock.FACING)).getOpposite();
        float f = direction.toYRot();
        matrixStack.mulPose(Axis.YP.rotationDegrees(-f));
        switch (direction) {
            case EAST -> matrixStack.translate(-1.0, 0.0, 1.0);
            case SOUTH -> matrixStack.translate(0.0, 0.0, 1.0);
            case NORTH -> matrixStack.translate(-1.0, 0.0, 0.0);
        }

        if (isLid) {
            matrixStack.translate((double) g * 0.3, (double) g * 0.3, 0.0);
            matrixStack.mulPose(Axis.ZN.rotationDegrees(g * 45.0F));
        }

        Minecraft.getInstance()
                .getBlockRenderer()
                .getModelRenderer()
                .renderModel(
                        matrixStack.last(),
                        vertexConsumer.getBuffer(ItemBlockRenderTypes.getRenderType(entity.getBlockState(), true)),
                        entity.getBlockState(),
                        model,
                        1.0F,
                        1.0F,
                        1.0F,
                        light,
                        overlay
                );
        matrixStack.popPose();
    }


}