package employee;

import Checkers.Checker;
import csv.CSVWriter;

/**
 *
 * The employee.Employee class represents an employee with various attributes like name, job category,
 * job role, pay scale, and salary details. It also provides functionality for promoting an employee
 * to a higher job role within their category.
 * @author Iwan Teague / Simon Alexander
 */
public class Employee {

    private String PPSno;
    private String name;
    private JobCategory jobCategory;
    private JobType jobRole;
    private int payScale;
    private String userType;
    private String promotion;
    private String salary;
    private String usc;
    private String prsi;
    private String paye;
    private int afterTaxSalary;

    /**
     *
     * Promotes the given employee to a higher job role within their category.
     * If the current job role is the highest in the category, an exception is thrown.
     *
     * @param newRole The employee to be promoted.
     * @throws IllegalArgumentException if the job role is already at the highest in the category.
     */
    public void promotion(String newRole) {
        if (payScale == Checker.findHighestScalePoint("csv/csv files/ULPayScales.csv",jobRole.name())){
            CSVWriter.updateCSVCell("csv/csv files/Employees.csv", Checker.findRowByPPS("csv/csv files/Employees.csv", PPSno, 1) , 6, newRole);
            System.out.println("This employee has been offered a promotion");
        } else{
            System.out.println("This employee is not at the top of their pay scale, they can not be promoted");
        }
    }

    /**
     * Accepts promotion for an employee and updates details in "Employees.csv"
     * It finds the row by PPS number and updates promotion status.
     *
     */
    public void acceptPromotion(){
        int row = Checker.findRowByPPS("csv/csv files/Employees.csv", PPSno, 1);
        CSVWriter.updateCSVCell("csv/csv files/Employees.csv", row , 3, promotion);
        CSVWriter.updateCSVCell("csv/csv files/Employees.csv", row , 4, "1");
        CSVWriter.updateCSVCell("csv/csv files/Employees.csv", row , 6, "null");
    }

    /**
     * rejects promotion for an employee and updates details in "Employees.csv"
     * It finds the row by PPS number and sets the promotion status to "null".
     *
     */
    public void rejectPromotion(){
        int row = Checker.findRowByPPS("csv/csv files/Employees.csv", PPSno, 1);
        CSVWriter.updateCSVCell("csv/csv files/Employees.csv", row , 6, "null");
    }

    /**
     * 
     * Enum to store job categories and their associated job types.
     * Each category is linked to a set of job types specific to that category.
     */
    public enum JobCategory {
        presidential {
            public JobType[] getJobTypes() {
                return JobType.valuesPresidential();
            }
        },

        academic {
            public JobType[] getJobTypes() {
                return JobType.valuesAcademic();
            }
        },

        administrative {
            public JobType[] getJobTypes() {
                return JobType.valuesAdministrative();
            }
        },

        education {
            public JobType[] getJobTypes() {
                return JobType.valuesEducation();
            }
        },


        library {
            public JobType[] getJobTypes() {
                return JobType.valuesLibrary();
            }
        },

        it {
            public JobType[] getJobTypes() {
                return JobType.valuesIT();
            }
        },

        technical {
            public JobType[] getJobTypes() {
                return JobType.valuesTechnical();
            }
        },

        serviceStaff {
            public JobType[] getJobTypes() {
                return JobType.valuesServiceStaff();
            }
        },

        teachers {
            public JobType[] getJobTypes() {
                return JobType.valuesTeachers();
            }
        },

        clinical {
            public JobType[] getJobTypes() {
                return JobType.valuesClinical();
            }
        },

        ulac {
            public JobType[] getJobTypes() {
                return JobType.valuesULAC();
            }
        },

        research {
            public JobType[] getJobTypes() {
                return JobType.valuesResearch();
            }
        };

        /**
         * method that makes sure each JobCategory enum calls a method
         * to get the job types associated with the category.
         *
         * @return JobType[] the array of values for the category.
         */
        public abstract JobType[] getJobTypes();
    }

    /**
     * 
     * Enum representing all job roles (JobTypes) across different categories.
     */
    public enum JobType {
        // Presidential category
        president,
        vicePresident,

