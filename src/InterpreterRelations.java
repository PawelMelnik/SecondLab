import java.io.IOException;
import java.util.Map;

public class InterpreterRelations {
    protected static final String COLON = ":";
    public static void Interpreted(String config, Map<String, String> resultMap) throws IOException
    {
        MyReader reader = new MyReader(config);
        String ParamString;

        while ((ParamString = reader.ReadLine()) != null)
        {
            if ((ParamString = ParamString.replaceAll("\\s", "")).isEmpty())
            {
                continue;
            }
            String[] ParamPair = ParamString.split(COLON);
            if (!IsPairCorrect(ParamPair))
            {
                throw new IOException();
            }
            resultMap.put(ParamPair[0], ParamPair[1]);
        }
        reader.CloseStream();
    }

    public static boolean IsPairCorrect(String[] ParamPair)
    {
        if (ParamPair.length != 2|| ParamPair[0].isEmpty() || ParamPair[1].isEmpty())
        {
            Log.report("Invalid syntax in config file");
            return false;
        }
        return true;
    }
}
