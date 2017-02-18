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
package org.apache.flink.yarn;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.apache.flink.yarn.cli.FlinkYarnCliUtils.retrieveDynamicProperties;

public class FlinkYarnCliUtilsTest {

	@Test
	public void testDynamicProperties() throws IOException {
		Options options = new Options();
		Option dynamicProperty = new Option("D", true, "Dynamic properties");
		options.addOption(dynamicProperty);
		options.addOption(new Option("N", true, "Non important option"));

		CommandLineParser parser = new DefaultParser();
		CommandLine commandLine = null;
		try {
			commandLine = parser.parse(options, new String[]{"-D", "key1=value1", "-D", "key2=value2", "-N", "option1"});
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail("Parsing failed with " + e.getMessage());
		}

		final Map<String, String> dynamicProperties = retrieveDynamicProperties(commandLine, dynamicProperty);

		Assert.assertEquals(2, dynamicProperties.size());
		Assert.assertEquals("value1", dynamicProperties.get("key1"));
		Assert.assertEquals("value2", dynamicProperties.get("key2"));
	}
}
