/*
 * Copyright 2014, Leanplum, Inc. All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.leanplum.internal;

import com.leanplum.Leanplum;

import java.util.Map;

public class RequestFactory {
  public static RequestFactory defaultFactory;

  public synchronized static RequestFactory getInstance() {
    if (defaultFactory == null) {
      defaultFactory = new RequestFactory();
    }
    return defaultFactory;
  }

  public Request createRequest(
      String httpMethod, String apiMethod, Map<String, Object> params) {
    Leanplum.countAggregator().incrementCount("createRequest");
    return new Request(httpMethod, apiMethod, params);
  }

  LPRequesting createGetForApiMethod(String apiMethod, Map<String, Object> params) {
    if (shouldReturnLPRequestClass()) {
      return LPRequest.get(apiMethod, params);
    }
    return Request.get(apiMethod, params);
  }

  LPRequesting createPostForApiMethod(String apiMethod, Map<String, Object> params) {
    if (shouldReturnLPRequestClass()) {
      return LPRequest.post(apiMethod, params);
    }
    return Request.post(apiMethod, params);
  }

  Boolean shouldReturnLPRequestClass() {
    return Leanplum.featureFlagManager().isFeatureFlagEnabled(Leanplum.featureFlagManager().FEATURE_FLAG_REQUEST_REFACTOR);
  }

}
