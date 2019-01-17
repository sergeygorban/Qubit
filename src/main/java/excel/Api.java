package excel;

/**
 * Created by Admin on 21.03.2018.
 */
public enum Api {


    �����_������������_�����_�����_������������("http://10.100.174.36:8086/prenifi/upload","POST-MULT","�����_������������_�����_�����","������������"),

    �����_������������_�������_�����_��������_�����("http://10.100.174.36:8089/data-processing-settings","POST","�����_������������_�������_�����","��������_�����"),
    �����_������������_�������_�����_���������_�����("http://10.100.174.36:8089/get-data-processing-settings","POST","�����_������������_�������_�����","���������_�����"),
    �����_������������_�������_�����_���������_������_䳿("http://10.100.174.36:8089/data-processing-settings","DELETE","�����_������������_�������_�����","���������_�����"),
    �����_������������_�������_�����_�����������_�����("http://10.100.174.36:8089/data-processing-settings","PUT","�����_������������_�������_�����","�����������_�����"),

    ������_������_��������_���������_������("http://10.100.174.36:8085/tree/directories/create","POST","������ ������ ��������","��������_�����"),
    ������_������_��������_���������_������("http://10.100.174.36:8085/tree/directories/delete","DELETE","������ ������ ��������","���������_�����"),
    ������_������_��������_����������_�����_��_���������("http://http://10.100.174.36:8085/tree/directories/role/set","POST","������_������_��������","����������_����"),

    ������_���������_�������_����������_���������_�����("http://10.100.174.36:8087/metadata","POST","������ ��������� �������","����������_���������"),
    ������_���������_�������_����������_�����("http://10.100.174.36:8087/files","POST","������ ��������� �������","����������_�����"),
    ������_���������_�������_���������_����������("http://10.100.174.36:8087/find-files-metadata","POST","������ ��������� �������","���������_����������"),

    ������_�������_������_�����_����������_��������_������_�����("http://10.100.174.36:8091/incoming-packets","POST","������_�������_������_�����","����������_������"),
    ������_�������_������_�����_�����������_�������_������_�����("http://10.100.174.36:8091/incoming-packets","PATCH","������_�������_������_�����","�����������_������"),

    �����_����������_��������_�����_���������_��������_�����("http://10.100.174.36:8089/stored-data-attributes","POST","�����_����������_��������_�����","��������� ��������"),
    �����_����������_��������_�����_���������_��������_�����("http://10.100.174.36:8089/stored-data-attributes/params","GET","�����_����������_��������_�����","��������� ��������"),


    �����_������_����������_��������_�����_�_������_��������������_�����_����������_�����("http://10.100.174.36:8089/data-link-attr","POST","�����_������_����������_��������_�����_�_������_��������������_�����","���������� �����"),
    �����_������_����������_��������_�����_�_������_��������������_�����_��������_�����("http://10.100.174.36:8089/data-link-attr","DELETE","�����_������_����������_��������_�����_�_������_��������������_�����","�������� �����"),
    �����_������_����������_��������_�����_�_������_��������������_�����_���������_�����("http://10.100.174.36:8089/data-link-attr/dbAttr","GET-WITHOUT_PARAM","�����_������_����������_��������_�����_�_������_��������������_�����","���������"),

    �����_������������_���������_��������_���������_���������_��������("http://10.100.174.36:8097//incoming-packets-attrs","POST","�����_������������_���������_��������","��������� ��������"),
    �����_������������_���������_��������_�����������_���������_��������("http://10.100.174.36:8097//incoming-packets-attrs","PATCH","�����_������������_���������_��������","����������� ��������"),

    �����_��������_����������_��������_����������_��������("http://10.100.174.36:8079/summary-data-attr","POST","�����_��������_����������_��������","��������� ��������"),
    �����_��������_����������_��������_����������_��������("http://10.100.174.36:8079/summary-data-attr","PATCH","�����_��������_����������_��������","����������� ��������"),

