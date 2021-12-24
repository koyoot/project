import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import java.awt.*;
import javax.swing.JPanel;

public class ChartBuilder extends ApplicationFrame {

    public ChartBuilder(String[] labels, float[] values, 
        String xAxisLabel, String yAxisLabel) {
        super("Гистограмма распределения экономики по странам за 2015 год");
        setContentPane(CreatePanel(BuildChart(labels, values, xAxisLabel, yAxisLabel)));
    }

    public JFreeChart BuildChart(String[] labels, float[] values,
                                 String xAxisLabel, String yAxisLabel) throws RuntimeException{
        if (labels.length != values.length)
            throw new RuntimeException("Произошла ошибка. Количество столбцов не соответствует количеству значений!");
        var dataset = new DefaultCategoryDataset();
        for (var i = 0; i < labels.length; i++)
            dataset.addValue((float) Math.round(values[i] * 100000) / 100000, labels[i], xAxisLabel);
        var chart = ChartFactory.createBarChart("Распределение экономики стран за 2015 год",
                                            "Распределение стран по цветам на графике", yAxisLabel, dataset);
        var plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        return chart;
    }

    public JPanel CreatePanel(JFreeChart chart) {
        chart.setPadding(new RectangleInsets(1, 5, 5, 10));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setMouseWheelEnabled(false);
        chartPanel.setPreferredSize(new Dimension(1920, 1080));
        return chartPanel;
    }
}
