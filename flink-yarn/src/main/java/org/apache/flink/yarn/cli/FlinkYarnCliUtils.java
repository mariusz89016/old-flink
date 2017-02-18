/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink.yarn.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlinkYarnCliUtils {

	public static Map<String, String> retrieveDynamicProperties(CommandLine commandLine, Option dynamicPropertiesOption) {
		if (commandLine.hasOption(dynamicPropertiesOption.getOpt())) {
			return getDynamicProperties(commandLine.getOptionValues(dynamicPropertiesOption.getOpt()));
		}
		else {
			return Collections.emptyMap();
		}
	}

	private static Map<String, String> getDynamicProperties(String[] dynamicProperties) {
		if (dynamicProperties != null && dynamicProperties.length > 0) {
			Map<String, String> map = new HashMap<>();

			for (String property : dynamicProperties) {
				if (property == null) {
					continue;
				}

				String[] kv = property.split("=");
				if (kv.length >= 2 && kv[0] != null && kv[1] != null && kv[0].length() > 0) {
					map.put(kv[0], kv[1]);
				}
			}
			return map;
		}
		else {
			return Collections.emptyMap();
		}
	}

	/** private constructor to prevent instantiation */
	private FlinkYarnCliUtils() {}
}
