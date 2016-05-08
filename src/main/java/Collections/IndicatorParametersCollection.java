package Collections;

import Parameters.IndicatorParameters;

import java.util.ArrayList;

/**
 * Created by mike on 16.04.16.
 */

public class IndicatorParametersCollection {

    private static IndicatorParametersCollection collection;

    private static ArrayList<IndicatorParameters> indicators;

    private IndicatorParametersCollection()
    {
        initializeIndicators();
    }

    public static ArrayList<IndicatorParameters> getIndicators()
    {
        if(collection == null) collection = new IndicatorParametersCollection();
        return indicators;
    }

    //Really long function
    private void initializeIndicators()
    {
        indicators = new ArrayList<IndicatorParameters>();

        IndicatorParameters IEMA = new IndicatorParameters("IEMA");
        IEMA.addParameter("period", "integer");
        indicators.add(IEMA);

        IndicatorParameters ISMA = new IndicatorParameters("ISMA");
        ISMA.addParameter("period", "integer");
        indicators.add(ISMA);

        IndicatorParameters ISMMA = new IndicatorParameters("ISMMA");
        ISMMA.addParameter("period", "integer");
        indicators.add(ISMMA);

        IndicatorParameters IWMA = new IndicatorParameters("IWMA");
        IWMA.addParameter("period", "integer");
        indicators.add(IWMA);

        IndicatorParameters MACD = new IndicatorParameters("MACD");
        MACD.addParameter("longLength", "integer");
        MACD.addParameter("shortLength", "integer");
        MACD.addParameter("signalLength", "integer");
        indicators.add(MACD);

        IndicatorParameters ATR = new IndicatorParameters("ATR");
        ATR.addParameter("period", "integer");
        ATR.addParameter("ATRperiod", "integer");
        indicators.add(ATR);

        IndicatorParameters EMACrossover = new IndicatorParameters("EMACrossover");
        EMACrossover.addParameter("short_period", "integer");
        EMACrossover.addParameter("long_period", "integer");
        indicators.add(EMACrossover);

        IndicatorParameters SMACrossover = new IndicatorParameters("SMACrossover");
        SMACrossover.addParameter("short_period", "integer");
        SMACrossover.addParameter("long_period", "integer");
        indicators.add(SMACrossover);

        IndicatorParameters WMACrossover = new IndicatorParameters("WMACrossover");
        WMACrossover.addParameter("short_period", "integer");
        WMACrossover.addParameter("long_period", "integer");
        indicators.add(WMACrossover);

        IndicatorParameters SMMACrossover = new IndicatorParameters("SMMACrossover");
        SMMACrossover.addParameter("short_period", "integer");
        SMMACrossover.addParameter("long_period", "integer");
        indicators.add(SMMACrossover);

        //TODO: add remaining indicators with their parameters
    }





}
