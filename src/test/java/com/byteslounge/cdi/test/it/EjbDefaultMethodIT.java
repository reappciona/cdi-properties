/*
 * Copyright 2014 byteslounge.com (Gonçalo Marques).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.byteslounge.cdi.test.it;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.byteslounge.cdi.test.configuration.TestConstants;
import com.byteslounge.cdi.test.it.common.IntegrationTestDeploymentUtils;
import com.byteslounge.cdi.test.it.common.IntegrationTestDeploymentUtils.DeploymentClassAppenderFactory;
import com.byteslounge.cdi.test.utils.MessageBundleUtils;

/**
 * Integration Test
 * 
 * @author Gonçalo Marques
 * @since 1.0.0
 */
@RunWith(Arquillian.class)
public class EjbDefaultMethodIT {

    @Drone
    private WebDriver browser;

    @FindBy(id = "hello")
    private WebElement hello;

    @FindBy(id = "system")
    private WebElement system;

    @FindBy(id = "other")
    private WebElement other;

    @FindBy(id = "otherabc")
    private WebElement otherAbc;

    @Deployment(name = "EAR", order = 1)
    public static Archive<?> createEnterpriseArchive() throws IOException {

        JavaArchive ejbModule = ShrinkWrap.create(JavaArchive.class, "cdipropertiesejb.jar");
        DeploymentClassAppenderFactory.create(ejbModule).append("com.byteslounge.cdi.test.edm.ServiceEjbDefaultMethod")
                .append("com.byteslounge.cdi.test.common.InjectedBean")
                .append("com.byteslounge.cdi.test.model.TestEntity")
                .append("com.byteslounge.cdi.test.edm.ServiceEjbDefaultMethodBean").appendBeansXml()
                .appendPersistenceXml();
        IntegrationTestDeploymentUtils.printArchive(ejbModule);

        JavaArchive resourcesJar = ShrinkWrap.create(JavaArchive.class, "resources.jar");
        DeploymentClassAppenderFactory.create(resourcesJar).appendLogging().appendProperties().appendOtherProperties()
                .appendCDIPropertiesConfig();
        IntegrationTestDeploymentUtils.printArchive(resourcesJar);

        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "cdiproperties.ear").addAsModule(ejbModule)
                .addAsLibrary(resourcesJar);
        DeploymentClassAppenderFactory.create(ear).appendApplicationXml().appendCDIPropertiesLib();
        IntegrationTestDeploymentUtils.printArchive(ear);

        return ear;
    }

    @Deployment(name = "WAR", order = 2)
    public static WebArchive createWebArchive() throws IOException {

        WebArchive webModule = ShrinkWrap.create(WebArchive.class, "cdipropertiestest.war");
        DeploymentClassAppenderFactory.create(webModule).append("com.byteslounge.cdi.test.common.InjectedBean")
                .append("com.byteslounge.cdi.test.edm.ServiceEjbDefaultMethod")
                .append("com.byteslounge.cdi.test.edm.TestEjbDefaultBean")
                .appendWebXml("src/test/resources/assets/common/ejbCommon/WEB-INF/web.xml")
                .appendWebResource("src/test/resources/assets/common/ejbCommon/webapp/cditestejb.xhtml")
                .appendFacesConfig()
                .appendBeansXml();
        IntegrationTestDeploymentUtils.printArchive(webModule);

        return webModule;
    }

    @Test
    @RunAsClient
    @OperateOnDeployment("WAR")
    public void test(@ArquillianResource URL contextPath) {
        browser.get(contextPath + "cditestejb.xhtml");
        Assert.assertEquals(hello.getText(), MessageBundleUtils.resolveProperty("hello.world", "bl.messages", Locale.getDefault()));
        Assert.assertEquals(system.getText(), MessageBundleUtils.resolveProperty("system.linux.box", "bl.messages", Locale.getDefault(), "Linux", "16"));
        Assert.assertEquals(other.getText(), MessageBundleUtils.resolveProperty("other.message", TestConstants.OTHER_RESOURCE_BUNDLE_NAME, Locale.getDefault()));
        Assert.assertEquals(otherAbc.getText(),
                MessageBundleUtils.resolveProperty("other.parameter", TestConstants.OTHER_RESOURCE_BUNDLE_NAME, Locale.getDefault(), "B"));

    }

}
