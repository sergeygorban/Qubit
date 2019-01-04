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
                    new Auth().getCredentialForSheet(Const.PATH_TO_SERVICE_ACCOUNT_KEY.getValue());
        }

        return credentialServiceAccount;
    }
}
