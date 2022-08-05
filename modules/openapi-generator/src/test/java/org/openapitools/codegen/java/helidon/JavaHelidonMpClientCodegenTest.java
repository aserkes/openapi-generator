package org.openapitools.codegen.java.helidon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.TestUtils;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.openapitools.codegen.java.assertions.JavaFileAssert.assertThat;

public class JavaHelidonMpClientCodegenTest {

    private String outputPath;
    private List<File> generatedFiles;

    @BeforeClass
    public void setup() throws IOException {
        File output = Files.createTempDirectory("test").toFile();
        output.deleteOnExit();
        outputPath = output.getAbsolutePath().replace('\\', '/');

        System.out.println("Generating java-helidon-client MP project in " + outputPath);

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("java-helidon-client")
                .setLibrary("mp")
                .setInputSpec("src/test/resources/3_0/helidon/petstore-no-multipart-for-testing.yaml")
                .setOutputDir(outputPath);

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        generator.opts(clientOptInput);
        generatedFiles = generator.generate();
    }

    @Test
    public void testPom() {
        TestUtils.ensureContainsFile(generatedFiles, new File(outputPath), "pom.xml");
    }

    @Test
    public void testPetApi() {
        assertThat(Paths.get(outputPath + "/src/main/java/org/openapitools/client/api/PetApi.java"))
                .assertMethod("addPet", "Pet")
                .toFileAssert()
                .assertMethod("deletePet", "Long", "String", "Long", "String", "Integer",
                        "List<Integer>", "List<String>")
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
                .toFileAssert();
    }

    @Test
    public void testStoreApi() {
        assertThat(Paths.get(outputPath + "/src/main/java/org/openapitools/client/api/StoreApi.java"))
                .assertMethod("deleteOrder", "String")
                .toFileAssert()
                .assertMethod("getInventory")
                .toFileAssert()
                .assertMethod("getOrderById", "BigDecimal")
                .toFileAssert()
                .assertMethod("placeOrder", "Order")
                .toFileAssert();
    }

    @Test
    public void testUserApi() {
        assertThat(Paths.get(outputPath + "/src/main/java/org/openapitools/client/api/UserApi.java"))
                .assertMethod("createUser", "User")
                .toFileAssert()
                .assertMethod("createUsersWithArrayInput", "List<User>")
                .toFileAssert()
                .assertMethod("createUsersWithListInput", "List<User>")
                .toFileAssert()
                .assertMethod("getUserByName", "String")
                .toFileAssert()
                .assertMethod("loginUser", "String", "String", "String", "Long", "BigDecimal")
                .toFileAssert()
                .assertMethod("updateUser", "String", "User")
                .toFileAssert();
    }
}
