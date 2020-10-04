package com.travall.game.blocks;

import com.travall.game.blocks.materials.Material;
import com.travall.game.renderer.block.BlockTextures;
import com.travall.game.renderer.block.UltimateTexture;

public class Grass extends Block {
	public static short id = 4;

	public Grass(UltimateTexture ultimate) {
		this.textures = new BlockTextures(ultimate.createRegion(4, 0), ultimate.createRegion(3, 0), ultimate.createRegion(2, 0));
		this.material = Material.BLOCK;
	}
}
