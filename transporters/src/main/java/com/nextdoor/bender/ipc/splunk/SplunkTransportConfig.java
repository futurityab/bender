/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright 2017 Nextdoor.com, Inc
 *
 */

package com.nextdoor.bender.ipc.splunk;

import java.io.UnsupportedEncodingException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDefault;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.nextdoor.bender.ipc.TransportConfig;
import com.nextdoor.bender.utils.Passwords;

@JsonTypeName("Splunk")
@JsonSchemaDescription("Writes events to a Splunk HEC endpoint.")
public class SplunkTransportConfig extends TransportConfig {

  @JsonSchemaDescription("Splunk HTTP endpoint hostname.")
  @JsonProperty(required = true)
  private String hostname = null;

  @JsonSchemaDescription("Splunk HTTP endpoint port.")
  @JsonSchemaDefault(value = "443")
  @JsonProperty(required = false)
  @Min(1)
  @Max(65535)
  private Integer port = 443;

  @JsonSchemaDescription("Splunk auth token. If value is kms encrypted prefix with 'KMS='.")
  @JsonProperty(required = true)
  private String authToken = null;

  @JsonSchemaDescription("Splunk data index.")
  @JsonProperty(required = false)
  private String index = null;

  @JsonSchemaDescription("Use SSL connections (certificates are not validated).")
  @JsonSchemaDefault(value = "false")
  @JsonProperty(required = false)
  private Boolean useSSL = false;

  @JsonSchemaDescription("Use GZIP compression on HTTP calls.")
  @JsonSchemaDefault(value = "false")
  @JsonProperty(required = false)
  private Boolean useGzip = false;

  @JsonSchemaDescription("Maximum number of documents in  bulk api call.")
  @JsonSchemaDefault(value = "500")
  @JsonProperty(required = false)
  @Min(500)
  @Max(100000)
  private Integer batchSize = 500;

  @JsonSchemaDescription("Number of retries to make when a put failure occurs.")
  @JsonSchemaDefault(value = "0")
  @JsonProperty(required = false)
  @Min(0)
  @Max(10)
  private Integer retryCount = 0;

  @JsonSchemaDescription("Initial delay between retries. If more than one retries specified exponential backoff is used.")
  @JsonSchemaDefault(value = "1000")
  @JsonProperty(required = false)
  @Min(1)
  @Max(60000)
  private Long retryDelay = 1000l;

  @JsonSchemaDescription("Socket timeout on HTTP connection.")
  @JsonSchemaDefault(value = "40000")
  @JsonProperty(required = false)
  @Min(1000)
  @Max(300000)
  private Integer timeout = 40000;

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getAuthToken() {
    /*
     * Decrypt token using KMS automatically.
     */
    if (this.authToken != null) {
      try {
        return Passwords.getPassword(this.authToken);
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }

    return authToken;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getIndex() {
    return this.index;
  }

  public Boolean isUseSSL() {
    return useSSL;
  }

  public void setUseSSL(Boolean useSSL) {
    this.useSSL = useSSL;
  }

  public Boolean isUseGzip() {
    return useGzip;
  }

  public void setUseGzip(Boolean useGzip) {
    this.useGzip = useGzip;
  }

  public Integer getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(Integer batchSize) {
    this.batchSize = batchSize;
  }

  public Integer getRetryCount() {
    return retryCount;
  }

  public void setRetryCount(Integer retryCount) {
    this.retryCount = retryCount;
  }

  public Long getRetryDelay() {
    return retryDelay;
  }

  public void setRetryDelay(Long retryDelay) {
    this.retryDelay = retryDelay;
  }

  public Integer getTimeout() {
    return timeout;
  }

  public void setTimeout(Integer timeout) {
    this.timeout = timeout;
  }

  @Override
  public Class<?> getFactoryClass() {
    return SplunkTransportFactory.class;
  }
}
