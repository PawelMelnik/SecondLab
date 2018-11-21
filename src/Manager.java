import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class Manager {
    static Map<Enum, String> configMap;
    Manager(String config) throws IOException
    {
        configMap = new EnumMap(InterpreterConfig.Params.class);
        InterpreterConfig.Interpreted(config, configMap);
        Map<String, WorkerProperties> workersMap = new HashMap<>();
        Map<String, String> workers_relationsMap = new HashMap<>();
        InterpreterWorkers.Interpreted(configMap.get(InterpreterConfig.Params.WORKERS_FILE), workersMap);
        InterpreterRelations.Interpreted(configMap.get(InterpreterConfig.Params.RELATIONS_FILE), workers_relationsMap);
    }
}
