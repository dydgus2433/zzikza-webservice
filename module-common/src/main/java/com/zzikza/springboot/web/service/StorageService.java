package com.zzikza.springboot.web.service;
import com.zzikza.springboot.web.domain.FileAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();

//    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    FileAttribute fileUpload(MultipartFile file) throws IOException, InterruptedException;
}
