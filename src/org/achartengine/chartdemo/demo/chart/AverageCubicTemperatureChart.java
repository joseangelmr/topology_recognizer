/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.achartengine.chartdemo.demo.chart;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

//import com.isis.wifimanager.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Average temperature demo chart.
 */
public class AverageCubicTemperatureChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Average temperature";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The average temperature in 4 Greek islands (cubic line chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  
//   MainActivity main;
  
   
  public Intent execute(Context context) {
	  
//	  MainActivity main = new MainActivity();
	  
	 
	  
    String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos", "Algo" };
    List<double[]> x = new ArrayList<double[]>();
    for (int i = 0; i < titles.length; i++) {
      x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
//      x.add(new double []{main.count});
    }
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { 0.3, 0.5, 0.8, 0.8, 0.4, 0.4, 0.4, 0.1, 0.6, 0.3, 0.2,
        0.9 });
    values.add(new double[] { 1, 1, 0.2, 0.5, 0.1, 0.4, 0.6, 0.6, 0.9, 1, 0.4, 1 });
    values.add(new double[] { 0.5, 0.3, 0.8, 1, 0.7, 0.2, 0.2, 0.4, 0.9, 0.5, 0.9, 0.6 });
    values.add(new double[] { 0.7, 0.4, 1, 0.5, 0.9, 0.3, 0.6, 0.5, 0.2, 0.8, 1, 0.9 });
    values.add(new double[] { 0.9, 1, 0.1, 0.5, 0.6, 0.3, 0.2, 0.5, 0.2, 0.8, 0.73, 0.20 });
    int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.WHITE };
//    PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
//        PointStyle.TRIANGLE, PointStyle.SQUARE, PointStyle.TRIANGLE};
    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT,
            PointStyle.POINT, PointStyle.POINT, PointStyle.POINT};
//    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    int length = renderer.getSeriesRendererCount();
    for (int i = 0; i < length; i++) {
      ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
    }
    setChartSettings(renderer, "Average Scans", "Numeros de Scans", "Porcentage", 0.5, 12.5, 0, 32,
        Color.LTGRAY, Color.LTGRAY);
    renderer.setXLabels(12);
    renderer.setYLabels(10);
//    renderer.setXLabels(10);
//    renderer.setYLabels(1);
    renderer.setShowGrid(true);
    renderer.setXLabelsAlign(Align.RIGHT);
    renderer.setYLabelsAlign(Align.RIGHT);
    renderer.setZoomButtonsVisible(false);
    renderer.setPanLimits(new double[] { 0, 40, 0, 1.2 });
    renderer.setZoomLimits(new double[] { -2, 20, 0, 30 });
    renderer.setYAxisMin(0);
    renderer.setYAxisMax(1.1);
    Intent intent = ChartFactory.getCubicLineChartIntent(context, buildDataset(titles, x, values),
        renderer, 0.33f, "Average temperature");
    return intent;
  }

}
