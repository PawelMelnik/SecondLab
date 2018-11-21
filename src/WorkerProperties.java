public class WorkerProperties {
    public String config_name;
    public InterpreterWorkers.WorkersTypes type_of_worker;

    WorkerProperties(String filename, InterpreterWorkers.WorkersTypes type)
    {
        this.config_name = filename;
        this.type_of_worker = type;
    }
}
