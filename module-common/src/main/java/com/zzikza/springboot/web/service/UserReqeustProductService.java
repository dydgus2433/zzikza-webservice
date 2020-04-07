package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.product.ProductFile;
import com.zzikza.springboot.web.domain.request.*;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.FileResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
import com.zzikza.springboot.web.dto.UserRequestProductRequestDto;
import com.zzikza.springboot.web.dto.UserRequestProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserReqeustProductService {
    private final StorageServiceImpl storageService;
    private final UserRequestProductFileTempRepository requestProductFileTempRepository;
    private final UserRequestProductFileRepository userRequestProductFileRepository;
    private final UserRequestProductRepository userRequestProductRepository;
    private final StudioRepository studioRepository;
    private final UserRequestRepository userRequestRepository;
    @Value("${service.fileupload.basedirectory}")
    private String FILE_PATH;
    @Value("${service.fileupload.thumb.directory}")
    private String FILE_THUMB_PATH;
    @Value("${service.fileupload.midsize.directory}")
    private String FILE_MIDSIZE_PATH;
    @Value("${service.fileupload.large.directory}")
    private String FILE_LARGE_PATH;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public FileResponseDto saveProductImageFileTemp(String tempKey, MultipartFile file) throws IOException {
        /*
        스튜디오로
        파일들 찾고 사이즈 + 1을 fileOrder로 세팅해야함.
         */
        FileAttribute fileAttribute = storageService.fileUpload(file, "request_product_temp");
        fileAttribute.setFileOrder(requestProductFileTempRepository.findAllByTempKey(tempKey).size() + 1);
        UserRequestProductFileTemp productFileTemp = UserRequestProductFileTemp.builder().fileAttribute(fileAttribute).tempKey(tempKey).build();
        requestProductFileTempRepository.save(productFileTemp);
        return new FileResponseDto(productFileTemp);
    }

    @Transactional
    public void saveFileTempOrders(String tempKey, String index) {
        List<UserRequestProductFileTemp> productFileTempList = requestProductFileTempRepository.findAllByTempKey(tempKey);
        List<String> indexes = Arrays.asList(index.split(","));

        for (UserRequestProductFileTemp productFileTemp : productFileTempList) {
            for (int j = 0; j < indexes.size(); j++) {
                if (productFileTemp.getId().equals(indexes.get(j))) {
                    productFileTemp.setFileOrder(j + 1);
                }
            }
        }
    }

    public void deleteProductFileTempById(String id) {
        UserRequestProductFileTemp file = requestProductFileTempRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
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
        requestProductFileTempRepository.deleteById(id);
    }


    public UserRequestProductResponseDto saveUserRequestProductTempToReal(UserRequestProductRequestDto productRequestDto, String tempKey, StudioResponseDto studioResponseDto) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));
        UserRequestProduct product = productRequestDto.toEntity();
        List<UserRequestProductFileTemp> productFileTemps = requestProductFileTempRepository.findAllByTempKey(tempKey);
        for (UserRequestProductFileTemp productFileTemp : productFileTemps) {
            UserRequestProductFile productFile = new UserRequestProductFile(productFileTemp);
            product.addRequestProductFile(productFile);
        }
        UserRequest userRequest = userRequestRepository.findById(productRequestDto.getReqId()).orElseThrow(() -> new IllegalArgumentException("요청한 글이 없습니다."));
        userRequest.addUserRequestProduct(product);
        userRequestProductRepository.save(product);

        studio.addUserRequestProduct(product);
        return new UserRequestProductResponseDto(product);
    }

    @Transactional
    public FileResponseDto saveUserRequestProductImageFile(String id, MultipartFile file) throws IOException {
        /*
        스튜디오로
        파일들 찾고 사이즈 + 1을 fileOrder로 세팅해야함.
         */
        UserRequestProduct product = userRequestProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
        FileAttribute fileAttribute = storageService.fileUpload(file, "request_product");
        fileAttribute.setFileOrder(userRequestProductFileRepository.findAllByUserRequestProduct(product).size() + 1);

        UserRequestProductFile productFile = UserRequestProductFile.builder().fileAttribute(fileAttribute).build();
        product.addRequestProductFile(productFile);
        userRequestProductFileRepository.save(productFile);
        userRequestProductRepository.save(product);
        return new FileResponseDto(productFile);
    }


    @Transactional
    public void saveFileOrders(String id, String index) {
        UserRequestProduct product = userRequestProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        List<UserRequestProductFile> productFileList = userRequestProductFileRepository.findAllByUserRequestProduct(product);
        List<String> indexes = Arrays.asList(index.split(","));

        for (UserRequestProductFile productFileTemp : productFileList) {
            for (int j = 0; j < indexes.size(); j++) {
                if (productFileTemp.getId().equals(indexes.get(j))) {
                    productFileTemp.setFileOrder(j + 1);
                }
            }
        }
    }

    public UserRequestProductResponseDto saveUserRequestProduct(UserRequestProductRequestDto productRequestDto, StudioResponseDto studioResponseDto) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));
