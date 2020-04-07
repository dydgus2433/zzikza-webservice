package com.zzikza.springboot.web.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;
import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.util.FileNameUtil;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {


    private AmazonS3 s3Client;

    @Value("${service.fileupload.basedirectory}")
    private String FILE_PATH;
    @Value("${service.fileupload.editordirectory}")
    private String FILE_EDITOR_PATH;

    @Value("${service.fileupload.thumb.directory}")
    private String FILE_THUMB_PATH;

    @Value("${service.fileupload.midsize.directory}")
    private String FILE_MIDSIZE_PATH;

    @Value("${service.fileupload.large.directory}")
    private String FILE_LARGE_PATH;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    @Override
    public Resource loadAsResource(String filePath) throws IOException {

        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucket, FILE_PATH + filePath));
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(s3ObjectInputStream);
        Resource resource = new ByteArrayResource(bytes);
        return resource;
    }

    @Override
    public void delete(String filePath) {
        s3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
    }

    @Override
    public FileAttribute fileUpload(MultipartFile file) throws IOException, AmazonClientException {
        FileAttribute fileAttribute = getMultipartFileToFileAttribute(file);

        String randomName = getRandomFileName(file);
        fileAttribute.setFileName(randomName);
        try {
            randomName = FILE_PATH + randomName;
            String urlPath = putObjectByS3ClientWithMultipartFile(file, randomName);
            fileAttribute.setFilePath(urlPath);
        } catch (AmazonClientException | IOException e) {
            throw e;
        }
        return fileAttribute;
    }

    public FileAttribute fileUpload(MultipartFile file, String type) throws IOException, AmazonClientException {
        FileAttribute fileAttribute = getMultipartFileToFileAttribute(file);

        String randomName = getRandomFileName(file);
        fileAttribute.setFileName(randomName);


        String filePath = FILE_PATH + randomName;
        if ("business".equals(type)) {
            filePath = FILE_PATH + "business/" + randomName;
        }
        String urlPath = putObjectByS3ClientWithMultipartFile(file, filePath);
        fileAttribute.setFilePath(urlPath);

        try {
            if ("studio".equals(type)) {
                fileAttribute.setFileThumbPath(makeThumbnail(file, FILE_THUMB_PATH + randomName, 200, 172));
                fileAttribute.setFileMidsizePath(makeThumbnail(file, FILE_MIDSIZE_PATH + randomName, 600, 520));
                fileAttribute.setFileLargePath(makeThumbnail(file, FILE_LARGE_PATH + randomName, 1400, 1000));
            } else if ("product_temp".equals(type)) {
                fileAttribute.setFileThumbPath(makeThumbnail(file, FILE_THUMB_PATH + randomName, 200, 172));
                fileAttribute.setFileMidsizePath(makeThumbnail(file, FILE_MIDSIZE_PATH + randomName, 600, 520));
                fileAttribute.setFileLargePath(makeThumbnail(file, FILE_LARGE_PATH + randomName, 1400, 1000));
            } else if ("product".equals(type)) {
                fileAttribute.setFileThumbPath(makeThumbnail(file, FILE_THUMB_PATH + randomName, 200, 172));
                fileAttribute.setFileMidsizePath(makeThumbnail(file, FILE_MIDSIZE_PATH + randomName, 600, 520));
                fileAttribute.setFileLargePath(makeThumbnail(file, FILE_LARGE_PATH + randomName, 1400, 1000));
            } else if ("request_product_temp".equals(type)) {
                fileAttribute.setFileThumbPath(makeThumbnail(file, FILE_THUMB_PATH + randomName, 200, 172));
                fileAttribute.setFileMidsizePath(makeThumbnail(file, FILE_MIDSIZE_PATH + randomName, 600, 520));
                fileAttribute.setFileLargePath(makeThumbnail(file, FILE_LARGE_PATH + randomName, 1400, 1000));
            } else if ("request_product".equals(type)) {
                fileAttribute.setFileThumbPath(makeThumbnail(file, FILE_THUMB_PATH + randomName, 200, 172));
                fileAttribute.setFileMidsizePath(makeThumbnail(file, FILE_MIDSIZE_PATH + randomName, 600, 520));
                fileAttribute.setFileLargePath(makeThumbnail(file, FILE_LARGE_PATH + randomName, 1400, 1000));
            } else if("business".equals(type)){ //"business"

            }


        } catch (AmazonClientException | IOException e) {
            throw e;
        }
        return fileAttribute;
    }

    private String makeThumbnail(MultipartFile file, String fileName, int dw, int dh) throws IOException {
        String ext = FileNameUtil.getExtension(fileName);
        BufferedImage srcImg = ImageIO.read(file.getInputStream());
        BufferedImage destImg = getCropBufferedImage(srcImg, dw, dh);
        return putObjectByS3ClientWithBufferedImage(destImg, ext, fileName);
    }

    private String putObjectByS3ClientWithBufferedImage(BufferedImage destImg, String fileExt, String pathName) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(destImg, fileExt, os);
        byte[] buffer = os.toByteArray();
        InputStream bis = new ByteArrayInputStream(buffer);

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(buffer.length);

        PutObjectRequest request = new PutObjectRequest(bucket, pathName, bis, meta);
        s3Client.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, pathName).toString();
    }

    private String putObjectByS3ClientWithMultipartFile(MultipartFile file, String randomName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        s3Client.putObject(new PutObjectRequest(bucket, randomName, file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, randomName).toString();
    }

    /**
     * 확장자 포함된 파일 이름 반환
     *
     * @param file
     * @return
     */
    private String getRandomFileName(MultipartFile file) {
        String uid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        String ext = FileNameUtil.getExtension(fileName);
        return uid + "." + ext;

    }

    /**
     * 멀티 파트 파일 어트리뷰트로 전환
     *
     * @param file
     * @return
     */
    private FileAttribute getMultipartFileToFileAttribute(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String ext = FileNameUtil.getExtension(fileName);
        FileAttribute fileAttribute = new FileAttribute();
        fileAttribute.setFileName(file.getOriginalFilename());
        fileAttribute.setFileExt(ext);
        fileAttribute.setFileSourceName(file.getOriginalFilename());
        fileAttribute.setFileSize(file.getSize());
        return fileAttribute;
    }


    private IIOMetadata readJPEGMetadataSafe(ImageReader reader) throws IOException {
        try {
            return reader.getImageMetadata(0);
        } catch (IIOException e) {
//            processWarningOccurred("Could not read metadata metadata JPEG compressed TIFF: " + e.getMessage() + " colors may look incorrect");

            return null;
        }
    }


    private BufferedImage getCropBufferedImage(BufferedImage srcImg, int targetWidth, int targetHeight) throws IOException {
        // 원본 이미지의 너비와 높이 입니다.
        int originWidth = srcImg.getWidth();
        int originHeight = srcImg.getHeight();
        // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
        BufferedImage destImg = null;
        BufferedImage cropImg = null;
        if (targetWidth >= originWidth && targetHeight >= originHeight) {
            cropImg = srcImg;
        } else {
            // 원본 이미지의 너비와 높이 입니다.
            int ow = srcImg.getWidth();
            int oh = srcImg.getHeight();
            // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
            int nw = ow;
            int nh = (ow * targetHeight) / targetWidth;
            // 계산된 높이가 원본보다 높다면 crop이 안되므로
            // 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
            if (nh > oh) {
                nw = (oh * targetWidth) / targetHeight;
                nh = oh;
            }

            // 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
            cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);

//			// crop된 이미지로 썸네일을 생성합니다.
//			destImg = Scalr.resize(cropImg, updateWidth, updateHeight);

        }

        ResampleOp resampleOp = new ResampleOp(targetWidth, targetHeight);
        resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
        destImg = resampleOp.filter(cropImg, null);
        return destImg;
    }


}
