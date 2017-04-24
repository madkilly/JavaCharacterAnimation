package com.zhu.tools.animater.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: TiledGrayscaleMatrix</p>
 * <p>Description: </p>
 * @author zhukai
 */
public class TiledGrayscaleMatrix {	
	
	/** The tiles. */
	private final List<GrayscaleMatrix> tiles;
	
	/** Width of a tile. */
	private final int tileWidth;

	/** Height of a tile. */
	private final int tileHeight;

	/** Number of tiles on x axis. */
	private final int tilesX;

	/** Number of tiles on y axis. */
	private final int tilesY;
	
	public TiledGrayscaleMatrix(final GrayscaleMatrix matrix,
			final int tileWidth, final int tileHeight) {

		if (matrix.getWidth() < tileWidth || matrix.getHeight() < tileHeight) {
			throw new IllegalArgumentException(
					"Tile size must be smaller than original matrix!");
		}

		if (tileWidth <= 0 || tileHeight <= 0) {
			throw new IllegalArgumentException("Illegal tile size!");
		}

		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;

		// we won't allow partial tiles
		this.tilesX = matrix.getWidth() / tileWidth;
		this.tilesY = matrix.getHeight() / tileHeight;
		int roundedWidth = tilesX * tileWidth;
		int roundedHeight = tilesY * tileHeight;

		tiles = new ArrayList<GrayscaleMatrix>(roundedWidth * roundedHeight);

		// create each tile as a subregion from source matrix
		for (int i = 0; i < tilesY; i++) {
			for (int j = 0; j < tilesX; j++) {
				tiles.add(GrayscaleMatrix.createFromRegion(matrix, tileWidth,
						tileHeight, this.tileWidth * j, this.tileHeight * i));
			}
		}
	}
	
	public GrayscaleMatrix getTile(final int index) {
		return this.tiles.get(index);
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTilesX() {
		return tilesX;
	}

	public int getTilesY() {
		return tilesY;
	}
	
	public int getTileCount() {
		return this.tiles.size();
	}
	

}
