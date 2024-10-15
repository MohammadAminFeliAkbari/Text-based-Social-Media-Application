import java.io.IOException;
import java.io.RandomAccessFile;

public class DataBase {
    public String fixString(String str, int fixNumber) {
        String s = str;

        for (int i = 0; i < fixNumber; i++)
            s += " ";


        return s.substring(0, fixNumber);
    }

    public String readOneStr(RandomAccessFile file, int fixNumber) throws IOException {
        String str = "";

        for (int i = 0; i < fixNumber; i++)
            str += file.readChar();

        return str.trim();
    }
}
