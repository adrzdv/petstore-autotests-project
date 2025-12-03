package api.tests.pet;

import api.model.ApiResponseDto;
import api.model.CategoryDto;
import api.model.PetDto;
import api.model.TagDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetGetTests extends PetBaseTest {

    @Test
    @DisplayName("GET /pet/findByStatus: get list of pets filtering by status")
    void testPetGetPetsByStatuses() {
        List<String> statuses = List.of("pending", "sold");

        Response response = petApi.findPetsByStatus(statuses);
        List<PetDto> responseBody = response.getBody().jsonPath().getList("", PetDto.class);

        assertEquals(200, response.statusCode(), "Expected code 200 but got: " + response.statusCode());
        assertFalse(responseBody.isEmpty(), "List of result is empty");
    }

    @Test
    @DisplayName("GET /pet/{petId}: boundary value tests for petId")
    void testPetGetPetWithMinAndMaxId() {
        Response maxValueResponse = petApi.getPetById(Long.MAX_VALUE + 1);
        Response minValueResponse = petApi.getPetById(Long.MIN_VALUE);

        ApiResponseDto maxResponse = maxValueResponse.getBody().as(ApiResponseDto.class);
        ApiResponseDto minResponse = minValueResponse.getBody().as(ApiResponseDto.class);

        assertAll(
                "Validating response with Long overflow",
                () -> assertEquals(404, maxValueResponse.statusCode(),
                        "Expected code 404, got: " + maxValueResponse.statusCode()),
                () -> assertTrue(maxResponse.message().contains("Pet not found"))
        );

        assertAll(
                "Validating response with Long minimum value",
                () -> assertEquals(404, minValueResponse.statusCode(),
                        "Expected code 404, got: " + minValueResponse.statusCode()),
                () -> assertTrue(minResponse.message().contains("Pet not found"))
        );
    }

    @Test
    @DisplayName("GET /pet/{petId}: test with invalid petId")
    void testPetGetPetWithInvalidId() {
        Response response = petApi.getPetById(Double.MAX_VALUE);

        assertEquals(404, response.statusCode(), "Expected code 404, got: " + response.statusCode());
    }

    @Test
    @DisplayName("GET /pet/{petId}: test without petId")
    void testPetGetPetWithInvalidIdD() {
        Response response = petApi.getPetById();

        assertEquals(405, response.statusCode(), "Expected code 405, got: " + response.statusCode());
    }

    @Test
    @DisplayName("GET /pet/{petId}: get unknown pet by id and code 404")
    void testPetGetUnknownPet() {
        petApi.deletePetById(1L);
        Response response = petApi.getPetById(1L);
        ApiResponseDto apiResponseDto = response.getBody().as(ApiResponseDto.class);

        assertAll(
                "Validating response on unknown pet",
                () -> assertEquals(404, response.statusCode(),
                        "Expected code 404, got: " + response.statusCode()),
                () -> assertTrue(apiResponseDto.message().contains("Pet not found"))
        );
    }

    @Test
    @DisplayName("GET /pet/{petId}: get pet by id and code 200")
    void testPetGetPetById() {
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

        Response response = petApi.getPetById(1L);
        PetDto actualPetDto = response.getBody().as(PetDto.class);

        assertEquals(petDto, actualPetDto, "Actual and existing pet is not equal");
    }
}
