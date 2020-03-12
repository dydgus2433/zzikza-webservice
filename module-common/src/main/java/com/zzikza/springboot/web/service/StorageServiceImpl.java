package com.zzikza.springboot.web.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.zzikza.springboot.web.domain.FileAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {

    public static final char EXTENSION_SEPARATOR = '.';
    private static final int NOT_FOUND = -1;
    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

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

    public static int indexOfLastSeparator(final String filename) {
        if (filename == null) {
            return NOT_FOUND;
        }
        final int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        final int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    @Override
    public void init() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public FileAttribute fileUpload(MultipartFile file) throws IOException, InterruptedException {

        String fileName = file.getOriginalFilename();
        String ext = getExtension(fileName);
        String uid = UUID.randomUUID().toString();
        String randomName = uid + "." + ext;

        try {
            //저장하고 옮기는것보다는 바로 보내는게 나은듯.
            /*
            TransferManager tm = TransferManagerBuilder
                    .standard()
                    .withS3Client(s3Client)
                    .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
                    .build();
            File tempFile = new File("C:\\home\\zzikza\\studio\\upload\\"+randomName);
            file.transferTo(tempFile);
            Upload upload = tm.upload(bucket, FILE_PATH + randomName, tempFile);
            upload.waitForCompletion();
            tempFile.deleteOnExit();
            */
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult = s3Client.putObject(
                    new PutObjectRequest(bucket, FILE_PATH + randomName, file.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (AmazonClientException | IOException e) {
            throw e;
        }
        FileAttribute fileAttribute = new FileAttribute();
        fileAttribute.setFileName(randomName);
        fileAttribute.setFileExt(ext);
        fileAttribute.setFileSourceName(file.getOriginalFilename());
        fileAttribute.setFilePath(s3Client.getUrl(bucket, FILE_PATH + randomName).toString());
        fileAttribute.setFileSize(file.getSize());
        return fileAttribute;
    }

    public String getExtension(final String filename) {
        if (filename == null) {
            return null;
        }
        final int index = indexOfExtension(filename);
        if (index == NOT_FOUND) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public int indexOfExtension(final String filename) {
        if (filename == null) {
            return NOT_FOUND;
        }
        final int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        final int lastSeparator = indexOfLastSeparator(filename);
        return lastSeparator > extensionPos ? NOT_FOUND : extensionPos;
    }
}
