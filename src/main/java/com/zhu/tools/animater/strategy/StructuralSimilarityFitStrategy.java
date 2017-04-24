package com.zhu.tools.animater.strategy;

import com.zhu.tools.animater.matrix.GrayscaleMatrix;

/**
 * <p>Title: StructuralSimilarityFitStrategy</p>
 * <p>Description: </p>
 * @author zhukai
 */
public class StructuralSimilarityFitStrategy implements FitStrategyItf{

	private final float K1 = 0.01f;
	private final float K2 = 0.03f;
	private float L = 255f;

	
	/**
	 * Description:
	 * @see com.zhu.tools.animater.strategy.FitStrategyItf#similarCompare()
	 */
	public float similarCompare(GrayscaleMatrix character, GrayscaleMatrix tile) {


		float C1 = K1 * L;
		C1 *= C1;
		float C2 = K2 * L;
		C2 *= C2;

		final int imgLength = character.getData().length;

		float score = 0f;
		for (int i = 0; i < imgLength; i++) {
			float pixelImg1 = character.getData()[i];
			float pixelImg2 = tile.getData()[i];

			score += (2 * pixelImg1 * pixelImg2 + C1) * (2 + C2)
					/ (pixelImg1 * pixelImg1 + pixelImg2 * pixelImg2 + C1) / C2;
		}

		// average and convert score to error
		return 1 - (score / imgLength);
	
	}



	
}
