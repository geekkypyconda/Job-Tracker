import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    static String COMPANY_FOLDER = "Companies/";
    static String JOB_DETAILS_FOLDER = "Job_Details/";
    static String OPEN_JOBS_FILE = "Open_Jobs" + "/open_jobs.txt";
    static String ALL_COMPANY_FILE = "All_Companies" + "/companies.txt";
    static String CLOSED_JOBS_FILE = "Closed_Jobs" + "/closed_jobs.txt";

    static FastIO fio;


    public static void main(String[] args) throws IOException {
        println("------- Job Tracker V 1.0 ---------");
        fio = new FastIO();

        while (true){
            println("\n----------------------------------\n");
            println("Enter 1 to Update the status of an existing job application");
            println("Enter 2 to create a new Job");
            println("Enter 3 to get the details of a job Id");
            println("Enter 4 to get all the Open Jobs");
            println("Enter 5 to get all the Closed Jobs");
            println("Enter 6 to get all the Jobs of a Company");
            println("Enter 7 to get all the Open Jobs of a Company");
            println("Enter 8 to get all the Closed Jobs of a Company");
            println("Enter 9 to see all the companies");
            println("Enter 10 to see At a Glance");

            int choice = fio.nextInt();

            if(choice == -1){
                println("Stopping Tracker");
                break;
            }

            if(choice == 1){
                updateJobStatus();
            }else if(choice == 2){
                createNewJob();
            }else if(choice == 3){
               getDetailsOfJob();
            }else if(choice == 4){
                getAllOpenJobs();
            }else if(choice == 5){
                getAllClosedJobs();
            }else if(choice == 6){
                getAllJobsOfCompany();
            }else if(choice == 7){
                getAllOpenJobsOfCompany();
            }else if(choice == 8){
                getAllClosedJobsOfCompany();
            }else if(choice == 9){
                showAllCompanies();
            }else if(choice == 10){
                atAGlance();
            }
        }

    }

    static void atAGlance() throws IOException {
        HashSet<String> allCompanies = fio.readJobsFromFile(ALL_COMPANY_FILE);

        int i = 1;
        for(String s : allCompanies){
            int openCount = getAllOpenJobsCountOfACompany(s);
            int closeCount = getAllClosedJobsCountOfACompany(s);

            println(i++ + ". " + s + ",    Open: " + openCount + ",    Closed: " + closeCount);
        }

        println();
    }

    static void showAllCompanies() throws IOException {
        HashSet<String> set = fio.readJobsFromFile(ALL_COMPANY_FILE);

        println("\nAll Companies:- ");
        int total = set.size();
        println("Total Count: " + total + "\n");

        int i = 1;
        for(String j : set)
            println(i++ + ". " + j);

        println();
    }

    static void getAllJobsOfCompany() throws IOException {
        println("Enter Company Name:");
        String companyName = fio.readLine();

        getAllOpenJobsOfCompanyUtil(companyName);
        getAllClosedJobsOfCompanyUtil(companyName);
    }

    static void getAllOpenJobsOfCompany() throws IOException{
        println("Enter Company Name:");
        String companyName = fio.readLine();

        getAllOpenJobsOfCompanyUtil(companyName);
    }

    static void getAllClosedJobsOfCompany() throws IOException{
        println("Enter Company Name:");
        String companyName = fio.readLine();

        getAllClosedJobsOfCompanyUtil(companyName);
    }

    static int getAllClosedJobsCountOfACompany(String companyName) throws IOException {
        HashSet<String> set = fio.readJobsFromFile(CLOSED_JOBS_FILE);

        ArrayList<String> l = new ArrayList<>();
        for(String j : set){
            String name = j.split("_")[0];
            if(name.equalsIgnoreCase(companyName))
                l.add(j);
        }

        return l.size();
    }

    static int getAllOpenJobsCountOfACompany(String companyName) throws IOException {
        HashSet<String> set = fio.readJobsFromFile(OPEN_JOBS_FILE);

        ArrayList<String> l = new ArrayList<>();
        for(String j : set){
            String name = j.split("_")[0];
            if(name.equalsIgnoreCase(companyName))
                l.add(j);
        }

        return l.size();
    }

    static void getAllOpenJobsOfCompanyUtil(String companyName) throws IOException {
        HashSet<String> set = fio.readJobsFromFile(OPEN_JOBS_FILE);

        println("\nX---------------------" + companyName + "---------------------X\n");
        println("\nOpen Jobs:- ");

        ArrayList<String> l = new ArrayList<>();
        for(String j : set){
            String name = j.split("_")[0];
            if(name.equalsIgnoreCase(companyName))
                l.add(j);
        }

        int total = l.size();
        println("Total Count: " + total + "\n");

        int i = 1;
        for(String j : l)
            println(i++ + ". " + j + "   -->   " + getJobFromDetails(j).getStatus());

        println();
    }

    static void getAllClosedJobsOfCompanyUtil(String companyName) throws IOException {
        HashSet<String> set = fio.readJobsFromFile(CLOSED_JOBS_FILE);

        println("\nX---------------------" + companyName + "---------------------X\n");
        println("\nClosed Jobs:- ");
        ArrayList<String> l = new ArrayList<>();

        for(String j : set){
            String name = j.split("_")[0];
            if(name.equalsIgnoreCase(companyName))
                l.add(j);
        }

        int total = l.size();
        println("Total Count: " + total + "\n");

        int i = 1;
        for(String j : l)
            println(i++ + ". " + j + "   -->   " + getJobFromDetails(j).getStatus());

        println();
    }

    static void getAllClosedJobs() throws IOException {
        HashSet<String> set = fio.readJobsFromFile(CLOSED_JOBS_FILE);

        println("\nClosed Jobs:- ");
        int total = set.size();
        println("Total Count: " + total + "\n");

        int i = 1;
        for(String j : set)
            println(i++ + ". " + j + "   -->   " + getJobFromDetails(j).getStatus());

        println();
    }

    static void getAllOpenJobs() throws IOException {
        HashSet<String> set = fio.readJobsFromFile(OPEN_JOBS_FILE);

        println("\nOpen Jobs:- ");
        int total = set.size();
        println("Total Count: " + total + "\n");

        int i = 1;
        for(String j : set)
            println(i++ + ". " + j + "   -->   " + getJobFromDetails(j).getStatus());

        println();
    }

    static void getDetailsOfJob() throws IOException {
        println("Enter Company Name:- ");
        String companyName = fio.readLine(); companyName = companyName.toLowerCase();

        println("Enter Job Id:- ");
        long jobId = fio.nextLong();

        String fileName = companyName + "_" + jobId;
        String filePath = JOB_DETAILS_FOLDER + fileName + ".txt";

        String content = fio.readFile(filePath);

        Job job = Job.JobDecoder(content);

        println(job.toString());
    }

    static Job getJobFromDetails(String fileName) throws IOException {
        String filePath = JOB_DETAILS_FOLDER + fileName + ".txt";

        String content = fio.readFile(filePath);

        Job job = Job.JobDecoder(content);

        return job;
    }

    static void createNewJob() throws IOException {
        println("Enter Company Name:- ");
        String companyName = fio.readLine();companyName = companyName.toLowerCase();

        println("Enter Job Id:- ");
        long jobId = fio.nextLong();

        println("Enter the Job Link:-");
        String link = fio.readLine();

        Job job = new Job(jobId,link,companyName);

        fio.createNewJob(job);
    }

    static void updateJobStatus() throws IOException {
        println("Enter Company Name:- ");
        String companyName = fio.readLine();companyName = companyName.toLowerCase();

        println("Enter Job Id:- ");
        long jobId = fio.nextLong();

        String fileName = companyName + "_" + jobId;
        String filePath = JOB_DETAILS_FOLDER + fileName + ".txt";

        String content = fio.readFile(filePath);
        Job job = Job.JobDecoder(content);

        println("Enter the new status for the Job: " + fileName + ", Current: " + job.getStatus());
        String newStatus = fio.readLine();
        job.setStatus(newStatus);

        fio.updateJobStatus(job);

        if(newStatus.equalsIgnoreCase("Reject") || newStatus.equalsIgnoreCase("Rejected")){
            fio.closeJob(job);
            println("Job Closed!");
        }

    }

    static void print(Object o){
        System.out.print(String.valueOf(o));
    }

    static void println(Object o){
        System.out.println(String.valueOf(o));
    }

    static void println(){
        System.out.println();
    }
}