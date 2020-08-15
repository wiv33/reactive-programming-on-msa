package org.psawesome;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 15. Saturday
 */
@Service
@RequiredArgsConstructor
public class ImageService {

  public static final String UPLOAD_ROOT = "upload-dir";
  private final ResourceLoader resourceLoader;

  public Flux<Image> findAllImages() {
    try {
      return Flux.fromIterable(Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
              .map(path -> new Image(path.hashCode(), path.getFileName().toString()));
    } catch (IOException e) {
      return Flux.empty();
    }
  }

  public Mono<Resource> findOneImage(String filename) {
    return Mono.fromSupplier(() -> resourceLoader
            .getResource(String.format("file:%s/%s", UPLOAD_ROOT, filename))
    );
  }

  public Mono<Void> createImage(Flux<FilePart> files) {
    return files.flatMap(file -> file
            .transferTo(Paths.get(UPLOAD_ROOT, file.filename()).toFile()))
            .then();
  }

  public Mono<Void> deleteImage(String filename) {
    return Mono.fromRunnable(() -> {
      try {
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }


  @Bean
  CommandLineRunner setUp() {
    return args -> {
      FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

      Files.createDirectory(Paths.get(UPLOAD_ROOT));

      FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/learning.jpg"));
      FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/mybody.jpg"));
      FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/psawesome.jpg"));
    };
  }

}
