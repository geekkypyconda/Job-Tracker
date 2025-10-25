import java.util.Arrays;

public class Job {
    private long jobId;
    private String link;
    private String status;
    private String companyName;

    public Job(long jobId, String link, String companyName) {
        this.jobId = jobId;
        this.link = link;
        status = "Applied";
        this.companyName = companyName.trim();
    }

    public Job(long jobId, String link, String status, String companyName) {
        this.jobId = jobId;
        this.link = link;
        this.status = status;
        this.companyName = companyName.trim();
    }

    String JobEncoder(){
        return this.jobId + "$" + this.link + "$" + this.status + "$" + this.companyName;
    }

    static Job JobDecoder(String jobString){
        String s[] = jobString.split("[$]");
        int id = Integer.parseInt(s[0]);

        return new Job(id, s[1], s[2],s[3]);
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getLink() {
        return link;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n" + companyName +
                "\n  --jobId= " + jobId +
                "\n  --link= " + link +
                "\n  --status= " + status;
    }
}
