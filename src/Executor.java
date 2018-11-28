
public interface Executor {
    void SetInput (String input);
    void SetOutput (String output);

    void SetConsumer(Executor ex);
    void Run();
    void Put(Object obj);
}
