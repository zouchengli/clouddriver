/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.clouddriver.aws.deploy.validators

import com.netflix.spinnaker.clouddriver.aws.AmazonOperation
import com.netflix.spinnaker.clouddriver.deploy.ValidationErrors
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperations
import com.netflix.spinnaker.clouddriver.aws.deploy.description.RebootInstancesDescription
import org.springframework.stereotype.Component

@AmazonOperation(AtomicOperations.REBOOT_INSTANCES)
@Component("rebootInstancesDescriptionValidator")
class RebootInstancesDescriptionValidator extends AmazonDescriptionValidationSupport<RebootInstancesDescription> {
  @Override
  void validate(List priorDescriptions, RebootInstancesDescription description, ValidationErrors errors) {
    def key = RebootInstancesDescription.class.simpleName
    if (!description.instanceIds) {
      errors.rejectValue("instanceIds", "${key}.instanceIds.empty")
    } else {
      description.instanceIds.each {
        if (!it) {
          errors.rejectValue("instanceIds", "${key}.instanceId.invalid")
        }
      }
    }

    validateRegion(description, description.region, key, errors)
  }
}
