/*
 * Copyright 2020 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"; you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.rest.entity;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.Immutable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the lags associated with a particular state store on a particular host.
 */
@Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateStoreLags {

  private final ImmutableMap<Integer, LagInfoEntity> lagByPartition;

  @JsonCreator
  public StateStoreLags(
      @JsonProperty("lagByPartition") final Map<Integer, LagInfoEntity> lagByPartition) {
    this.lagByPartition = ImmutableMap.copyOf(requireNonNull(lagByPartition, "lagByPartition"));
  }

  public Optional<LagInfoEntity> getLagByPartition(final int partition) {
    return Optional.ofNullable(lagByPartition.get(partition));
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "lagByPartition is ImmutableMap")
  public Map<Integer, LagInfoEntity> getLagByPartition() {
    return lagByPartition;
  }

  public int getSize() {
    return lagByPartition.size();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final StateStoreLags that = (StateStoreLags) o;
    return Objects.equals(lagByPartition, that.lagByPartition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lagByPartition);
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("lagByPartition", lagByPartition)
        .toString();
  }
}
