package com.zzikza.springboot.web.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.zzikza.springboot.web.domain.FileAttribute;
import com.zzikza.springboot.web.util.FileNameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {


    private AmazonS3 s3Client;

    @Value("${service.fileupload.basedirectory}")
    private String FILE_PATH;

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
    public void delete(String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    @Override
    public FileAttribute fileUpload(MultipartFile file) throws IOException, AmazonClientException {
        FileAttribute fileAttribute = getMultipartFileToFileAttribute(file);

        String randomName = getRandomFileName(file);
        fileAttribute.setFileName(randomName);
        try {
            String urlPath = putObjectByS3Client(file, randomName);
            fileAttribute.setFilePath(urlPath);
        } catch (AmazonClientException | IOException e) {
            throw e;
        }
        return fileAttribute;
    }

    private String putObjectByS3Client(MultipartFile file, String randomName) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        s3Client.putObject(new PutObjectRequest(bucket, FILE_PATH + randomName, file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, FILE_PATH + randomName).toString();
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

}
