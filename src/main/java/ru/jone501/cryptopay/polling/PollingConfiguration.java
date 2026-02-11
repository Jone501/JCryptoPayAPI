package ru.jone501.cryptopay.polling;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;

@Getter
@ToString
@Accessors(fluent = true)
public class PollingConfiguration {
    @SerializedName("period")
    private long period = Duration.ofSeconds(3).toMillis();
    @SerializedName("max_tracker_lifetime")
    private long maxTrackerLifetime = -1;

    public PollingConfiguration() {
    }

    public PollingConfiguration period(Duration period) {
        this.period = period.toMillis();
        return this;
    }

    public PollingConfiguration maxTrackerLifetime(Duration maxTrackLifetime) {
        this.maxTrackerLifetime = maxTrackLifetime.toMillis();
        return this;
    }

    public static PollingConfiguration fromJsonFile(File file) throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(file), PollingConfiguration.class);
    }
}
