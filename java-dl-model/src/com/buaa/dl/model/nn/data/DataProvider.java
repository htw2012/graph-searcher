
package com.buaa.dl.model.nn.data;

import org.jblas.DoubleMatrix;
public interface DataProvider {
    DoubleMatrix getInput();
    DoubleMatrix getTarget();
}
