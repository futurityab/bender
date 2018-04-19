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
 * Copyright 2018 Nextdoor.com, Inc
 *
 */

package com.nextdoor.bender.operation.json.key;

import java.util.regex.Pattern;

import com.nextdoor.bender.config.AbstractConfig;
import com.nextdoor.bender.operation.Operation;
import com.nextdoor.bender.operation.OperationFactory;

/**
 * Create a {@link KeyNameReplacementOperation}.
 */
public class KeyNameReplacementOperationFactory implements OperationFactory {

  private KeyNameReplacementOperationConfig config;
  private Pattern pattern;

  @Override
  public Operation newInstance() {


    return new KeyNameReplacementOperation(this.pattern, this.config.getReplacement(),
        this.config.getDrop());
  }

  @Override
  public Class<KeyNameReplacementOperation> getChildClass() {
    return KeyNameReplacementOperation.class;
  }

  @Override
  public void setConf(AbstractConfig config) {
    this.config = (KeyNameReplacementOperationConfig) config;
    this.pattern = Pattern.compile(this.config.getRegex());
  }
}