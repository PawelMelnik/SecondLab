import java.io.*;

public class RLECoder implements Executor {

    private String input = null;
    private String output = null;
    private Executor Consumer;
    public byte[] block = null;
    private int CODE_MODE;

    /*RLECoder(String input, String output, int CODE_MODE)
    {
        this.input = input;
        this.output = output;
        this.CODE_MODE = CODE_MODE;
    }*/

    public void SetInput (String input)
    {
        this.input = input;
    }
    public void SetOutput (String output)
    {
        this.output = output;
    }

    public void SetCodeMode(int CODE_MODE)
    {
        this.CODE_MODE = CODE_MODE;
    }

    public void SetConsumer(Executor ex)
    {
        this.Consumer = ex;
    }
    public int Run()
    {
        int err;
        if (this.CODE_MODE == 0)
        {
            err = RunEncode();
        }
        else
        {
            err = RunDecode();
        }

        if (err != 0)
        {
            Log.report("Can't run");//////////
            return -1;
        }
        return Consumer.Run();
    }
    public void Put(Object obj)
    {
        this.block = (byte [])obj;
    }

    private int RunEncode()
    {
        if (input != null && block != null)
        {
            Log.report("Incorrect first worker");
            return -1;
        }

        if (input != null)
        {
            if (ReadToArray() == -1)
            {
                return -1;
            }
        }
        else if (block == null)
        {
            return  -1;
        }

        int block_index = 0;
        int out_arr_index = 0;
        byte count = 1;
        byte i = block[block_index];
        int size = block.length;
        byte[] out_array = new byte[2 * size];

        while (i != -1 && block_index < size)
        {
            byte j = block[block_index + 1];

            while (j == i && block_index < size)
            {
                count++;
                block_index++;
                j = block[block_index];
            }

            out_array[out_arr_index] = count;
            out_arr_index++;
            out_array[out_arr_index] = i;
            out_arr_index++;
            i = j;
            count = 1;

        }

        if (out_arr_index < 2 * size)
        {
            out_array[out_arr_index] = -1;
        }

        if (output != null)
        {
            if (PrintArray(out_array) == -1)
            {
                return -1;
            }
        }
        else
        {
            if (Consumer == null)
            {
                return -1;
            }
            Consumer.Put(out_array);
        }
        return 0;
    }


    private int RunDecode()
    {
        if (input != null && block != null)
        {
            Log.report("Incorrect first worker");
            return -1;
        }

        if (input != null)
        {
            if (ReadToArray() == -1)
            {
                return -1;
            }
        }
        else if (block == null)
        {
            return  -1;
        }

        int block_index = 0;
        int out_arr_index = 0;
        byte count = 1;
        byte i = block[block_index];
        int size = GetNewArraySize();
        byte[] out_array = new byte[size];

        while(i != -1 && block_index < block.length)
        {
            byte j = block[block_index + 1];
            while( i > 0)
            {
                out_array[out_arr_index] = j;
                out_arr_index++;
                i--;
            }
            block_index += 2;
        }

        if (output != null)
        {
            if (PrintArray(out_array) == -1)
            {
                return -1;
            }
        }
        else
        {
            if (Consumer == null)
            {
                return -1;
            }
            Consumer.Put(out_array);
        }
        return 0;
    }

    private int ReadToArray()
    {
        byte[] array = new byte[4];
        try (FileInputStream readerInput = new FileInputStream(input))
        {

            byte i = 0;
            int k = 0;

            while (i != -1 && k < 4)
            {
                i = (byte)readerInput.read();
                array[k] = i;
                k++;
            }
        }
        catch (IOException e)
        {
            Log.report("Can't encode file");
            return -1;
        }
        block = array;
        return 0;
    }

    private int GetNewArraySize()
    {
        int i;
        int size = 0;
        for (i = 0; i < block.length && block[i] != -1; i += 2)
        {
            size += (int)block[i];
        }
        return size;
    }

    int PrintArray(byte[] out_arr)
    {
        try (FileOutputStream writerOutput = new FileOutputStream(output))
        {
            int i = 0;
            while( out_arr[i] != -1 && i < out_arr.length)
            {
                writerOutput.write((char)out_arr[i]);
                writerOutput.flush();
                i++;
            }
        }

        catch (IOException e)
        {
            Log.report("Can't write to output file");
            return -1;
        }
        return 0;
    }
    /*DataInputStream ChooseInput() throws IOException
    {
        DataInputStream inp;
        if(input == null)
            inp = new DataInputStream(new ByteArrayInputStream(this.block));
        else
            inp = new DataInputStream(new FileInputStream(input));

        return inp;
    }

    DataOutputStream ChooseOutput() throws IOException
    {
        DataOutputStream out;
        if(input == null)
            out = new DataOutputStream(new ByteArrayOutputStream());
        else
            out = new DataOutputStream(new FileOutputStream(output));

        return out;
    }*/

    /*private void RunEncode() throws IOException
    {
        DataInputStream readerInput = null;
        DataOutputStream writerOutput = null;
        try
        {
            readerInput = ChooseInput();
            writerOutput = ChooseOutput();
        }
        catch(IOException ex){

            Log.report("Can't decode file");
            throw new IOException();
        }

        try
        {
            byte i = readerInput.readByte();
            int k = 1;

            while (i != -1)
            {
                byte j = readerInput.readByte();

                while (j == i)
                {
                    k++;
                    j = readerInput.readByte();
                }

                writerOutput.writeByte((byte)k);
                writerOutput.flush();
                writerOutput.writeByte(i);
                writerOutput.flush();

                i = j;
                k = 1;
            }
        }

        catch (IOException e)
        {

        }

        if(output == null)
        {
            Consumer.Put(writerOutput.toString());
        }
    }


    private void RunDecode() throws IOException
    {
        DataInputStream readerInput = null;
        DataOutputStream writerOutput = null;
        try
        {
            readerInput = ChooseInput();
            writerOutput = ChooseOutput();
        }
        catch(IOException ex){

            Log.report("Can't decode file");
            throw new IOException();
        }


        byte k = 0;
        try {
            while((k = readerInput.readByte()) != -1)
            {
                byte j = readerInput.readByte();
                while( k > 0)
                {
                    writerOutput.writeByte((char)j);
                    writerOutput.flush();
                    k--;
                }
            }
        }
        catch (IOException e)
        {

        }
        readerInput.close();
        writerOutput.close();

    }*/

}
