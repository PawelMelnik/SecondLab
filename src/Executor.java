import java.io.FileInputStream;
import java.io.FileOutputStream;

interface Executor {
    void SetInput (FileInputStream input);
    void SetOutput (FileOutputStream output);

    void SetConsumer(Executor ex);
    void Run();
    void Put(Object obj);
}