    �����_����������_��������_�����_���_����������_���������_��������_�����("http://10.100.174.36:8098/summary-data","POST","�����_����������_��������_�����_���_����������","��������� �����"),
    �����_����������_��������_�����_���_����������_����������_��������_�����("http://10.100.174.36:8098/summary-data","PATCH","�����_����������_��������_�����_���_����������","����������� �����"),
    �����_����������_��������_�����_���_����������_���������_��������_�����("http://10.100.174.36:8098/find-summary-data","POST","�����_����������_��������_�����_���_����������","��������� �����"),

    ������_��������_����������_�������_��������_���������_�����_�_�������("http://10.100.174.36:8093/attrValueMatch/attributes","POST","������_��������_����������_�������_��������","��������_�����"),
    ������_��������_����������_�������_��������_���������_�����_�_��������("http://10.100.174.36:8093/attrValueMatch/attributes","DELETE","������_��������_����������_�������_��������","���������_�����"),


    �����_������������_�������_�����_���������_�������_�����("http://10.100.174.36:8089/supported-data-format","POST","�����_������������_�������_�����","���������"),
    �����_������������_�������_�����_���������_�������_�����("http://10.100.174.36:8089/supported-data-format","DELETE","�����_������������_�������_�����","���������"),

    �����_����������_�����_�_����_���������_������_����������_�����("http://10.100.174.36:8089/file-data-separator","POST","�����_����������_�����_�_����","���������"),
    �����_����������_�����_�_����_���������_����������_�����("http://10.100.174.36:8089/file-data-separator","DELETE","�����_����������_�����_�_����","���������"),

    �����_���������_�����_��_�����������_���������_����_���������_�����("http://10.100.174.36:8089/dimension-stored-data","POST","�����_���������_�����_��_�����������","���������"),
    �����_���������_�����_��_�����������_���������_���������("http://10.100.174.36:8089/dimension-stored-data","DELETE","�����_���������_�����_��_�����������","���������"),

    �����_�������_�������_�����_���������_������_�������("http://10.100.174.36:8089/data-processing-status","POST","�����_�������_�������_�����","���������"),

    �����_����_�����_��_�����������_���������_������_����("http://10.100.174.36:8089/type-stored-data","POST","�����_����_�����_��_�����������","���������"),
    �����_����_�����_��_�����������_���������_����_�����("http://10.100.174.36:8089/type-stored-data","DELETE","�����_����_�����_��_�����������","���������"),

    �����_�������_���_���������_������_�������_����("http://10.100.174.36:8089/date-format","POST","�����_�������_���","���������"),
    �����_�������_���_���������_�������_����("http://10.100.174.36:8089/date-format","DELETE","�����_�������_���","���������"),


    ������_��������_�������_���������_�����_�_�������("http://10.100.174.36:8092/functionData/functions","POST","������_��������_�������","���������"),
    ������_��������_�������_���������_�����("http://10.100.174.36:8092/functionData/functions","DELETE","������_��������_�������","���������"),

    �����_����_��������������_�����_���������_������_����("http://10.100.174.36:8089/type-uploaded-data","POST","�����_����_��������������_�����","���������"),
    �����_����_��������������_�����_���������_����("http://10.100.174.36:8089/type-uploaded-data","DELETE","�����_����_��������������_�����","���������"),

    ������_���_�������_�_���_���_�����_������_������������("http://10.100.174.36:8080/recommendations/details/fetch","POST","������_���_�������_�_���_���","�����_������_������������"),
    ������_���_�������_�_���_���_�����_���������_�����("http://10.100.174.36:8084/get-data-for-sododv","POST","������_���_�������_�_���_���","�����_���������_�����"),

    ������_���("https://uniagr.test.it.loc/PDRAPI/process/extraction","POST","���","���"),

    ����("http://10.100.174.36:8089/data-processing-settings","PUT","����","����");




    public final String STORAGE = "C:\\Users\\Admin\\Desktop\\������";
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