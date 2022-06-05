import com.jp.IStatusListener;
import com.jp.Movie;
import com.jp.MovieReport;
import com.jp.Status;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MovieReportTest {

    @Test
    public void testReport() {
        MovieReport fixture = new MovieReport(new Status());
        File[] files = getResourcePath();

        for(File f : files) {
            List<Movie> movies = fixture.runReport(f);
            fixture.createCSV(f, movies);
        }
    }


    private File[] getResourcePath() {
        Path resourceDirectory = Paths.get("src","test","resources");
        return resourceDirectory.toFile().listFiles();
    }
}
