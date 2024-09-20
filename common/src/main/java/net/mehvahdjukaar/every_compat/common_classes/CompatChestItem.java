package net.mehvahdjukaar.every_compat.common_classes;


import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.moonlight.api.client.ICustomItemRendererProvider;
import net.mehvahdjukaar.moonlight.api.client.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CompatChestItem extends BlockItem implements ICustomItemRendererProvider {

    public CompatChestItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Supplier<ItemStackRenderer> getRendererFactory() {
        return () -> ClientProxy.getItemStackRenderer(this);
    }

    private static class ClientProxy {
        public static @NotNull ItemStackRenderer getItemStackRenderer(CompatChestItem compatChestItem) {
            ChestBlock block = (ChestBlock) compatChestItem.getBlock();
            return new ItemStackRenderer() {

                @Override
                public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType,
                                         PoseStack poseStack, MultiBufferSource BufferSource, int i, int i1) {
                    renderer.renderItem(dummy, poseStack, BufferSource, i, i1);
                }

                final BlockEntityRenderDispatcher renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher();
                final CompatChestBlockEntity dummy = (CompatChestBlockEntity) block
                        .newBlockEntity(BlockPos.ZERO, block.defaultBlockState());

            };
        }
    }


}