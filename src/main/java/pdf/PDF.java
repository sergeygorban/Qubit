package pdf;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.assertj.core.util.Streams;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Log
public class PDF {

    private PDDocument document;

    @SneakyThrows
    public PDF(String pathToFile) {
        this.document = PDDocument.load(new File(pathToFile));
    }

    public Map<String, PDImageXObject> getAllImages() {

        AtomicReference<PDResources> pdResources = new AtomicReference<>();
        AtomicReference<String> imageName = new AtomicReference<>();

        return Streams.stream(document.getPages().iterator())
                .flatMap(page -> {

                    pdResources.set(page.getResources());
                    return Streams.stream(page.getResources().getXObjectNames());
                })
                .filter(name -> pdResources.get().isImageXObject(name))
                .peek(name -> imageName.set(name.getName()))
                .map(name -> {
                    try {
                        return (PDImageXObject) pdResources.get().getXObject(name);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toMap(key -> imageName.get(), value -> value));
    }


    public List<PDImageXObject> getListAllImages() {

        AtomicReference<PDResources> pdResources = new AtomicReference<>();

        return Streams.stream(document.getPages().iterator())
                .flatMap(page -> {
                    pdResources.set(page.getResources());
                    return Streams.stream(page.getResources().getXObjectNames());
                })
                .filter(name -> pdResources.get().isImageXObject(name))
                .map(name -> {
                    try {
                        return (PDImageXObject) pdResources.get().getXObject(name);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
