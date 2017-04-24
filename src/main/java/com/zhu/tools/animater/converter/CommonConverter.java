package com.zhu.tools.animater.converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import com.zhu.tools.animater.CharCache.CharImgCache;
import com.zhu.tools.animater.matrix.GrayscaleMatrix;
import com.zhu.tools.animater.matrix.TiledGrayscaleMatrix;
import com.zhu.tools.animater.strategy.FitStrategyItf;
import com.zhu.tools.animater.utils.ArrayUtils;

/**
 * <p>Title: CommonConverter</p>
 * <p>Description: </p>
 * @author zhukai
 */
public class CommonConverter {
	
	private CharImgCache cache;
	private FitStrategyItf stagey;
	private BufferedImage img;
	
	public CommonConverter(CharImgCache cache,FitStrategyItf stagey){
		this.cache=cache;
		this.stagey=stagey;
	}
	
	public BufferedImage convert(BufferedImage source){
		Dimension tileSize = this.cache.getCharacterImageSize();
		
		int outputImageWidth = (source.getWidth() / tileSize.width)	* tileSize.width;
		int outputImageHeight = (source.getHeight() / tileSize.height)* tileSize.height;

		int [] imgPixels = source.getRGB(0, 0, outputImageWidth, outputImageHeight, null, 0, outputImageWidth);
		
		GrayscaleMatrix sgm = new GrayscaleMatrix(imgPixels,outputImageWidth, outputImageHeight);
		TiledGrayscaleMatrix tgm = new TiledGrayscaleMatrix(sgm, tileSize.width, tileSize.height);
		img=initializeOutput(outputImageWidth,outputImageHeight);
		
		for(int i=0;i<tgm.getTileCount();i++){
			GrayscaleMatrix tmp = tgm.getTile(i);
			
			float minError = Float.MAX_VALUE;
			Entry<Character, GrayscaleMatrix> bestFit = null;
			
			for (Entry<Character, GrayscaleMatrix> charImage : cache){
				float tmpError=stagey.similarCompare(charImage.getValue(), tmp);
				if(minError>tmpError){
					minError=tmpError;
					bestFit = charImage;
				}
			} 
			
			int tileX = ArrayUtils.convert1DtoX(i, tgm.getTilesX());
			int tileY = ArrayUtils.convert1DtoY(i, tgm.getTilesX());
			
			addCharacterToOutput(bestFit, imgPixels, tileX, tileY,outputImageWidth);
			finalizeOutput(imgPixels, outputImageWidth, outputImageHeight);

			
		}
		return img;
	}
	
	protected BufferedImage initializeOutput(final int imageWidth, final int imageHeight) {
		return new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_ARGB);
	}
	
	public void addCharacterToOutput(final Entry<Character, GrayscaleMatrix> characterEntry,final int[] sourceImagePixels, final int tileX, final int tileY, final int imageWidth) {
		int startCoordinateX = tileX
				* this.cache.getCharacterImageSize().width;
		int startCoordinateY = tileY
				* this.cache.getCharacterImageSize().height;

		// copy winner character
		for (int i = 0; i < characterEntry.getValue().getData().length; i++) {
			int xOffset = i % this.cache.getCharacterImageSize().width;
			int yOffset = i / this.cache.getCharacterImageSize().width;

			int component = (int) characterEntry.getValue().getData()[i];
			sourceImagePixels[ArrayUtils.convert2DTo1D(startCoordinateX
					+ xOffset, startCoordinateY + yOffset, imageWidth)] = new Color(
					component, component, component).getRGB();
		}

	}
	
	protected void finalizeOutput(final int[] sourceImagePixels, final int imageWidth,
			final int imageHeight) {
		this.img.setRGB(0, 0, imageWidth, imageHeight, sourceImagePixels, 0,
				imageWidth);

	}

}
