
public interface Executor {
    void SetInput (String input);
    void SetOutput (String output);
    void SetCodeMode(int CODE_MODE);

    void SetConsumer(Executor ex);
    int Run();
    void Put(Object obj);
}
