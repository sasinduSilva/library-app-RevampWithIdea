package com.library.app.common.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.library.app.author.model.Author;
import com.library.app.author.resource.AuthorJsonConverter;
import com.library.app.common.model.PaginatedData;

public final class JsonUtils {

	private JsonUtils() {
	}

	public static JsonElement getJsonElementWithId(final Long id) {
		final JsonObject idJson = new JsonObject();
		idJson.addProperty("id", id);

		return idJson;
	}

    public static <T> JsonElement getJsonElementWithPagingAndEntries(PaginatedData<T> paginatedData, EntityJsonConverter<T> entityJsonConverter) {
		JsonObject jsonWithEntriesAndPaging = new JsonObject();

		JsonObject jsonPaging = new JsonObject();
		jsonPaging.addProperty("totalRecords", paginatedData.getNumberOfRows());

		jsonWithEntriesAndPaging.add("paging", jsonPaging);
		jsonWithEntriesAndPaging.add("entries", entityJsonConverter.convertToJsonElement(paginatedData.getRows()));

		return jsonWithEntriesAndPaging;
    }
}