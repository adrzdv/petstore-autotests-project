package api.model;

public record ApiResponseDto(
        int code,
        String type,
        String message
) {
}
