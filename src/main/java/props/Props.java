package props;

import constants.ErrorMessage;
import lombok.Builder;
import lombok.Getter;

import java.io.FileInputStream;
import java.util.Properties;

@Getter
public class Props {

    private Properties props;
    private String fileName;
    private String fullPath;

    @Builder
    private Props(String fileName, String fullPath) {

        this.fileName = fileName;
        this.fullPath = fullPath;

        if (fileName == null && fullPath == null) {
            throw new RuntimeException(ErrorMessage.E101.getMessage());

        } else if ((fileName != null && !fileName.isEmpty()) && (fullPath != null && !fullPath.isEmpty())) {
            throw new RuntimeException(ErrorMessage.E102.getMessage());
        }

        if (fileName != null) {
            createPropsUsingResources();
        } else {
            createPropsUsingUrl();
        }
    }

    private void createPropsUsingResources() {

        props = new Properties();
        try {
            props.load(this.getClass().getResourceAsStream("/" + fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createPropsUsingUrl() {

        props = new Properties();
        try {
            props.load(new FileInputStream(fullPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
