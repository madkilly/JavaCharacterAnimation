package com.zhu.tools.animater.CharCache;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.zhu.tools.animater.common.Constants;
import com.zhu.tools.animater.matrix.GrayscaleMatrix;

/**
 * <p>Title: CharImgCache</p>
 * <p>Description: </p>
 * @author zhukai
 */
public class CharImgCache implements Iterable<Entry<Character, GrayscaleMatrix>>{

	private final Map<Character, GrayscaleMatrix> imgCache;
	
	private static final char[] imgCharacters = Constants.DEFAULT_IMG_STRING.toCharArray();
	
	/** Dimension of character image data. */
	private final Dimension characterImageSize;
	
	private Dimension getCharacterDimension(Font font){
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		Dimension maxCharacter = new Dimension();
		for(int i=0;i<imgCharacters.length;i++){
			String character = Character.toString(imgCharacters[i]);		
			Rectangle rect = new TextLayout(character, fm.getFont(), fm.getFontRenderContext()).getOutline(null).getBounds();
			if(maxCharacter.getWidth()<rect.getWidth()){
				maxCharacter.width=(int)rect.getWidth();
			}
			if(maxCharacter.height<rect.getWidth()){
				maxCharacter.height=(int)rect.getWidth();
			}
			
		}
		return maxCharacter;
	}
	
	public Map<Character, GrayscaleMatrix> createCharacterImages(final Font font, final Dimension characterSize,final char[] characters){
		
		BufferedImage img = new BufferedImage(characterSize.width,characterSize.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		FontMetrics fm = g2d.getFontMetrics();
		Map<Character, GrayscaleMatrix> cache = new HashMap<Character, GrayscaleMatrix>();
		for(int i=0;i<characters.length;i++){
			String character = Character.toString(characters[i]);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, characterSize.width, characterSize.height);
			g.setColor(Color.BLACK);
			
			Rectangle rect = new TextLayout(character, fm.getFont(),fm.getFontRenderContext()).getOutline(null).getBounds();
			g.drawString(character, 0, (int) (rect.getHeight() - rect.getMaxY()));
			int [] pixels = img.getRGB(0, 0, characterSize.width, characterSize.height, null, 0, characterSize.width);
			
			GrayscaleMatrix gm = new GrayscaleMatrix(pixels, characterSize.width, characterSize.height);
			imgCache.put(characters[i], gm);
			
		}
		return imgCache;
	}
	
	public CharImgCache(Font font){
		imgCache = new HashMap<Character, GrayscaleMatrix>(32);
		characterImageSize=getCharacterDimension(font);
		for(int i=0;i<imgCharacters.length;i++){
			
		}
		
		
	}
	
	/**
	 * Description:
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Entry<Character, GrayscaleMatrix>> iterator() {
		
		return imgCache.entrySet().iterator();
	}

	public Map<Character, GrayscaleMatrix> getImgCache() {
		return imgCache;
	}

	public static char[] getImgcharacters() {
		return imgCharacters;
	}

	public Dimension getCharacterImageSize() {
		return characterImageSize;
	}

	
	
}
