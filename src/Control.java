import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Control {

    public boolean login(String username, String password) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        if (save.incorrectUserPass(username, password)) {
            save.close();
            return true;
        }
        return false;
    }

    public void insertNewUser(User user) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        save.insert(user);

        save.close();
    }

    public void insertNewPost(Post pst) throws IOException {
        SavePost save = new SavePost();
        save.create();

        save.insert(pst);

        save.close();
    }

    public void insertNewComment(Comment cs) throws IOException {
        SaveComment save = new SaveComment();
        save.createFile();

        save.insert(cs);

        save.close();
    }

    public void set_one_fllowing(String personFollowing, String personFollower) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        save.set_one_fllowing(personFollowing, personFollower);

        save.close();
    }

    public void deletePost(int numberRecord, String personPosted) throws IOException {
        SavePost save = new SavePost();
        save.create();

        save.deletePost(numberRecord, personPosted);

        save.close();
    }

    public void update_post(int numberRecord, String personPosted, String new_textPost) throws IOException {
        SavePost save = new SavePost();
        save.create();

        save.update_text(numberRecord, personPosted, new_textPost);

        save.close();
    }

    public void update_person_postedName(String before_userName, String New_UserName) throws IOException {
        SavePost save = new SavePost();
        save.create();

        ArrayList<Post> posts = allPost();

        for (int i = 0; i < posts.size(); i++)
            if (posts.get(i).getPersonPost().equals(before_userName))
                save.update_person(i, New_UserName);

        save.close();
    }

    public void update_person_CommentName(String before_userName, String New_username) throws IOException {
        SaveComment save = new SaveComment();
        save.createFile();

        ArrayList<Comment> comments = (ArrayList<Comment>) save.selectAll();

        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getName().equals(before_userName))
                save.update_userName(i, New_username);
        }
    }

    public boolean not_same_userName(String user_name) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        ArrayList<User> users = (ArrayList<User>) save.selectAll();

        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUser_Name().equals(user_name))
                return false;

        return true;
    }

    public ArrayList<User> allUser() throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        ArrayList<User> all = (ArrayList<User>) save.selectAll();

        save.close();

        return all;
    }

    public ArrayList<Post> allPost() throws IOException {
        SavePost savepost = new SavePost();
        SaveComment saveComment = new SaveComment();
        LikePOST likePOST = new LikePOST();
        saveComment.createFile();
        savepost.create();
        likePOST.create();

        ArrayList<Comment> allComment = (ArrayList<Comment>) saveComment.selectAll();
        ArrayList<Post> allPost = (ArrayList<Post>) savepost.selectAll();
        ArrayList<LikePOST> allLikeDis = (ArrayList<LikePOST>) likePOST.selectAll();

        likePOST.close();
        savepost.close();
        saveComment.close();

        for (Comment comment : allComment)
            for (Post post : allPost)
                if (post.getId() == comment.getIdPostComment())
                    post.setComments(comment);

        for (Post post : allPost)
            for (LikePOST like : allLikeDis) {
                if (like.getPostID() == post.getId() && like.getLikeOrdis() == 'L' && like.getPosition() == 'T')
                    post.setNumber_like_one_one();
                if (like.getPostID() == post.getId() && like.getLikeOrdis() == 'D' && like.getPosition() == 'T')
                    post.setNumber_Dis_like_one_one();
            }
        return allPost;
    }

    public void deleteFollowing(int recordForDelete, String personLogged) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        save.deleteFollwing(recordForDelete , personLogged);

        save.close();
    }

    public String allPost(int idPerson) throws IOException {
        ArrayList<Post> postArrayList = allPost();
        System.out.println();
        String str = "-----------------------------------\n";
        for (int i = 0; i < postArrayList.size(); i++) {
            Post post = postArrayList.get(i);
            if ((idPerson == -1 || post.getId_personPost() == idPerson) && post.getId() != -10)
                str += String.format("%d-(%s): %s\nnumberLike = %d | numberDisLike = %d\n\n[comments]\n%s\n------------------------------------\n", i, post.getPersonPost(), post.getText_pst(), post.getNumber_like(), post.getNumber_Dis_like(), commentINstr(post.getComments()));
        }
        return str;
    }

    public int numberPostOnePerson(int idPerson) throws IOException {
        ArrayList<Post> posts = allPost();
        int kk = 0;
        for (int i = 0; i < posts.size(); i++)
            if (posts.get(i).getId_personPost() == idPerson && posts.get(i).getId() != -10)
                kk++;

        return kk;
    }

    public void like_post(int idPerson, int recordNumberPost) throws IOException {
        LikePOST save = new LikePOST();
        save.create();

        save.insert(idPerson, recordNumberPost, 'L');

        save.close();
    }

    public void dislike_post(int idPerson, int recordNumberPost) throws IOException {
        LikePOST save = new LikePOST();
        save.create();

        save.insert(idPerson, recordNumberPost, 'D');

        save.close();
    }

    public int getIDpost(int numberRecord) throws IOException {
        SavePost save = new SavePost();
        save.create();

        int id_post = save.getPost(numberRecord).getId();

        save.close();

        return id_post;
    }

    private String commentINstr(List<Comment> comments) {
        String str = "";
        for (Comment comment : comments)
            if (comment != null)
                str += comment + "\n";

        return str;
    }

    public User selectUser(String username, String password) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        ArrayList<User> users = (ArrayList<User>) save.selectAll();

        for (User usr : users)
            if (usr.getUser_Name().equals(username) && usr.getPassword().equals(password))
                return usr;

        return null;
    }

    public User selectUser(String username) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        ArrayList<User> users = (ArrayList<User>) save.selectAll();

        for (User usr : users)
            if (usr.getUser_Name().equals(username))
                return usr;

        return null;
    }

    public void update_username_pass_bio(String username, String password, String new_name, String new_pass, String bio) throws IOException {
        SaveUser save = new SaveUser();
        save.createFile();

        save.updateUser_user_pass(username, password, new_name, new_pass, bio);

        save.close();
    }
}

