import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Meno {
    private Control control;
    private Scanner input;

    private User userLogged;

    public void meno_login_SIGNUP_EXIT() throws IOException {
        control = new Control();
        input = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            System.out.println("-------------------------------------------------");
            System.out.println("1-Login");
            System.out.println("2-SignUp");
            System.out.println("3-Exit");
            System.out.println("-------------------------------------------------");

            int case_input;
            Scanner input = new Scanner(System.in);
            try {
                case_input = input.nextInt();
            } catch (Exception e) {
                case_input = 3;
            }
            switch (case_input) {
                case 1: {
                    FirstMeno_Login();
                    break;
                }
                case 2: {
                    FirstMeno_signUp();
                    break;
                }
                default:
                    flag = false;
            }
        }
    }

    public void FirstMeno_Login() throws IOException {
        Scanner input = new Scanner(System.in);
        String name = "";
        String password = "";
        try {
            System.out.println("inter your name for login:");
            name = input.nextLine();
            System.out.println("inter your password for login:");
            password = input.nextLine();
        } catch (Exception e) {
            System.out.println("please try again");
        }

        if (control.login(name, password)) {
            System.out.println("\n\n");
            userLogged = control.selectUser(name, password);
            FirstMeno_Sign_Login();
        } else System.out.println("\n[incorrect password or username]\n");
    }

    public void FirstMeno_signUp() throws IOException {
        Scanner input = new Scanner(System.in);
        User user = new User();

        boolean correct_user = true;
        boolean correct_pass = true;

        try {
            while (correct_user) {
                System.out.println("inter your name:");
                String user_sign = input.next();
                if (control.not_same_userName(user_sign) && user_sign.length() > 0) {
                    user.setUser_Name(user_sign);
                    correct_user = false;
                } else {
                    System.out.println("invalid userName");
                }
            }
            input.nextLine();
            while (correct_pass) {
                System.out.println("inter your password:");
                String password_sign = input.nextLine();
                if (password_sign.length() > 0) {
                    user.setPassword(password_sign);
                    correct_pass = false;
                } else {
                    System.out.println("the password at last one character");
                }
            }
            System.out.println("inter your bio:");
            user.setBio(input.nextLine());
        } catch (Exception e) {
            System.out.println("please try again");
        }

        control.insertNewUser(user);
        userLogged = user;
        FirstMeno_Sign_Login();
    }

    public void FirstMeno_Sign_Login() throws IOException {
        Scanner input = new Scanner(System.in);

        boolean flag_while = true;

        while (flag_while) {
            System.out.println("-------------------------------------------------");
            System.out.println("welcome " + userLogged.getUser_Name() + "!!");
            System.out.println("1-profit");
            System.out.println("2-post");
            System.out.println("3-search");
            System.out.println("4-return to main meno");
            System.out.println("-------------------------------------------------");

            int number;
            try {
                number = input.nextInt();
            } catch (Exception e) {
                number = 10;
            }

            switch (number) {
                case 1: {
                    FirstMeno_Sign_Login_profile();
                    break;
                }
                case 2: {
                    FirstMeno_Sign_Login_Post();
                    break;
                }
                case 3:
                    FirstMeno_Sign_Login_Search();
                    break;
                case 4:
                    flag_while = false;
                    break;
                default:
                    flag_while = false;
                    break;
            }
        }
    }

    public void FirstMeno_Sign_Login_Search() throws IOException {
        Scanner input = new Scanner(System.in);

        boolean flag = true;

        while (flag) {
            System.out.println("inter a user name for search:");
            String User_Name_for_search = input.next();
            input.nextLine();

            boolean founded = false;

            ArrayList<User> users = control.allUser();
            for (int i = 0; i < control.allUser().size(); i++)
                if (users.get(i).getUser_Name().equals(User_Name_for_search) && !User_Name_for_search.equals(userLogged.getUser_Name())) {
                    User test = control.selectUser(User_Name_for_search);
                    System.out.println("-------------------------------------------------\n");
                    System.out.println(String.format("name : %s\nbio :%s\nfollowing : %s\nfollower : %s\nnumber posted : %d"
                            , test.getUser_Name(), test.getBio(), test.getFollowing(), test.getFollower(), control.numberPostOnePerson(test.getId())));

                    System.out.println(control.allPost(control.selectUser(User_Name_for_search).getId()));

                    System.out.println("do you like the follow the user (y/n)?");
                    String follow = input.next();

                    if (follow.charAt(0) == 'y' || follow.charAt(0) == 'Y')
                        control.set_one_fllowing(userLogged.getUser_Name(), User_Name_for_search);

                    founded = true;
                }

            if (false == founded)
                System.out.println("[not found this user]\n");

            flag = false;
        }
    }

    public void FirstMeno_Sign_Login_profile() throws IOException {
        Scanner input = new Scanner(System.in);

        boolean flag_while = true;

        while (flag_while) {
            System.out.println("-------------------------------------------------");
            System.out.println("1-Edit profit");
            System.out.println("2-look profile me");
            System.out.println("3-return to main meno");
            System.out.println("-------------------------------------------------");

            int number;
            try {
                number = input.nextInt();
            } catch (Exception e) {
                number = 10;
            }

            switch (number) {
                case 1: {
                    boolean correct_user = true;
                    boolean correct_pass = true;

                    String before_name = userLogged.getUser_Name();
                    String before_pass = userLogged.getPassword();


                    while (correct_user) {
                        System.out.println("inter your new name:");
                        input.nextLine();
                        String new_user = input.nextLine();
                        if ((control.not_same_userName(new_user) && new_user.length() > 0) || new_user.equals(userLogged.getUser_Name())) {

                            control.update_person_postedName(userLogged.getUser_Name(), new_user);
                            control.update_person_CommentName(userLogged.getUser_Name(), new_user);

                            userLogged.setUser_Name(new_user);
                            correct_user = false;

                        } else {
                            System.out.println("invalid userName");
                        }
                    }

                    while (correct_pass) {
                        System.out.println("inter your new password:");
                        String password_sign = input.nextLine();
                        if (password_sign.length() > 0) {
                            userLogged.setPassword(password_sign);
                            correct_pass = false;
                        } else {
                            System.out.println("the password at least 8 character");
                        }
                    }

                    System.out.println("inter your new bio:");
                    userLogged.setBio(input.nextLine());

                    control.update_username_pass_bio(before_name, before_pass, userLogged.getUser_Name(), userLogged.getPassword(), userLogged.getBio());
                    break;
                }
                case 2: {
                    userLogged = control.selectUser(userLogged.getUser_Name());
                    System.out.println(String.format("name : %s\nbio :%s\nfollowing : %s\nfollower : %s\nnumber posted : %d"
                            , userLogged.getUser_Name(), userLogged.getBio(), userLogged.getFollowing(), userLogged.getFollower(), control.numberPostOnePerson(userLogged.getId())));

                    if (userLogged.getFollowing().size() != 0) {
                        System.out.println("----------------------------------");
                        System.out.println("1-do you delete a following");
                        System.out.println("2-exit");
                        System.out.println("----------------------------------");

                        List<String> following = userLogged.getFollowing();

                        int inp;
                        try {
                            inp = input.nextInt();
                        } catch (Exception e) {
                            inp = 2;
                        }

                        if (inp == 1) {
                            System.out.println("----------------------------------");
                            for (int i = 0; i < following.size(); i++)
                                System.out.println(i + "-" + following.get(i));
                            System.out.println("----------------------------------");

                            System.out.println("which one for delete:");

                            int for_delete;

                            try {
                                for_delete = input.nextInt();
                            } catch (Exception e) {
                                for_delete = -1;
                            }
                            try {
                                control.deleteFollowing(for_delete, userLogged.getUser_Name());
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        break;
                    }
                }
                default:
                    flag_while = false;
                    break;
            }
        }
    }

    public void FirstMeno_Sign_Login_Post() throws IOException {
        Scanner input = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            System.out.println("-------------------------------------------------");
            System.out.println("1-look All post me");
            System.out.println("2-look all post");
            System.out.println("3-add post");
            System.out.println("4-Exit the main meno");
            System.out.println("-------------------------------------------------");

            int number;
            try {
                number = input.nextInt();
            } catch (Exception e) {
                number = 0;
            }


            switch (number) {
                case 1: {
                    System.out.println(control.allPost(userLogged.getId()));

                    boolean deleteOReditPost = true;

                    int choise_deleteOREdit;
                    while (deleteOReditPost) {
                        System.out.println("""
                                1-delete post
                                2-edit post
                                3-exit
                                """);
                        try {
                            choise_deleteOREdit = input.nextInt();
                        } catch (Exception e) {
                            choise_deleteOREdit = -1;
                        }


                        switch (choise_deleteOREdit) {
                            case 1:
                                System.out.println("which one delete or edit?");
                                int choise_post_delete;

                                try {
                                    choise_post_delete = input.nextInt();
                                } catch (Exception e) {
                                    choise_post_delete = -1;
                                }
                                try {
                                    control.deletePost(choise_post_delete, userLogged.getUser_Name());
                                } catch (Exception e) {
                                    break;
                                }
                                break;
                            case 2:
                                System.out.println("which one post edit?");

                                int choise_post_edit;
                                try {
                                    choise_post_edit = input.nextInt();
                                } catch (Exception e) {
                                    choise_post_edit = -1;
                                }
                                try {
                                    System.out.println("inter a text for edit the post:");
                                    input.nextLine();
                                    control.update_post(choise_post_edit, userLogged.getUser_Name(), input.nextLine());
                                } catch (Exception e) {
                                    break;
                                }
                                break;
                            default:
                                deleteOReditPost = false;
                                break;
                        }
                        break;
                    }
                }
                case 2:
                    System.out.println(control.allPost(-1));

                    boolean fjf = true;

                    while (fjf) {
                        System.out.println("""
                                -------------------------------------------------
                                1- menu for like or comment
                                2- exit
                                -------------------------------------------------
                                """);

                        int coisww;

                        try {
                            coisww = input.nextInt();
                        } catch (Exception e) {
                            coisww = -1;
                        }


                        switch (coisww) {
                            case 1:
                                System.out.println("which post for like or comment?");
                                boolean lik_dis_comment = true;

                                int number_record_post;

                                try {
                                    number_record_post = input.nextInt();
                                } catch (Exception e) {
                                    number_record_post = -1;
                                }

                                while (lik_dis_comment) {
                                    System.out.println("""
                                            -------------------------------------------------
                                            1- like
                                            2- dislike
                                            3- comment
                                            4- exit
                                            -------------------------------------------------
                                            """);
                                    int lik_comment;
                                    try {
                                        lik_comment = input.nextInt();
                                    } catch (Exception e) {
                                        lik_comment = -1;
                                    }
                                    try {
                                        switch (lik_comment) {
                                            case 1:
                                                control.like_post(userLogged.getId(), control.getIDpost(number_record_post));
                                                lik_dis_comment = false;
                                                break;
                                            case 2:
                                                control.dislike_post(userLogged.getId(), control.getIDpost(number_record_post));
                                                lik_dis_comment = false;
                                                break;
                                            case 3:
                                                System.out.println("inter a text comment:");
                                                input.nextLine();
                                                String text = input.nextLine();
                                                control.insertNewComment(new Comment(control.getIDpost(number_record_post), userLogged.getUser_Name(), text));
                                                lik_dis_comment = false;
                                                break;
                                            default:
                                                lik_dis_comment = false;
                                                break;
                                        }
                                    } catch (Exception e) {
                                        break;
                                    }
                                }
                            default:
                                fjf = false;
                        }
                    }
                    break;
                case 3: {
                    System.out.println("inter a new post:");
                    input.nextLine();

                    Post pst = new Post();

                    String text = input.nextLine();

                    String name = userLogged.getUser_Name();
                    pst.setText_pst(text);
                    pst.setId_personPost(userLogged.getId());
                    pst.setPersonPost(userLogged.getUser_Name());

                    control.insertNewPost(pst);
                    System.out.println("added post !!\n");
                    break;
                }
                default:
                    flag = false;

            }
        }
    }
}