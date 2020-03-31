package com.zzikza.springboot.web.service;

import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.domain.exhibition.Exhibition;
import com.zzikza.springboot.web.domain.exhibition.ExhibitionRepository;
import com.zzikza.springboot.web.domain.product.*;
import com.zzikza.springboot.web.domain.studio.Studio;
import com.zzikza.springboot.web.domain.studio.StudioRepository;
import com.zzikza.springboot.web.dto.FileResponseDto;
import com.zzikza.springboot.web.dto.ProductRequestDto;
import com.zzikza.springboot.web.dto.ProductResponseDto;
import com.zzikza.springboot.web.dto.StudioResponseDto;
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
public class ProductService {
    private final StorageServiceImpl storageService;
    private final ProductFileTempRepository productFileTempRepository;
    private final ProductFileRepository productFileRepository;
    private final ProductRepository productRepository;
    private final ProductKeywordRepository productKeywordRepository;
    private final StudioRepository studioRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final ProductExhibitionRepository productExhibitionRepository;
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
        FileAttribute fileAttribute = storageService.fileUpload(file, "product_temp");
        fileAttribute.setFileOrder(productFileTempRepository.findAllByTempKey(tempKey).size() + 1);
        ProductFileTemp productFileTemp = ProductFileTemp.builder().fileAttribute(fileAttribute).tempKey(tempKey).build();
        productFileTempRepository.save(productFileTemp);
        return new FileResponseDto(productFileTemp);
    }

    @Transactional
    public void saveFileTempOrders(String tempKey, String index) {
        List<ProductFileTemp> productFileTempList = productFileTempRepository.findAllByTempKey(tempKey);
        List<String> indexes = Arrays.asList(index.split(","));

        for (ProductFileTemp productFileTemp : productFileTempList) {
            for (int j = 0; j < indexes.size(); j++) {
                if (productFileTemp.getId().equals(indexes.get(j))) {
                    productFileTemp.setFileOrder(j + 1);
                }
            }
        }
    }

    public void deleteProductFileTempById(String id) {
        ProductFileTemp file = productFileTempRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));
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
        productFileTempRepository.deleteById(id);
    }

    public ProductResponseDto saveProduct(ProductRequestDto productRequestDto, String tempKey, StudioResponseDto studioResponseDto) {
        Studio studio = studioRepository.findById(studioResponseDto.getId()).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));
        Product product = productRequestDto.toEntity();
        List<ProductFileTemp> productFileTemps = productFileTempRepository.findAllByTempKey(tempKey);
        for (ProductFileTemp productFileTemp : productFileTemps) {
            ProductFile productFile = new ProductFile(productFileTemp);
            product.addProductFile(productFile);
        }
        productRepository.save(product);
        String[] keywordIds = productRequestDto.getSplitKeywords();
        for (String keywordId : keywordIds) {
            ProductKeyword productKeyword = productKeywordRepository.findById(keywordId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 키워드입니다."));
            product.addProductKeyword(productKeyword);

        }

        Exhibition exhibition = exhibitionRepository.findById(productRequestDto.getExhId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전시회입니다."));
        product.addProductExhibition(exhibition);

        studio.addProudct(product);
        return new ProductResponseDto(product);
    }

    @Transactional
    public FileResponseDto saveProductImageFile(String prdId, MultipartFile file) throws IOException {
        /*
        스튜디오로
        파일들 찾고 사이즈 + 1을 fileOrder로 세팅해야함.
         */
        Product product = productRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
        FileAttribute fileAttribute = storageService.fileUpload(file, "product");
        fileAttribute.setFileOrder(productFileRepository.findAllByProduct(product).size() + 1);

        ProductFile productFile = ProductFile.builder().fileAttribute(fileAttribute).product(product).build();
        product.addProductFile(productFile);
        productFileRepository.save(productFile);
        productRepository.save(product);
        return new FileResponseDto(productFile);
    }

    @Transactional
    public void saveFileOrders(String prdId, String index) {
        Product product = productRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        List<ProductFile> productFileList = productFileRepository.findAllByProduct(product);
        List<String> indexes = Arrays.asList(index.split(","));

        for (ProductFile productFileTemp : productFileList) {
            for (int j = 0; j < indexes.size(); j++) {
                if (productFileTemp.getId().equals(indexes.get(j))) {
                    productFileTemp.setFileOrder(j + 1);
                }
            }
        }
    }

    public void deleteProductFileById(String prdId, String id) {
//        Product product = productRepository.findById(prdId).orElseThrow(()-> new IllegalArgumentException("스튜디오가 없습니다."));


        ProductFile file = productFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 없습니다."));

        if (!file.getProduct().getId().equals(prdId)) {
            try {
                throw new IllegalAccessException("권한이 없습니다.");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

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
        productFileRepository.deleteById(id);
    }

    public void deleteProduct(String prdId) {

        Product product = productRepository.findById(prdId).orElseThrow(() -> new IllegalArgumentException("스튜디오가 없습니다."));

        productRepository.delete(product);

        for (ProductFile productFile : product.getProductFiles()) {
            FileAttribute file = productFile.getFile();
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
        }
    }

    @Transactional
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto, StudioResponseDto studioResponseDto) {
        Product product = productRepository.findById(productRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        product.update(productRequestDto);
        String[] keywordIds = productRequestDto.getSplitKeywords();
        for (String keywordId : keywordIds) {
            ProductKeyword productKeyword = productKeywordRepository.findById(keywordId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 키워드입니다."));
            product.addProductKeyword(productKeyword);
        }

//        product.removeProductExhibition();
        //전시회 적용X인 경우 기존것 삭제
        List<ProductExhibition> productExhibitions = product.getProductExhbitions();
        productExhibitionRepository.deleteInBatch(productExhibitions);
        if (!productRequestDto.getExhId().equals("")) {
            //모두 삭제하고 다시 추가하는 방법이 있지만 현재는 상품하나당 전시회 일대일 대응이기 때문에

            Exhibition exhibition = exhibitionRepository.findById(productRequestDto.getExhId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전시회입니다."));
            long count = product.getProductExhbitions().stream().filter((e) -> e.getExhibition().getId().equals(exhibition.getId())).count();
            if (count == 0) {
                product.addProductExhibition(exhibition);
            }
        }


        productRepository.save(product);
        return new ProductResponseDto(product);
    }
}