        // Academic category
        fullProfessor,
        professorFormerlyAssociateProfessor,
        associateProfessorAFormerlySeniorLecturer,
        associateProfessorBFormerlyLecturer,
        assistantProfessorFormerlyLecturerBelowTheBar,
        teachingAssistant,

        // Administrative category
        seniorAdministrativeOfficer3,
        seniorAdministrativeOfficer2,
        seniorAdministrativeOfficer1,
        seniorAdministrativeAdministrator,
        executiveAdministrator,
        seniorAdministrator,
        administrator,

        // Education category
        epsPortfolioManager,
        epsCategoryManager,
        epsCategorySpecialistHigher,
        epsCategorySpecialist,

        // Library category
        subLibrarian,
        assistantLibrarian2,
        assistantLibrarian1,
        seniorLibraryAssistant,
        libraryAssistant,
        libraryAttendant,

        // IT category
        analystProgrammer3,
        analystProgrammer2,
        analystProgrammer1,
        seniorComputerOperator,
        computerOperator,
        printOperator2,
        printOperator1,
        computerLabAttendant,
        temporaryComputerAssistant,

        // Technical category
        chiefTechnicalOfficer,
        technicalOfficer,
        seniorTechnicalOfficer,
        seniorLabAttendant,
        laboratoryAttendant,

        // Service Staff category
        senPorterAttendant,
        porterAttendant,
        groundsSupervisor,
        groundsWorkPerson,
        seniorAide,
        groundsWorkPersonMachineAttendant,
        serviceStaff,
        serviceStaffShift,
        plantMaintenanceAide,
        groundsForePerson,

        // Teachers category
        teachingFellow,
        universityTeacher,
        associateTeacher,

        // Clinical category
        therapiesRegionalSupervisorsRegionalPlacementFacilitator,
        clinicalTutor,
        clinicalFellow,

        // ULAC category
        assistantSeniorInstructor,
        leadInstructor,
        multiActivityInstructorGrade1,
        multiActivityInstructorGrade2,
        assistantInstructor,
        coopStudents,

        // Research category
        researchAssistant,
        postDoctoralResearcher,
        researchFellow,
        seniorResearchFellow;

        // methods to link correct jobRoles to correct jobCatagories
        public static JobType[] valuesPresidential() {
            return new JobType[]{ president, vicePresident };
        }

        public static JobType[] valuesAcademic() {
            return new JobType[]{
                    fullProfessor,
                    professorFormerlyAssociateProfessor,
                    associateProfessorAFormerlySeniorLecturer,
                    associateProfessorBFormerlyLecturer,
                    assistantProfessorFormerlyLecturerBelowTheBar,
                    teachingAssistant
            };
        }

        public static JobType[] valuesAdministrative() {
            return new JobType[]{
                    seniorAdministrativeOfficer3,
                    seniorAdministrativeOfficer2,
                    seniorAdministrativeOfficer1,
                    seniorAdministrativeAdministrator,
                    executiveAdministrator,
                    seniorAdministrator,
                    administrator
            };
        }

        public static JobType[] valuesEducation() {
            return new JobType[]{
                    epsPortfolioManager,
                    epsCategoryManager,
                    epsCategorySpecialistHigher,
                    epsCategorySpecialist
            };
        }

        public static JobType[] valuesLibrary() {
            return new JobType[]{
                    subLibrarian,
                    assistantLibrarian2,
                    assistantLibrarian1,
                    seniorLibraryAssistant,
                    libraryAssistant,
                    libraryAttendant
            };
        }

        public static JobType[] valuesIT() {
            return new JobType[]{
                    analystProgrammer3,
                    analystProgrammer2,
                    analystProgrammer1,
                    seniorComputerOperator,
                    computerOperator,
                    printOperator2,
                    printOperator1,
                    computerLabAttendant,
                    temporaryComputerAssistant
            };
        }

        public static JobType[] valuesTechnical() {
            return new JobType[]{
                    chiefTechnicalOfficer,
                    technicalOfficer,
                    seniorTechnicalOfficer,
                    seniorLabAttendant,
                    laboratoryAttendant
            };
        }

