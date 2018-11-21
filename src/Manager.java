import java.util.EnumMap;
import java.util.Map;

public class Manager {
    static Map<Enum, String> configMap;
    Manager(String config)
    {
        configMap = new EnumMap(InterpreterConfig.Params.class);
    }
}
