import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

public class FastIO {

    static String COMPANY_FOLDER = "Companies/";
    static String OPEN_JOBS_FILE = "Open_Jobs/open_jobs.txt";
    static String JOB_DETAILS_FOLDER = "Job_Details/";
    static String CLOSED_JOBS_FILE = "Closed_Jobs" + "/closed_jobs.txt";
    static String ALL_COMPANY_FILE = "All_Companies" + "/companies.txt";

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    void ensureExists(String path) throws IOException {
        if(path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Wrong Path! Please check once!");
        }

        Path filePath = Path.of(path).toAbsolutePath().normalize();

        String pathStr = filePath.toString();
        if(pathStr.matches(".*[<>\"|?*].*"))
            throw new IllegalArgumentException("Path contains invalid characters: < > \" | ? *");

        if(!Files.exists(filePath)){
            if(path.endsWith(File.separator) || new File(path).isDirectory()){
                Files.createDirectories(filePath);
            }else{
                if(filePath.getParent() != null)
                    Files.createDirectories(filePath.getParent());

                Files.createFile(filePath);
            }
        }
    }

    HashSet<String> readJobsFromFile(String path) throws IOException {
        ensureExists(path);

        HashSet<String> set = new HashSet<>();
        String contents = this.readFile(path).trim();

        if(contents.isEmpty()) return set;

        contents = contents.substring(1, contents.length() - 1);
        if(contents.isEmpty()) return set;

        if(contents.contains(",")){
            String s[] = contents.split(",");
            for(String str : s) set.add(str.trim());
        }else{
            set.add(contents.trim());
        }

        return set;
    }

    void addJobInList(String filePath, String fileName) throws IOException {
        HashSet<String> set = readJobsFromFile(filePath);
        set.add(fileName);

        writeToFile(filePath, set.toString());
    }

    void removeJobFromList(String filePath, String fileName) throws IOException {
        HashSet<String> set = readJobsFromFile(filePath);

        if(!set.contains(fileName)){
            new Exception("FileName: " + fileName + ", does not exists!");
            return;
        }else{
            set.remove(fileName);
        }

        writeToFile(filePath, set.toString());
    }

    void closeJob(Job job) throws IOException {
        String content = this.readFile(OPEN_JOBS_FILE);
        String fileName = job.getCompanyName() + "_" + job.getJobId();

        this.addJobInList(CLOSED_JOBS_FILE, fileName);
        this.removeJobFromList(OPEN_JOBS_FILE, fileName);
    }

    void updateJobStatus(Job job) throws IOException {
        String fileName = job.getCompanyName() + "_" + job.getJobId();

        // Write to Job Details Folder
        writeToFile(JOB_DETAILS_FOLDER + fileName + ".txt", job.JobEncoder());

        // Write to Companies
        writeToFile(COMPANY_FOLDER + job.getCompanyName() + "/" + fileName + ".txt", job.JobEncoder());
    }

    void createNewJob(Job job) throws IOException {
        String fileName = job.getCompanyName() + "_" + job.getJobId();

        // append to Open Jobs
        this.addJobInList(OPEN_JOBS_FILE, fileName);

        // append to Job Details Folder
        writeToFile(JOB_DETAILS_FOLDER + fileName + ".txt", job.JobEncoder());

        // append to Companies
        writeToFile(COMPANY_FOLDER + job.getCompanyName() + "/" + fileName + ".txt", job.JobEncoder());

        // add the name to the companies file
        this.addJobInList(ALL_COMPANY_FILE, job.getCompanyName());
    }

    void appendToFile(String fileName, String message) throws IOException {
        ensureExists(fileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(message);
            writer.newLine();
            writer.flush();
        }
    }

    void writeToFile(String fileName, String message) throws IOException {
        ensureExists(fileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))){
            writer.write(message);
            writer.newLine();
            writer.flush();
        }
    }

    String readFile(String fileName) throws IOException {
        String contents = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while((line = reader.readLine()) != null){
                contents = contents + line + "\n";
            }
        }

        return contents;
    }

    String readLine() throws IOException {
        return br.readLine().trim();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long nextLong() throws IOException {
        return Long.parseLong(br.readLine().trim());
    }
}
