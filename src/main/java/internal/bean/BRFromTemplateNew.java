/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package internal.bean;

import java.util.Map;

/**
 * Rough new representation of Business Rule from Template
 */
public class BRFromTemplateNew {
    private String uuid;
    private String name;
    private String templateGroupName;
    private String ruleTemplateName;
    private String type; // "template" or "fromScratch"
    private Map<String, String> properties;

    public BRFromTemplateNew(String uuid, String name, String templateGroupName, String ruleTemplateName, String type, Map<String, String> properties) {
        this.uuid = uuid;
        this.name = name;
        this.templateGroupName = templateGroupName;
        this.ruleTemplateName = ruleTemplateName;
        this.type = type;
        this.properties = properties;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateGroupName() {
        return templateGroupName;
    }

    public void setTemplateGroupName(String templateGroupName) {
        this.templateGroupName = templateGroupName;
    }

    public String getRuleTemplateName() {
        return ruleTemplateName;
    }

    public void setRuleTemplateName(String ruleTemplateName) {
        this.ruleTemplateName = ruleTemplateName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BusinessRuleFromTemplate{" +
                "\nuuid='" + uuid + '\'' +
                ",\n name='" + name + '\'' +
                ",\n templateGroupName='" + templateGroupName + '\'' +
                ",\n ruleTemplateName='" + ruleTemplateName + '\'' +
                ",\n type='" + type + '\'' +
                ",\n properties=" + properties +
                "\n}";
    }
}
