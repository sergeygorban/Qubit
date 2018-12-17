package constants;

public enum Const {

    // Path to the service account key for google
    PATH_TO_KEY ("D:\\google\\Jmeter-adb2fb5d0f49.json"),

    API_KEY ("AIzaSyBbc-p6rWEDfiUcaBI9tSBYFWM3-8H3E4Y");


    private String value;

    Const(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
