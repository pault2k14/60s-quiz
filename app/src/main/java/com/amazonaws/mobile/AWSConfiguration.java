//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.18
//
package com.amazonaws.mobile;

import android.graphics.Color;

import com.amazonaws.mobilehelper.config.AWSMobileHelperConfiguration;
import com.amazonaws.regions.Regions;
import paulbthompson.me.a1960squiz.R;

/**
 * This class defines constants for the developer's resource
 * identifiers and API keys. This configuration should not
 * be shared or posted to any public source code repository.
 */
public class AWSConfiguration {
    // AWS MobileHub user agent string
    public static final String AWS_MOBILEHUB_USER_AGENT =
        "MobileHub 0668003f-b5dd-43fe-bd3f-43e2ab00cd27 aws-my-sample-app-android-v0.18";
    // AMAZON COGNITO
    public static final Regions AMAZON_COGNITO_REGION =
      Regions.fromName("us-east-1");
    public static final String  AMAZON_COGNITO_IDENTITY_POOL_ID =
        "us-east-1:122466fd-0e2b-4442-85e7-440ec9bfc4a8";
    public static final Regions AMAZON_DYNAMODB_REGION =
       Regions.fromName("us-east-1");

    private static final AWSMobileHelperConfiguration helperConfiguration = new AWSMobileHelperConfiguration.Builder()
        .withCognitoRegion(AMAZON_COGNITO_REGION)
        .withCognitoIdentityPoolId(AMAZON_COGNITO_IDENTITY_POOL_ID)
        .build();
    /**
     * @return the configuration for AWSKit.
     */
    public static AWSMobileHelperConfiguration getAWSMobileHelperConfiguration() {
        return helperConfiguration;
    }
}
