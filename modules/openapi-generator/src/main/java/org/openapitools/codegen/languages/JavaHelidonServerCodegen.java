/*
 * Copyright (c) 2022 Oracle and/or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openapitools.codegen.languages;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;
import org.openapitools.codegen.CliOption;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.meta.features.DocumentationFeature;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.OperationMap;
import org.openapitools.codegen.model.OperationsMap;

import static org.openapitools.codegen.utils.StringUtils.camelize;

public class JavaHelidonServerCodegen extends JavaHelidonCommonCodegen {

    protected boolean useBeanValidation = true;
    protected String implFolder = "src/main/java";
    protected String basePackage = "org.openapitools";

    public static final String BASE_PACKAGE = "basePackage";

    public JavaHelidonServerCodegen() {
        super();

        modifyFeatureSet(features -> features.includeDocumentationFeatures(DocumentationFeature.Readme));

        embeddedTemplateDir = templateDir = "java-helidon/server";
        invokerPackage = "org.openapitools.api";
        apiPackage = "org.openapitools.api";
        modelPackage = "org.openapitools.model";

        // clioOptions default redefinition need to be updated
        updateOption(CodegenConstants.INVOKER_PACKAGE, this.getInvokerPackage());
        updateOption(CodegenConstants.ARTIFACT_ID, this.getArtifactId());
        updateOption(CodegenConstants.API_PACKAGE, apiPackage);
        updateOption(CodegenConstants.MODEL_PACKAGE, modelPackage);

        // clear model and api doc template as this codegen
        // does not support auto-generated markdown doc at the moment
        modelDocTemplateFiles.remove("model_doc.mustache");
        apiDocTemplateFiles.remove("api_doc.mustache");

        // clear test templates
        // as this codegen does not support api tests at the moment
        apiTestTemplateFiles.clear();

        supportedLibraries.put(HELIDON_SE, "Helidon SE server");

        CliOption libraryOption = new CliOption(CodegenConstants.LIBRARY, "library template (sub-template) to use");
        libraryOption.setEnum(supportedLibraries);
        libraryOption.setDefault(HELIDON_SE);
        cliOptions.add(libraryOption);
        setLibrary(HELIDON_SE);

        cliOptions.add(new CliOption(BASE_PACKAGE, "base package (invokerPackage) for generated code")
                .defaultValue(this.getBasePackage()));
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey(BASE_PACKAGE)) {
            this.setBasePackage((String) additionalProperties.get(BASE_PACKAGE));
        } else {
            additionalProperties.put(BASE_PACKAGE, basePackage);
        }

        supportingFiles.add(new SupportingFile("pom.mustache", "", "pom.xml"));
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));
        supportingFiles.add(new SupportingFile("openapi.mustache",
                ("src/main/resources/META-INF").replace("/", java.io.File.separator), "openapi.yml"));
        supportingFiles.add(new SupportingFile("logging.mustache",
                ("src.main.resources").replace(".", java.io.File.separator), "logging.properties"));
        supportingFiles.add(new SupportingFile("package-info.mustache",
                (sourceFolder + File.separator + basePackage).replace(".", java.io.File.separator),
                "package-info.java"));

        if (additionalProperties.containsKey(USE_BEANVALIDATION)) {
            this.setUseBeanValidation(convertPropertyToBoolean(USE_BEANVALIDATION));
        }
        writePropertyBack(USE_BEANVALIDATION, useBeanValidation);

        if (!additionalProperties.containsKey(CodegenConstants.PARENT_VERSION)) {
            additionalProperties.put(CodegenConstants.PARENT_VERSION, parentVersion);
        }

        importMapping.put("Handler", "io.helidon.webserver.Handler");
        importMapping.put("Map", "java.util.Map");
        importMapping.put("HashMap", "java.util.HashMap");
        importMapping.put("InputStream", "java.io.InputStream");
        importMapping.put("ReadableBodyPart", "io.helidon.media.multipart.ReadableBodyPart");
        importMapping.put("ArrayList", "java.util.ArrayList");
        importMapping.put("ByteArrayOutputStream", "java.io.ByteArrayOutputStream");
        importMapping.put("DataChunk", "io.helidon.common.http.DataChunk");
        importMapping.put("UncheckedIOException", "java.io.UncheckedIOException");
        importMapping.put("IOException", "java.io.IOException");
        importMapping.put("ByteArrayInputStream", "java.io.ByteArrayInputStream");

        if (HELIDON_SE.equals(getLibrary())) {
            dateLibrary = "legacy";
            artifactId = "openapi-helidon-se-server";
            parentVersion = "2.5.0";
            supportingFiles.add(new SupportingFile("application.mustache",
                    ("src.main.resources").replace(".", java.io.File.separator), "application.yaml"));
            supportingFiles.add(new SupportingFile("mainTest.mustache",
                    (testFolder + File.separator + basePackage).replace(".", java.io.File.separator),
                    "MainTest.java"));
            supportingFiles.add(new SupportingFile("main.mustache",
                    (sourceFolder + File.separator + basePackage).replace(".", java.io.File.separator),
                    "Main.java"));
            supportingFiles.add(new SupportingFile("validatorUtils.mustache",
                    (sourceFolder + File.separator + apiPackage).replace(".", java.io.File.separator),
                    "ValidatorUtils.java"));
        }
    }

    @Override
    public CodegenOperation fromOperation(String path, String httpMethod, Operation operation, List<Server> servers) {
        CodegenOperation codegenOperation = super.fromOperation(path, httpMethod, operation, servers);
        if (HELIDON_SE.equals(getLibrary())) {
            if (codegenOperation.bodyParam != null) {
                codegenOperation.imports.add("Handler");
            }
            if (codegenOperation.pathParams.size() > 0) {
                codegenOperation.imports.add("Objects");
            }
            if (codegenOperation.queryParams.size() > 0) {
                codegenOperation.imports.add("List");
            }
            if (codegenOperation.formParams.size() > 0) {
                codegenOperation.imports.add("Map");
                codegenOperation.imports.add("HashMap");
                codegenOperation.imports.add("InputStream");
                codegenOperation.imports.add("ReadableBodyPart");
                codegenOperation.imports.add("ArrayList");
                codegenOperation.imports.add("DataChunk");
                codegenOperation.imports.add("ByteArrayOutputStream");
                codegenOperation.imports.add("IOException");
                codegenOperation.imports.add("UncheckedIOException");
                codegenOperation.imports.add("ByteArrayInputStream");
            }
        }
        return codegenOperation;
    }

    @Override
    public Map<String, Object> postProcessSupportingFileData(Map<String, Object> objs) {
        generateYAMLSpecFile(objs);
        return super.postProcessSupportingFileData(objs);
    }

    @Override
    public String toApiName(String name) {
        if (name.length() == 0) {
            return "DefaultService";
        }
        name = sanitizeName(name);
        return camelize(name) + "Service";
    }

    @Override
    public CodegenModel fromModel(String name, Schema model) {
        CodegenModel codegenModel = super.fromModel(name, model);
        // remove swagger imports
        codegenModel.imports.remove("ApiModelProperty");
        codegenModel.imports.remove("ApiModel");

        return codegenModel;
    }

    @Override
    public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {
        OperationMap operations = objs.getOperations();
        if (operations != null && HELIDON_SE.equals(getLibrary())) {
            List<CodegenOperation> ops = operations.getOperation();
            for (CodegenOperation operation : ops) {
                if (operation.formParams.size() > 0) {
                    objs.put("isFormParamsFunctions", true);
                }
            }
        }
        return objs;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getBasePackage() {
        return basePackage;
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public String getName() {
        return "java-helidon-server";
    }

    @Override
    public String getHelp() {
        return "Generates a Java Helidon Server application.";
    }


    @Override
    public void setUseBeanValidation(boolean useBeanValidation) {
        this.useBeanValidation = useBeanValidation;
    }

    @Override
    public void setPerformBeanValidation(boolean performBeanValidation) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
