import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class InterpreterWorkersConfigs  {
    public enum Params {CODE_MODE}
    protected static final String COLON = ":";

    public static final Map<String, Enum> worc_conf_lexemeMap;

    static {
        worc_conf_lexemeMap = new HashMap<>();
        worc_conf_lexemeMap.put("CODE_MODE", Params.CODE_MODE);
    }

    public static void Interpreted(  Map<String, WorkerProperties> workersMap, Map<String, Map<Enum, String>> resultMap) throws IOException
    {
        for (String key : workersMap.keySet())
        {
            Map<Enum, String> paramMap = new EnumMap(Params.class);
            InterpretWorker(workersMap.get(key).config_name, paramMap);
            resultMap.put(key, paramMap);
        }
    }

    public static void InterpretWorker(String paramFile, Map<Enum, String> resultMap) throws IOException
    {
        MyReader reader = new MyReader(paramFile);
        String Paramstring;

        while ((Paramstring = reader.ReadLine()) != null)
        {
            if ((Paramstring = Paramstring.replaceAll("\\s", "")).isEmpty())
            {
                continue;
            }

            String[] ParamPair = Paramstring.split(COLON);

            if (!IsPairCorrect(ParamPair, worc_conf_lexemeMap))
            {
                throw new IOException();
            }

            resultMap.put(worc_conf_lexemeMap.get(ParamPair[0]), ParamPair[1]);
        }

        if (resultMap.putIfAbsent(Params.CODE_MODE, "0") == null)
        {
            Log.report("Missing CODE_MODE, using default: 0");
        }

        reader.CloseStream();
    }

    public static boolean IsPairCorrect(String[] ParamPair, Map<String, Enum> lexemeMap)
    {
        if (ParamPair.length != 2|| ParamPair[0].isEmpty() || ParamPair[1].isEmpty())
        {
            Log.report("Invalid syntax in config file");
            return false;
        }
        if (!isLexeme(ParamPair[0]))
        {
            Log.report("Unknown lexeme " + ParamPair[0]);
            return false;
        }
        return true;
    }

    public static boolean isLexeme(String lexeme)
    {
        for (String key : worc_conf_lexemeMap.keySet())
        {
            if (lexeme.equals(key))
            {
                return true;
            }
        }
        return false;
    }
}