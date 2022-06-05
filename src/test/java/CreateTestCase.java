import com.jp.Movie;
import com.jp.MovieReport;
import com.jp.Movies;
import com.jp.Status;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CreateTestCase {

    /**
     * Ensure the directory is correct for the movies directory.
     * This "test" creates the directory in which to run the report against.
     * It is intended to simulate the large data set in which this report is run.
     * Reads the existing directory where movies are stored and creates a file for each in the test directory
     */
    @Test
    public void testCreateTestCase() {
        File f = new File("D:\\video");
        List<Movie> movies = new ArrayList<>();

        assessChildren(f, f, movies);

        System.out.println("Movies: " + movies.size());
        Path resourceDirectory = Paths.get("src","test","resources");
        for(Movie m : movies) {
            createDir(resourceDirectory, m.getLocationFromRoot());
        }
        System.out.println("Directories created");


    }

    private void assessChildren(File root, File directory, List<Movie> movies) {
        File[] dirs = directory.listFiles();
        if(dirs == null) {
            return;
        }
        for(File dir : dirs) {
            if(dir.isDirectory()) {
                if(isMovie(dir)) {
                    movies.add(Movies.parseReport(root, dir));
                }
                assessChildren(root, dir, movies);
            }
        }
    }

    private void createTestCase(File location) {

    }

    private boolean isMovie(File file) {
        String name = file.getName();
        return name.contains("(") && name.contains(")");
    }


    private void createDir(Path root, List<String> path) {
        String [] s = new String[]{};
        Path p = Paths.get(root.toString(), path.toArray(s));

        if(!p.toFile().mkdirs()){
            System.out.println("Unable to create " + p.toString());
        }
    }


}
