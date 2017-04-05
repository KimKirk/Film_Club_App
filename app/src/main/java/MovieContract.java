import android.provider.BaseColumns;

/**
 * Created by Kim Kirk on 4/5/2017.
 */
public class MovieContract {




    public static final class MovieID implements BaseColumns {
        public static final String _COUNT = "count";
        //MYNOTES
            //this is the "id" element from the JSON array
            //this is primary key
        public static final String _ID = "movie_id";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_IMAGE = "movie_image";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        //uri

    }

    public static final class StatusID implements BaseColumns {
        public static final String _COUNT = "count";
        //this is primary key - autoincrement
        public static final String _ID = "status_id";
        public static final String COLUMN_STATUS = "status";

        //uri

    }

    public static final class ReviewID implements BaseColumns {
        public static final String _COUNT = "count";
        //this is primary key - autoincrement
        public static final String _ID = "review_id";
        public static final String COLUMN_REVIEW = "review";

        //uri

    }

    public static final class TrailerID implements BaseColumns {
        public static final String _COUNT = "count";
        //this is primary key - autoincrement
        public static final String _ID = "trailer_id";
        public static final String COLUMN_TRAILER = "trailer";

        //uri

    }

    public static final class MovieStatus implements BaseColumns {
        public static final String _COUNT = "count";
        public static final String COLUMN_MOVIE = "movie_id";
        public static final String COLUMN_STATUS = "status_id";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";

        //uri


    }

    public static final class MovieReviews implements BaseColumns{
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";
        public static final String _COUNT = "count";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_REVIEW_ID = "review_id";

        //uri

    }

    public static final class MovieTrailers implements BaseColumns{
        public static final String _COUNT = "count";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TRAILER_ID = "trailer_id";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";

        //uri

    }


}
