package com.zhu.tools.animater.matrix;

import com.zhu.tools.animater.utils.ArrayUtils;

/**
 * <p>Title: GrayscaleMatrix</p>
 * <p>Description: 
 * 	将图片转换成灰度矩阵类
 * </p>
 * @author zhukai
 */
public class GrayscaleMatrix {
	
	private final float data[];

	private final int width;

	private final int height;
	
	
	public GrayscaleMatrix(final int width, final int height) {
		this.data = new float[width * height];
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 */
	public GrayscaleMatrix(final int[] pixels, final int width, final int height) {
		this.data = new float[width * height];
		this.width = width;
		this.height = height;
		
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = convertRGBToGrayscale(pixels[i]);
		}
		
	}
	
	private float convertRGBToGrayscale(final int rgbColor) {
		// extract components
		int red = (rgbColor >> 16) & 0xFF;
		int green = (rgbColor >> 8) & 0xFF;
		int blue = rgbColor & 0xFF;

		// convert to grayscale
		return 0.3f * red + 0.59f * green + 0.11f * blue;
	}
	
	public static GrayscaleMatrix createFromRegion(
			final GrayscaleMatrix source, final int width, final int height,
			final int startPixelX, final int startPixelY) {
		if (width <= 0 || height <= 0 || width > source.width
				|| height > source.height) {
			throw new IllegalArgumentException("Illegal sub region size!");
		}

		GrayscaleMatrix output = new GrayscaleMatrix(width, height);

		for (int i = 0; i < output.data.length; i++) {
			int xOffset = i % width;
			int yOffset = i / width;

			int index = ArrayUtils.convert2DTo1D(startPixelX + xOffset,
					startPixelY + yOffset, source.width);
			output.data[i] = source.data[index];
		}

		return output;
	}

	public float[] getData() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
}
