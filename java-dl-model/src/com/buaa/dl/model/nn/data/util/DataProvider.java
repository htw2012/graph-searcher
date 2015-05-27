
package com.buaa.dl.model.nn.data.util;

import org.jblas.DoubleMatrix;
public interface DataProvider {
    DoubleMatrix getInput();
    DoubleMatrix getTarget();
}
