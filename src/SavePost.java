import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class SavePost extends DataBase {
    private RandomAccessFile file;
    private String filePath = "post.dat";
    private int numberFixStr = 20;
    private int numberFixText = 200;

    public void insert(Post pst) throws IOException {
        file.seek(file.length());

        file.writeInt(pst.getId());
        file.writeInt(pst.getId_personPost());
        file.writeChars(fixString(pst.getPersonPost(), numberFixStr));
        file.writeChars(fixString(pst.getText_pst(), numberFixText));

//        file.writeInt(pst.getNumber_like());
//        file.writeInt(pst.getNumber_Dis_like());
    }

    public List<Post> selectAll() throws IOException {
        file.seek(0);

        ArrayList<Post> posts = new ArrayList<>();

        while (true) {
            try {
                int idPost = file.readInt();
                int id_person_posted = file.readInt();
                String person_posted = readOneStr(file, numberFixStr);
                String text_post = readOneStr(file, numberFixText);
//                int numberLike = file.readInt();
//                int numberDisLike = file.readInt();

                Post p1 = new Post();

                p1.setId_post(idPost);
                p1.setId_personPost(id_person_posted);
                p1.setPersonPost(person_posted);
                p1.setText_pst(text_post);
//                p1.setNumber_like(numberLike);
//                p1.setNumber_Dis_like(numberDisLike);

                posts.add(p1);

            } catch (Exception e) {
                break;
            }
        }
        return posts;
    }

//    public void like_post(int numberRecord) throws IOException {
//        file.seek((numberRecord * 448) + 440);
//        int like = file.readInt();
//        like++;
//        file.seek((numberRecord * 456) + 448);
//        file.writeInt(like);
//    }

//    public void disLike(int numberRecord) throws IOException {
//        file.seek((numberRecord * 456) + 452);
//        int dis_like = file.readInt();
//        dis_like++;
//        file.seek((numberRecord * 456) + 452);
//        file.writeInt(dis_like);
//    }
    public void deletePost(int numberRecord, String personPosted) throws IOException {
        Post postFORdelete = getPost(numberRecord);

        if (postFORdelete.getPersonPost().equals(personPosted)) {
            file.seek(numberRecord * 448);
            file.writeInt(-10);
        } else return;
    }

    public void update_text(int numberRecord, String personPosted, String new_textPost) throws IOException {
        Post postFORupdate = getPost(numberRecord);

        file.seek(numberRecord * 448);

        if (postFORupdate.getPersonPost().equals(personPosted)) {
            file.seek((numberRecord * 448) + 48);
            file.writeChars(fixString(new_textPost, numberFixText));
        }
    }

    public void update_person(int numberRecord, String new_name) throws IOException {
        file.seek((numberRecord * 448) + 8);
        file.writeChars(fixString(new_name, numberFixStr));
    }

    public Post getPost(int numberRecord) throws IOException {
        file.seek(numberRecord * 448);

        int idPost = file.readInt();
        int id_person_posted = file.readInt();
        String person_posted = readOneStr(file, numberFixStr);
        String text_post = readOneStr(file, numberFixText);
//        int numberLike = file.readInt();
//        int numberDisLike = file.readInt();

        Post p1 = new Post();

        p1.setId_post(idPost);
        p1.setId_personPost(id_person_posted);
        p1.setPersonPost(person_posted);
        p1.setText_pst(text_post);
//        p1.setNumber_like(numberLike);
//        p1.setNumber_Dis_like(numberDisLike);

        return p1;
    }

    public void close() throws IOException {
        file.close();
    }

    public void create() throws FileNotFoundException {
        file = new RandomAccessFile(filePath, "rw");
    }

    public RandomAccessFile getFile() {
        return file;
    }

    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getNumberFixStr() {
        return numberFixStr;
    }

    public void setNumberFixStr(int numberFixStr) {
        this.numberFixStr = numberFixStr;
    }

    public int getNumberFixText() {
        return numberFixText;
    }

    public void setNumberFixText(int numberFixText) {
        this.numberFixText = numberFixText;
    }

    public double getLength() throws IOException {
        return file.length();
    }
}
