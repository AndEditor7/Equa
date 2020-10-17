package com.travall.game.blocks.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.travall.game.blocks.Block;
import com.travall.game.renderer.block.BlockTextures;
import com.travall.game.renderer.quad.QuadBuilder;
import com.travall.game.renderer.quad.QuadNode;
import com.travall.game.utils.BlockPos;
import com.travall.game.utils.Facing;

public class TorchModel implements IBlockModel {

	private final TextureRegion texture;

	private final QuadNode quad1, quad2, quad3, quad4, quad5, quad6;

	public TorchModel(Block block, BlockTextures textures) {
		this.texture = textures.north;

		quad1 = new QuadNode();
		quad1.v1.setPos(1, 1, 0);
		quad1.v2.setPos(0, 1, 0);
		quad1.v3.setPos(0, 1, 1);
		quad1.v4.setPos(1, 1, 1);
		quad1.face = Facing.UP;
		quad1.simpleLight = true;
		quad1.region.setRegion(textures.top);

		quad2 = new QuadNode();
		quad2.v1.setPos(0, 0, 0);
		quad2.v2.setPos(1, 0, 0);
		quad2.v3.setPos(1, 0, 1);
		quad2.v4.setPos(0, 0, 1);
		quad2.face = Facing.DOWN;
		quad2.simpleLight = true;
		quad2.region.setRegion(textures.bottom);

		quad3 = new QuadNode();
		quad3.v1.setPos(1, 0, 1);
		quad3.v2.setPos(1, 1, 1);
		quad3.v3.setPos(0, 1, 1);
		quad3.v4.setPos(0, 0, 1);
		quad3.face = Facing.NORTH;
		quad3.simpleLight = true;
		quad3.region.setRegion(textures.south);

		quad4 = new QuadNode();
		quad4.v1.setPos(1, 0, 0);
		quad4.v2.setPos(1, 1, 0);
		quad4.v3.setPos(1, 1, 1);
		quad4.v4.setPos(1, 0, 1);
		quad4.face = Facing.EAST;
		quad4.simpleLight = true;
		quad4.region.setRegion(textures.south);

		quad5 = new QuadNode();
		quad5.v1.setPos(0, 0, 0);
		quad5.v2.setPos(0, 1, 0);
		quad5.v3.setPos(1, 1, 0);
		quad5.v4.setPos(1, 0, 0);
		quad5.face = Facing.SOUTH;
		quad5.simpleLight = true;
		quad5.region.setRegion(textures.south);

		quad6 = new QuadNode();
		quad6.v1.setPos(0, 0, 1);
		quad6.v2.setPos(0, 1, 1);
		quad6.v3.setPos(0, 1, 0);
		quad6.v4.setPos(0, 0, 0);
		quad6.face = Facing.WEST;
		quad6.simpleLight = true;
		quad6.region.setRegion(textures.west);
		

		quad1.mul(1,10/16f,1);
		quad3.mul(1,1,9/16f);
		quad4.mul(9/16f,1,1);
		quad5.add(0,0,7/16f);
		quad6.add(7/16f,0,0);
	}

	@Override
	public void build(QuadBuilder builder, BlockPos position) {
		quad1.rect(builder, position);
		quad2.rect(builder, position);
		quad3.rect(builder, position);
		quad4.rect(builder, position);
		quad5.rect(builder, position);
		quad6.rect(builder, position);
	}

	@Override
	public TextureRegion getDefaultTexture() {
		return texture;
	}
}