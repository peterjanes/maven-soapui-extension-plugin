/*
 * Copyright 2012 Thomas Bouffard (redfish4ktc)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ktc.soapui.maven.extension;

import com.eviware.soapui.SoapUIProMockServiceRunner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

public class MockServiceMojo extends AbstractSoapuiRunnerMojo {
    private String mockService;
    private String path;
    private String port;
    private boolean noBlock;

    @Override
    public void performRunnerExecute() throws MojoExecutionException, MojoFailureException {
        SoapUIProMockServiceRunner runner = new SoapUIProMockServiceRunner("SoapUI Pro Maven2 MockService Runner");

        runner.setProjectFile(projectFile);

        if (mockService != null) {
            runner.setMockService(mockService);
        }
        if (path != null) {
            runner.setPath(path);
        }
        if (port != null) {
            runner.setPort(port);
        }
        if (settingsFile != null) {
            runner.setSettingsFile(settingsFile);
        }
        runner.setBlock(!noBlock);
        runner.setSaveAfterRun(saveAfterRun);

        if (projectPassword != null) {
            runner.setProjectPassword(projectPassword);
        }
        if (settingsPassword != null) {
            runner.setSoapUISettingsPassword(settingsPassword);
        }
        if (globalProperties != null) {
            runner.setGlobalProperties(globalProperties);
        }
        if (projectProperties != null)
            runner.setProjectProperties(projectProperties);
        if (this.soapuiProperties != null && !this.soapuiProperties.isEmpty()) {
            for (Object keyObject : this.soapuiProperties.keySet()) {
                String key = (String) keyObject;
                getLog().info("Setting " + key + " value " + this.soapuiProperties.getProperty(key));
                System.setProperty(key, this.soapuiProperties.getProperty(key));
            }
        }

        try {
            runner.run();
        } catch (Exception e) {
            getLog().debug(e);
            throw new MojoFailureException("SoapUI has errors: " + e.getMessage(), e);
        }
    }
}
