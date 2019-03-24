package constants;

public enum ErrorMessage {

    E100("Properties was not created"),
    E101("File path or file name are not set for properties"),
    E102("Or the file path or file name must be set");


    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
