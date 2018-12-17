package singleton;

import com.google.api.client.auth.oauth2.Credential;
import constants.Const;
import google.authorization.Auth;

public enum Singleton {

    INSTANCE () {

    };
    private Credential credentialServiceAccount;



    public Credential getCredential() {

        if (credentialServiceAccount == null) {
            this.credentialServiceAccount =
                    new Auth().getCredentialUsingServiceAccountByFilePath(Const.PATH_TO_KEY.getValue());
        }

        return credentialServiceAccount;
    }
}
