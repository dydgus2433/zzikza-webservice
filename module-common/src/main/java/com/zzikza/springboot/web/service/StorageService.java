package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.FileAttribute;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    Resource loadAsResource(String filePath) throws IOException;

    void delete(String filename);

    FileAttribute fileUpload(MultipartFile file) throws IOException, InterruptedException;
}
