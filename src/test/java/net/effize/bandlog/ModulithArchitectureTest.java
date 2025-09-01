package net.effize.bandlog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ModulithArchitectureTest {

    ApplicationModules modules = ApplicationModules.of(BandlogBeApplication.class);

    @Test
    void verifyModularStructure() {
        // Verify Spring Modulith architecture constraints
        modules.verify();
        System.out.println("Spring Modulith structure verification passed - All module boundaries respected");
    }

    @Test
    void createModuleDocumentation() {
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void printModules() {
        modules.forEach(System.out::println);
    }
}