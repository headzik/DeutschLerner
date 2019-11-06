package fh.ooe.mcm.germanlehrner

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import kotlinx.android.synthetic.main.activity_statistics.*
import java.util.ArrayList
import java.text.DecimalFormat
import com.github.mikephil.charting.utils.ViewPortHandler


class DataGraph(total: Float): IValueFormatter {
    private var percentageFormat: DecimalFormat = DecimalFormat("###,###,##0.0")
    private val total = total

    override fun getFormattedValue(
        value: Float,
        entry: Entry?,
        dataSetIndex: Int,
        viewPortHandler: ViewPortHandler?
    ): String {
        value
        return "${DecimalFormat("###,###,###,##0").format(value)} (${percentageFormat.format(value/total*100)}%)";
    }

}

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val scores = intent.getSerializableExtra("scores") as Map<Float, String>

        pieChart.data = getPieData(scores)

        pieChart.data.setValueFormatter(DataGraph(pieChart.data.yValueSum))
        pieChart.data.setValueTextColor(Color.WHITE)
        pieChart.data.setValueTypeface(Typeface.SANS_SERIF)
        pieChart.data.setValueTextSize(15f)

        pieChart.centerText = "Score"
        pieChart.setCenterTextSize(40f)
        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.animateY(1000)

        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTypeface(Typeface.SANS_SERIF)
        pieChart.setEntryLabelTextSize(15f)

        pieChart.invalidate();
        pieChart.legend.isEnabled = false
    }


    private fun getPieData(dataMap: Map<Float, String>): PieData {
        val pieEntries = ArrayList<PieEntry>()
        for ((key, value) in dataMap) {
            pieEntries.add(PieEntry(key, value))

        }
        val pieDataSet = PieDataSet(pieEntries, "Score")
        var colorList = mutableListOf(
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorPrimaryDark)
        )
        pieDataSet.colors = colorList

        pieDataSet.sliceSpace = 3f
        pieDataSet.selectionShift = 3f

        return PieData(pieDataSet)

    }
}

