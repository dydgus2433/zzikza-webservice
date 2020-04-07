package com.zzikza.springboot.web.service;


import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.enums.EDateStatus;
import com.zzikza.springboot.web.domain.reservation.Reservation;
import com.zzikza.springboot.web.domain.reservation.ReservationRepository;
import com.zzikza.springboot.web.domain.studio.*;
import com.zzikza.springboot.web.dto.*;
import com.zzikza.springboot.web.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class StudioService {
    private final StudioRepository studioRepository;
    private final StudioDetailRepository studioDetailRepository;
    private final StudioBoardRepository studioBoardRepository;
    private final StudioBoardFileRepository studioBoardFileRepository;
    private final StudioFileRepository studioFileRepository;
    private final StudioBusinessFileRepository studioBusinessFileRepository;
    private final StudioHolidayRepository studioHolidayRepository;
    private final StudioKeywordRepository studioKeywordRepository;
    private final StudioKeywordMapRepository studioKeywordMapRepository;
    private final ReservationRepository reservationRepository;
    private final StorageServiceImpl storageService;
    @Value("${service.fileupload.basedirectory}")
    private String FILE_PATH;
    @Value("${service.fileupload.thumb.directory}")
    private String FILE_THUMB_PATH;
    @Value("${service.fileupload.midsize.directory}")
    private String FILE_MIDSIZE_PATH;
    @Value("${service.fileupload.large.directory}")
    private String FILE_LARGE_PATH;

    public StudioResponseDto findByStudioIdAndPassword(StudioRequestDto params) {
        String studioId = (String) params.getStudioId();
        String encPassword = params.getEncodingPassword();
        return new StudioResponseDto(studioRepository.findByStudioIdAndPassword(studioId, encPassword).orElseThrow(() -> new IllegalArgumentException("회원정보가 맞지 않습니다.")));
    }

    public StudioResponseDto findByStudioId(StudioRequestDto params) {
        return new StudioResponseDto(studioRepository.findByStudioId(params.getStudioId()).orElseThrow(() -> new IllegalArgumentException("회원정보가 맞지 않습니다.")));
    }

    public StudioResponseDto findById(String id) {
        return new StudioResponseDto(studioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원정보가 맞지 않습니다.")));
    }

    @Transactional
    public StudioBoardResponseDto saveStudioBoardWithFiles(StudioBoardRequestDto studioBoardDto, MultipartRequest httpServletRequest) throws IOException {
        Studio studio = studioRepository.findById(studioBoardDto.getStudioSeq()).orElseThrow(() -> new IllegalArgumentException("권한이 없습니다."));

        StudioBoard studioBoard = studioBoardDto.toEntity();

        for (int i = 0; i < studioBoardDto.getFileLength(); i++) {
            try {
                MultipartFile file = httpServletRequest.getFiles("files[" + i + "]").get(0);
                FileAttribute fileAttribute = storageService.fileUpload(file);
                fileAttribute.setFileOrder(i);
                //가져와서 바로 만들어 파일로
                StudioBoardFile studioBoardFile = new StudioBoardFile(fileAttribute);
                studioBoard.addStudioBoardFile(studioBoardFile);
                studioBoardFileRepository.save(studioBoardFile);
            } catch (IOException e) {
                throw new IOException("파일업로드에 실패했습니다.");
            }
        }
        studioBoardRepository.save(studioBoard);
        studio.addStudioBoard(studioBoard);

        studioRepository.save(studio);
        return new StudioBoardResponseDto(studioBoard);
    }

    /*
    스튜디오 찾아서 파일 넣고 하는건데 파라미터 받아지는부터 하자
     */
    @Transactional
    public StudioHolidayResponseDto saveHoliday(StudioResponseDto studioResponseDto, StudioHolidayRequestDto holidayDto) throws IllegalAccessException {

        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));

        studio.addStudioHoliday(holidayDto.getEntity());
//        type w value 0

        return new StudioHolidayResponseDto(holidayDto);
    }

    @Transactional
    public StudioHolidayResponseDto deleteHoliday(StudioResponseDto studioResponseDto, StudioHolidayRequestDto params) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));
        StudioHoliday holiday = studioHolidayRepository.findById(params.getId()).orElseThrow(() -> new IllegalArgumentException("해당 날짜가 없습니다."));
        studio.getStudioHolidays().remove(holiday);
        studioHolidayRepository.delete(holiday);
        return new StudioHolidayResponseDto(params);
    }

    public List<StudioHolidayResponseDto> selectStudioHolidays(StudioResponseDto studioResponseDto) {
        List<StudioHoliday> studioHolidays = studioHolidayRepository.findAllByStudio(studioResponseDto.toEntity());
        List<StudioHolidayResponseDto> studioHolidayResponseDtos = new ArrayList<>();
        for (StudioHoliday holiday : studioHolidays) {
            studioHolidayResponseDtos.add(new StudioHolidayResponseDto(holiday));
        }
        return studioHolidayResponseDtos;
    }


    @Transactional
    public List<StudioHolidayResponseDto> selectStudioWeekHolidays(StudioResponseDto studioResponseDto) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));

        List<StudioHoliday> studioHolidays = studioHolidayRepository.findAllByStudioAndDateCode(studio, EDateStatus.W);
        List<StudioHolidayResponseDto> studioHolidayResponseDtos = new ArrayList<>();
        for (StudioHoliday holiday : studioHolidays) {
            studioHolidayResponseDtos.add(new StudioHolidayResponseDto(holiday));
        }
        return studioHolidayResponseDtos;
    }


    @Transactional
    public List<StudioHolidayResponseDto> selectStudioDayHolidays(StudioResponseDto studioResponseDto) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));

        List<StudioHoliday> studioHolidays = studioHolidayRepository.findAllByStudioAndDateCode(studio, EDateStatus.D);
        List<StudioHolidayResponseDto> studioHolidayResponseDtos = new ArrayList<>();
        for (StudioHoliday holiday : studioHolidays) {
            studioHolidayResponseDtos.add(new StudioHolidayResponseDto(holiday));
        }
        return studioHolidayResponseDtos;
    }

    @Transactional
    public void saveFileOrders(StudioResponseDto studioResponseDto, String index) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));

        List<StudioFile> studioFiles = studio.getStudioFiles();
        List<String> indexes = Arrays.asList(index.split(","));
        /*

         */

        for (StudioFile studioFile : studioFiles) {
            for (int j = 0; j < indexes.size(); j++) {
                if (studioFile.getId().equals(indexes.get(j))) {
                    studioFile.setFileOrder(j + 1);
                }
            }
        }

    }

    @Transactional
    public void updateStudioDetail(StudioResponseDto studioResponseDto, StudioDetailRequestDto studioDetailRequestDto) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));

        StudioDetail studioDetail = studio.getStudioDetail();
        studioDetail.update(studioDetailRequestDto);


        System.out.println("end");
    }

    public void updateStudioKeyword(StudioResponseDto studioResponseDto, String keyword) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));


        studioKeywordMapRepository.deleteInBatch(studio.getStudioKeywordMaps());
        String[] iterator = keyword.split(",");
        List<StudioKeyword> addKeywords = studioKeywordRepository.findAllById(Arrays.asList(iterator));
        addKeywords.forEach((addKeyword) -> studio.addStudioKeywordMap(StudioKeywordMap.builder().studioKeyword(addKeyword).build()));
    }

    public void deleteStudioFileById(String id) {
        StudioFile file = studioFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
        if (Objects.nonNull(file.getFileName())) {
            storageService.delete(FILE_PATH + file.getFileName());
        }
        if (Objects.nonNull(file.getFileLargePath())) {
            storageService.delete(FILE_LARGE_PATH + file.getFileName());
        }
        if (Objects.nonNull(file.getFileMidsizePath())) {
            storageService.delete(FILE_MIDSIZE_PATH + file.getFileName());
        }
        if (Objects.nonNull(file.getFileThumbPath())) {
            storageService.delete(FILE_THUMB_PATH + file.getFileName());
        }
        studioFileRepository.deleteById(id);
    }

    public List<ReservationResposeDto> selectReservationYYYYMM(StudioResponseDto studioResponseDto) {
        List<Reservation> reservationRepositoryAll = reservationRepository.findAll();
        List<ReservationResposeDto> reservationResposeDtos = new ArrayList<>();
        for (Reservation reservation : reservationRepositoryAll) {
            reservationResposeDtos.add(new ReservationResposeDto(reservation));
        }
        return reservationResposeDtos;
    }

    @Transactional
    public void updateStudio(StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException {
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 존재하지 않습니다."));
        if (!studio.getPassword().equals(PasswordUtil.getEncriptPassword(studioInfoRequestDto.getPassword(), studio.getStudioId()))) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
        studio.update(studioInfoRequestDto);
    }


    @Transactional
    public void updateStudioPassword(StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto) throws IllegalAccessException {
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 존재하지 않습니다."));
        if (!studio.getPassword().equals(PasswordUtil.getEncriptPassword(studioInfoRequestDto.getPassword(), studio.getStudioId()))) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
        studio.update(studioInfoRequestDto);
    }

    @Transactional
    public void updateStudioBusiness(StudioResponseDto sessionVo, StudioInfoRequestDto studioInfoRequestDto, MultipartFile file) throws IllegalAccessException, IOException {
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 존재하지 않습니다."));
        if (!studio.getPassword().equals(PasswordUtil.getEncriptPassword(studioInfoRequestDto.getPassword(), studio.getStudioId()))) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
        studio.update(studioInfoRequestDto);

        if (!file.isEmpty()) {
            FileAttribute fileAttribute = storageService.fileUpload(file, "business");
            studio.addBusinessFile(new BusinessFile(fileAttribute));
        }

    }

    @Transactional
    public void withdrawalStudio(StudioResponseDto sessionVo) {
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 존재하지 않습니다."));
        studio.withdrawal();
    }

    @Transactional
    public void deleteStudio(StudioResponseDto sessionVo) {
        Studio studio = studioRepository.findById(sessionVo.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 존재하지 않습니다."));
        studioRepository.delete(studio);
    }

    @Transactional
    public void saveStudio(StudioSaveRequestDto dto, MultipartFile file) throws IOException, SQLException {
        Studio studio = dto.toEntity();

        studioRepository.save(studio);
        StudioDetail studioDetail = StudioDetail.builder().studioDescription("")
//                .closeTime(18)
//                .openTime(10)
//                .weekendCloseTime(18)
//                .weekendOpenTime(10)
                .build();
        studio.setStudioDetail(studioDetail);
        studioDetailRepository.save(studioDetail);
        FileAttribute fileAttribute = storageService.fileUpload(file, "business");
        BusinessFile businessFile = BusinessFile.builder().fileAttribute(fileAttribute).build();

        studioBusinessFileRepository.save(businessFile);
        studio.addBusinessFile(businessFile);

    }
}
