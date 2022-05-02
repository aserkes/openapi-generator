/*
 * Copyright 2018 OpenAPI-Generator Contributors (https://openapi-generator.tech)
 * Copyright 2018 SmartBear Software
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.swagger.v3.oas.models.OpenAPI;
import org.apache.commons.lang3.BooleanUtils;
import org.openapitools.codegen.CliOption;
import org.openapitools.codegen.CodegenConstants;
import org.openapitools.codegen.CodegenModel;
import org.openapitools.codegen.CodegenOperation;
import org.openapitools.codegen.CodegenProperty;
import org.openapitools.codegen.CodegenType;
import org.openapitools.codegen.SupportingFile;
import org.openapitools.codegen.config.GlobalSettings;
import org.openapitools.codegen.languages.features.BeanValidationFeatures;
import org.openapitools.codegen.languages.features.OptionalFeatures;
import org.openapitools.codegen.languages.features.PerformBeanValidationFeatures;
import org.openapitools.codegen.languages.features.SwaggerUIFeatures;
import org.openapitools.codegen.meta.features.DocumentationFeature;
import org.openapitools.codegen.model.ModelMap;
import org.openapitools.codegen.model.ModelsMap;
import org.openapitools.codegen.model.OperationMap;
import org.openapitools.codegen.model.OperationsMap;
import org.openapitools.codegen.utils.URLPathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openapitools.codegen.languages.features.BeanValidationFeatures.USE_BEANVALIDATION;
import static org.openapitools.codegen.languages.features.SwaggerUIFeatures.USE_SWAGGER_UI;
import static org.openapitools.codegen.utils.StringUtils.camelize;


public class JavaHelidonSeServerCodegen extends AbstractJavaCodegen
        implements
        BeanValidationFeatures//,
//        PerformBeanValidationFeatures,
//        OptionalFeatures, SwaggerUIFeatures
{

    private final Logger LOGGER = LoggerFactory.getLogger(JavaHelidonSeServerCodegen.class);

    public static final String BASE_PACKAGE = "basePackage";

    protected String title = "Helidon SE Server";
    protected String implFolder = "src/main/java";
    protected String basePackage = "org.openapitools";

    public JavaHelidonSeServerCodegen() {
        super();

        modifyFeatureSet(features -> features.includeDocumentationFeatures(DocumentationFeature.Readme));

        embeddedTemplateDir = templateDir = "java-helidon-se-server";
        invokerPackage = "org.openapitools.api";
        artifactId = "openapi-helidon-se-server";
        apiPackage = "org.openapitools.api";
        modelPackage = "org.openapitools.model";

        if (!additionalProperties.containsKey(DATE_LIBRARY)) {
            setDateLibrary("java8");
        }

        // clioOptions default redefinition need to be updated
        updateOption(CodegenConstants.INVOKER_PACKAGE, this.getInvokerPackage());
        updateOption(CodegenConstants.ARTIFACT_ID, this.getArtifactId());
        updateOption(CodegenConstants.API_PACKAGE, apiPackage);
        updateOption(CodegenConstants.MODEL_PACKAGE, modelPackage);
        updateOption(DATE_LIBRARY, this.getDateLibrary());

        apiTestTemplateFiles.clear(); // TODO: add test template

        // clear model and api doc template as this codegen
        // does not support auto-generated markdown doc at the moment
        //TODO: add doc templates
        modelDocTemplateFiles.remove("model_doc.mustache");
        apiDocTemplateFiles.remove("api_doc.mustache");

        additionalProperties.put("title", title);

        cliOptions.add(new CliOption(BASE_PACKAGE, "base package (invokerPackage) for generated code")
                .defaultValue(this.getBasePackage()));


//        cliOptions
//                .add(CliOption.newBoolean(USE_BEANVALIDATION, "Use BeanValidation API annotations", true));
//        cliOptions.add(CliOption.newBoolean(USE_SWAGGER_UI,
//                "Open the OpenApi specification in swagger-ui. Will also import and configure needed dependencies",
//                true));
//        cliOptions.add(CliOption.newBoolean(PERFORM_BEANVALIDATION,
//                "Use Bean Validation Impl. to perform BeanValidation", performBeanValidation));
    }

//========================================================================

//    boolean useOptional = false;
//    @Override
//    public void setUseOptional(boolean useOptional) {
//        this.useOptional = useOptional;
//    }

//    boolean performBeanValidation = true;
//    @Override
//    public void setPerformBeanValidation(boolean performBeanValidation) {
//        this.performBeanValidation = performBeanValidation;
//    }

//    boolean useSwaggerUI = true;
//    @Override
//    public void setUseSwaggerUI(boolean useSwaggerUI) {
//        this.useSwaggerUI = useSwaggerUI;
//    }


//    public List<DocumentationProvider> supportedDocumentationProvider() {
//        List<DocumentationProvider> supportedProviders = new ArrayList<>();
//        supportedProviders.add(DocumentationProvider.NONE);
//        supportedProviders.add(DocumentationProvider.SOURCE);
//        supportedProviders.add(DocumentationProvider.SPRINGFOX);
//        supportedProviders.add(DocumentationProvider.SPRINGDOC);
//        return supportedProviders;
//    }
//
//    @Override
//    public List<AnnotationLibrary> supportedAnnotationLibraries() {
//        List<AnnotationLibrary> supportedLibraries = new ArrayList<>();
//        supportedLibraries.add(AnnotationLibrary.NONE);
//        supportedLibraries.add(AnnotationLibrary.SWAGGER1);
//        supportedLibraries.add(AnnotationLibrary.SWAGGER2);
//        return supportedLibraries;
//    }

    protected boolean useBeanValidation = true;
    @Override
    public void setUseBeanValidation(boolean useBeanValidation) {
        this.useBeanValidation = useBeanValidation;
    }

//=====================================

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public String getName() {
        return "java-helidon-se-server";
    }

    @Override
    public String getHelp() {
        return "Generates a Java Helidon SE Server application.";
    }

    @Override
    public void processOpts() {
        super.processOpts();
//todo !!!LAST!!! maybe need to remove this line
//        apiTemplateFiles.remove("api.mustache");
//todo !!!LAST!!!
        supportingFiles.add(new SupportingFile("pom.mustache", "", "pom.xml"));
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md")
                .doNotOverwrite());

        // keep the yaml in config folder for framework validation.
        supportingFiles.add(new SupportingFile("openapi.mustache",
                ("src/main/resources/META-INF").replace("/", java.io.File.separator), "openapi.yml"));
        supportingFiles.add(new SupportingFile("application.mustache",
                ("src.main.resources").replace(".", java.io.File.separator), "application.yaml"));
        supportingFiles.add(new SupportingFile("logging.mustache",
                ("src.main.resources").replace(".", java.io.File.separator), "logging.properties"));
        supportingFiles.add(new SupportingFile("mainTest.mustache",
                (testFolder + File.separator + basePackage).replace(".", java.io.File.separator),
                "MainTest.java"));
//todo !!!REMOVE!!!
        supportingFiles.add(new SupportingFile("interface.mustache", (String.format(Locale.ROOT, "src.main.java.%s", apiPackage)).replace(".", java.io.File.separator), "PathHandlerInterface.java"));
//todo !!!REMOVE!!!
        supportingFiles.add(new SupportingFile("handler.mustache", (String.format(Locale.ROOT, "src.main.java.%s", apiPackage)).replace(".", java.io.File.separator), "PathHandlerProvider.java"));
        supportingFiles.add(new SupportingFile("service.mustache", ("src.main.resources.META-INF.services").replace(".", java.io.File.separator), "com.networknt.server.HandlerProvider"));

        // configuration files
        supportingFiles.add(new SupportingFile("server.json", ("src.main.resources.config").replace(".", java.io.File.separator), "server.json"));
        supportingFiles.add(new SupportingFile("security.json", ("src.main.resources.config").replace(".", java.io.File.separator), "security.json"));
        supportingFiles.add(new SupportingFile("primary.crt", ("src.main.resources.config.oauth").replace(".", java.io.File.separator), "primary.crt"));

        //============
        if (additionalProperties.containsKey(USE_BEANVALIDATION)) {
            this.setUseBeanValidation(convertPropertyToBoolean(USE_BEANVALIDATION));
        }
        writePropertyBack(USE_BEANVALIDATION, true);//useBeanValidation

        if (!additionalProperties.containsKey(BASE_PACKAGE)
                && additionalProperties.containsKey(CodegenConstants.INVOKER_PACKAGE)) {
            // set invokerPackage as basePackage:
            this.setBasePackage((String) additionalProperties.get(CodegenConstants.INVOKER_PACKAGE));
            additionalProperties.put(BASE_PACKAGE, basePackage);
            LOGGER.info("Set base package to invoker package ({})", basePackage);
        }

        if (additionalProperties.containsKey(BASE_PACKAGE)) {
            this.setBasePackage((String) additionalProperties.get(BASE_PACKAGE));
        } else {
            additionalProperties.put(BASE_PACKAGE, basePackage);
        }

        supportingFiles.add(new SupportingFile("main.mustache",
                (sourceFolder + File.separator + basePackage).replace(".", java.io.File.separator),
                "Main.java"));

//        if (additionalProperties.containsKey(PERFORM_BEANVALIDATION)) {
//            this.setPerformBeanValidation(convertPropertyToBoolean(PERFORM_BEANVALIDATION));
//        }
//        writePropertyBack(PERFORM_BEANVALIDATION, performBeanValidation);
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getBasePackage() {
        return basePackage;
    }

    @Override
    public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {
        OperationMap operations = objs.getOperations();
        if (operations != null) {
            List<CodegenOperation> ops = operations.getOperation();
            for (CodegenOperation operation : ops) {
                if (operation.returnType == null) {
                    operation.returnType = "Void";
                } else if (operation.returnType.startsWith("List")) {
                    String rt = operation.returnType;
                    int end = rt.lastIndexOf(">");
                    if (end > 0) {
                        operation.returnType = rt.substring("List<".length(), end);
                        operation.returnContainer = "List";
                    }
                } else if (operation.returnType.startsWith("Map")) {
                    String rt = operation.returnType;
                    int end = rt.lastIndexOf(">");
                    if (end > 0) {
                        operation.returnType = rt.substring("Map<".length(), end);
                        operation.returnContainer = "Map";
                    }
                } else if (operation.returnType.startsWith("Set")) {
                    String rt = operation.returnType;
                    int end = rt.lastIndexOf(">");
                    if (end > 0) {
                        operation.returnType = rt.substring("Set<".length(), end);
                        operation.returnContainer = "Set";
                    }
                }
            }
        }
        return objs;
    }

    @Override
    public void postProcessModelProperty(CodegenModel model, CodegenProperty property) {
        super.postProcessModelProperty(model, property);

        //Add imports for Jackson
        if (!BooleanUtils.toBoolean(model.isEnum)) {
            model.imports.add("JsonProperty");

            if (BooleanUtils.toBoolean(model.hasEnums)) {
                model.imports.add("JsonValue");
            }
        }
    }

    @Override
    public ModelsMap postProcessModelsEnum(ModelsMap objs) {
        objs = super.postProcessModelsEnum(objs);

        //Add imports for Jackson
        List<Map<String, String>> imports = objs.getImports();
        for (ModelMap mo : objs.getModels()) {
            CodegenModel cm = mo.getModel();
            // for enum model
            if (Boolean.TRUE.equals(cm.isEnum) && cm.allowableValues != null) {
                cm.imports.add(importMapping.get("JsonValue"));
                Map<String, String> item = new HashMap<>();
                item.put("import", importMapping.get("JsonValue"));
                imports.add(item);
            }
        }

        return objs;
    }

//    public String apiFilename(String templateName, String tag) {
//        String result = super.apiFilename(templateName, tag);
//
//        if (templateName.endsWith("api.mustache")) {
//            int ix = result.indexOf(sourceFolder);
//            String beg = result.substring(0, ix);
//            String end = result.substring(ix + sourceFolder.length());
//            new java.io.File(beg + implFolder).mkdirs();
//            result = beg + implFolder + end;
//        }
//        return result;
//    }

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
        name = name.replaceAll("[^a-zA-Z0-9]+", "_"); // FIXME: a parameter should not be assigned. Also declare the methods parameters as 'final'.
        return camelize(name) + "Service";
    }

    @Override
    public void preprocessOpenAPI(OpenAPI openAPI) {
        super.preprocessOpenAPI(openAPI);
        final URL url = URLPathUtils.getServerURL(openAPI, serverVariableOverrides());
        final String port = URLPathUtils.getPort(url, "8080");
        final String host = URLPathUtils.getHost(openAPI, serverVariableOverrides());
    }

}
