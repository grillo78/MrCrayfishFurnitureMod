package com.mrcrayfish.furniture.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrcrayfish.furniture.block.DoorMatBlock;
import com.mrcrayfish.furniture.block.PhotoFrameBlock;
import com.mrcrayfish.furniture.tileentity.PhotoFrameTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @author Grillo78
 */
public class PhotoFrameTileEntityRenderer extends TileEntityRenderer<PhotoFrameTileEntity> {
    private HashMap<String, DynamicTexture> textures = new HashMap<>();
    private HashMap<String, ResourceLocation> textureLocations = new HashMap<>();
    private HashMap<String, NativeImage> textureImages = new HashMap<>();

    public PhotoFrameTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PhotoFrameTileEntity tileEntity, float v, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntity.getUrl() != "") {
            BlockState state = tileEntity.getBlockState();
            if (state.getBlock() instanceof PhotoFrameBlock) {

                if (!textures.containsKey(tileEntity.getUrl())) {
                    if (!textureImages.containsKey(tileEntity.getUrl())) {
                        Thread t1 = new Thread(() -> downloadTexture(tileEntity.getUrl()));
                        t1.start();
                    } else {
                        getTexture(tileEntity.getUrl());
                    }
                }

                matrixStack.push(); //Push
                if (textures.containsKey(tileEntity.getUrl())) {
                    DynamicTexture texture = textures.get(tileEntity.getUrl());
                    texture.bindTexture();
                    IVertexBuilder builder = renderTypeBuffer
                            .getBuffer(RenderType.getEntityTranslucent(textureLocations.get(tileEntity.getUrl())));
                    Matrix4f posMatrix = matrixStack.getLast().getMatrix();
                    Matrix3f normalMatrix = matrixStack.getLast().getNormal();
                    switch (state.get(DoorMatBlock.DIRECTION)) {
                        case WEST:
                            builder.pos(posMatrix, 0.035F, 0.937F, 0.063F).color(255, 255, 255, 255).tex(1, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            builder.pos(posMatrix, 0.035F, 0.063F, 0.063F).color(255, 255, 255, 255).tex(1, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            builder.pos(posMatrix, 0.035F, 0.063F, 0.937F).color(255, 255, 255, 255).tex(0, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            builder.pos(posMatrix, 0.035F, 0.937F, 0.937F).color(255, 255, 255, 255).tex(0, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            break;
                        case EAST:
                            builder.pos(posMatrix, 0.965F, 0.937F, 0.063F).color(255, 255, 255, 255).tex(0, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            builder.pos(posMatrix, 0.965F, 0.063F, 0.063F).color(255, 255, 255, 255).tex(0, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            builder.pos(posMatrix, 0.965F, 0.063F, 0.937F).color(255, 255, 255, 255).tex(1, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            builder.pos(posMatrix, 0.965F, 0.937F, 0.937F).color(255, 255, 255, 255).tex(1, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0.93F, 0, 0).endVertex();
                            break;
                        case NORTH:
                            builder.pos(posMatrix, 0.937F, 0.937F, 0.035F).color(255, 255, 255, 255).tex(1, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            builder.pos(posMatrix, 0.937F, 0.063F, 0.035F).color(255, 255, 255, 255).tex(1, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            builder.pos(posMatrix, 0.063F, 0.063F, 0.035F).color(255, 255, 255, 255).tex(0, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            builder.pos(posMatrix, 0.063F, 0.937F, 0.035F).color(255, 255, 255, 255).tex(0, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            break;
                        case SOUTH:
                            builder.pos(posMatrix, 0.937F, 0.937F, 0.965F).color(255, 255, 255, 255).tex(0, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            builder.pos(posMatrix, 0.937F, 0.063F, 0.965F).color(255, 255, 255, 255).tex(0, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            builder.pos(posMatrix, 0.063F, 0.063F, 0.965F).color(255, 255, 255, 255).tex(1, 1).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            builder.pos(posMatrix, 0.063F, 0.937F, 0.965F).color(255, 255, 255, 255).tex(1, 0).overlay(combinedOverlayIn)
                                    .lightmap(combinedLightIn).normal(normalMatrix, 0, 0, 1).endVertex();
                            break;
                    }
                }
                matrixStack.pop(); //Pop
            }
        }
    }

    private void downloadTexture(String urlS) {
        try {
            URL url = new URL(urlS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            NativeImage nativeImage = NativeImage.read(conn.getInputStream());
            textureImages.put(urlS, nativeImage);
        } catch (Exception e) {
            if (textureImages.containsKey(urlS)) {
                textureImages.remove(urlS);
            }
        }
    }

    public void getTexture(String urlS) {
        try {
            NativeImage nativeImage = textureImages.get(urlS);
            DynamicTexture texture = new DynamicTexture(nativeImage);
            textureLocations.put(urlS, new ResourceLocation("dynamic_texture_" + textureLocations.size()));
            Minecraft.getInstance().textureManager.loadTexture(textureLocations.get(urlS), texture);
            textures.put(urlS, texture);
        } catch (Exception e) {
            if (textures.containsKey(urlS)) {
                textures.remove(urlS);
            }
        }
    }
}
