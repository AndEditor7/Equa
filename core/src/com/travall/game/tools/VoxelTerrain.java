package com.travall.game.tools;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE;
import static com.badlogic.gdx.graphics.glutils.ShaderProgram.TEXCOORD_ATTRIBUTE;

import java.nio.ByteBuffer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.BufferUtils;
import com.travall.game.glutils.QuadIndexBuffer;
import com.travall.game.glutils.VertContext;

// Needs update comments after attribute change.
/** The static class contains vertex attributes and shader */
public final class VoxelTerrain {
	// Data[sideLight&Ambiant, source-light, sunlight, unused]
	/** 3 Position, 4 Data (Packed into 1 float) and 2 TextureCoordinates [x,y,z,d,u,v] */
	public static final VertexAttributes attributes = new VertexAttributes(
			 	new VertexAttribute(Usage.Position, 3, POSITION_ATTRIBUTE),
				new VertexAttribute(Usage.ColorPacked, 4, "a_data"),
				new VertexAttribute(Usage.TextureCoordinates, 2, TEXCOORD_ATTRIBUTE)
			);
	
	/** 24 bytes in a single vertex with 6 float components. */ 
	public static final int byteSize = attributes.vertexSize;
	
	/** 6 floats in a single vertex. */ 
	public static final int floatSize = byteSize/Float.BYTES;
	
	public static ShaderProgram shaderProgram;
	public static int[] locations;
	
	public static ByteBuffer BUFFER;
	
	public static void ints() {
		shaderProgram = new ShaderProgram(files.internal("Shaders/voxel.vert"), files.internal("Shaders/voxel.frag"));
		locations = Utils.locateAttributes(shaderProgram, attributes);
		
		// 1,572,864 bytes of data, or 1.57MB.
		BUFFER = BufferUtils.newUnsafeByteBuffer(QuadIndexBuffer.maxVertex*byteSize);
		
		QuadIndexBuffer.ints();
	}
	
	/** Begins the shader. */
	public static void begin(Camera cam) {
		shaderProgram.begin();
		shaderProgram.setUniformMatrix("u_projTrans", cam.combined);
		shaderProgram.setUniformf("sunLightIntensity", 0f); // 1f for full sun light.
	}
	
	/** End the shader. */
	public static void end() {
		shaderProgram.end();
	}
	
	public static void dispose() {
		shaderProgram.dispose();
		BufferUtils.disposeUnsafeByteBuffer(BUFFER);
		QuadIndexBuffer.dispose();
	}	
	
	public final static VertContext context = new VertContext() {
		public VertexAttributes getAttrs() {
			return attributes;
		}
		public ShaderProgram getShader() {
			return shaderProgram;
		}
		public int getLocation(int i) {
			return locations[i];
		}
	};
}