package br.edu.ufcg.virtus.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

@Configuration
@Profile({"dev", "homolog", "prod", "infra"})
public class AWSConfig {

    @Bean
    public AmazonS3 amazonS3() {
        final String key = System.getenv("IAM_USER_S3_KEY");
        final String secret = System.getenv("IAM_USER_S3_SECRET");

        final AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(key, secret)))
                .build();
        return amazonS3;
    }

    @Bean
    public TransferManager transferManager(@Autowired AmazonS3 s3Client) {
        final TransferManager transferManager = TransferManagerBuilder.standard()
                .withS3Client(s3Client)
                .build();
        return transferManager;
    }

}
