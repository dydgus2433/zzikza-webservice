package com.zzikza.springboot.web.service;


import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.dto.StudioBoardRequestDto;
import com.zzikza.springboot.web.dto.StudioBoardResponseDto;
import com.zzikza.springboot.web.dto.StudioRequestDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class StudioService {


    private final StudioRepository studioRepository;
    private final StudioBoardRepository studioBoardRepository;
    private final StudioBoardFileRepository studioBoardFileRepository;
    private final StorageServiceImpl storageService;

    public StudioResponseDto findByStudioIdAndPassword(StudioRequestDto params) {
        String stdoId = (String) params.getStudioId();
        String encPw = params.getEncodingPassword();
        return new StudioResponseDto(studioRepository.findByStudioIdAndPassword(stdoId, encPw).orElseThrow(()-> new IllegalArgumentException("회원정보가 맞지 않습니다.")));
    }

    public StudioResponseDto findById(StudioRequestDto params) {
        return new StudioResponseDto(studioRepository.findByStudioId(params.getStudioId()).orElseThrow(()-> new IllegalArgumentException("회원정보가 맞지 않습니다.")));

    }

    @Transactional
    public StudioBoardResponseDto saveStudioBoardWithFiles(StudioBoardRequestDto studioBoardDto, MultipartRequest httpServletRequest) throws IOException, InterruptedException {
        Studio studio = studioRepository.findById(studioBoardDto.getStudioSeq()).orElseThrow(()-> new IllegalArgumentException("권한이 없습니다."));

        StudioBoard studioBoard = studioBoardDto.toEntity();

        for(int i =0; i < studioBoardDto.getFileLength(); i++) {
            try{
                MultipartFile file = httpServletRequest.getFiles("files["+i+"]").get(0);
                FileAttribute fileAttribute = storageService.fileUpload(file);
                fileAttribute.setFileOrder(i);
                //가져와서 바로 만들어 파일로
                StudioBoardFile studioBoardFile = new StudioBoardFile(fileAttribute);
                studioBoard.addStudioBoardFile(studioBoardFile);
                studioBoardFileRepository.save(studioBoardFile);
            }catch (IOException e){
                throw new IOException("파일업로드에 실패했습니다.");
            }catch (InterruptedException e){
                throw e;
            }
        }
        studioBoardRepository.save(studioBoard);
        studio.addStudioBoard(studioBoard);

        studioRepository.save(studio);
//        studioBoardRepository.save(studioBoard);


        /*
                파라미터 받고

                세션에서 studio id 가져오고

                List<MultiPartFile> 받고 (그전에 일단 하나만 받아봐)

                파일업로드 하고 저장하고 반환값. 받아와

                저장할떄는 save()하고 return 하면 될듯?
             */

        return new StudioBoardResponseDto(studioBoard);
    }
}
