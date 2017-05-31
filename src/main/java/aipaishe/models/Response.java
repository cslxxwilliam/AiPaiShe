package aipaishe.models;

/**
 * Created by hillmon on 1/6/2017.
 */
public class Response<T> {

    private String status;
    private String message;
    private T data;

    public Response(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage (String message) {
        this.message = message;
    }

}