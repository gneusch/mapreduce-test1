package utils;

import org.json.*;

public class JobPostingHandler {

    public static String getJobTitleFromPosting(String jobPosting ) {

        JSONObject jobPostingJSON = new JSONObject(jobPosting);
        return jobPostingJSON.get("title").toString();

    }

}
