/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.carbondata.store.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.carbondata.common.annotations.InterfaceAudience;
import org.apache.carbondata.core.constants.CarbonCommonConstants;
import org.apache.carbondata.core.util.CarbonProperties;
import org.apache.carbondata.hadoop.CarbonInputSplit;
import org.apache.carbondata.store.devapi.ScanUnit;

/**
 * It contains a block to scan, and a destination worker who should scan it
 */
@InterfaceAudience.Internal
public class BlockScanUnit implements ScanUnit {

  // the data block to scan
  private CarbonInputSplit inputSplit;

  // the worker who should scan this unit
  private Schedulable schedulable;

  public BlockScanUnit() {
  }

  public BlockScanUnit(CarbonInputSplit inputSplit, Schedulable schedulable) {
    this.inputSplit = inputSplit;
    this.schedulable = schedulable;
  }

  public CarbonInputSplit getInputSplit() {
    return inputSplit;
  }

  public Schedulable getSchedulable() {
    return schedulable;
  }

  @Override
  public String[] preferredLocations() {
    if (isTaskLocality()) {
      try {
        return inputSplit.getLocations();
      } catch (IOException e) {
        return new String[0];
      }
    } else {
      return new String[0];
    }
  }

  private static final String CARBON_TASK_LOCALITY = "carbon.task.locality";

  private boolean isTaskLocality() {
    String taskLocality = CarbonProperties.getInstance().getProperty(
        CARBON_TASK_LOCALITY, "true");
    return taskLocality.equalsIgnoreCase("true");
  }

  @Override
  public void write(DataOutput out) throws IOException {
    inputSplit.write(out);
    schedulable.write(out);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    inputSplit = new CarbonInputSplit();
    inputSplit.readFields(in);
    schedulable = new Schedulable();
    schedulable.readFields(in);
  }
}
