import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private int id_post;
    private int id_personPost;
    private String personPost;
    private static int allPost = 0;
    private int Number_like = 0;
    private int Number_Dis_like = 0;
    private List<Comment> comments;
    private String Text_pst = "";

    static {
        try {
            allPost = Alluser_read_with_saveuser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int Alluser_read_with_saveuser() throws IOException {
        Control control = new Control();
        return control.allPost().size();
    }

    public Post() {
        allPost++;
        comments = new ArrayList<>();
        id_post = allPost;
    }

    public int getNumber_like() {
        return Number_like;
    }

    public String getText_pst() {
        return Text_pst;
    }

    public String getPersonPost() {
        return personPost;
    }

    public void setPersonPost(String personPost) {
        this.personPost = personPost;
    }

    public void setNumber_like(int number_like) {
        Number_like = number_like;
    }

    public void setNumber_Dis_like(int number_Dis_like) {
        Number_Dis_like = number_Dis_like;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(User user, String text_pst) {
        this.comments.add(new Comment(user.getId(), user.getUser_Name(), text_pst));
    }

    public void setText_pst(String text_pst) {
        Text_pst = text_pst;
    }

    public void setNumber_like_one_one() {
        Number_like++;
    }

    public int getNumber_Dis_like() {
        return Number_Dis_like;
    }

    public void setNumber_Dis_like_one_one() {
        Number_Dis_like++;
    }

    public void setOneComment(String comment, User user) {
        if (this.comments == null)
            this.comments = new ArrayList<>();

        comments.add(new Comment(user.getId(), user.getUser_Name(), comment));
    }

    @Override
    public String toString() {
        return "Post{" +
                "id Post=" + id_post +
                ", id_personPost=" + id_personPost +
                ", personPost='" + personPost + '\'' +
                ", Number_like=" + Number_like +
                ", Number_Dis_like=" + Number_Dis_like +
                ", comments=" + comments +
                ", Text_pst='" + Text_pst + '\'' +
                '}';
    }

    private String commentInStr() {
        String str = "";
        for (int i = 0; i < this.comments.size(); i++)
            if (comments.get(i) != null)
                str += String.format("( %s ) - %s %n", this.comments.get(i).getName(), this.comments.get(i).getText());

        return str;
    }

    public int getId() {
        return id_post;
    }

    public void setId_post(int id) {
        this.id_post = id;
    }

    public int getId_personPost() {
        return id_personPost;
    }

    public void setId_personPost(int id_personPost) {
        this.id_personPost = id_personPost;
    }

    public static int getAllPost() {
        return allPost;
    }

    public static void setAllPost(int allPost) {
        Post.allPost = allPost;
    }

    public void setComments(Comment comment) {
        this.comments.add(comment);
    }
}
