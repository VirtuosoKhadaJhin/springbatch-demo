package com.nuanyou.merchant.batch.support;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.integration.aws.support.S3Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.remote.session.SharedSessionCapable;
import org.springframework.util.Assert;

/**
 * An Amazon S3 specific {@link SessionFactory} implementation.
 * Also this class implements {@link SharedSessionCapable} around the single instance,
 * since the {@link S3Session} is simple thread-safe wrapper for the {@link AmazonS3}.
 *
 * @author Artem Bilan
 */
public class NYS3SessionFactory implements SessionFactory<S3ObjectSummary>, SharedSessionCapable {

    private final NYS3Session s3Session;

    public NYS3SessionFactory() {
        this(new AmazonS3Client());
    }

    public NYS3SessionFactory(AmazonS3 amazonS3) {
        this(amazonS3, null);
    }

    public NYS3SessionFactory(AmazonS3 amazonS3, ResourceIdResolver resourceIdResolver) {
        Assert.notNull(amazonS3, "'amazonS3' must not be null.");
        this.s3Session = new NYS3Session(amazonS3, resourceIdResolver);
    }

    @Override
    public NYS3Session getSession() {
        return this.s3Session;
    }

    @Override
    public boolean isSharedSession() {
        return true;
    }

    @Override
    public void resetSharedSession() {
        // No-op. The S3Session is stateless and can be used concurrently.
    }
}