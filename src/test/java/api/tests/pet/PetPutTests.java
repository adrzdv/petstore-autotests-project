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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetPutTests extends BaseSetup {
    private final PetApi petApi = new PetApi(API_KEY);

    @Test
    @DisplayName("PUT /pet: add and update an existing pet")
    void testPetUpdateExistingPet() {
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

        PetDto updatePet = PetDto.builder()
                .category(categoryDto)
                .tags(List.of(tagDto))
                .id(1L)
                .name("kitty")
                .photoUrls(List.of(FILE_SRC))
                .status("available")
                .build();

        petDto.setName("kitty");
        Response putResponse = petApi.updateExistingPet(updatePet);
        Response getResponse = petApi.getPetById(1L);
        PetDto actualPut = putResponse.getBody().as(PetDto.class);
        PetDto actualGet = getResponse.getBody().as(PetDto.class);


        assertAll(
                "Response object after update validating",
                () -> assertEquals(petDto.getId(), actualPut.getId(), "ID mismatch"),
                () -> assertEquals(petDto.getName(), actualPut.getName(), "Name mismatch"),
                () -> assertEquals(petDto.getCategory(), actualPut.getCategory(), "Category mismatch"),
                () -> assertEquals(petDto.getTags(), actualPut.getTags(), "Tags mismatch"),
                () -> assertEquals(petDto.getPhotoUrls(), actualPut.getPhotoUrls(), "URLs mismatch"),
                () -> assertEquals(petDto.getStatus(), actualPut.getStatus(), "Status mismatch")
        );

        assertAll(
                () -> assertEquals(petDto.getId(), actualGet.getId(), "ID mismatch"),
                () -> assertEquals(petDto.getName(), actualGet.getName(), "Name mismatch"),
                () -> assertEquals(petDto.getCategory(), actualGet.getCategory(), "Category mismatch"),
                () -> assertEquals(petDto.getTags(), actualGet.getTags(), "Tags mismatch"),
                () -> assertEquals(petDto.getPhotoUrls(), actualGet.getPhotoUrls(), "URLs mismatch"),
                () -> assertEquals(petDto.getStatus(), actualGet.getStatus(), "Status mismatch"),
                () -> assertEquals(petDto, actualGet)
        );
    }


    @Test
    @DisplayName("PUT /pet: get 405 with response without body")
    void testPetUpdatePetWithoutBody() {
        Response response = petApi.updateExistingPet();
        ApiResponseDto apiResponseDto = response.getBody().as(ApiResponseDto.class);

        assertEquals(405, response.statusCode(), "Expected code 405 but got: " + response.statusCode());
        assertFalse(apiResponseDto.message().isEmpty(), "Response message doesn't contain data");
    }
}
