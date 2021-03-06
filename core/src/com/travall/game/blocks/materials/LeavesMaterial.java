package com.travall.game.blocks.materials;

public class LeavesMaterial extends Material {
	@Override
	public boolean isFullCube() {
		return true;
	}
	
	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public boolean hasCollision() {
		return true;
	}

	@Override
	public boolean canBlockSunRay() {
		return true;
	}

	@Override
	public boolean canBlockLights() {
		return false;
	}
}
