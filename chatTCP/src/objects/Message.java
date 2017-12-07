package objects;

/**
 * @author Emanuele Pagotto
 */
public class Message implements java.io.Serializable {

    String message;
    String src, dst, op;

    public Message(String message, String src, String dst, String op) {
        this.message = message;
        this.src = src;
        this.dst = dst;
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public String toString() {
        return message;
    }

    public String getMessage() {
        return message;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }
}
