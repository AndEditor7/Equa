package com.travall.game.blocks;

import com.travall.game.blocks.materials.Material;
import com.travall.game.blocks.models.CubeModel;
import com.travall.game.renderer.block.BlockTextures;
import com.travall.game.renderer.block.UltimateTexture;
import com.travall.game.ui.actors.BlockSeletion;

public class Bedrock extends Block {
	
	public Bedrock(int blockID) {
		super(blockID);
		this.model = new CubeModel(this, new BlockTextures(UltimateTexture.createRegion(0, 0)));
		this.material = Material.BLOCK;
		BlockSeletion.add(this);
	}
}
