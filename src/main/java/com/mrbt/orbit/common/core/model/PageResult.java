package com.mrbt.orbit.common.core.model;

import java.util.List;

public record PageResult<T>(List<T> content, long totalElements, int totalPages, int page, int size) {

}
