package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.editor.EditorFile;
import com.zzikza.springboot.web.domain.editor.EditorFileRepository;
import com.zzikza.springboot.web.domain.product.ProductFile;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioFile;
import com.zzikza.springboot.web.domain.studio.StudioFileRepository;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.EditorFileDto;
import com.zzikza.springboot.web.dto.EditorFileResponseDto;
import com.zzikza.springboot.web.dto.FileResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileService {

    @PersistenceContext
    private EntityManager em;

    private final EditorFileRepository editorFileRepository;
    private final StudioFileRepository studioFileRepository;
    private final StudioRepository studioRepository;

    private final StorageServiceImpl storageService;

    public FileResponseDto saveEditorImageFile(MultipartFile file) throws IOException, InterruptedException {
        FileAttribute fileAttribute = storageService.fileUpload(file);
        fileAttribute.setFileOrder(0);
        EditorFileDto dto = new EditorFileDto(fileAttribute);
        EditorFile editorFile = dto.toEntity();
        editorFileRepository.save(editorFile);

        return new FileResponseDto(editorFile);
    }

    @Transactional
    public FileResponseDto saveStudioImageFile(StudioResponseDto studioResponseDto, MultipartFile file) throws IOException {

        /*
        스튜디오로
        파일들 찾고 사이즈 + 1을 fileOrder로 세팅해야함.
         */
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(()-> new IllegalArgumentException("스튜디오 정보가 없습니다."));
        FileAttribute fileAttribute = storageService.fileUpload(file, "studio");
        fileAttribute.setFileOrder(studioFileRepository.findAllByStudio(studio).size()+1);
        StudioFile studioFileDto = new StudioFile(fileAttribute);
        em.persist(studioFileDto);
        studio.addStudioFile(studioFileDto);
        em.flush();
        return new FileResponseDto(studioFileDto);
    }



    //삭제는 현재 미구현 (언제 삭제할지도 의문.. 그리고 복사해서 붙여놓은 부분때문에 한파일 지워지면 다른 본문 지워질 우려있음.)
}
