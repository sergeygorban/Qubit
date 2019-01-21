package excel;

/**
 * Created by Admin on 21.03.2018.
 */
public enum Api {


    Сервіс_завантаження_даних_файлів_Завантаження("http://10","POST-MULT","","");

    public final String STORAGE = "C:\\Users\\Admin\\Desktop\\111";
    public final String url;
    public final String method;
    public final String dataFile;
    public final String dataSheet;

    Api(String url, String method, String dataFile, String dataSheet) {
        this.url = url;
        this.method = method;
        this.dataFile = dataFile;
        this.dataSheet = dataSheet;
    }
}
