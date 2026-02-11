package ru.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.types.AppStats;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GetStatsRequest extends Request<BaseResponse<AppStats>> {
    @SerializedName("start_at")
    private String startAt;

    @SerializedName("end_at")
    private String endAt;

    private GetStatsRequest() {
    }

    @Override
    public String method() {
        return "getStats";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<AppStats> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, AppStats.class);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        GetStatsRequest request = new GetStatsRequest();

        private Builder() {
        }

        public Builder startAt(Date startAt) {
            request.startAt = ZonedDateTime.ofInstant(startAt.toInstant(), ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_INSTANT);
            return this;
        }

        public Builder endAt(Date endAt) {
            request.endAt = ZonedDateTime.ofInstant(endAt.toInstant(), ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_INSTANT);
            return this;
        }

        public GetStatsRequest build() {
            return request;
        }
    }
}
