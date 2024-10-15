import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class LikePOST extends DataBase {
    private int userID;
    private int postID;
    private char LikeOrdis;
    private char position;
    private RandomAccessFile file;
    private String path = "likeDis.dat";


    public void insert(int personID, int postID, char likeORdis) throws IOException {
        int record = again_likeORdis(personID, postID , likeORdis);

        if (record != -1) {
            update(record);
        } else {
            file.writeInt(personID);
            file.writeInt(postID);
            file.writeChar(likeORdis);
            file.writeChar('T');
        }
    }

    public void update(int record) throws IOException {
        file.seek((record * 12) + 10);
        char bool = file.readChar();

        if (bool == 'T') {
            file.seek((record * 12) + 10);
            file.writeChar('F');
        } else {
            file.seek((record * 12) + 10);
            file.writeChar('T');
        }
    }

    public int again_likeORdis(int personID, int postID , char likeOrdis) throws IOException {
        file.seek(0);
        int kk = 0;

        while (true) {
            try {
                int id_person = file.readInt();
                int id_post = file.readInt();

                char likeORdis = file.readChar();
                char bool = file.readChar();

                if (id_person == personID && id_post == postID && likeORdis == likeOrdis)
                    return kk;
                kk++;
            } catch (Exception e) {
                break;
            }
        }
        return -1;
    }

    public List<LikePOST> selectAll() throws IOException {
        file.seek(0);

        ArrayList<LikePOST> post_user_saves = new ArrayList<>();

        while (true) {
            try {
                int id_person = file.readInt();
                int id_post = file.readInt();
                char likeORdis = file.readChar();
                char bool = file.readChar();

                LikePOST save = new LikePOST();

                save.setUserID(id_person);
                save.setPostID(id_post);
                save.setLikeOrdis(likeORdis);
                save.setPosition(bool);

                post_user_saves.add(save);
            } catch (Exception e) {
                break;
            }
        }
        return post_user_saves;
    }

    public void create() throws FileNotFoundException {
        file = new RandomAccessFile(path, "rw");
    }

    public void close() throws IOException {
        file.close();
    }

    @Override
    public String toString() {
        return "Post_user_save{" +
                "userID=" + userID +
                ", postID=" + postID +
                ", LikeOrdis=" + LikeOrdis +
                ", position=" + position +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public char getLikeOrdis() {
        return LikeOrdis;
    }

    public void setLikeOrdis(char likeOrdis) {
        LikeOrdis = likeOrdis;
    }

    public char getPosition() {
        return position;
    }

    public void setPosition(char position) {
        this.position = position;
    }

    public RandomAccessFile getFile() {
        return file;
    }

    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
