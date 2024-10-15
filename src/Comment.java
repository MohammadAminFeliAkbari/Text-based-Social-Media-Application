public class Comment {
    private int idPostComment;
    private String name;
    private String text;

    public Comment(int idPostComment, String name, String text) {
        this.name = name;
        this.text = text;
        this.idPostComment = idPostComment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdPostComment() {
        return idPostComment;
    }

    public void setIdPostComment(int idPostComment) {
        this.idPostComment = idPostComment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", this.name, this.text);
    }


//    @Override
//    public String toString() {
//        return "Comment{" +
//                "idPostComment=" + idPostComment +
//                ", name='" + name + '\'' +
//                ", text='" + text + '\'' +
//                '}';
//    }
}
