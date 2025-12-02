package api.client;

import api.model.PetDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PetApi {
    private static final String PET = "/pet";
    private final String apiKey;

    public PetApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response addPetImage(long petId,
                                String additionalMetadata,
                                File mediaFile) {
        String mimeType;
        try {
            mimeType = Files.probeContentType(mediaFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cant handle MIME-type", e);
        }

        return given()
                .accept(ContentType.JSON)
                .multiPart("additionalMetadata", additionalMetadata)
                .multiPart("file", mediaFile, mimeType)
                .when()
                .post(PET + "/" + petId + "/uploadImage");
    }

    public Response addPetImage(long petId,
                                File mediaFile) {
        String mimeType;
        try {
            mimeType = Files.probeContentType(mediaFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cant handle MIME-type", e);
        }

        return given()
                .accept(ContentType.JSON)
                .multiPart("file", mediaFile, mimeType)
                .when()
                .post(PET + "/" + petId + "/uploadImage");
    }

    public Response addPetImage(long petId) {

        return given()
                .accept(ContentType.JSON)
                .when()
                .post(PET + "/" + petId + "/uploadImage");
    }

    public Response addNewPet(PetDto petDto) {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(petDto)
                .post(PET);
    }

    public Response addNewPet() {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .post(PET);
    }

    public Response updateExistingPet(PetDto petDto) {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(petDto)
                .put(PET);
    }

    public Response updateExistingPet() {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .put(PET);
    }

    public Response getPetById(Object petId) {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get(PET + "/" + petId);
    }

    public Response getPetById() {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get(PET);
    }

    public Response findPetsByStatus(List<String> statuses) {

        return given()
                .queryParam("status", String.join(",", statuses))
                .get(PET + "/findByStatus");
    }

    public Response updatePetById(Object petId, String name, String status) {
        RequestSpecification specification = given().contentType(ContentType.URLENC);

        if (name != null) {
            specification.formParam("name", name);
        }

        if (status != null) {
            specification.formParam("status", status);
        }

        return specification.post(PET + "/" + petId);
    }

    public Response deletePetById(Object petId) {

        return given()
                .header("apiKey", apiKey)
                .delete(PET + "/" + petId);
    }
}
