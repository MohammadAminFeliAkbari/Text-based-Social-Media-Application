import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class User {
    private String User_Name;
    private String Password;
    private String Bio;
    private List<Post> Posts;
    private List<String> Following;
    public static int alluser;


    private List<String> Follower;
    private int Id_user;

    static {
        try {
            alluser = Alluser_read_with_saveuser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int Alluser_read_with_saveuser() throws IOException {
        Control control = new Control();
        return control.allUser().size();
    }

    public User() {
        alluser++;
        Posts = new ArrayList<>();
        Following = new ArrayList<>();
        Follower = new ArrayList<>();
        this.Id_user = alluser;
    }

    public List<String> getFollower() {
        return Follower;
    }

    public void setFollower(String name_follower) {
        Follower.add(name_follower);
    }

    public List<String> getFollowing() {
        return Following;
    }

    public StringBuilder get_All_following() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Following.size(); i++)
            if (Following.get(i) != null)
                str.append(String.format("%d-%s%n", i + 1, Following.get(i)));
        return str;
    }

    public StringBuilder get_All_follower() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Follower.size(); i++)
            if (Follower.get(i) != null)
                str.append(String.format("%d-%s%n", i + 1, Follower.get(i)));
        return str;
    }

    public void setFollowing(String following) {
        Following.add(following);
    }

    public void setPosts(String new_post) {
        Posts.add(new Post());
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "User_Name='" + User_Name + '\'' +
                ", Password='" + Password + '\'' +
                ", Bio='" + Bio + '\'' +
                ", Posts=" + Posts +
                ", Following=" + Following +
                ", Follower=" + Follower +
                ", Id_user=" + Id_user +
                '}';
    }

//    @Override
//    public String toString() {
//        return
//                "name='" + User_Name + '\'' +
//                        ", Bio='" + Bio + '\'' +
//                        ", Following=" + Following.size() +
//                        ", Follower=" + Follower.size() +
//                        '}';
//    }

    public void setPosts(List<Post> posts) {
        Posts = posts;
    }

    public StringBuilder All_post_me() {
        StringBuilder All_pst = new StringBuilder();
        if (Posts.size() == 0)
            return new StringBuilder("Nothing a post");
        for (int i = 0; i < Posts.size(); i++)
            if (Posts.get(i) != null)
                All_pst.append(String.format("%d-%s", i + 1, Posts.get(i)));
        return All_pst;
    }

    public List<Post> getPosts() {
        return Posts;
    }

    public void setPosts(Post post) {
        Posts.add(post);
    }

    public void setFollowing(List<String> following) {
        Following = following;
    }

    public int getId() {
        return Id_user;
    }

    public void setId_user(int id) {
        this.Id_user = id;
    }

    public void setFollower(List<String> follower) {
        Follower = follower;
    }
}
