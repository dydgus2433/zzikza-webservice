package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.editor.EditorFile;
import com.zzikza.springboot.web.domain.editor.EditorFileRepository;
import com.zzikza.springboot.web.dto.EditorFileDto;
import com.zzikza.springboot.web.dto.EditorFileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class EditorFileService {

    private final EditorFileRepository editorFileRepository;

    private final StorageServiceImpl storageService;

    public EditorFileResponseDto save(MultipartFile file) throws IOException, InterruptedException {
        FileAttribute fileAttribute = storageService.fileUpload(file);
        fileAttribute.setFileOrder(0);
        EditorFileDto dto = new EditorFileDto(fileAttribute);
        EditorFile editorFile = dto.toEntity();
        editorFileRepository.save(editorFile);

        return new EditorFileResponseDto(editorFile);
    }
    //저장

    //삭제는 현재 미구현 (언제 삭제할지도 의문.. 그리고 복사해서 붙여놓은 부분때문에 한파일 지워지면 다른 본문 지워질 우려있음.)
}
