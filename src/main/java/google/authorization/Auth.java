package google.authorization;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Auth {

    // Available OAuth 2.0 scopes for use with the google sheets API.
    private List<String> scope = Arrays.asList(SheetsScopes.DRIVE);

    // OAuth 2.0 using Service account
    // fileName - File name of the key for service account. It is file should exist in the same directory as the class
    public Credential getCredentialUsingServiceAccountByFileName(String fileName) {
        try {
            return GoogleCredential
                    .fromStream(Auth.class.getResourceAsStream(fileName))
                    .createScoped(scope);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // OAuth 2.0 using Service account
    public Credential getCredentialUsingServiceAccountByFilePath(String pathToFile) {
        try {
            return GoogleCredential
                    .fromStream(Files.newInputStream(Paths.get(pathToFile)))
                    .createScoped(scope);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
