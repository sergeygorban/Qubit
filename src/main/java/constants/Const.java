package constants;

public enum Const {

    // Path to the service account key for google
    PATH_TO_SERVICE_ACCOUNT_KEY ("D:\\Git\\Modules\\src\\main\\java\\google\\GoogleApi-bab0e73e5451.json"),

    PATH_TO_CREDENTIALS ("D:\\Git\\Modules\\src\\main\\java\\google\\credentials.json"),

    API_KEY ("");


    private String value;

    Const(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
