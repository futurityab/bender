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
 */

package com.nextdoor.bender.ipc.sumologic;

import com.nextdoor.bender.ipc.TransportSerializer;
import com.nextdoor.bender.ipc.generic.GenericTransportSerializer;
import com.nextdoor.bender.ipc.http.AbstractHttpTransportFactory;
import com.nextdoor.bender.ipc.http.HttpTransport;

/**
 * Creates a {@link HttpTransport} from a {@link SumoLogicTransportConfig}.
 */
public class SumoLogicTransportFactory extends AbstractHttpTransportFactory {
  @Override
  protected String getPath() {
    SumoLogicTransportConfig config = (SumoLogicTransportConfig) super.config;
    return "/receiver/v1/http/" + config.getAuthToken();
  }

  @Override
  protected TransportSerializer getSerializer() {
    return new GenericTransportSerializer('\n');
  }
}
