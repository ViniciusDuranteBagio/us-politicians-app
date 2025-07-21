package com.vinicius.uspoliticiansapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedApiResponseDTO<T> {
    private List<T> data;
    private PaginationMetadataDTO pagination;
    private String message;
    private boolean success;
    // TODO: passar para ingles
    private static final String SUCCESS_MESSAGE = "Dados recuperados com sucesso";

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaginationMetadataDTO {
        private int currentPage;
        private int perPage;
        private int totalItems;
        private int totalPages;
        private boolean hasNext;
        private boolean hasPrevious;
        private int nextPage;
        private int previousPage;
        private int from;
        private int to;
    }

    public static <T> PaginatedApiResponseDTO<T> success(List<T> data, int currentPage, int perPage) {
        int totalItems = data.size();
        int fromIndex = Math.max(0, (currentPage - 1) * perPage);
        int toIndex = Math.min(fromIndex + perPage, totalItems);
        List<T> pagedDataList = data.subList(fromIndex, toIndex);

        int totalPages = (int) Math.ceil((double) totalItems / perPage);
        int from = (currentPage - 1) * perPage + 1;
        int to = Math.min(currentPage * perPage, totalItems);

        boolean hasNext = currentPage < totalPages;
        boolean hasPrevious = currentPage > 1;
        int nextPage = hasNext ? currentPage + 1 : currentPage;
        int previousPage = hasPrevious ? currentPage - 1 : currentPage;

        PaginationMetadataDTO pagination = PaginationMetadataDTO.builder()
                .currentPage(currentPage)
                .perPage(perPage)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .nextPage(nextPage)
                .previousPage(previousPage)
                .from(from)
                .to(to)
                .build();

        return PaginatedApiResponseDTO.<T>builder()
                .data(pagedDataList)
                .pagination(pagination)
                .success(true)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    public static <T> PaginatedApiResponseDTO<T> success(List<T> data) {
        return PaginatedApiResponseDTO.<T>builder()
                .data(data)
                .success(true)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    public static <T> PaginatedApiResponseDTO<T> error(String message) {
        return PaginatedApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
} 