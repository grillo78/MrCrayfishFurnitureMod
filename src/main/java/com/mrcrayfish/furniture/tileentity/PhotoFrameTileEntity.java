package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.core.ModTileEntities;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

/**
 * @author Grillo78
 */
public class PhotoFrameTileEntity extends TileEntity {
    private String url = "https://media.discordapp.net/attachments/710520903758381106/813809663489474590/731012820572831755.png";

    public PhotoFrameTileEntity() {
        super(ModTileEntities.PHOTO_FRAME);
    }

    public void setUrl(String url) {
        this.url = url;
        TileEntityUtil.sendUpdatePacket(this);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        if (compound.contains("URL", Constants.NBT.TAG_STRING)) {
            this.url = compound.getString("URL");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("URL", this.url);
        return super.write(compound);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(this.getBlockState(), pkt.getNbtCompound());
    }
}
