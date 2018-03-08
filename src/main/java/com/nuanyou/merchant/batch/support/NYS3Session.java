package com.nuanyou.merchant.batch.support;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.http.HttpStatus;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class NYS3Session implements Session<S3ObjectSummary> {

    private final AmazonS3 amazonS3;

    private final ResourceIdResolver resourceIdResolver;

    public NYS3Session(AmazonS3 amazonS3) {
        this(amazonS3, null);
    }

    public NYS3Session(AmazonS3 amazonS3, ResourceIdResolver resourceIdResolver) {
        this.resourceIdResolver = resourceIdResolver;
        Assert.notNull(amazonS3, "'amazonS3' must not be null.");
        this.amazonS3 = amazonS3;
    }

    @Override
    public S3ObjectSummary[] list(String path) throws IOException {
        Assert.hasText(path, "'path' must not be empty String.");
        String[] bucketPrefix = path.split("/");
        Assert.state(bucketPrefix.length > 0 && bucketPrefix[0].length() >= 3,
                "S3 bucket name must be at least 3 characters long.");

        String bucket = resolveBucket(bucketPrefix[0]);

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucket);
        if (bucketPrefix.length > 1) {
            listObjectsRequest.setPrefix(bucketPrefix[1]);
        }

		/*
		For listing objects, Amazon S3 returns up to 1,000 keys in the response.
		If you have more than 1,000 keys in your bucket, the response will be truncated.
		You should always check for if the response is truncated.
		*/
        ObjectListing objectListing;
        List<S3ObjectSummary> objectSummaries = new ArrayList<>();
        do {
            objectListing = this.amazonS3.listObjects(listObjectsRequest);
            objectSummaries.addAll(objectListing.getObjectSummaries());
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        }
        while (objectListing.isTruncated());

        return objectSummaries.toArray(new S3ObjectSummary[objectSummaries.size()]);
    }

    private String resolveBucket(String bucket) {
        if (this.resourceIdResolver != null) {
            return this.resourceIdResolver.resolveToPhysicalResourceId(bucket);
        }
        else {
            return bucket;
        }
    }

    @Override
    public String[] listNames(String path) throws IOException {
        String[] bucketPrefix = path.split("/");
        Assert.state(bucketPrefix.length > 0 && bucketPrefix[0].length() >= 3,
                "S3 bucket name must be at least 3 characters long.");

        String bucket = resolveBucket(bucketPrefix[0]);

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucket);
        if (bucketPrefix.length > 1) {
            listObjectsRequest.setPrefix(bucketPrefix[1]);
        }

		/*
		For listing objects, Amazon S3 returns up to 1,000 keys in the response.
		If you have more than 1,000 keys in your bucket, the response will be truncated.
		You should always check for if the response is truncated.
		*/
        ObjectListing objectListing;
        List<String> names = new ArrayList<>();
        do {
            objectListing = this.amazonS3.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                names.add(objectSummary.getKey());
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        }
        while (objectListing.isTruncated());

        return names.toArray(new String[names.size()]);
    }

    @Override
    public boolean remove(String path) throws IOException {
        String[] bucketKey = splitPathToBucketAndKey(path);
        this.amazonS3.deleteObject(bucketKey[0], bucketKey[1]);
        return true;
    }

    @Override
    public void rename(String pathFrom, String pathTo) throws IOException {
        String[] bucketKeyFrom = splitPathToBucketAndKey(pathFrom);
        String[] bucketKeyTo = splitPathToBucketAndKey(pathTo);
        CopyObjectRequest copyRequest = new CopyObjectRequest(bucketKeyFrom[0], bucketKeyFrom[1],
                bucketKeyTo[0], bucketKeyTo[1]);
        this.amazonS3.copyObject(copyRequest);

        //Delete the source
        this.amazonS3.deleteObject(bucketKeyFrom[0], bucketKeyFrom[1]);
    }

    @Override
    public void read(String source, OutputStream outputStream) throws IOException {
        String[] bucketKey = splitPathToBucketAndKey(source);
        S3Object s3Object = this.amazonS3.getObject(bucketKey[0], bucketKey[1]);
        S3ObjectInputStream objectContent = s3Object.getObjectContent();
        try {
            StreamUtils.copy(objectContent, outputStream);
        }
        finally {
            objectContent.close();
        }
    }

    @Override
    public void write(InputStream inputStream, String destination) throws IOException {
        Assert.notNull(inputStream, "'inputStream' must not be null.");
        String[] bucketKey = splitPathToBucketAndKey(destination);
        this.amazonS3.putObject(bucketKey[0], bucketKey[1], inputStream, new ObjectMetadata());
    }

    @Override
    public void append(InputStream inputStream, String destination) throws IOException {
        throw new UnsupportedOperationException("The 'append' operation isn't supported by the Amazon S3 protocol.");
    }

    @Override
    public boolean mkdir(String directory) throws IOException {
        this.amazonS3.createBucket(directory);
        return true;
    }

    @Override
    public boolean rmdir(String directory) throws IOException {
        this.amazonS3.deleteBucket(resolveBucket(directory));
        return true;
    }

    @Override
    public boolean exists(String path) throws IOException {
        String[] bucketKey = splitPathToBucketAndKey(path);
        try {
            this.amazonS3.getObjectMetadata(bucketKey[0], bucketKey[1]);
        }
        catch (AmazonS3Exception e) {
            if (HttpStatus.SC_NOT_FOUND == e.getStatusCode()) {
                return false;
            }
            else {
                throw e;
            }
        }
        return true;
    }

    @Override
    public InputStream readRaw(String source) throws IOException {
        String[] bucketKey = splitPathToBucketAndKey(source);
        S3Object s3Object = this.amazonS3.getObject(bucketKey[0], bucketKey[1]);
        return s3Object.getObjectContent();
    }

    @Override
    public void close() {
        // No-op. This session is just direct wrapper for the AmazonS3
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public boolean finalizeRaw() throws IOException {
        return true;
    }

    @Override
    public Object getClientInstance() {
        return this.amazonS3;
    }

    protected String[] splitPathToBucketAndKey(String path) {
        Assert.hasText(path, "'path' must not be empty String.");
        String[] bucketKey = new String[2];
        int index = path.indexOf("/");
        Assert.state(index > 0, "'path' must in pattern [BUCKET/KEY].");
        bucketKey[0] = path.substring(0, index);
        bucketKey[1] = path.substring(index + 1);
        Assert.state(bucketKey[0].length() >= 3, "S3 bucket name must be at least 3 characters long.");
        bucketKey[0] = resolveBucket(bucketKey[0]);
        return bucketKey;
    }


}
