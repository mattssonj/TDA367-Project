package corp.skaj.foretagskvitton.model;

/**
 * Created by lasanjin on 4/5/17.
 */

public class Comment {
    private String comment;

    public Comment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}