package api.tests.pet;

import api.model.ApiResponseDto;
import api.model.CategoryDto;
import api.model.PetDto;
import api.model.TagDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetDeleteTests extends PetBaseTest {

    @Test
    @DisplayName("DELETE /pet/{petId}: delete an existing pet and get 200")
    void testPetDeleteExistingPet() {
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

        Response response = petApi.deletePetById(1L);
        assertAll(
                "Validate response",
                () -> assertEquals(200, response.statusCode(),
                        "Expected code 200, got: " + response.statusCode()),
                () -> assertEquals(200, response.getBody().as(ApiResponseDto.class).code(),
                        "Unexpected code in response")
        );

        Response getResponse = petApi.getPetById(1L);
        assertEquals(404, getResponse.statusCode(),
                "Expected code: 404, got: " + getResponse.statusCode());
    }

    @Test
    @DisplayName("DELETE /pet/{petId}: delete unknown pet, get 404")
    void testPetDeleteUnknownPet() {
        Response response = petApi.deletePetById(1L);

        assertEquals(404, response.statusCode(), "Expected code 404, got: " + response.statusCode());
    }

    @Test
    @DisplayName("DELETE /pet/{petId}: delete pet by invalid id")
    void testPetDeletePetByInvalidId() {
        Response responseInvalidId = petApi.deletePetById("test");
        Response responseNullId = petApi.deletePetById(null);

        assertEquals(404, responseInvalidId.statusCode(), "Expected code 404, got: " + responseInvalidId.statusCode());
        assertEquals(404, responseNullId.statusCode(), "Expected code 404, got: " + responseNullId.statusCode());
    }

}