//        UserRequestProduct product = productRequestDto.toEntity();
//        UserRequest userRequest = userRequestRepository.findById(productRequestDto.getReqId()).orElseThrow(() -> new IllegalArgumentException("요청한 글이 없습니다."));
//        userRequest.addUserRequestProduct(product);
//        userRequestProductRepository.save(product);
        UserRequestProduct product = userRequestProductRepository.findById(productRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("요청한 글이 없습니다."));
        product.update(productRequestDto);
        userRequestProductRepository.save(product);
        return new UserRequestProductResponseDto(product);
    }

    public void deleteProductFileById(String id) {
//        Product product = productRepository.findById(prdId).orElseThrow(()-> new IllegalArgumentException("스튜디오가 없습니다."));


        UserRequestProductFile file = userRequestProductFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
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
        userRequestProductFileRepository.deleteById(id);
    }

//
//    @Transactional
//    public FileResponseDto saveProductImageFile(String prdId, MultipartFile file) throws IOException {
//        /*
//        스튜디오로
//        파일들 찾고 사이즈 + 1을 fileOrder로 세팅해야함.
//         */
//        Product product = requestProductRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
//        FileAttribute fileAttribute = storageService.fileUpload(file, "product");
//        fileAttribute.setFileOrder(requestProductFileRepository.findAllByProduct(product).size() + 1);
//
//        ProductFile productFile = ProductFile.builder().fileAttribute(fileAttribute).product(product).build();
//        product.addProductFile(productFile);
//        requestProductFileRepository.save(productFile);
//        requestProductRepository.save(product);
//        return new FileResponseDto(productFile);
//    }
//
//    @Transactional
//    public void saveFileOrders(String prdId, String index) {
//        Product product = requestProductRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
//
//        List<ProductFile> productFileList = requestProductFileRepository.findAllByProduct(product);
//        List<String> indexes = Arrays.asList(index.split(","));
//
//        for (ProductFile productFileTemp : productFileList) {
//            for (int j = 0; j < indexes.size(); j++) {
//                if (productFileTemp.getId().equals(indexes.get(j))) {
//                    productFileTemp.setFileOrder(j + 1);
//                }
//            }
//        }
//    }
//
//    public void deleteProductFileById(String prdId, String id) {
////        Product product = productRepository.findById(prdId).orElseThrow(()-> new IllegalArgumentException("스튜디오가 없습니다."));
//
//
//        ProductFile file = requestProductFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
//
//        if (!file.getProduct().getId().equals(prdId)) {
//            try {
//                throw new IllegalAccessException("권한이 없습니다.");
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (Objects.nonNull(file.getFileName())) {
//            storageService.delete(FILE_PATH + file.getFileName());
//        }
//        if (Objects.nonNull(file.getFileLargePath())) {
//            storageService.delete(FILE_LARGE_PATH + file.getFileName());
//        }
//        if (Objects.nonNull(file.getFileMidsizePath())) {
//            storageService.delete(FILE_MIDSIZE_PATH + file.getFileName());
//        }
//        if (Objects.nonNull(file.getFileThumbPath())) {
//            storageService.delete(FILE_THUMB_PATH + file.getFileName());
//        }
//        requestProductFileRepository.deleteById(id);
//    }
//
//    public void deleteProduct(String prdId) {
//
//        Product product = requestProductRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));
//
//        requestProductRepository.delete(product);
//
//        for (ProductFile productFile : product.getProductFiles()) {
//            FileAttribute file = productFile.getFile();
//            if (Objects.nonNull(file.getFileName())) {
//                storageService.delete(FILE_PATH + file.getFileName());
//            }
//            if (Objects.nonNull(file.getFileLargePath())) {
//                storageService.delete(FILE_LARGE_PATH + file.getFileName());
//            }
//            if (Objects.nonNull(file.getFileMidsizePath())) {
//                storageService.delete(FILE_MIDSIZE_PATH + file.getFileName());
//            }
//            if (Objects.nonNull(file.getFileThumbPath())) {
//                storageService.delete(FILE_THUMB_PATH + file.getFileName());
//            }
//        }
//    }
//
//    @Transactional
//    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, StudioResponseDto studioResponseDto) {
//        Product product = requestProductRepository.findById(productRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
//        product.update(productRequestDto);
//        String[] keywordIds = productRequestDto.getSplitKeywords();
//        for (String keywordId : keywordIds) {
//            ProductKeyword productKeyword = productKeywordRepository.findById(keywordId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 키워드입니다."));
//            product.addProductKeyword(productKeyword);
//        }
//
////        product.removeProductExhibition();
//        //전시회 적용X인 경우 기존것 삭제
//        List<ProductExhibition> productExhibitions = product.getProductExhbitions();
//        productExhibitionRepository.deleteInBatch(productExhibitions);
//        if (!productRequestDto.getExhId().equals("")) {
//            //모두 삭제하고 다시 추가하는 방법이 있지만 현재는 상품하나당 전시회 일대일 대응이기 때문에
//
//            Exhibition exhibition = exhibitionRepository.findById(productRequestDto.getExhId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전시회입니다."));
//            long count = product.getProductExhbitions().stream().filter((e) -> e.getExhibition().getId().equals(exhibition.getId())).count();
//            if (count == 0) {
//                product.addProductExhibition(exhibition);
//            }
//        }
//
//
//        requestProductRepository.save(product);
//        return new ProductResponseDto(product);
//    }
}
