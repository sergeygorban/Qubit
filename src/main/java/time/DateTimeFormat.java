package time;

import lombok.Getter;

@Getter
public enum DateTimeFormat {

    // 2020-05-14 00:00:00
    ISO_1("yyyy-MM-dd HH:mm:ss"),
    ISO_2("yyyy-MM-dd"),
    ISO_3("yyyy-MM-dd 00:00:00"),
    ISO_4("1900-01-01 00:00:00");


    private String format;

    DateTimeFormat(String dateTimeFormat) {
        this.format = dateTimeFormat;
    }
}
