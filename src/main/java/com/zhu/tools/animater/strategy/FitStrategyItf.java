package com.zhu.tools.animater.strategy;

import com.zhu.tools.animater.matrix.GrayscaleMatrix;

/**
 * <p>Title: FitStrategyItf</p>
 * <p>Description: </p>
 * @author zhukai
 */
public interface FitStrategyItf {
	
	public float similarCompare(GrayscaleMatrix character, GrayscaleMatrix tile);

}
