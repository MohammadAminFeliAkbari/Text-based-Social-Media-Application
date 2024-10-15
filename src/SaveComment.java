import com.sun.jdi.connect.spi.TransportService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class SaveComment extends DataBase {

    private RandomAccessFile file;
    private String filePath = "comment.dat";
    private int strNumberFix = 20;
    private int strTextFix = 200;

    public void insert(Comment comment) throws IOException {
        file.seek(file.length());

        file.writeInt(comment.getIdPostComment());
        file.writeChars(fixString(comment.getName(), strNumberFix));
        file.writeChars(fixString(comment.getText(), strTextFix));
    }

    public List<Comment> selectAll() throws IOException {
        List<Comment> comments = new ArrayList<>();

        file.seek(0);
        while (true) {
            try {
                int idPost = file.readInt();
                String personComment = readOneStr(file, strNumberFix);
                String textComment = readOneStr(file, strTextFix);

                comments.add(new Comment(idPost, personComment, textComment));
            } catch (Exception e) {
                break;
            }
        }
        return comments;
    }

    public void update_userName(int record, String new_user) throws IOException {
        file.seek((record * 444) + 4);
        file.writeChars(fixString(new_user, strNumberFix));
    }

    public double getLength() throws IOException {
        return file.length();
    }

    public void createFile() throws FileNotFoundException {
        file = new RandomAccessFile(filePath, "rw");
    }

    public void close() throws IOException {
        file.close();
    }
}
