package org.openapitools.codegen.java.helidon;

import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.TestUtils;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.openapitools.codegen.java.assertions.JavaFileAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.openapitools.codegen.CodegenConstants.SERIALIZATION_LIBRARY;

public class JavaHelidonMpServerCodegenTest {

    private DefaultGenerator generator;
    private String outputPath;
    private String apiPackage;
    private String modelPackage;

    @BeforeMethod
    public void setup() throws IOException {
        File output = Files.createTempDirectory("test").toFile();
        output.deleteOnExit();
        outputPath = output.getAbsolutePath().replace('\\', '/');
        apiPackage = outputPath + "/src/main/java/org/openapitools/server/api";
        modelPackage = outputPath + "/src/main/java/org/openapitools/server/model";
        generator = new DefaultGenerator();
    }

    private CodegenConfigurator createConfigurator() {
        return new CodegenConfigurator()
                .setGeneratorName("java-helidon-server")
                .setLibrary("mp")
                .setInputSpec("src/test/resources/3_0/helidon/petstore-for-testing.yaml")
                .setOutputDir(outputPath);
    }

    private void generate(CodegenConfigurator config) {
        generator.opts(config.toClientOptInput());
        generator.setGenerateMetadata(false);
        generator.generate();
    }

    private void generate() {
        generate(createConfigurator());
    }

    @Test
    public void testRestApiFilesOnly() {
        generate(createConfigurator().addAdditionalProperty("fullProject", "false"));

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/PetService.java"))
                .fileContains("public class PetService");

        File outputFile = Paths.get(outputPath).toFile();
        assertThat(Objects.requireNonNull(outputFile.listFiles()).length, is(1));
    }

    @Test
    public void testJackson() {
        generate(createConfigurator().addAdditionalProperty(SERIALIZATION_LIBRARY, "jackson"));

        JavaFileAssert.assertThat(Paths.get(modelPackage + "/Color.java"))
                .fileContains("com.fasterxml.jackson.annotation.JsonCreator")
                .fileContains("com.fasterxml.jackson.annotation.JsonValue");
    }

    @Test
    public void testJsonb() {
        generate(createConfigurator().addAdditionalProperty(SERIALIZATION_LIBRARY, "jsonb"));

        JavaFileAssert.assertThat(Paths.get(modelPackage + "/Color.java"))
                .fileContains("javax.json.bind.annotation.JsonbCreator");
    }

    @Test
    public void testAbstractClass() {
        generate(createConfigurator().addAdditionalProperty("useAbstractClass", "true"));

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/PetService.java"))
                .fileContains("public abstract class PetService")
                .assertMethod("addPet", "Pet")
                .doesNotHaveImplementation();

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/StoreService.java"))
                .fileContains("public abstract class StoreService")
                .assertMethod("placeOrder", "Order")
                .doesNotHaveImplementation()
                .hasReturnType("Response");

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/StoreServiceImpl.java"))
                .fileContains("public class StoreServiceImpl extends StoreService")
                .assertMethod("placeOrder", "Order")
                .hasReturnType("Response")
                .bodyContainsLines("return Response.ok().entity(\"magic!\").build();");
    }

    @Test
    public void testFullProject() {
        generate(createConfigurator().addAdditionalProperty("fullProject", "true"));

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/PetService.java"))
                .fileContains("public class PetService")
                .assertMethod("addPet", "Pet");

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/StoreService.java"))
                .fileContains("public class StoreService")
                .assertMethod("placeOrder", "Order")
                .hasReturnType("Response");
    }

    @Test
    public void validatePetApi() {
        generate();

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/PetService.java"))
                .fileContains("org.openapitools.server.model.Pet")
                .assertMethod("addPet", "Pet")
                .toFileAssert()
                .assertMethod("addPets", "String", "InputStream", "InputStream", "List<String>", "List<Long>", "Integer")
                .toFileAssert()
                .assertMethod("deletePet", "Long", "String", "Long", "String", "Integer", "List<Integer>", "List<String>")
                .toFileAssert()
                .assertMethod("findPetsByStatus", "List<String>")
                .toFileAssert()
                .assertMethod("findPetsByTags", "List<Integer>")
                .toFileAssert()
                .assertMethod("getPetById", "Long")
                .toFileAssert()
                .assertMethod("updatePet", "Pet")
                .toFileAssert()
                .assertMethod("updatePetWithForm", "Long", "String", "String")
                .toFileAssert()
                .assertMethod("uploadFile", "Long", "Long", "String", "InputStream");
    }

    @Test
    public void validateStoreApi() {
        generate();

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/StoreService.java"))
                .fileContains("org.openapitools.server.model.Order")
                .assertMethod("deleteOrder", "String")
                .toFileAssert()
                .assertMethod("getInventory")
                .toFileAssert()
                .assertMethod("getOrderById", "BigDecimal")
                .toFileAssert()
                .assertMethod("placeOrder", "Order");
    }

    @Test
    public void validateUserApi() {
        generate();

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/UserService.java"))
                .fileContains("org.openapitools.server.model.User")
                .assertMethod("createUser", "User")
                .toFileAssert()
                .assertMethod("createUsersWithArrayInput", "List<User>")
                .toFileAssert()
                .assertMethod("createUsersWithListInput", "List<User>")
                .toFileAssert()
                .assertMethod("deleteUser", "String")
                .toFileAssert()
                .assertMethod("getUserByName", "String")
                .toFileAssert()
                .assertMethod("loginUser", "String", "String", "String", "Long", "BigDecimal")
                .toFileAssert()
                .assertMethod("logoutUser")
                .toFileAssert()
                .assertMethod("updateUser", "String", "User");
    }

    @Test
    public void doGenerateInterfaceOnly() {
        generate(createConfigurator().addAdditionalProperty("interfaceOnly", "true"));

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/PetService.java"))
                .fileContains("public interface PetService")
                .assertMethod("addPet", "Pet")
                .doesNotHaveImplementation();

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/StoreService.java"))
                .fileContains("public interface StoreService")
                .assertMethod("placeOrder", "Order")
                .doesNotHaveImplementation()
                .hasReturnType("Response");

        JavaFileAssert.assertThat(Paths.get(apiPackage + "/StoreServiceImpl.java"))
                .fileContains("public class StoreServiceImpl implements StoreService")
                .assertMethod("placeOrder", "Order")
                .hasReturnType("Response")
                .bodyContainsLines("return Response.ok().entity(\"magic!\").build();");
    }

    @Test
    public void testGenerateGradleProject() {
        generate(createConfigurator().addAdditionalProperty("gradleProject", "true"));

        assertThat(Paths.get(outputPath + "/build.gradle").toFile().exists(), is(true));
        assertThat(Paths.get(outputPath + "/settings.gradle").toFile().exists(), is(true));
        TestUtils.assertFileNotExists(Paths.get(outputPath + "/pom.xml"));
    }

}