import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SaveUser extends DataBase {
    private RandomAccessFile file;
    private String filePath = "user.dat";
    private int strNumberFix = 20;
    private int BioNumberFix = 200;
    private int number_followerANDFollowing = 10;

    public void createFile() throws FileNotFoundException {
        file = new RandomAccessFile(filePath, "rw");
    }

    public void insert(User user) throws IOException {
        file.seek(file.length());

        file.writeInt(user.getId());
        file.writeChars(fixString(user.getUser_Name(), strNumberFix));
        file.writeChars(fixString(user.getPassword(), strNumberFix));
        file.writeChars(fixString(user.getBio(), BioNumberFix));
        writeFollowingANDFollower(user.getFollower());
        writeFollowingANDFollower(user.getFollowing());
    }

    public List<User> selectAll() throws IOException {
        file.seek(0);

        List<User> allUser = new ArrayList<>();

        while (true) {
            try {
                int id = file.readInt();
                String userName = readOneStr(file, strNumberFix);
                String pass = readOneStr(file, strNumberFix);
                String bio = readOneStr(file, BioNumberFix);
                List<String> follower = selectFollwingAndFollwer();
                List<String> following = selectFollwingAndFollwer();

                User u = new User();

                u.setId_user(id);
                u.setUser_Name(userName);
                u.setPassword(pass);
                u.setBio(bio);
                u.setFollower(follower);
                u.setFollowing(following);

                allUser.add(u);
            } catch (Exception e) {
                break;
            }
        }
        return allUser;
    }

    public double getLength() {
        try {
            return file.length();
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public boolean incorrectUserPass(String userName, String password) throws IOException {
        ArrayList<User> allUser = (ArrayList<User>) selectAll();
        for (int i = 0; i < allUser.size(); i++)
            if (allUser.get(i) != null)
                if (allUser.get(i).getUser_Name().equals(userName) && allUser.get(i).getPassword().equals(password))
                    return true;
        return false;
    }

    public void updateUser_user_pass(String username, String password, String new_name, String new_pass, String new_bio) throws IOException {
        int numberRecord = findUser(username, password);

        file.seek((numberRecord * 1284) + 4);

        file.writeChars(fixString(new_name, strNumberFix));
        file.writeChars(fixString(new_pass, strNumberFix));
        file.writeChars(fixString(new_bio, BioNumberFix));
    }

    public void set_one_fllowing(String personFollowing, String personFollower) throws IOException {
        if (!personFollower.equals(personFollowing)) {
            {
                int numberRecordFollowing = findUser(personFollowing);
                if (numberRecordFollowing != -1) {
                    file.seek((numberRecordFollowing * 1284) + 484);
                    List<String> follower = selectFollwingAndFollwer();
                    List<String> following = selectFollwingAndFollwer();
                    for (int i = 0; i < following.size(); i++)
                        if (following.get(i).equals(personFollower))
                            return;
                    following.add(personFollower);
                    file.seek((numberRecordFollowing * 1284) + 484);
                    writeFollowingANDFollower(follower);
                    writeFollowingANDFollower(following);
                }
            }
            {
                int numberRecordFollower = findUser(personFollower);
                if (numberRecordFollower != -1) {
                    file.seek((1284 * numberRecordFollower) + 484);
                    List<String> follower = selectFollwingAndFollwer();
                    List<String> following = selectFollwingAndFollwer();
                    for (int i = 0; i < follower.size(); i++)
                        if (follower.get(i).equals(personFollowing))
                            return;
                    follower.add(personFollowing);
                    file.seek((numberRecordFollower * 1284) + 484);
                    writeFollowingANDFollower(follower);
                    writeFollowingANDFollower(following);
                }
            }
        }
    }

    private void updateFollowing_follower(User user, int recordNumber) throws IOException {
        if (recordNumber != -1) {
            file.seek(recordNumber * 1284);
            file.writeInt(user.getId());
            file.writeChars(fixString(user.getUser_Name(), strNumberFix));
            file.writeChars(fixString(user.getPassword(), strNumberFix));
            file.writeChars(fixString(user.getBio(), BioNumberFix));
            writeFollowingANDFollower(user.getFollower());
            writeFollowingANDFollower(user.getFollowing());
        }
    }

    public void deleteFollwing(int recordForDelete, String personLogged) throws IOException {
        ArrayList<User> users = (ArrayList<User>) selectAll();

        User userFollowing = null;
        User userFollower = null;

        int recordNumberFollowing = -1;
        int recordNumberFollower = -1;

        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUser_Name().equals(personLogged)) {
                recordNumberFollowing = i;
                userFollowing = users.get(i);
                break;
            }

        ArrayList<String> following = (ArrayList<String>) userFollowing.getFollowing();

        String name_string = following.get(recordForDelete);
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUser_Name().equals(name_string)) {
                recordNumberFollower = i;
                userFollower = users.get(i);
                break;
            }

        try {
            following.remove(recordForDelete);
            userFollowing.setFollowing(following);
            updateFollowing_follower(userFollowing, recordNumberFollowing);

            ArrayList<String> follower = (ArrayList<String>) userFollower.getFollower();

            int numberForDeletefollower = -1;

            for (int i = 0; i < follower.size(); i++) {
                if (follower.get(i).equals(personLogged)) {
                    numberForDeletefollower = i;
                    break;
                }
            }

            follower.remove(numberForDeletefollower);
            userFollower.setFollower(follower);
            updateFollowing_follower(userFollower, recordNumberFollower);
        } catch (Exception e) {
        }
    }

    private int findUser(String username) throws IOException {
        int i = 0;
        file.seek(0);

        while (true) {
            try {
                int id = file.readInt();
                String userName = readOneStr(file, strNumberFix);
                String pass = readOneStr(file, strNumberFix);
                String bio = readOneStr(file, BioNumberFix);

                List<String> follower = selectFollwingAndFollwer();
                List<String> following = selectFollwingAndFollwer();

                if (userName.equals(username))
                    return i;
                i++;
            } catch (Exception e) {
                break;
            }
        }
        return -1;
    }

    private int findUser(String username, String password) {
        int i = 0;
        while (true) {
            try {
                int id = file.readInt();
                String userName = readOneStr(file, strNumberFix);
                String pass = readOneStr(file, strNumberFix);
                String bio = readOneStr(file, BioNumberFix);

                List<String> follower = selectFollwingAndFollwer();
                List<String> following = selectFollwingAndFollwer();

                if (username.equals(username) && pass.equals(password))
                    return i;
                i++;
            } catch (Exception e) {
                break;
            }
        }
        return -1;
    }

    private List<String> selectFollwingAndFollwer() throws IOException {
        List<String> foll = new ArrayList<>();

        for (int i = 0; i < number_followerANDFollowing; i++) {
            String followANDfollower = readOneStr(file, strNumberFix);

            if (followANDfollower.trim() != "")
                foll.add(followANDfollower.trim());
        }

        return foll;
    }

    private void writeFollowingANDFollower(List<String> follow) throws IOException {
        int size = follow.size();
        if (size < 10) {
            for (int i = 0; i < size; i++)
                if (follow.get(i) != null)
                    file.writeChars(fixString(follow.get(i), strNumberFix));
                else
                    file.writeChars(fixString(" ", strNumberFix));

            for (int i = 0; i < number_followerANDFollowing - size; i++)
                file.writeChars(fixString("", strNumberFix));
        }
    }

    public void close() throws IOException {
        file.close();
    }
}