        public static JobType[] valuesServiceStaff() {
            return new JobType[]{
                    senPorterAttendant,
                    porterAttendant,
                    groundsSupervisor,
                    groundsWorkPerson,
                    seniorAide,
                    groundsWorkPersonMachineAttendant,
                    serviceStaff,
                    serviceStaffShift,
                    plantMaintenanceAide,
                    groundsForePerson
            };
        }

        public static JobType[] valuesTeachers() {
            return new JobType[]{
                    teachingFellow,
                    universityTeacher,
                    associateTeacher
            };
        }

        public static JobType[] valuesClinical() {
            return new JobType[]{
                    therapiesRegionalSupervisorsRegionalPlacementFacilitator,
                    clinicalTutor,
                    clinicalFellow
            };
        }

        public static JobType[] valuesULAC() {
            return new JobType[]{
                    assistantSeniorInstructor,
                    leadInstructor,
                    multiActivityInstructorGrade1,
                    multiActivityInstructorGrade2,
                    assistantInstructor,
                    coopStudents
            };
        }

        public static JobType[] valuesResearch() {
            return new JobType[]{
                    researchAssistant,
                    postDoctoralResearcher,
                    researchFellow,
                    seniorResearchFellow
            };
        }
    }

    /**
     * 
     * Constructor for creating an employee.Employee object.
     *
     * @param name The name of the employee.
     * @param PPSno The PPS number of the employee.
     * @param jobCategory The category of the job the employee holds.
     * @param jobRole The specific role the employee holds in the job category.
     * @param payScale The pay scale assigned to the employee.
     * @throws IllegalArgumentException if the jobRole is invalid for the given jobCategory.
     */
    public Employee( String name, String PPSno, JobCategory jobCategory, JobType jobRole, int payScale) {
        if (!isRoleValidForCategory(jobCategory, jobRole)) {
            throw new IllegalArgumentException("Invalid JobRole for the given JobCategory");
        }
        this.PPSno = PPSno;
        this.name = name;
        this.jobCategory = jobCategory;
        this.jobRole = jobRole;
        this.payScale = payScale;
    }

    /**
     * 
     * Constructor for creating an employee.Employee object.
     *
     * @param name The name of the employee.
     * @param PPSno The PPS number of the employee.
     * @param jobCategory The category of the job the employee holds.
     * @param jobRole The specific role the employee holds in the job category.
     * @param payScale The pay scale assigned to the employee.
     * @param userType The user type of the user. Used in CLI.CLI
     * @throws IllegalArgumentException if the jobRole is invalid for the given jobCategory.
     */
    public Employee( String name, String PPSno, JobCategory jobCategory, JobType jobRole, int payScale, String userType) {
        if (!isRoleValidForCategory(jobCategory, jobRole)) {
            throw new IllegalArgumentException("Invalid JobRole for the given JobCategory");
        }
        this.PPSno = PPSno;
        this.name = name;
        this.jobCategory = jobCategory;
        this.jobRole = jobRole;
        this.payScale = payScale;
        this.userType = userType;
    }

    public Employee( String name, String PPSno, JobCategory jobCategory, JobType jobRole, int payScale, String userType, String promotion) {
        if (!isRoleValidForCategory(jobCategory, jobRole)) {
            throw new IllegalArgumentException("Invalid JobRole for the given JobCategory");
        }
        this.PPSno = PPSno;
        this.name = name;
        this.jobCategory = jobCategory;
        this.jobRole = jobRole;
        this.payScale = payScale;
        this.userType = userType;
        this.promotion = promotion;
    }

    /**
     * 
     * Returns the name of the employee.
     *
     * @return The employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the PPS number of the employee.
     *
     * @return The employee's PPS number.
     */
    public String getPPSno() {
        return PPSno;
    }

    /**
     * 
     * Returns the job category of the employee.
     *
     * @return The employee's job category.
     */
    public JobCategory getJobCategory() {
        return jobCategory;
    }

