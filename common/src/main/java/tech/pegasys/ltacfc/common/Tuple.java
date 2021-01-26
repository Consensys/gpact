/*
 * Copyright 2020 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package tech.pegasys.ltacfc.common;

public class Tuple<K, V, U> {

  private K first;
  private V second;
  private U third;

  public Tuple(K first, V second){
    this.first = first;
    this.second = second;
    this.third = null;
  }
  public Tuple(K first, V second, U third){
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public K getFirst() {
    return first;
  }

  public V getSecond() {
    return second;
  }

  public U getThird() {
    return third;
  }
}
