package api.tests.pet;

import api.base.BaseSetup;
import api.client.PetApi;
import api.model.ApiResponseDto;
import api.model.CategoryDto;
import api.model.PetDto;
import api.model.TagDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetPostTest extends BaseSetup {
    private final PetApi petApi = new PetApi(API_KEY);

    @Test
    @DisplayName("POST /pet/{petId}/uploadImage: upload image and get 200")
    void testPetUploadImage() {
        File file = new File(FILE_SRC);
        Response response = petApi.addPetImage(1L, file);
        ApiResponseDto apiResponseDto = response.getBody().as(ApiResponseDto.class);

        assertEquals(response.statusCode(), 200, "Expected code: 200, got: " + response.statusCode());
        assertTrue(
                apiResponseDto.message().contains(file.getName()),
                "Response don't have a file path"
        );
    }

    @Test
    @DisplayName("POST /pet/{petId}/uploadImage: upload image and get 200 with metadata")
    void testPetUploadImageWithAdditionalMetadata() {
        File file = new File(FILE_SRC);
        String additionalMetadata = "metadata";
        Response response = petApi.addPetImage(1L, additionalMetadata, file);
        ApiResponseDto apiResponseDto = response.getBody().as(ApiResponseDto.class);

        assertEquals(200, response.statusCode(), "Expected code: 200, got: " + response.statusCode());
        assertTrue(
                apiResponseDto.message().contains(file.getName()),
                "Response don't have a file path"
        );
        assertTrue(
                apiResponseDto.message().contains(additionalMetadata),
                "Response don't have an additional metadata info"
        );
    }

    @Test
    @DisplayName("POST /pet/{petId}/uploadImage: upload empty file and get 4XX")
    void testPetUploadEmptyImage() {
        Response response = petApi.addPetImage(1L);

        assertTrue(response.statusCode() > 300 || response.statusCode() < 400,
                "Expected code 4XX but got: " + response.statusCode());
    }


    @Test
    @DisplayName("POST /pet: add new pet, get 200")
    void testPetAddNew() {
        CategoryDto categoryDto = new CategoryDto(1L, "category");
        TagDto tagDto = new TagDto(1L, "tag");
        PetDto petDto = PetDto.builder()
                .category(categoryDto)
                .tags(List.of(tagDto))
                .id(1L)
                .name("doggie")
                .photoUrls(List.of(FILE_SRC))
                .status("available")
                .build();

        Response response = petApi.addNewPet(petDto);
        PetDto actualPetDto = response.getBody().as(PetDto.class);

        assertEquals(200, response.statusCode(),
                "Expected code: 200 but got:" + response.statusCode());
        assertEquals(petDto, actualPetDto,
                "Expected and actual PetDto not equal. Expected: " + petDto + " actual: " + actualPetDto);
    }

    @Test
    @DisplayName("POST /pet: add new pet without body, get 405")
    void testPetAddNewWithoutBody() {
        Response response = petApi.addNewPet();
        ApiResponseDto apiResponseDto = response.getBody().as(ApiResponseDto.class);

        assertEquals(405, response.statusCode(), "Expected code 405 but got: " + response.statusCode());
        assertFalse(apiResponseDto.message().isEmpty(), "Response message doesn't contain data");
    }

    @Test
    @DisplayName("POST /pet/{petId}: successfully update existing pet by id")
    void testPetUpdateExistingPetById() {
        CategoryDto categoryDto = new CategoryDto(1L, "category");
        TagDto tagDto = new TagDto(1L, "tag");
        PetDto petDto = PetDto.builder()
                .category(categoryDto)
                .tags(List.of(tagDto))
                .id(1L)
                .name("doggie")
                .photoUrls(List.of(FILE_SRC))
                .status("available")
                .build();

        petApi.addNewPet(petDto);

        petApi.updatePetById(1L, "kitty", null);
        PetDto actualPetDtoNameChanged = petApi.getPetById(1L).getBody().as(PetDto.class);

        assertAll(
                "Validating name changes",
                () -> assertEquals("kitty", actualPetDtoNameChanged.getName(), "Name mismatch"),
                () -> assertEquals("available", actualPetDtoNameChanged.getStatus(), "Status mismatch")
        );

        petApi.updatePetById(1L, null, "pending");

        PetDto actualPetDtoChangedStatus = petApi.getPetById(1L).getBody().as(PetDto.class);

        assertAll(
                "Validating status changes",
                () -> assertEquals("kitty", actualPetDtoChangedStatus.getName(), "Name mismatch"),
                () -> assertEquals("pending", actualPetDtoChangedStatus.getStatus(), "Status mismatch")
        );

        petApi.updatePetById(1L, "piggy", "sold");

        PetDto actualPetDtoAllChanged = petApi.getPetById(1L).getBody().as(PetDto.class);

        assertAll(
                "Validating name and status changes",
                () -> assertEquals("piggy", actualPetDtoAllChanged.getName(), "Name mismatch"),
                () -> assertEquals("sold", actualPetDtoAllChanged.getStatus(), "Status mismatch")
        );
    }

    @Test
    @DisplayName("POST /pet/{petId}: fail with invalid id")
    void testPetUpdatePetWithInvalidId() {
        Response responseStringId = petApi.updatePetById("null", null, null);
        Response responseNullId = petApi.updatePetById(null, null, null);

        assertEquals(404, responseStringId.statusCode(),
                "Expected code 404 but got: " + responseStringId.statusCode());
        assertEquals(404, responseNullId.statusCode(),
                "Expected code 404 but got: " + responseNullId.statusCode());
    }
}
