package com.travall.game.utils;

import java.nio.IntBuffer;

import com.badlogic.gdx.utils.BufferUtils;
import com.travall.game.utils.math.Size;

public final class Utils {
	private Utils () {}
	
	/** A cached screen size. */
	public static final Size screen = new Size();
	
	/** A temporally int buffer. */ 
	public static final IntBuffer intbuf = BufferUtils.newIntBuffer(1);

	public static double normalize(double Input, int max) {
		return ((Input - -1) / (1 - -1) * (max - 0) + 0);
	}
	
	public static float normalize(float Input, int max) {
		return ((Input - -1) / (1 - -1) * (max - 0) + 0);
	}

	public static float gamma(double num) {
		return (float)Math.pow(num, 2.2);
	}

	public static int createANDbits(final int bitSize) {
		return -1 >>> 32 - bitSize;
	}

	public static boolean inBounds(int index, int length) {
		return (index >= 0) && (index < length);
	}
}
