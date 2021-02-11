package rest_api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractMethod {

    private URI uri;


    // Creating URI
    public URI createURI(RestRequest restRequest) {

        AtomicReference<String> path = new AtomicReference<>(restRequest.getPath());

        try {

            if (restRequest.getPathWithParam() != null && restRequest.getRequestParameters() != null) {
                restRequest.getRequestParameters().forEach((key, value) -> path.getAndSet(path.get().replace("?", value.toString())));

            } else if (restRequest.getPathWithParam() == null && restRequest.getRequestParameters() != null){
                path.getAndSet(path.get().concat("?"));
                restRequest.getRequestParameters().forEach((key, value) -> path.getAndSet(path.get().concat(key).concat("=").concat(value.toString())));

            } else {
                throw new RuntimeException("!!! URI was not created !!!");
            }

            uri = new URI(restRequest.getUrl() + path.get());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri;
    }
}
