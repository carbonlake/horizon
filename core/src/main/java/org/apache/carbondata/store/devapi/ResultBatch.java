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

package org.apache.carbondata.store.devapi;

import org.apache.carbondata.common.annotations.InterfaceAudience;
import org.apache.carbondata.common.annotations.InterfaceStability;

@InterfaceAudience.Developer("Integration")
@InterfaceStability.Unstable
public interface ResultBatch<T> {

  /**
   * Return true if the result is returned in columnar batch, otherwise is row by row.
   * By default, it is columnar batch.
   */
  default boolean isColumnar() {
    return true;
  }

  /**
   * Return true if there is more elements in this batch.
   */
  boolean hasNext();

  /**
   * Return next item.
   * If {@link #isColumnar()} return true, there is only one element in this batch
   * which is {@link ColumnarBatch}, otherwise, this batch return row by row, caller
   * should call next() until no element left.
   */
  T next();
}
