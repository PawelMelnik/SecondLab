import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InterpreterConfig {
    public enum Params {INPUT_FILE, OUTPUT_FILE, WORKERS_FILE, RELATIONS_FILE}
    protected static final String COLON = ":";
    public static final Map<String, Enum> lexemeMap;

    static
    {
        lexemeMap = new HashMap<>();
        lexemeMap.put("IN", Params.INPUT_FILE);
        lexemeMap.put("OUT", Params.OUTPUT_FILE);
        lexemeMap.put("WORKERS", Params.WORKERS_FILE);
        lexemeMap.put("RELATIONS", Params.RELATIONS_FILE);
    }

    public static void Interpreted(String config, Map<Enum, String> resultMap) throws IOException
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
            if (!IsPairCorrect(ParamPair, lexemeMap))
            {
                throw new IOException();
            }
            resultMap.put(lexemeMap.get(ParamPair[0]), ParamPair[1]);
        }

        if (!resultMap.containsKey(Params.INPUT_FILE))
        {
            Log.report("Input file is not found");
            throw new FileNotFoundException();
        }

        if (!resultMap.containsKey(Params.WORKERS_FILE))
        {
            Log.report("Workers file is not found");
            throw new FileNotFoundException();
        }

        if (!resultMap.containsKey(Params.RELATIONS_FILE))
        {
            Log.report("Relations file is not found");
            throw new FileNotFoundException();
        }

        if (resultMap.putIfAbsent(Params.OUTPUT_FILE, "output_default.txt") == null)
        {
            Log.report("Output file is not found, using output_default.txt file");
        }

        reader.CloseStream();
    }

    public static boolean isLexeme(String lexeme, Map<String, Enum> map)
    {
        for (String key : map.keySet())
        {
            if (lexeme.equals(key))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean IsPairCorrect(String[] ParamPair, Map<String, Enum> lexemeMap)
    {
        if (ParamPair.length != 2|| ParamPair[0].isEmpty() || ParamPair[1].isEmpty())
        {
            Log.report("Invalid syntax in config file");
            return false;
        }
        if (!isLexeme(ParamPair[0], lexemeMap))
        {
            Log.report("Unknown lexeme " + ParamPair[0]);
            return false;
        }
        return true;
    }

}