    /**
     * 
     * Returns the job role of the employee.
     *
     * @return The employee's job role.
     */
    public JobType getJobRole() {
        return jobRole;
    }

    /**
     * 
     * Returns the pay scale of the employee.
     *
     * @return The employee's pay scale.
     */
    public int getPayScale() {
        return payScale;
    }


    public String getPromotion(){
        return promotion;
    }

    /**
     * Returns the salary of the employee.
     *
     * @return The employee's salary.
     */

    public String getSalary() {
        return salary;
    }

    /**
     * 
     * Returns the USC of the employee.
     *
     * @return The employee's USC.
     */
    public String getUSC() {
        return usc;
    }

    /**
     * Returns the PRSI of the employee.
     *
     * @return The employee's PRSI.
     */
    public String getPRSI() {
        return prsi;
    }

    /**
     * Returns the PAYE of the employee.
     *
     * @return The employee's PAYE.
     */
    public String getPAYE() {
        return paye;
    }

    /**
     * Returns the after tax salary of the employee.
     *
     * @return The employee's after tax salary.
     */
    public int getAfterTaxSalary() {
        return afterTaxSalary;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name The new name of the employee.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the PPS number of the employee.
     *
     * @param PPSno The new PPS number of the employee.
     */
    public void setPPSno(String PPSno){
        this.PPSno = PPSno;
    }

    /**
     * Sets the job category of the employee.
     *
     * @param jobCategory The new job category of the employee.
     */
    public void setJobCategory(JobCategory jobCategory){
        this.jobCategory = jobCategory;
    }

    /**
     * Sets the job role of the employee.
     *
     * @param jobRole The new job role of the employee.
     */
    public void setJobRole(JobType jobRole){
        this.jobRole = jobRole;
    }

    /**
     * Sets the pay scale of the employee.
     *
     * @param payScale The new pay scale of the employee.
     */
    public void setPayScale(int payScale){
        this.payScale = payScale;
    }

    /**
     * Sets the salary of the employee.
     *
     * @param salary The new salary of the employee.
     */
    public void setSalary(String salary){
        this.salary = salary;
    }

    /**
     * Sets the USC of the employee.
     *
     * @param USC The USC of the employee based on salary.
     */
    public void setUSC(String USC){
        this.usc = USC;
    }

    /**
     * Sets the PRSI of the employee.
     *
     * @param PRSI The PRSI of the employee based on salary.
     */
    public void setPRSI(String PRSI){
        this.prsi = PRSI;
    }

    /**
     * Sets the PAYE of the employee.
     *
     * @param PAYE The PAYE of the employee based on salary.
     */
    public void setPAYE(String PAYE){
        this.paye = PAYE;
    }

    /**
     * Sets the after tax salary of the employee.
     *
     * @param afterTaxSalary The after tax salary of the employee based on salary.
     */
    public void setAfterTaxSalary(int afterTaxSalary){
        this.afterTaxSalary = afterTaxSalary;
    }

    /**
     * Checks if the job role is valid for the given job category.
     *
     * @param jobCategory The job category.
     * @param jobRole The job role.
     * @return True if the job role is valid for the category, false otherwise.
     */
    public boolean isRoleValidForCategory(JobCategory jobCategory, JobType jobRole) {
        for (JobType validJobRole : jobCategory.getJobTypes()) {
            if (validJobRole == jobRole) {
                return true;
            }
        }
        return false;
    }

    /**
     * adds an employees PPSno, name, job category, job role, pay scale and user type to the Employees.csv
     */
    public void addEmployee(){
        CSVWriter.writeToCSV("csv/csv files/Employees.csv", name, PPSno, jobCategory.toString(), jobRole.toString(), Integer.toString(payScale), userType, promotion);
    }

    /**
     * Returns a string representation of the employee.Employee object.
     *
     * @return A string describing the employee's attributes.
     */
    @Override
    public String toString() {
        return "\tname= '" + name +",\tPPSno= " + PPSno  + ",\tjobCategory= " + jobCategory + ",\t\tjobRole= " + jobRole;
    }

}
